package com.today.demo.controller.activity;

import com.today.demo.entity.Activity;
import com.today.demo.entity.Marker;
import com.today.demo.redisService.ActivityRedisTemplateService;
import com.today.demo.repository.ActivityRepository;
import com.today.demo.service.ActivityService;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ActivityController {


    private final ActivityRedisTemplateService activityRedisTemplateService;
    private final ActivityService activityService;

    @GetMapping("/admin/activity")
    public String getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<Activity> RedisActivities = activityRedisTemplateService.findAll();
        Page<Activity> activityPage = activityService.getAllActivity(page,size);
        List<Activity> DBActivities = activityPage.getContent();

        int Count = RedisActivities.size();

        model.addAttribute("RedisCount", Count);
        model.addAttribute("DBActivities", DBActivities);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activityPage.getTotalPages());
        return "admin/activities";
    }
}
