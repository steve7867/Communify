package com.communify.domain.member.presentation;

import com.communify.domain.auth.annotation.CurrentMemberId;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberSignUpService memberSignUpService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid MemberSignUpRequest request) {
        memberSignUpService.signUp(request);
    }

    @GetMapping("/{memberId}")
    @ResponseStatus(OK)
    @LoginCheck
    public MemberInfo getMemberInfo(@PathVariable @NotNull @Positive Long memberId) {
        return memberService.findMemberInfoById(memberId);
    }

    @DeleteMapping("/me")
    @ResponseStatus(OK)
    @LoginCheck
    public void withdraw(@RequestBody @Valid MemberWithdrawRequest request,
                         @CurrentMemberId Long memberId) {

        memberService.withdraw(request, memberId);
    }
}
