package com.today.demo.dto;

import com.today.demo.entity.Images;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ImgDTO {
    private String imgName;
    private String imgUrl;
    private static ModelMapper modelMapper = new ModelMapper();

    // Entity -> DTO
    public static ImgDTO of(Images images) {
        return modelMapper.map(images,ImgDTO.class);
    }
}
