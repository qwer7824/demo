package com.today.demo.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.today.demo.dto.BoardResponseDTO;
import com.today.demo.entity.Board;
import com.today.demo.entity.Heart;
import com.today.demo.entity.Member;
import com.today.demo.repository.BoardRepository;
import com.today.demo.repository.HeartRepository;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final MemberService memberService;

    @Transactional
    public void insert(String userId,int boardId) {

        Member member = memberRepository.findByUserid(userId)
                .orElseThrow(() -> new NotFoundException("Could not found member id : "));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Could not found board id : "));


        if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
            delete(member.getUserid(),boardId);
            return;
        }

        Heart heart = Heart.builder()
                .board(board)
                .member(member)
                .build();

        board.addCount();
        boardRepository.save(board);
        heartRepository.save(heart);
    }

    @Transactional
    public void delete(String userId,int boardId) {

        Member member = memberRepository.findByUserid(userId)
                .orElseThrow(() -> new NotFoundException("Could not found member id : "));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Could not found board id : "));

        Heart heart = heartRepository.findByMemberAndBoard(member, board).orElseThrow(null);

        board.cenCount();
        boardRepository.save(board);
        heartRepository.delete(heart);
    }

    @Transactional
    public boolean heartCheck(String userId, int boardId) {

        Member member = memberRepository.findByUserid(userId)
                .orElseThrow(() -> new NotFoundException("Could not found member id : "));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Could not found board id : "));


        if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
            return true;
        }
        return false;
    }

    public List<BoardResponseDTO> getMemberHeartList(String userId, int page, int size) {
        Member member = memberService.findOne(userId).orElseThrow(null);
        Page<Heart> heartPage = heartRepository.findByMember(member, PageRequest.of(page, size));
        List<Heart> heartList = heartPage.getContent();
        List<Board> boardList = heartList.stream()
                .map(Heart::getBoard)
                .collect(Collectors.toList());

        return boardService.mapBoardListToDTOList(boardList);
    }
}
