package com.today.demo.service;

import com.today.demo.dto.BoardResponseDTO;
import com.today.demo.dto.ImgDTO;
import com.today.demo.dto.Request.BoardRequestDTO;
import com.today.demo.entity.*;
import com.today.demo.repository.BoardRepository;
import com.today.demo.repository.ImagesRepository;
import com.today.demo.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryService categoryService;
    private final MarkerRepository markerRepository;
    private final ImagesRepository imagesRepository;
    private final MarkerService markerService;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;
    private final ModelMapper modelMapper;

    @Transactional
    public void postAdd(BoardRequestDTO boardRequestDTO, String userId, List<MultipartFile> images) throws IOException {
        Category category = categoryService.getCategory(boardRequestDTO.getCategory());
        Marker marker = markerService.getMarker(boardRequestDTO.getMarker());
        Optional<Member> member = memberService.findOne(userId);

        Member resolvedMember = member.orElseThrow();

            Board post = Board.builder()
                    .member(resolvedMember)
                    .content(boardRequestDTO.getContent())
                    .category(category)
                    .marker(marker)
                    .address(boardRequestDTO.getAddress())
                    .build();

            boardRepository.save(post);

        if (!images.isEmpty()) {
            List<Images> storedImages = new ArrayList<>();
            for (MultipartFile image : images) {
                String storedFileName = s3Uploader.upload(image, "images");
                Images newImage = Images.builder()
                        .imgName(image.getOriginalFilename())
                        .imgUrl(storedFileName)
                        .board(post)
                        .build();
                storedImages.add(newImage);
            }
            imagesRepository.saveAll(storedImages);
        }
    }
    public List<Board> getAllTop9(){
        return boardRepository.findTop9ByOrderByLikeCountDescCreatedAtDesc();
    }

    public BoardResponseDTO getDetail(int boardId){

        List<Images> imageList = imagesRepository.findByBoardId(boardId);

        List<ImgDTO> boardImgDtoList = new ArrayList<>();
        for (Images images : imageList) {
            ImgDTO imgDTO = ImgDTO.of(images);
            boardImgDtoList.add(imgDTO);
        }

        Board board = boardRepository.findById(boardId).orElseThrow(null);
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.of(board);
        boardResponseDTO.setBoardImgDtoList(boardImgDtoList);
        return boardResponseDTO;
    }

    public List<BoardResponseDTO> getCategoryAndVenue(int venueId, int categoryId, int page, int size) {
        if (venueId == 0 && categoryId == 0) {
            Page<Board> boardPage = boardRepository.findAll(PageRequest.of(page, size));
            List<Board> boardList = boardPage.getContent();
            return mapBoardListToDTOList(boardList);
        } else if (venueId == 0) {
            // venueId가 0인 경우 categoryId에 해당하는 게시판을 조회
            List<Board> boardList = boardRepository.findByCategoryId(categoryId, PageRequest.of(page, size)).getContent();
            return mapBoardListToDTOList(boardList);
        } else if (categoryId == 0) {
            // categoryId가 0인 경우 venueId에 해당하는 모든 게시판을 조회
            List<Marker> markers = markerRepository.findByVenue(venueId);
            List<Board> boardList = new ArrayList<>();
            for (Marker marker : markers) {
                List<Board> boards = boardRepository.findByMarkerId(marker.getId(), PageRequest.of(page, size)).getContent();
                boardList.addAll(boards);
            }
            return mapBoardListToDTOList(boardList);
        } else {
            // venueId와 categoryId가 모두 0이 아닌 경우 venueId와 categoryId에 해당하는 게시판을 조회
            List<Marker> markers = markerRepository.findByVenue(venueId);

            List<Board> boardList = new ArrayList<>();
            for (Marker marker : markers) {
                List<Board> boards = boardRepository.findByMarkerIdAndCategoryId(marker.getId(), categoryId, PageRequest.of(page, size)).getContent();
                boardList.addAll(boards);
            }
            return mapBoardListToDTOList(boardList);
        }
    }

    public List<BoardResponseDTO> getMemberBoardList(String userId,int page , int size){

        Member member = memberService.findOne(userId).orElseThrow(null);

        List<Board> boardList = boardRepository.findByMember(member,PageRequest.of(page, size)).getContent();

        return mapBoardListToDTOList(boardList);
    }


    public List<BoardResponseDTO> mapBoardListToDTOList(List<Board> boardList) {
        return boardList.stream()
                .map(board -> modelMapper.map(board, BoardResponseDTO.class))
                .collect(Collectors.toList());
    }
}
