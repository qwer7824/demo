package com.today.demo.dto;

import com.today.demo.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkerDTO {
    private int id;
    private String category;
    private int venue;
    private String tel;
    private String name;
    private double latitude;
    private double longitude;

    public MarkerDTO(int id,String category, int venue , String tel, String name, double latitude, double longitude) {
        this.id = id;
        this.category = category;
        this.venue = venue;
        this.tel = tel;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
