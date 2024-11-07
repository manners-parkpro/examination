package com.examination.api.repository.hotel;

import com.examination.api.model.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, HotelRepositoryCustom {

    long countByName(String name);
}
