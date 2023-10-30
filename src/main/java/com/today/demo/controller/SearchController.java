package com.today.demo.controller;


import com.today.demo.service.ActivityService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ActivityService activityService;

    @PostMapping("/search")
    public String searchActivities(@RequestParam("venue") int venue, @RequestParam("capacity") int capacity) {
        return activityService.searchRandomActivityByVenueAndCapacity(venue, capacity);
    }

}
