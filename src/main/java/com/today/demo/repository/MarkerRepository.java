package com.today.demo.repository;

import com.today.demo.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Integer> {
    List<Marker> findByVenue(int venue);
}