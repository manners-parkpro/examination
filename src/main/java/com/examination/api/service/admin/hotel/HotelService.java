package com.examination.api.service.admin.hotel;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.model.domain.Amenity;
import com.examination.api.model.domain.Hotel;
import com.examination.api.model.dto.*;
import com.examination.api.model.types.GradeType;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.repository.hotel.HotelRepository;
import com.examination.api.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.examination.api.utils.CommonUtil.*;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository repository;

    @Transactional(readOnly = true)
    public PageResponseDto readPage(HotelDto.PageRequestDto dto, Pageable pageable) {

        Page<Hotel> pages = fetchPageable(dto, pageable);
        List<HotelDto.HotelResponseDto> list = pages.stream().map(this::hotelResponseDto).toList();

        return PageResponseDto
                .builder()
                .contents(list)
                .pageable(pageable)
                .totalCount(pages.getTotalElements())
                .build();
    }

    @Transactional
    public HotelDto.HotelResponseDto save(HotelDto dto) throws RequiredParamNonException, AlreadyEntity {
        if (dto == null || CollectionUtils.isEmpty(dto.getAmenities()))
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        existsByName(dto);

        Hotel hotel = Hotel.builder()
                .name(dto.getName())
                .contactNumber(convertContactNumber(dto.getContactNumber()))
                .area(dto.getArea())
                .address(dto.getAddress())
                .grade(dto.getGrade())
                .build();

        // Set 호텔 편의시설
        setAmenities(hotel, dto.getAmenities());
        repository.save(hotel);

        return hotelResponseDto(hotel);
    }

    @Transactional
    public HotelDto.HotelResponseDto put(Long id, HotelDto dto) throws RequiredParamNonException, AlreadyEntity, NotFoundException {
        if (id == null || dto == null || CollectionUtils.isEmpty(dto.getAmenities()))
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        existsByName(dto);

        Hotel hotel = repository.findById(id).orElseThrow(() -> new NotFoundException("Hotel Entity : " + ResponseMessage.NOT_FOUND.getMessage()));
        hotel.put(dto.getName(), dto.getContactNumber(), dto.getArea(), dto.getAddress(), dto.getGrade());

        // Set 호텔 편의시설
        setAmenities(hotel, dto.getAmenities());
        repository.save(hotel);

        return hotelResponseDto(hotel);
    }

    @Transactional
    public void delete(Long id) throws NotFoundException {
        Hotel hotel = findById(id);
        repository.delete(hotel);
    }

    public Hotel findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Hotel Entity : " + ResponseMessage.NOT_FOUND.getMessage()));
    }

    public HotelDto.HotelResponseDto hotelResponseDto(Hotel hotel) {
        return HotelDto.HotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .contactNumber(hotel.getContactNumber())
                .area(hotel.getArea())
                .address(hotel.getAddress())
                .grade(hotel.getGrade().getDescription())
                .amenities(hotel.getAmenities().stream().map(item -> AmenityDto.AmenityResponseDto.builder()
                        .title(item.getTitle())
                        .description(item.getDescription())
                        .location(item.getLocation())
                        .manageTime(markTime(item.getManageStartTime(), item.getManageEndTime()))
                        .build()).toList())
                .build();
    }

    private void setAmenities(Hotel hotel, List<AmenityDto> amenities) throws RequiredParamNonException {
        if (CollectionUtils.isEmpty(amenities))
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        hotel.getAmenities().clear();
        List<Amenity> list = new ArrayList<>();

        amenities.forEach(a -> {
            Amenity amenity = Amenity.builder()
                    .hotel(hotel)
                    .title(a.getTitle())
                    .description(a.getDescription())
                    .location(a.getLocation())
                    .manageStartTime(a.getManageStartTime())
                    .manageEndTime(a.getManageEndTime())
                    .build();

            list.add(amenity);
        });

        hotel.getAmenities().addAll(list);
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
