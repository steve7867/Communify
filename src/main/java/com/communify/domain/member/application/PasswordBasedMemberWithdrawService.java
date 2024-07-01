package com.communify.domain.member.application;

import com.communify.domain.auth.application.LoginService;
import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import com.communify.domain.member.dto.event.MemberWithdrawEvent;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordBasedMemberWithdrawService implements MemberWithdrawService {

    private final MemberFindService memberFindService;
    private final LoginService loginService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void withdraw(final MemberWithdrawRequest request) {
        final Long memberId = request.getMemberId();

        final MemberInfo memberInfo = memberFindService.findMemberInfoById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        final String password = request.getPassword();
        final String hashed = memberInfo.getHashed();

        if (!PasswordEncryptor.isMatch(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        loginService.logout();

        eventPublisher.publishEvent(new MemberWithdrawEvent(request));
    }
}
