package com.today.demo.controller.activity;


import com.today.demo.redisService.ActivityRedisTemplateService;
import com.today.demo.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RedisActivityRestController {

    private final ActivityRedisTemplateService activityRedisTemplateService;
    private final ActivityService activityService;

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/admin/activity/redis/save")
    public void save() {
        activityService.save();
    }

    @DeleteMapping("/admin/activity/redis/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        activityRedisTemplateService.delete(id);
        return ResponseEntity.ok("Activity Redis with ID " + id + " has been deleted.");
    }
    @DeleteMapping("/admin/activity/redis/deleteAll")
    public void deleteAllActivity() {
        activityRedisTemplateService.deleteAll();
    }
}
