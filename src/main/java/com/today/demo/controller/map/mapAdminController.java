package com.today.demo.controller.map;

import com.today.demo.entity.Category;
import com.today.demo.entity.Marker;
import com.today.demo.service.CategoryService;
import com.today.demo.service.MarkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class mapAdminController {

    private final MarkerService markerService;
    private final CategoryService categoryService;
    @GetMapping("/admin/maps")
    public String getAllMaps(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<Marker> markerPage = markerService.getAllMarker(page,size);
        List<Marker> DBMaps = markerPage.getContent();
        List<Category> DBCategory = categoryService.getCategory();

        model.addAttribute("DBMaps", DBMaps);
        model.addAttribute("DBCategory", DBCategory);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", markerPage.getTotalPages());

        return "admin/maps";
    }
}
