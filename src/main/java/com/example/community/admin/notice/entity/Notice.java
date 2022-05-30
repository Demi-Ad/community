package com.example.community.admin.notice.entity;

import com.example.community.admin.notice.dto.NoticeResponseDto;
import com.example.community.admin.user.entity.Admin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id",foreignKey = @ForeignKey(name = "admin_fk"),nullable = false)
    private Admin admin;

    @CreatedDate
    private LocalDateTime createdAt;

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void postedAdmin(Admin admin) {
        this.admin = admin;
    }

    public NoticeResponseDto toDto() {
        return new NoticeResponseDto(title,content,createdAt, admin.getAdminName());
    }
}
