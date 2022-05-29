package com.example.community.domain.account.entity;

import com.example.community.config.security.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;



    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String profileImg;

    @Column(nullable = false)
    private String nickname;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @Builder
    public Account(String email, String password, String profileImg,String nickname) {
        this.email = email;
        this.password = password;
        this.role = Role.ROLE_USER;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public void changeProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "role = " + role + ", " +
                "nickname = " + nickname + ", " +
                "registeredAt = " + registeredAt + ")";
    }
}
