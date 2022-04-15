package com.example.community.domain.account.entity;

import com.example.community.config.security.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    private Boolean lock;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String profileImg;

    private String nickname;

    @CreatedDate
    private LocalDateTime registeredAt;

    @Builder
    public Account(String email, String password, String profileImg,String nickname) {
        this.email = email;
        this.password = password;
        this.lock = true;
        this.role = Role.ROLE_USER;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public void changeProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public boolean isLock() {
        return lock;
    }

    public void unLock() {
        this.lock = false;
    }
}
