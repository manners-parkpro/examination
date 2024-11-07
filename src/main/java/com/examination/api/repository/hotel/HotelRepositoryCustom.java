package com.examination.api.repository.hotel;

import com.examination.api.model.domain.Hotel;
import com.examination.api.model.types.GradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelRepositoryCustom {

    Page<Hotel> searchPagination(String name, List<GradeType> grade, String area, Pageable pageable);
}
