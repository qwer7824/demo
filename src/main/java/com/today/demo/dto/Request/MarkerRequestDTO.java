package com.today.demo.dto.Request;

import com.today.demo.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
public class MarkerRequestDTO {
    private int id;
    private int category;
    private int venue;
    private String tel;
    @NotBlank
    private String name;
    @Min(1)
    private double latitude;
    @Min(1)
    private double longitude;
}
