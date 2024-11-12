package com.examination.api.service.front.reserve;

import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.domain.Account;
import com.examination.api.model.domain.Reserve;
import com.examination.api.model.domain.Room;
import com.examination.api.model.dto.LoginUser;
import com.examination.api.model.dto.ReserveDto;
import com.examination.api.model.dto.RoomDto;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.repository.reserve.ReserveRepository;
import com.examination.api.service.admin.room.RoomService;
import com.examination.api.service.front.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.examination.api.utils.CommonUtil.convertContactNumber;
import static com.examination.api.utils.CommonUtil.markDate;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository repository;
    private final RoomService roomService;
    private final AccountService accountService;

    @Transactional
    public ReserveDto.ResponseDto save(Long roomId, ReserveDto dto, LoginUser loginUser) throws RequiredParamNonException, NotFoundException {
        if (roomId == null || dto == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());
        else if (loginUser == null)
            throw new UserNotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        Room room = roomService.findById(roomId);
        if (room == null)
            throw new NotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        Account account = accountService.findAccount(loginUser.getPrincipal());
        if (account == null)
            throw new UserNotFoundException(loginUser.getPrincipal() + ResponseMessage.NOT_FOUND.getMessage());

        // Validation
        valid(room.getId(), dto);

        Reserve reserve = Reserve.builder()
                .hotel(room.getHotel())
                .room(room)
                .account(account)
                .name(dto.getName())
                .englishName(dto.getEnglishName().toUpperCase())
                .phone(convertContactNumber(dto.getPhone()))
                .reserveStartDate(dto.getReserveStartDate())
                .reserveEndDate(dto.getReserveEndDate())
                .people(dto.getPeople())
                .build();

        repository.save(reserve);

        return ReserveDto.ResponseDto.builder()
                .name(dto.getName())
                .reserveDate(markDate(dto.getReserveStartDate(), dto.getReserveEndDate()))
                .people(dto.getPeople())
                .build();
    }

    @Transactional
    public void cancel(Long roomId, LoginUser loginUser) throws NotFoundException, RequiredParamNonException {
        if (roomId == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());
        else if (loginUser == null)
            throw new UserNotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        Room room = roomService.findById(roomId);
        if (room == null)
            throw new NotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        Account account = accountService.findAccount(loginUser.getPrincipal());
        if (account == null)
            throw new UserNotFoundException(loginUser.getPrincipal() + ResponseMessage.NOT_FOUND.getMessage());

        Reserve reserve = repository.findByRoomAndAccount(room, account);
        if (reserve == null)
            throw new NotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        repository.delete(reserve);
    }

    @Transactional(readOnly = true)
    public List<ReserveDto.ResponseDto> findReservation(String usernam) throws UserNotFoundException {
        if (!StringUtils.hasText(usernam))
            throw new UserNotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        Account account = accountService.findAccount(usernam);
        if (account == null)
            throw new UserNotFoundException(ResponseMessage.NOT_FOUND.getMessage());

        return account.getReserves().stream().map(item -> ReserveDto.ResponseDto.builder()
                .name(item.getName())
                .reserveDate(markDate(item.getReserveStartDate(), item.getReserveEndDate()))
                .people(item.getPeople())
                .build()).toList();
    }

    private void valid(Long roomId, ReserveDto dto) throws NotFoundException {
        if (dto.getReserveStartDate().isAfter(dto.getReserveEndDate()))
            throw new RuntimeException("예약 시작일자는 종료일자보다 미래일 수 없습니다.");

        RoomDto roomDto = roomService.findByIdToDto(roomId);

        if (dto.getPeople() > roomDto.getPeople())
            throw new RuntimeException("객실 이용가능한 최대 인원은 " + roomDto.getPeople() + "명 입니다.");

        if (repository.countByReserveDate(dto.getReserveStartDate(), dto.getReserveEndDate()) > 0)
            throw new RuntimeException("선택하신 날짜는 이미 예약이 되어 있습니다.");
    }
}
