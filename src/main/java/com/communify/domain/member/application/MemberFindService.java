package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFindService {

    private final MemberRepository memberRepository;

    public Optional<MemberInfo> findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberInfo findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    public String findFcmTokenById(Long memberId) {
        return memberRepository.findFcmTokenById(memberId)
                .orElseThrow(() -> new FcmTokenNotSetException(memberId));
    }
}
