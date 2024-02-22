package com.today.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.today.demo.entity.Board;
import com.today.demo.entity.Category;
import com.today.demo.entity.Marker;
import com.today.demo.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardResponseDTO {
    private int id;
    private String markerName;
    private String content;
    private String memberUserid;
    private String categoryName;
    private String address;
    private int likeCount;
    private String createdAt;
    private List<ImgDTO> boardImgDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // Entity -> DTO
    public static BoardResponseDTO of(Board board){
        return modelMapper.map(board,BoardResponseDTO.class);
    }

}
