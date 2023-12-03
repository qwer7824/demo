package com.today.demo.dto.Request;

import com.today.demo.entity.Category;
import lombok.Getter;
import lombok.Setter;
@Getter
public class MarkerRequestDTO {
    private int id;
    private int category;
    private int venue;
    private String tel;
    private String name;
    private double latitude;
    private double longitude;
}
