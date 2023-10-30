package com.today.demo.repository;

import com.today.demo.entity.Activity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByVenueAndCapacityGreaterThanEqual(int venue, int capacity);

    List<Activity> findByCapacityGreaterThanEqual(int capacity);

    List<Activity> findByVenueGreaterThanEqual(int venue);


}
