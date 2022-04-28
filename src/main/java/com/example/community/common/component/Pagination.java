package com.example.community.common.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class Pagination<T> {

    private int totalRecordCount;
    private int totalPageCount;
    private int startPage;
    private int endPage;
    private int limitStart;
    private boolean existPrevPage;
    private boolean existNextPage;

    private int currentPage;

    private List<T> dataList = new ArrayList<>();

    public Pagination(int totalRecordCount, int currentPageNum) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.currentPage = currentPageNum;
            this.calculation(currentPageNum);
        }
    }

    private void calculation(int currentPageNum) {

        // 전체 페이지 수 계산
        totalPageCount = ((totalRecordCount - 1) / 10) + 1;

        // 첫 페이지 번호 계산
        startPage = ((currentPageNum - 1) / 5) * 5 + 1;

        // 끝 페이지 번호 계산
        endPage = startPage + 5 - 1;

        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        // LIMIT 시작 위치 계산
        limitStart = (currentPageNum - 1) * 10;

        // 이전 페이지 존재 여부 확인
        existPrevPage = startPage != 1;

        // 다음 페이지 존재 여부 확인
        existNextPage = (endPage * 10) < totalRecordCount;
    }

    public void setDataList(Collection<T> dataList) {
        this.dataList.addAll(dataList);
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "totalRecordCount=" + totalRecordCount +
                ", totalPageCount=" + totalPageCount +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", limitStart=" + limitStart +
                ", existPrevPage=" + existPrevPage +
                ", existNextPage=" + existNextPage +
                '}';
    }

    public static <T, U> Pagination<T> of(Page<U> page, Function<List<U>, List<T>> function) {
        List<U> uList = page.toList();
        Pageable pageable = page.getPageable();
        List<T> tList = function.apply(uList);
        Pagination<T> pagination = new Pagination<>((int) page.getTotalElements(), pageable.getPageNumber() + 1);
        pagination.setDataList(tList);
        return pagination;
    }
}