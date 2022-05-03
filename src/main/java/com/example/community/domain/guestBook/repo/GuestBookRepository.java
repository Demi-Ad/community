package com.example.community.domain.guestBook.repo;

import com.example.community.domain.guestBook.dto.GuestBookResponseDto;
import com.example.community.domain.guestBook.entity.GuestBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {


    @Query("select new com.example.community.domain.guestBook.dto.GuestBookResponseDto(g.id,g.content,g.guest.nickname,g.createdAt) " +
            "from GuestBook g where g.owner.id = :ownerId order by g.createdAt desc")
    Page<GuestBookResponseDto> findGuestBookPaging(@Param("ownerId") Long id, Pageable pageable);



}