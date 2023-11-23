package com.today.demo.controller.map;

import com.today.demo.dto.MarkerDTO;
import com.today.demo.dto.MarkerRequestDTO;
import com.today.demo.entity.Marker;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RestMapController {

    private final MarkerRepository markerRepository;
    private final MarkerService markerService;

    @GetMapping("/map/marker/{venue}")
    public List<MarkerDTO> getMarkers(@PathVariable("venue") int venue) {
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

    @PostMapping("/admin/db/markerAdd")
    public ResponseEntity<String> addMarker(@RequestBody MarkerRequestDTO dto) {
        markerService.markerAdd(dto);
        return new ResponseEntity<>("Activity added successfully", HttpStatus.OK);
    }
}
