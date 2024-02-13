package com.today.demo.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.today.demo.entity.Board;
import com.today.demo.entity.Heart;
import com.today.demo.entity.Member;
import com.today.demo.repository.BoardRepository;
import com.today.demo.repository.HeartRepository;
import com.today.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void insert(String userId,int boardId) {

        Member member = memberRepository.findByUserid(userId)
                .orElseThrow(() -> new NotFoundException("Could not found member id : "));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Could not found board id : "));


        // 이미 좋아요되어있으면 에러 반환
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
}
