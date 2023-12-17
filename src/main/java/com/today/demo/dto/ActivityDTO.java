package com.today.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ActivityDTO {
    private int id;

    @NotBlank
    // 활동 이름
    private String name;

    // 활동 장소
    private int venue;

    // 인원
    private int capacity;
}
