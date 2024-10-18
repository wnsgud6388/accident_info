package com.projectITS.accident_info.dto;

// Mybatis를 이용한 프로젝트에서는 VO(Value Object)를 데이터베이스에 있는 데이터를 꺼내 뷰단으로 실어 나르는 객체로 사용했고
// DTO(Data Transfer Object)를 View단에서 Dao단으로 데이터를 실어 나르도록 하였다.

// JPA에서는 VO(데이터를 가지고 있는 객체) = Entity 객체
// Entity 객체를 영속 계층 바깥쪽에서 사용하는 방식은 매우 부담스럽고 불편한 일이다.
// -> Entity는 실제 데이터베이스와 관련이 있는 객체이고 내부적으로 Entity Manager에 의해 관리되기 때문이다.
// 그러므로 영속 계층 외에서는 DTO 객체를 만들어 사용할 것을 권장한다. 
// DTO 객체 또한 데이터를 담고 있다는 면에서 Entity 객체와 유사하나, 데이터의 전달, 읽기/쓰기가 모두 허용된다는 점에서 일회성으로 사용된다는 성격이 강하기 때문이다.

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Builder
@Data
public class Accident_infoDTO {
    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;


    // 기본 생성자
    protected Accident_infoDTO() {}

    // 모든 필드를 받는 생성자
    protected Accident_infoDTO(Long gno, String title, String content, String writer, LocalDateTime regDate, LocalDateTime modDate) {
        this.gno = gno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modDate = modDate;
    }



}
