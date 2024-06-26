package com.communify.domain.like.application;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final PostSearchService postSearchService;
    private final MemberFindService memberFindService;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushLikeNotification(LikeEvent event) {
        LikeRequest request = event.getLikeRequest();

        String memberName = request.getMemberName();
        Long memberId = request.getMemberId();
        Long postId = request.getPostId();

        Long writerId = postSearchService.getWriterId(postId);
        if (Objects.equals(memberId, writerId)) {
            return;
        }

        String token = memberFindService.findFcmTokenById(writerId)
                .orElseThrow(() -> new FcmTokenNotSetException(writerId));

        MessageDto messageDto = MessageDto.builder()
                .title(String.format("%s님이 회원님의 게시글에 '좋아요'를 눌렀습니다.", memberName))
                .build();

        pushService.push(token, messageDto);
    }
}
