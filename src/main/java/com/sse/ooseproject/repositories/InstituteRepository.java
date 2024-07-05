package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Institute findByName(String name);
}
