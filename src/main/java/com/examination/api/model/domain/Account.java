package com.examination.api.model.domain;

import com.examination.api.model.types.YNType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Account extends BaseEntity {

    @Id
    @JsonIgnore
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true)
    private String username;
    @JsonIgnore
    @Column(length = 100)
    private String password;
    @Column(length = 50)
    private String nickname;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YNType activeYn = YNType.Y;
    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountAuthority> authorities = new ArrayList<>();

    public void modifyPassword(String password) {
        this.password = password;
    }
}
