package com.today.demo.controller.activity;

import com.today.demo.dto.ActivityDTO;
import com.today.demo.dto.CategoryDTO;
import com.today.demo.entity.Activity;
import com.today.demo.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<String> addActivity(@Valid @RequestBody ActivityDTO activityDTO , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        activityService.addActivity(activityDTO);
        return new ResponseEntity<>("Activity added successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/activity/db/{id}")
    public ResponseEntity<String> updateActivity(@Valid @RequestBody ActivityDTO activityDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        activityService.updateActivity(activityDTO);
        return ResponseEntity.ok("Category DB with ID " + activityDTO.getId() + " has been updated.");
    }
    @GetMapping("/admin/activity/db/{id}")
    public Activity getActivity(@PathVariable("id") int id) {
        return activityService.getActivity(id);
    }
}
