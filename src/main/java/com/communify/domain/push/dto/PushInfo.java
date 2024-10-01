package com.communify.domain.push.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PushInfo implements PushableEvaluator {

    protected final String token;

    @Override
    public Boolean isPushable() {
        return isTokenExisting();
    }

    protected Boolean isTokenExisting() {
        return Objects.nonNull(token);
    }

    public final MessageDto getMessageDto() {
        if (!isPushable()) {
            throw new IllegalStateException("푸쉬 알림을 보낼 수 없습니다.");
        }

        return makeMessageDto();
    }

    protected abstract MessageDto makeMessageDto();
}