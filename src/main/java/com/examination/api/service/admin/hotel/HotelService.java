package com.examination.api.service.admin.hotel;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.model.domain.Hotel;
import com.examination.api.model.dto.HotelDto;
import com.examination.api.model.dto.PageResponseDto;
import com.examination.api.model.dto.RoomCategoryDto;
import com.examination.api.model.dto.RoomDto;
import com.examination.api.model.types.GradeType;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.repository.hotel.HotelRepository;
import com.examination.api.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.examination.api.utils.CommonUtil.convertContactNumber;
import static com.examination.api.utils.CommonUtil.convertCurrency;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository repository;

    @Transactional(readOnly = true)
    public PageResponseDto readPage(HotelDto.PageRequestDto dto, Pageable pageable) {

        Page<Hotel> pages = fetchPageable(dto, pageable);
        List<HotelDto.HotelResponseDto> list = pages.stream().map(this::responseData).toList();

        return PageResponseDto
                .builder()
                .contents(list)
                .pageable(pageable)
                .totalCount(pages.getTotalElements())
                .build();
    }

    @Transactional
    public HotelDto.HotelResponseDto save(HotelDto dto) throws RequiredParamNonException, AlreadyEntity {
        if (dto == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        existsByName(dto);

        Hotel hotel = Hotel.builder()
                .name(dto.getName())
                .contactNumber(convertContactNumber(dto.getContactNumber()))
                .area(dto.getArea())
                .address(dto.getAddress())
                .grade(dto.getGrade())
                .build();

        repository.save(hotel);

        return responseData(hotel);
    }

    @Transactional
    public HotelDto.HotelResponseDto put(Long id, HotelDto dto) throws RequiredParamNonException, AlreadyEntity, NotFoundException {
        if (id == null || dto == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        existsByName(dto);

        Hotel hotel = repository.findById(id).orElseThrow(() -> new NotFoundException("Hotel Entity : " + ResponseMessage.NOT_FOUND.getMessage()));
        hotel.put(dto.getName(), dto.getContactNumber(), dto.getArea(), dto.getAddress(), dto.getGrade());

        repository.save(hotel);

        return responseData(hotel);
    }

    @Transactional
    public void delete(Long id) throws NotFoundException {
        Hotel hotel = findById(id);
        repository.delete(hotel);
    }

    public Hotel findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Hotel Entity : " + ResponseMessage.NOT_FOUND.getMessage()));
    }

    public HotelDto.HotelResponseDto responseData(Hotel hotel) {
        return HotelDto.HotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .contactNumber(hotel.getContactNumber())
                .area(hotel.getArea())
                .address(hotel.getAddress())
                .grade(hotel.getGrade().getDescription())
                .rooms(hotel.getRooms().stream().map(item -> RoomDto.RoomResponseDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .size(item.getSize())
                        .people(item.getPeople())
                        .price(convertCurrency(item.getPrice().intValue()))
                        .roomCategories(item.getRoomCategories().stream().map(c -> RoomCategoryDto.RoomCategoryResponseDto.builder()
                                .id(c.getId())
                                .category(c.getCategory())
                                .build()).toList())
                        .build()).toList())
                .build();
    }

    private void existsByName(HotelDto dto) throws AlreadyEntity {
        if (repository.countByName(dto.getName()) > 0)
            throw new AlreadyEntity(dto.getName() + "은(는) 이미 존재하는 이름입니다.");
    }

    private Page<Hotel> fetchPageable(HotelDto.PageRequestDto dto, Pageable pageable) {

        return repository.searchPagination(
                dto.getName(),
                CommonUtil.convertToEnumList(dto.getGrade(), GradeType.class),
                dto.getArea(),
                pageable
        );
    }
}
