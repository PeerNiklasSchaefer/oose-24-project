package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByNumber(String number);
}
