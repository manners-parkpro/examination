package com.examination.api.repository.reserve;

import com.examination.api.model.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveRepositoryCustom {
}
