package com.today.demo.controller.map;

import com.today.demo.entity.Category;
import com.today.demo.entity.Marker;
import com.today.demo.repository.CategoryRepository;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MapController {
    private final CategoryService categoryService;

    @GetMapping("/map/0")
    public String map0View(Model model){
        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);
        return "map/0";
    }
    @GetMapping("/map/1")
    public String map1View(Model model){
        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);
        return "map/1";
    }
    @GetMapping("/map/2")
    public String map2View(Model model){
        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);
        return "map/2";
    }

}
