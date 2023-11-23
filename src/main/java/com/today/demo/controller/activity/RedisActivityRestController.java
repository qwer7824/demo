package com.today.demo.controller.activity;


import com.today.demo.redisService.ActivityRedisTemplateService;
import com.today.demo.entity.Activity;
import com.today.demo.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
public class RedisActivityRestController {

    private final ActivityRepository activityRepository;
    private final ActivityRedisTemplateService activityRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save() {
        List<Activity> activityList = activityRepository.findAll();

        List<Activity> activityDtoList = activityList.stream()
                .map(activity -> Activity.builder()
                        .id(activity.getId())
                        .name(activity.getName())
                        .venue(activity.getVenue())
                        .capacity(activity.getCapacity())
                        .build())
                .toList();

        activityDtoList.forEach(activityRedisTemplateService::save);

        return "success";
    }

    @DeleteMapping("/redis/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        activityRedisTemplateService.delete(id);
        return ResponseEntity.ok("Activity Redis with ID " + id + " has been deleted.");
    }
    @DeleteMapping("/redis/deleteAll")
    public void deleteAllActivity() {
        activityRedisTemplateService.deleteAll();
    }
}
