package com.today.demo.controller.map;

import com.today.demo.dto.MarkerDTO;
import com.today.demo.dto.Request.MarkerRequestDTO;
import com.today.demo.entity.Category;
import com.today.demo.entity.Marker;
import com.today.demo.repository.CategoryRepository;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RestMapController {

    private final MarkerService markerService;
    private final MarkerRepository markerRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/map/marker/{venue}")
    public List<MarkerDTO> getMarkers(@PathVariable("venue") int venue) {
        return markerService.getMarkers(venue);
    }

    @GetMapping("/map/marker/{venue}/{category}")
    public List<MarkerDTO> getCategoryMarkers(@PathVariable("venue") int venue, @PathVariable("category") int categoryId) {
        return markerService.getCategoryMarkers(venue,categoryId);
    }

    @PostMapping("/admin/db/markerAdd")
    public ResponseEntity<String> addMarker(@RequestBody MarkerRequestDTO dto) {
        markerService.markerAdd(dto);
        return new ResponseEntity<>("Marker added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/admin/marker/db/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        markerService.markerDelete(id);
        return ResponseEntity.ok("Marker DB with ID " + id + " has been deleted.");
    }

    @GetMapping("/admin/marker/db/{id}")
    public Marker getMarker(@PathVariable("id") int id) {
        return markerService.getMarker(id);
    }

    @PutMapping("/admin/marker/db/{id}")
    public void updateMarker(@RequestBody MarkerRequestDTO markerRequestDTO) {
        markerService.updateMarker(markerRequestDTO);
    }

}
