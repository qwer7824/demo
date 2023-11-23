package com.today.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkerRequestDTO {
    private int category;
    private int venue;
    private String tel;
    private String name;
    private double latitude;
    private double longitude;
}
