package com.today.demo.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategoryRequestDTO {
    @NotBlank
    private String name;
}
