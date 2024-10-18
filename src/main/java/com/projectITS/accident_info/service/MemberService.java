package com.projectITS.accident_info.service;

import com.projectITS.accident_info.entity.Member;

public interface MemberService {

    Member loginMember(String userId, String userPwd);
}
