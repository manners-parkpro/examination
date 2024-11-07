package com.examination.api.repository.room;

import com.examination.api.model.domain.Hotel;
import com.examination.api.model.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    long countByHotelAndName(Hotel hotel, String name);
    long countByHotelAndIdNotAndName(Hotel hotel, Long id, String name);
}
