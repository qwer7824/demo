package com.today.demo.entity;

import com.today.demo.dto.Request.MarkerRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Marker extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marker_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int venue;

    private String name;

    private String tel;

    private double latitude;

    private double longitude;

    @OneToMany(mappedBy = "marker")
    private List<Board> boards = new ArrayList<>();

    public void update(MarkerRequestDTO markerRequestDTO , Category category) {
        this.name = markerRequestDTO.getName();
        this.tel = markerRequestDTO.getTel();
        this.category = category;
        this.venue = markerRequestDTO.getVenue();
        this.latitude = markerRequestDTO.getLatitude();
        this.longitude = markerRequestDTO.getLongitude();
    }
}


