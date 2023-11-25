package com.today.demo.controller.activity;

import com.today.demo.entity.Activity;
import com.today.demo.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ActivityRestController {

    private final ActivityService activityService;

    @DeleteMapping("/admin/activity/db/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        activityService.delete(id);
        return ResponseEntity.ok("Activity DB with ID " + id + " has been deleted.");
    }

    @PostMapping("/admin/activity/db/add")
    public ResponseEntity<Activity> addActivity(
            @RequestParam String name,
            @RequestParam int venue,
            @RequestParam int capacity
    ) {
        Activity activity = activityService.addActivity(name, venue, capacity);
        return ResponseEntity.status(HttpStatus.CREATED).body(activity);
    }
}
