package com.today.demo.repository;

import com.today.demo.entity.Board;
import com.today.demo.entity.Heart;
import com.today.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndBoard(Member member, Board board);

    Page<Heart> findByMember(Member member, PageRequest pageRequest);
}
