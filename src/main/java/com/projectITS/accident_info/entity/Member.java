
package com.projectITS.accident_info.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="Member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userNo")
    private Long userNo;

    @Column(name = "userId", length = 50, nullable = false)
    private String userId;

    @Column(length = 100, nullable = false, name="userPwd")
    private String userPwd;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;


}
