package com.examination.api.model.domain;

import com.examination.api.core.CryptoConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Reserve extends BaseEntity {

    @Id
    @Column(name = "reserve_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 호텔
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    // 객실
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    // 신청자 ID
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    // 신청자
    @Column(length = 100)
    @Convert(converter = CryptoConverter.class)
    private String name;
    // 신청자 영문
    @Column(length = 100)
    private String englishName;
    // 연락처
    @Column(length = 50)
    @Convert(converter = CryptoConverter.class)
    private String phone;
    // 예약 시작 일자
    private LocalDate reserveStartDate;
    // 예약 종료 일자
    private LocalDate reserveEndDate;
    // 신청 인원
    private int people;
}
