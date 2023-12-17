package com.today.demo.service;

import com.today.demo.dto.ActivityDTO;
import com.today.demo.redisService.ActivityRedisTemplateService;
import com.today.demo.entity.Activity;
import com.today.demo.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
            if (activities.isEmpty()) { // 만약 레디스에 없으면 db호출
                activities = activityRepository.findAll();
            }
        } else if (venue == 0) {
            activities = activityRedisTemplateService.findByCapacityGreaterThanEqual(capacity);
            if (activities.isEmpty()) { // 만약 레디스에 없으면 db호출
                activities = activityRepository.findByCapacityGreaterThanEqual(capacity);
            }
        } else if (capacity == 0) {
            activities = activityRedisTemplateService.findByVenueGreaterThanEqual(venue);
            if (activities.isEmpty()) { // 만약 레디스에 없으면 db호출
                activities = activityRepository.findByVenueGreaterThanEqual(capacity);
            }
        } else {
            activities = activityRedisTemplateService.findByVenueAndCapacityGreaterThanEqual(venue, capacity);
            if (activities.isEmpty()) { // 만약 레디스에 없으면 db호출
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


    public Activity addActivity(ActivityDTO activityDTO) {
        if (activityDTO.getVenue() == 0 && activityDTO.getCapacity() == 0) {
            for (int i = 1; i <= 2; i++) {
                for (int j = 1; j <= 4; j++) {
                    Activity activity = Activity.builder()
                            .name(activityDTO.getName())
                            .venue(i)
                            .capacity(j)
                            .build();
                    activityRepository.save(activity);
                    log.info("addActivity venue{} capacity{}", i, j);
                }
            }
        } else if (activityDTO.getVenue() == 0) {
            for (int i = 1; i <= 2; i++) {
                Activity activity = Activity.builder()
                        .name(activityDTO.getName())
                        .venue(i)
                        .capacity(activityDTO.getCapacity())
                        .build();
                activityRepository.save(activity);
                log.info("addActivity venue{}", i);
            }
        } else if (activityDTO.getCapacity() == 0) {
            for (int i = 1; i <= 4; i++) {
                Activity activity = Activity.builder()
                        .name(activityDTO.getName())
                        .venue(activityDTO.getVenue())
                        .capacity(i)
                        .build();
                log.info("addActivity capacity{}", i);
                activityRepository.save(activity);
            }
        } else {
            Activity activity = Activity.builder()
                    .name(activityDTO.getName())
                    .venue(activityDTO.getVenue())
                    .capacity(activityDTO.getCapacity())
                    .build();
            log.info("addActivity");
            return activityRepository.save(activity);
        }
        return null;
    }
    public Page<Activity> getAllActivity(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return activityRepository.findAll(pageable);
    }
    public void save() {
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
    }

    public void updateActivity(ActivityDTO activityDTO) {
            Activity activity = activityRepository.findById(activityDTO.getId()).orElseThrow(null);
            activity.update(activityDTO);
            activityRepository.save(activity);
        }

    public Activity getActivity(int id) {
        return activityRepository.findById(id).orElseThrow(null);
    }
}