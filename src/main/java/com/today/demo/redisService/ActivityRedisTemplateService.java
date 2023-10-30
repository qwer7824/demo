package com.today.demo.redisService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.today.demo.entity.Activity;
import com.today.demo.repository.ActivityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ZSetOperations;


import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityRedisTemplateService {

    private static final String CACHE_KEY = "ACTIVITY";
    private final ActivityRepository activityRepository;

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations; // 활동 데이터를 담을 CACHE_KEY, 활동 데이터의 PK 값, 활동 객체를 String 형태로 변환

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }


    /**
     * 활동 객체를 저장합니다.
     *
     * @param activity 활동 객체
     */
    public void save(Activity activity) {
        if (Objects.isNull(activity) || activity.getId() == 0) {
            log.error("Required Values must not be null");
            return;
        }
        try {
            hashOperations.put(CACHE_KEY,
                    String.valueOf(activity.getId()),
                    serializeActivity(activity));
            log.info("[ActivityRedisTemplateService save success] id: {}", activity.getId());
        } catch (Exception e) {
            log.error("[ActivityRedisTemplateService save error] {}", e.getMessage());
        }
    }

    /**
     * 모든 활동 객체 목록을 조회합니다.
     *
     * @return 활동 객체 목록
     */
    public List<Activity> findAll() {
        try {
            List<Activity> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                Activity activity = deserializeActivity(value);
                list.add(activity);
            }
            return list;
        } catch (Exception e) {
            log.error("[ActivityRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 인원 수를 조회합니다.
     *
     */
    public List<Activity> findByCapacityGreaterThanEqual(int capacity) {
        try {
            List<Activity> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                Activity activity = deserializeActivity(value);
                if (activity.getCapacity() == capacity) {
                    list.add(activity);
                }
            }
            return list;
        } catch (Exception e) {
            log.error("[ActivityRedisTemplateService findByCapacityGreaterThanEqual error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 장소를 조회합니다.
     *
     */
    public List<Activity> findByVenueGreaterThanEqual(int venue) {
        try {
            List<Activity> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                Activity activity = deserializeActivity(value);
                if (activity.getVenue() == venue) {
                    list.add(activity);
                }
            }
            return list;
        } catch (Exception e) {
            log.error("[ActivityRedisTemplateService findByVenueGreaterThanEqual error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 특정 장소에서 인원이 주어진 값의 활동 객체를 조회합니다.
     *
     * venue    장소
     * capacity 인원
     */
    public List<Activity> findByVenueAndCapacityGreaterThanEqual(int venue, int capacity) {
        try {
            List<Activity> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                Activity activity = deserializeActivity(value);
                if (activity.getVenue() == venue && activity.getCapacity() == capacity) {
                    list.add(activity);
                }
            }
            return list;
        } catch (Exception e) {
            log.error("[ActivityRedisTemplateService findByVenueAndCapacityGreaterThanEqual error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }



    /**
     * 특정 ID에 해당하는 활동 객체를 삭제합니다.
     *
     * @param id 활동 ID
     */
    public void delete(int id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[ActivityRedisTemplateService delete]: {} ", id);
    }

    public void deleteAll() {
        LinkedHashSet<String> keys = new LinkedHashSet<>(hashOperations.keys(CACHE_KEY));
        for (String key : keys) {
            hashOperations.delete(CACHE_KEY, key);
        }
        log.info("[ActivityRedisTemplateService allDelete] ");
    }
    private String serializeActivity(Activity activity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(activity); // 활동 객체를 JSON 형태로 변환
    }

    private Activity deserializeActivity(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, Activity.class); // 문자열을 활동 객체로 변환
    }
}
