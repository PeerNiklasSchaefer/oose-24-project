package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChairRepository extends JpaRepository<Chair, Long> {
    Chair findByName(String name);
}
