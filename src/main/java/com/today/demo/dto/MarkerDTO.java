package com.today.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MarkerDTO {
    private int id;
    private int category;
    private int venue;
    private String tel;
    private String name;
    private double latitude;
    private double longitude;

    public MarkerDTO(int id,int category,int venue ,String tel,String name, double latitude, double longitude) {
        this.id = id;
        this.category = category;
        this.venue = venue;
        this.tel = tel;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
