package com.today.demo.service;

import com.today.demo.dto.MarkerDTO;
import com.today.demo.dto.Request.LocationDTO;
import com.today.demo.dto.Request.MarkerRequestDTO;
import com.today.demo.entity.Category;
import com.today.demo.entity.Marker;
import com.today.demo.entity.Member;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final MarkerRepository markerRepository;
    private final CategoryService categoryService;
    private final MemberRepository memberRepository;

    @Transactional
    public int markerAdd(MarkerRequestDTO dto,String userId){
        Category category = categoryService.getCategory(dto.getCategory());
        Member member = memberRepository.findByUserid(userId).orElseThrow(null);
        Marker marker = Marker.builder()
                .name(dto.getName())
                .member(member)
                .category(category)
                .tel(dto.getTel())
                .venue(dto.getVenue())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
        markerRepository.save(marker);
        return marker.getId();
    }

    @Transactional
    public void markerDelete(int id) {
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
                .map(marker -> new MarkerDTO(marker.getId(), marker.getCategory().getName(), marker.getVenue(),marker.getTel(), marker.getName(), marker.getLatitude(), marker.getLongitude()))
                .collect(Collectors.toList());
    }

    public Page<Marker> getAllMarker(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return markerRepository.findAll(pageable);
    }
    @Transactional
    public void updateMarker(MarkerRequestDTO markerRequestDTO) {
        Marker marker = markerRepository.findById(markerRequestDTO.getId()).orElseThrow(()-> new IllegalArgumentException("해당 ID의 마커를 찾을 수 없습니다."));
        Category category = categoryService.getCategory(markerRequestDTO.getCategory());
        marker.update(markerRequestDTO,category);
        markerRepository.save(marker);
    }

    public Marker getMarker(int id) {
       return markerRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 ID의 마커를 찾을 수 없습니다."));
    }

    public List<MarkerDTO> getCategoryMarkers(int venue, int categoryId) {
        List<Marker> markers;
        Category category = categoryService.getCategory(categoryId);
        if (venue == 0) {
            markers = markerRepository.findByCategory(category);
        } else {
            markers = markerRepository.findByVenueAndCategory(venue, category);
        }

        return markers.stream()
                .map(marker -> new MarkerDTO(
                        marker.getId(),
                        marker.getCategory().getName(),
                        marker.getVenue(),
                        marker.getTel(),
                        marker.getName(),
                        marker.getLatitude(),
                        marker.getLongitude()
                ))
                .collect(Collectors.toList());
    }

    public Marker getLatitudeAndLongitude(double Latitude, double Longitude){
        return markerRepository.findByLatitudeAndLongitude(Latitude, Longitude);
    }
}
