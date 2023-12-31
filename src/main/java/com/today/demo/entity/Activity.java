package com.today.demo.entity;

import com.today.demo.dto.ActivityDTO;
import com.today.demo.dto.Request.MarkerRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 활동 이름
    private String name;

    // 활동 장소
    private int venue;

    // 인원
    private int capacity;

    public void update(ActivityDTO activityDTO) {
        this.name = activityDTO.getName();
        this.venue = activityDTO.getVenue();
        this.capacity = activityDTO.getCapacity();
    }
}
