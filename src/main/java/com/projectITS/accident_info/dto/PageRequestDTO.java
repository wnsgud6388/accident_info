package com.projectITS.accident_info.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


// 페이징에 필요한 파라미터(현재 페이지 번호, 한 페이지당 보여줄 데이터 갯수)
// 검색조건 , 검색어
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;   // 현재페이지 번호
    private int size;   // 한 페이지당 보여줄 데이터 갯수

    private String searchType;  //null
    private String searchValue; //null

    public PageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(this.page-1, this.size,sort);
    }
}
