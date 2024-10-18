package com.projectITS.accident_info.service;

import com.projectITS.accident_info.entity.Member;
import com.projectITS.accident_info.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Member loginMember(String userId, String userPwd) {

        //log.info(userId.toString());

        return memberRepository.getLoginInfo(userId, userPwd);
    }
}
