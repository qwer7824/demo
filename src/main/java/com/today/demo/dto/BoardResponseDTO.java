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

@Getter
@Setter
public class BoardResponseDTO {
    private int id;
    private String content;
    private String memberUserid;
    private String categoryName;
    private String address;
    private String imgUrl;
    private int likeCount;
    private String createdAt;
}
