package com.projectITS.accident_info.repository;

import com.projectITS.accident_info.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {

    @Query(value="select * From Member where userId=?1 and userPwd=?2", nativeQuery = true)
    Member getLoginInfo(String userId, String userPwd);
}
