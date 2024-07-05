package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    University findByName(String name);
}
