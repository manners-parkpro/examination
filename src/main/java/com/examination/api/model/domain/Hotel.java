package com.examination.api.model.domain;

import com.examination.api.model.types.GradeType;
import com.examination.api.model.types.YNType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;

import java.util.ArrayList;
import java.util.List;

import static com.examination.api.utils.CommonUtil.convertContactNumber;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Hotel extends BaseEntity {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 호텔명
    @Column(length = 50)
    private String name;
    // 호텔 연락처
    @Column(length = 20)
    private String contactNumber;
    // 호텔 지역
    @Column(length = 50)
    private String area;
    // 호텔 주소
    private String address;
    // 등급
    private GradeType grade;
    @Builder.Default
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    public void put(String name, String contactNumber, String area, String address, GradeType grade) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.area = area;
        this.address = address;
        this.grade = grade;
    }
}
