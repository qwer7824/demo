package com.today.demo.controller.map;

import com.today.demo.dto.MarkerDTO;
import com.today.demo.dto.Request.MarkerRequestDTO;
import com.today.demo.entity.Marker;
import com.today.demo.service.MarkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestMapController {

    private final MarkerService markerService;
    @GetMapping("/map/marker/{venue}")
    public List<MarkerDTO> getMarkers(@PathVariable("venue") int venue) {
        return markerService.getMarkers(venue);
    }

    @GetMapping("/map/marker/{venue}/{category}")
    public List<MarkerDTO> getCategoryMarkers(@PathVariable("venue") int venue, @PathVariable("category") int categoryId) {
        return markerService.getCategoryMarkers(venue,categoryId);
    }

    @PostMapping("/admin/db/markerAdd")
    public ResponseEntity<String> addMarker(@Valid @RequestBody MarkerRequestDTO dto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // 유효성 검사 통과 시 처리
        markerService.markerAdd(dto,principal.getName());
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
    public ResponseEntity<String> updateMarker(@Valid @RequestBody MarkerRequestDTO markerRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        markerService.updateMarker(markerRequestDTO);
        return new ResponseEntity<>("Marker edited successfully", HttpStatus.OK);
    }

}
