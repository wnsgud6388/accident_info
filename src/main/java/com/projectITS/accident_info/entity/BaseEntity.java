package com.projectITS.accident_info.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 엔티티 관련 작업을 할 때 데이터의 등록 시간, 수정시간 같이
// 자동으로 처리되어야 하는 부분을 엔티티클래스로 만들자

@MappedSuperclass   // 테이블로 생성이 되지 않고, 다른 클래스의 부모 클래스가 되다는 것을 컴파일러에게 명시
@Getter
// AuditingEntityListener
// 수정 될 때 자동으로 감지하여 값을 넣어줌
@EntityListeners(value ={AuditingEntityListener.class})
abstract class BaseEntity {
    @CreatedDate    // 엔터티 객체의 데이터 생성 시간
    @Column(name="regDate",updatable=false)
    private LocalDateTime regDate;

    @LastModifiedDate // 엔터티 객체 데이터가 수정된 날짜시간
    @Column(name="modDate", updatable = true)     
    private LocalDateTime modDate;


}
