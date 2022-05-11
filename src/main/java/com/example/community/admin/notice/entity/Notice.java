package com.example.community.admin.notice.entity;

import com.example.community.admin.notice.dto.NoticeResponseDto;
import com.example.community.admin.user.entity.Admin;
import com.example.community.domain.baseentity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void postedAdmin(Admin admin) {
        this.admin = admin;
    }

    public NoticeResponseDto toDto() {
        return new NoticeResponseDto(title,content,super.getCreatedAt(), admin.getAdminName());
    }
}
