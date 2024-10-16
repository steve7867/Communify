package com.communify.domain.like;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikerInfo;
import com.communify.domain.member.MemberService;
import com.communify.domain.post.service.PostSearchService;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final PostSearchService postSearchService;
    private final MemberService memberService;
    private final PushService pushService;

    @EventListener
    public void pushLikeNotification(final LikeEvent event) {
        final Long postId = event.getPostId();
        final List<LikerInfo> likerInfoList = event.getLikerInfoList();

        final Optional<Long> writerIdOpt = postSearchService.getWriterId(postId);
        if (writerIdOpt.isEmpty()) {
            return;
        }
        final Long writerId = writerIdOpt.get();

        final Optional<String> tokenOpt = memberService.getToken(writerId);
        if (tokenOpt.isEmpty()) {
            return;
        }
        final String token = tokenOpt.get();

        for (LikerInfo info : likerInfoList) {
            final Long likerId = info.getLikerId();
            final String likerName = info.getLikerName();

            pushService.push(new PushInfoForLike(writerId, token, likerId, likerName));
        }
    }
}
