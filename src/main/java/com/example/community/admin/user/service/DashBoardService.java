package com.example.community.admin.user.service;

import com.example.community.admin.user.dto.DashBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final EntityManager em;

    private static DashBoardDto mappingDto(Object[] objects) {
        Long countAllPost = ((BigInteger) objects[0]).longValue();
        Long countAllAccount = ((BigInteger) objects[1]).longValue();
        Long countAllComment = ((BigInteger) objects[2]).longValue();
        return DashBoardDto.builder()
                .countAllPost(countAllPost)
                .countAllAccount(countAllAccount)
                .countAllComment(countAllComment)
                .build();
    }

    public DashBoardDto createDashBoard() {
        String nativeSQL = "select * from (select count(p.POST_ID) from post p) as countAllPost," +
                "(select count(a.ACCOUNT_ID) from account a) as countAllAccount," +
                "(select count(c.COMMENT_ID) from comment c) as countAllComment";

        Query nativeQuery = em.createNativeQuery(nativeSQL);

        List<Object[]> resultList = nativeQuery.getResultList();
        return resultList.stream()
                .map(DashBoardService::mappingDto)
                .findFirst()
                .orElseThrow();
    }

}
