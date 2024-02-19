package com.today.demo.controller;

import com.today.demo.entity.Activity;
import com.today.demo.redisService.ActivityRedisTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ActivityRedisTemplateService activityRedisTemplateService;

    @GetMapping("/")
        public String home(Model model) {
        List<Activity> ActivityList = activityRedisTemplateService.findAll();
        int activities = ActivityList.size();

        model.addAttribute("activities",activities);
        return "main";
    }
}
