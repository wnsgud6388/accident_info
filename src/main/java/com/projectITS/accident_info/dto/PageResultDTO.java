package com.projectITS.accident_info.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// 페이징 정보와 페이징된 데이터를 함께 가지고 있는 DTO클래스
// 여기에서 DTO는 페이징 된 실제 데이터의 DTO를 의미하고, EN은 페이징 정보를 가지고 있는 Entity객체
// Function<EN, DTO> fn : 엔터티 객체들을 DTO로 변환해 주는 함수를 매개변수로 받을

@Getter
@ToString
@AllArgsConstructor
public class PageResultDTO<DTO,EN> {

    // DTO리스트(실제 페이징된 데이터)
    private List<DTO> dtoList;

    // PagiNation을 출력하기 위한 리스트
    private List<Integer> pageList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 한 페이지당 출력될 데이터 갯수;
    private int size;

    // 시작페이지번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음 페이지
    private boolean prev, next;
    
    // 생성자의 역할 : 객체를 만들 때 자동으로 호출되는 일종의 메서드.
    //                (멤버 변수를 초기화, 객체가 생성될 때 해야 할 일이 있으면 그 일 수행)
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
        // 전체 페이지 수
        this.totalPage = result.getTotalPages();
        makePagingInfo(result.getPageable());
    }

    private void makePagingInfo(Pageable pageable) {
        
        this.page=pageable.getPageNumber() +1;
        this.size = pageable.getPageSize();
        
        // ceil 은 올림
        // int / int 를 하게되면 정수만 반환되기 때문에 int/double = double
        int tmpEnd = (int)(Math.ceil(page / 10.0)) * 10;
        this.start = tmpEnd - 9;
        this.prev = this.page > 1;
        this.end = this.totalPage > tmpEnd ? tmpEnd : this.totalPage;
        this.next = this.totalPage > tmpEnd;
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    public PageResultDTO(List<DTO> list){
        this.dtoList = list;
    }
}
