package com.today.demo.service;

import com.today.demo.dto.MarkerRequestDTO;
import com.today.demo.entity.Marker;
import com.today.demo.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final MarkerRepository markerRepository;

    public void markerAdd(MarkerRequestDTO dto){
        Marker marker = Marker.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .tel(dto.getTel())
                .venue(dto.getVenue())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
        markerRepository.save(marker);
    }
}
