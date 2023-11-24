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

    private final MarkerService markerService;

    @GetMapping("/map/marker/{venue}")
    public List<MarkerDTO> getMarkers(@PathVariable("venue") int venue) {
        return markerService.getMarkers(venue);
    }

    @PostMapping("/admin/db/markerAdd")
    public ResponseEntity<String> addMarker(@RequestBody MarkerRequestDTO dto) {
        markerService.markerAdd(dto);
        return new ResponseEntity<>("Marker added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/admin/marker/db/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable Long id) {
        markerService.markerDelete(id);
        return ResponseEntity.ok("Marker DB with ID " + id + " has been deleted.");
    }
}
