package com.today.demo.service;

import com.today.demo.redisService.ActivityRedisTemplateService;
import com.today.demo.entity.Activity;
import com.today.demo.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

        private final ActivityRepository activityRepository;
        private final ActivityRedisTemplateService activityRedisTemplateService;

        public String searchRandomActivityByVenueAndCapacity(int venue, int capacity) {

            List<Activity> activities;

            if (venue == 0 && capacity == 0) {
                activities = activityRedisTemplateService.findAll(); // Redis에서 모든 활동 객체 목록을 가져옴
                if(activities.isEmpty()){ // 만약 레디스에 없으면 db호출
                    activities = activityRepository.findAll();
                }
            } else if (venue == 0) {
                activities = activityRedisTemplateService.findByCapacityGreaterThanEqual(capacity);
                if(activities.isEmpty()){ // 만약 레디스에 없으면 db호출
                    activities = activityRepository.findByCapacityGreaterThanEqual(capacity);
                }
            } else if (capacity == 0) {
                activities = activityRedisTemplateService.findByVenueGreaterThanEqual(venue);
                if(activities.isEmpty()){ // 만약 레디스에 없으면 db호출
                    activities = activityRepository.findByVenueGreaterThanEqual(capacity);
                }
            } else {
                activities = activityRedisTemplateService.findByVenueAndCapacityGreaterThanEqual(venue, capacity);
                if(activities.isEmpty()) { // 만약 레디스에 없으면 db호출
                    activities = activityRepository.findByVenueAndCapacityGreaterThanEqual(venue, capacity);
                }
            }

            if (activities.isEmpty()) {
                return "No activities found.";
            }

            Random random = new Random();
            int index = random.nextInt(activities.size());
            Activity randomActivity = activities.get(index);

            return randomActivity.getName();
        }


    public void delete(int id) {
        activityRepository.deleteById(id);
    }


    public Activity addActivity(String name, int venue, int capacity) {
        Activity activity = Activity.builder()
                .name(name)
                .venue(venue)
                .capacity(capacity)
                .build();

        return activityRepository.save(activity);
    }

    }