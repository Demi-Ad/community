package com.example.community.admin.notice.repo;

import com.example.community.admin.notice.dto.NoticeResponseDto;
import com.example.community.admin.notice.entity.Notice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query(value = "select new com.example.community.admin.notice.dto.NoticeResponseDto(n.id,n.title,n.createdAt,n.admin.adminName) " +
            "from Notice n order by n.createdAt desc")
    List<NoticeResponseDto> findNoticeList();


    @Override
    @EntityGraph(attributePaths = {"admin"})
    Optional<Notice> findById(Long id);
}