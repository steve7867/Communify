package com.communify.domain.like.service;

import com.communify.domain.like.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikerInfo;
import com.communify.domain.post.PostRepository;
import com.communify.domain.post.dto.PostOutline;
import com.communify.global.util.HotPostDeterminer;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LikeSaveService {

    private final PostRepository postRepository;

    private final ApplicationEventPublisher eventPublisher;
    private final SqlSessionFactory sqlSessionFactory;

    @Async
    public void saveLike(final Long postId, final List<LikerInfo> likerInfoList) {
        final List<BatchResult> batchResultList = saveInDB(postId, likerInfoList);
        int[] updateCounts = batchResultList.get(0).getUpdateCounts();

        final List<LikerInfo> validLikerInfoList = filterValidLikerInfo(likerInfoList, updateCounts);

        eventPublisher.publishEvent(new LikeEvent(postId, validLikerInfoList));

        promoteToHotIfEligible(postId);
    }

    private List<BatchResult> saveInDB(final Long postId, final List<LikerInfo> likerInfoList) {
        final List<BatchResult> batchResultList;
        try (final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            final LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

            for (LikerInfo info : likerInfoList) {
                final Long likerId = info.getLikerId();

                likeRepository.insertLike(postId, likerId);
            }

            batchResultList = sqlSession.flushStatements();
        }
        return batchResultList;
    }

    private List<LikerInfo> filterValidLikerInfo(final List<LikerInfo> likerInfoList, final int[] updateCounts) {
        return IntStream.range(0, likerInfoList.size())
                .filter(i -> updateCounts[i] == 1)
                .mapToObj(likerInfoList::get)
                .toList();
    }

    private void promoteToHotIfEligible(final Long postId) {
        final Optional<PostOutline> postOutlineOpt = postRepository.findPostOutlineForHotSwitch(postId);
        if (postOutlineOpt.isEmpty()) {
            return;
        }

        final PostOutline postOutline = postOutlineOpt.get();
        if (postOutline.getIsHot()) {
            return;
        }

        if (!HotPostDeterminer.isCandidateForHot(postOutline)) {
            return;
        }

        postRepository.makePostAsHot(postId);
    }
}
