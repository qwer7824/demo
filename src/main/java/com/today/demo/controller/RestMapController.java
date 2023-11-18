package com.today.demo.controller;

import com.today.demo.dto.MarkerDTO;
import com.today.demo.entity.Marker;
import com.today.demo.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestMapController {

    private final MarkerRepository markerRepository;

    @GetMapping("/markers")
    public List<MarkerDTO> getAllMarkers() {
        List<Marker> markers = markerRepository.findAll();
        List<MarkerDTO> markerDTOs = new ArrayList<>();

        for (Marker marker : markers) {
            MarkerDTO markerDTO = new MarkerDTO(marker.getId(),marker.getName(), marker.getLatitude(), marker.getLongitude());
            markerDTOs.add(markerDTO);
        }

        return markerDTOs;
    }
}
