package com.today.demo.repository;

import com.today.demo.entity.Category;
import com.today.demo.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Integer> {
    List<Marker> findByVenue(int venue);

    List<Marker> findByVenueAndCategory(int venue,Category category);

    List<Marker> findByCategory(Category category);

    Marker findByLatitudeAndLongitude(double latitude,double longitude);

}