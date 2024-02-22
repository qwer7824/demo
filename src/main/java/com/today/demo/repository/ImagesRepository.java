package com.today.demo.repository;

import com.today.demo.dto.ImgDTO;
import com.today.demo.entity.Activity;
import com.today.demo.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {


    List<Images> findByBoardId(int boardId);
}
