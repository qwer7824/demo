package com.today.demo.controller.map;

import com.today.demo.entity.Activity;
import com.today.demo.entity.Marker;
import com.today.demo.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@PreAuthorize("hasAnyRole('ADMIN')")
public class mapAdminController {

    private final MarkerRepository markerRepository;
    @GetMapping("/admin/maps")
    public String getAllMaps(Model model) {

        List<Marker> DBMaps = markerRepository.findAll();

        model.addAttribute("DBMaps", DBMaps);
        return "admin/maps";
    }
}
