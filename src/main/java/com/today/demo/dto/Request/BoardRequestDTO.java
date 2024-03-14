package com.today.demo.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDTO {
    @NotBlank
    private String content;
    @NotNull
    private int category;
    @NotNull
    private int marker;
    private float star;
    private String address;
}
