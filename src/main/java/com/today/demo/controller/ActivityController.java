package com.today.demo.controller;

import com.today.demo.entity.Activity;
import com.today.demo.redisService.ActivityRedisTemplateService;
import com.today.demo.repository.ActivityRepository;
import com.today.demo.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@PreAuthorize("hasAnyRole('ADMIN')")
public class ActivityController {


    private final ActivityRedisTemplateService activityRedisTemplateService;
    private final ActivityService activityService;

    @GetMapping("/redis/home")
    public String getAllActivities(Model model) {
        List<Activity> RedisActivities = activityRedisTemplateService.findAll();
        List<Activity> DBActivities = activityService.allActivity();

        RedisActivities.sort(Comparator.comparing(Activity::getId));
        DBActivities.sort(Comparator.comparing(Activity::getId));

        model.addAttribute("RedisActivities", RedisActivities);
        model.addAttribute("DBActivities", DBActivities);
        return "activities";
    }
}
