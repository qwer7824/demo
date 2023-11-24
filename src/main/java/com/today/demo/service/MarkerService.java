package com.today.demo.service;

import com.today.demo.dto.MarkerDTO;
import com.today.demo.dto.MarkerRequestDTO;
import com.today.demo.entity.Marker;
import com.today.demo.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void markerDelete(Long id) {
        markerRepository.deleteById(id);
    }

    public List<MarkerDTO> getMarkers(int venue) {
        List<Marker> markers;

        if (venue == 0) {
            markers = markerRepository.findAll();
        } else {
            markers = markerRepository.findByVenue(venue);
        }

        return markers.stream()
                .map(marker -> new MarkerDTO(marker.getId(), marker.getCategory(), marker.getVenue(),marker.getTel(), marker.getName(), marker.getLatitude(), marker.getLongitude()))
                .collect(Collectors.toList());
    }

    public List<Marker> getAdminMarkers() {
        return markerRepository.findAll();
    }
}
