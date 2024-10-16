package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LikeEvent {

    private final Long postId;
    private final List<LikerInfo> likerInfoList;
}
