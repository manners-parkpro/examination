package com.examination.api.service.admin.room;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.model.domain.Hotel;
import com.examination.api.model.domain.Room;
import com.examination.api.model.domain.RoomCategory;
import com.examination.api.model.dto.HotelDto;
import com.examination.api.model.dto.RoomCategoryDto;
import com.examination.api.model.dto.RoomDto;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.model.types.YNType;
import com.examination.api.repository.hotel.HotelRepository;
import com.examination.api.repository.room.RoomRepository;
import com.examination.api.service.admin.hotel.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;
    private final HotelService hotelService;
    private final HotelRepository hotelRepository;

    @Transactional
    public HotelDto.HotelResponseDto save(Long hotelId, RoomDto dto) throws RequiredParamNonException, NotFoundException, AlreadyEntity {
        if (hotelId == null || dto == null || CollectionUtils.isEmpty(dto.getRoomCategories()))
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        Hotel hotel = hotelService.findById(hotelId);
        // 객실명 중복체크
        if (repository.countByHotelAndName(hotel, dto.getName()) > 0)
            throw new AlreadyEntity(dto.getName() + "은(는) 이미 존재하는 객실명 입니다.");

        setRooms(hotel, dto);

        hotelRepository.save(hotel);

        return hotelService.responseData(hotel);
    }

    @Transactional
    public void put(Long id, RoomDto dto) throws RequiredParamNonException, NotFoundException, AlreadyEntity {
        if (id == null || dto == null || CollectionUtils.isEmpty(dto.getRoomCategories()))
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        Room room = findById(id);

        // 객실명 중복체크
        if (repository.countByHotelAndIdNotAndName(room.getHotel(), id, dto.getName()) > 0)
            throw new AlreadyEntity(dto.getName() + "은(는) 이미 존재하는 객실명 입니다.");

        room.put(dto.getName(), dto.getDescription(), dto.getPeople(), dto.getPrice());
        setRoomCategory(room, dto.getRoomCategories());

        repository.save(room);
    }

    @Transactional
    public void usable(Long id, YNType usable) throws NotFoundException {
        Room room = findById(id);
        room.changeUsable(usable);
    }

    @Transactional(readOnly = true)
    Room findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Room Entity : " + ResponseMessage.NOT_FOUND.getMessage()));
    }

    private void setRooms(Hotel hotel, RoomDto dto) {
        hotel.getRooms().clear();

        List<Room> rooms = new ArrayList<>();
        Room room = Room.builder()
                .hotel(hotel)
                .name(dto.getName())
                .description(dto.getDescription())
                .size(dto.getSize() + "㎡")
                .people(dto.getPeople())
                .price(dto.getPrice())
                .deleteYn(YNType.N)
                .build();

        rooms.add(room);
        hotel.getRooms().addAll(rooms);

        setRoomCategory(room, dto.getRoomCategories());
    }

    private void setRoomCategory(Room room, List<RoomCategoryDto> categories) {
        room.getRoomCategories().clear();

        List<RoomCategory> roomCategories = new ArrayList<>();
        // 중복제거를 위한 distinct
        categories.stream().distinct().forEach(c -> {
            RoomCategory roomCategory = RoomCategory.builder()
                    .room(room)
                    .category(c.getCategory())
                    .build();

            roomCategories.add(roomCategory);
        });

        room.getRoomCategories().addAll(roomCategories);
    }
}
