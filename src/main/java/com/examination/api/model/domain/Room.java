package com.examination.api.model.domain;

import com.examination.api.model.types.YNType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Room extends BaseEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    // 방이름
    @Column(length = 50)
    private String name;
    // 방 설명
    private String description;
    // 방 공간 (㎡)
    @Column(length = 10)
    private String size;
    // 인원제한
    @Column(length = 10)
    private int people;
    // 가격
    private BigDecimal price;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YNType deleteYn = YNType.N;
    // Category
    @Builder.Default
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomCategory> roomCategories = new ArrayList<>();

    public void put(String name, String description, int people, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.people = people;
        this.price = price;
    }

    public void changeUsable(YNType deleteYn) { this.deleteYn = deleteYn; }
}
