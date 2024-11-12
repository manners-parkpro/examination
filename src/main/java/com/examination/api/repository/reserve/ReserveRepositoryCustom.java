package com.examination.api.repository.reserve;

import com.examination.api.model.domain.Account;
import com.examination.api.model.domain.Reserve;
import com.examination.api.model.domain.Room;

import java.time.LocalDate;

public interface ReserveRepositoryCustom {

    Long countByReserveDate(LocalDate reserveStartDate, LocalDate reserveEndDate);
    Reserve findByRoomAndAccount(Room room, Account account);
}
