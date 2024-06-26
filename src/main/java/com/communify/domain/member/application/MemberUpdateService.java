package com.communify.domain.member.application;

import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.PasswordUpdateRequest;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberFindService memberFindService;
    private final MemberRepository memberRepository;

    public void updatePassword(PasswordUpdateRequest request) {
        Long memberId = request.getMemberId();

        MemberInfo memberInfo = memberFindService.findMemberInfoById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        String currentPassword = request.getCurrentPassword();
        if (!PasswordEncryptor.isMatch(currentPassword, memberInfo.getHashed())) {
            throw new InvalidPasswordException(currentPassword);
        }

        memberRepository.updatePassword(request);
    }

    public void setFcmToken(String fcmToken, Long memberId) {
        memberRepository.setFcmToken(fcmToken, memberId);
    }
}
