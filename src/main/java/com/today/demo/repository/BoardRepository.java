package com.today.demo.repository;

import com.today.demo.dto.BoardResponseDTO;
import com.today.demo.entity.Board;
import com.today.demo.entity.Marker;
import com.today.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findTop9ByOrderByLikeCountDescCreatedAtDesc();

    Page<Board> findByCategoryId(int categoryId, PageRequest pageRequest);

    Page<Board> findByMarkerId(int markerId,PageRequest pageRequest);

    Page<Board> findByMarkerIdAndCategoryId(int markerId, int categoryId,PageRequest pageRequest);

    Page<Board> findByMember(Member member,PageRequest pageRequest);
}
