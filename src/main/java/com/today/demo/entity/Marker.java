package com.today.demo.entity;

import com.today.demo.dto.Request.MarkerRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Category category;

    private int venue;

    private String name;

    private String tel;

    private double latitude;

    private double longitude;

    public void update(MarkerRequestDTO markerRequestDTO , Category category) {
        this.name = markerRequestDTO.getName();
        this.tel = markerRequestDTO.getTel();
        this.category = category;
        this.venue = markerRequestDTO.getVenue();
        this.latitude = markerRequestDTO.getLatitude();
        this.longitude = markerRequestDTO.getLongitude();
    }
}


