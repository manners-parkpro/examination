package com.examination.api.model.domain;

import com.examination.api.model.types.YNType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static jakarta.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

// 호텔 편의시설

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Amenity extends BaseEntity {

    @Id
    @Column(name = "amenity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Column(length = 100)
    private String title;
    private String description;
    @Column(length = 50)
    private String location;
    private LocalTime manageStartTime;
    private LocalTime manageEndTime;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YNType deleteYn = YNType.N;
}
