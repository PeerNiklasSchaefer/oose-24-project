package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building findByName(String name);
}
