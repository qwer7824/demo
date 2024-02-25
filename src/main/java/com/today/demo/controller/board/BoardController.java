package com.today.demo.controller.board;

import com.today.demo.dto.BoardResponseDTO;
import com.today.demo.dto.Request.LocationDTO;
import com.today.demo.dto.Request.MarkerRequestDTO;
import com.today.demo.dto.Request.BoardRequestDTO;
import com.today.demo.entity.*;
import com.today.demo.service.CategoryService;
import com.today.demo.service.HeartService;
import com.today.demo.service.MarkerService;
import com.today.demo.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final MarkerService markerService;
    private final HeartService heartService;

    @GetMapping("/board")
    public String get(Model model){
        List<Board> board = boardService.getAllTop9();
        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories",categories);
        model.addAttribute("board",board);
        return "board/board";
    }


    @GetMapping("/board/{venueId}/{categoryId}")
    @ResponseBody
    public List<BoardResponseDTO> getCategoryAndVenue(@PathVariable int venueId, @PathVariable int categoryId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return boardService.getCategoryAndVenue(venueId, categoryId, page, size);
    }

    @GetMapping("/board/{boardId}")
    public String detail(Principal principal,Model model,@PathVariable int boardId){
        BoardResponseDTO board = boardService.getDetail(boardId);
        boolean heart = false; // 기본값으로 false 설정

        if (principal != null) {
            heart = heartService.heartCheck(principal.getName(), boardId);
        }
        model.addAttribute("board",board);
        model.addAttribute("heart",heart);
        return "board/detail";
    }

    @GetMapping("/board/marker/{markerId}")
    @ResponseBody
    public List<BoardResponseDTO> markerBoardList(@PathVariable int markerId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return boardService.markerBoardList(markerId,page,size);
    }


    @GetMapping("/post")
    public String post(Model model, Principal principal){
        if(principal == null){
            return "member/login";
        }

        List<Category> category = categoryService.getCategory();
        model.addAttribute("categories", category);
        return "board/post";
    }

    @PostMapping(value="/post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 게시글 생성
    @ResponseBody
    public ResponseEntity<?> postAdd(@Valid BoardRequestDTO boardRequestDTO, @RequestParam(value="image") List<MultipartFile> images, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            markerService.getMarker(boardRequestDTO.getMarker());
            boardService.postAdd(boardRequestDTO,principal.getName(),images);
            return ResponseEntity.ok().body("게시글이 등록되었습니다."); // String 반환
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/location") // 주소의 값이 있는지 없는지 검사
    @ResponseBody
    public ResponseEntity<?> locationSearch(@RequestBody LocationDTO locationDTO) {
        Marker marker = markerService.getLatitudeAndLongitude(locationDTO.getLatitude(), locationDTO.getLongitude());
        if(marker == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(marker.getId(), HttpStatus.OK);
    }

    @PostMapping("/newMarker") // 새로운 마커 생성
    @ResponseBody
    public ResponseEntity<?> newMarker(@Valid @RequestBody MarkerRequestDTO dto, BindingResult bindingResult,Principal principal) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Marker marker = markerService.getLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude());

        if(!(marker == null)){
            return new ResponseEntity<>("Duplicate", HttpStatus.CONFLICT);
        }

        // 유효성 검사 통과 시 처리
        int markerId = markerService.markerAdd(dto,principal.getName());
        return new ResponseEntity<>(markerId, HttpStatus.OK);
    }

    }
