package com.examination.api.repository.reserve;

import com.examination.api.model.domain.Account;

import java.time.LocalDate;

public interface ReserveRepositoryCustom {

    Long countByReserveDate(LocalDate reserveStartDate, LocalDate reserveEndDate);
}
