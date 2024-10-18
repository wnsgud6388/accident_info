package com.projectITS.accident_info.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="accident_info")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accident_info extends BaseEntity {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;
    
    @Column(length=200, nullable = false)
    private String title;

    @Column(length=2000)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;
    
    public void changeTitle(String title){
        if(title != null){
            this.title = title;
        }        
    }

    public void changeContent(String content){
        this.content = content;
        
    }

    public void changeAll(String title,String content){
        changeContent(content);
        changeTitle(title);
    }
}
