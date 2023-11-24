package com.today.demo.controller.map;

import com.today.demo.entity.Activity;
import com.today.demo.entity.Marker;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.service.MarkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class mapAdminController {

    private final MarkerRepository markerRepository;
    private final MarkerService markerService;
    @GetMapping("/admin/maps")
    public String getAllMaps(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Marker> markerPage = markerRepository.findAll(pageable);
        List<Marker> DBMaps = markerPage.getContent();

        model.addAttribute("DBMaps", DBMaps);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", markerPage.getTotalPages());

        return "admin/maps";
    }
}
