package com.today.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategoryDTO {
    private int id;
    @NotBlank
    private String name;
}
