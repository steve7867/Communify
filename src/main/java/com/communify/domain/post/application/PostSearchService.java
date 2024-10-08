package com.communify.domain.post.application;

import com.communify.domain.post.dto.HotPostSearchConditionByCategory;
import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostOutlineSearchConditionByCategory;
import com.communify.domain.post.dto.PostOutlineSearchConditionByWriter;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchService {

    private final HotPostSearchService hotPostSearchService;
    private final PostRepository postRepository;

    @Cacheable(cacheNames = CacheNames.POST_OUTLINES,
            key = "#searchCond.categoryId + '_' + #searchCond.lastPostId",
            sync = true)
    public List<PostOutline> getPostOutlinesByCategory(final PostOutlineSearchConditionByCategory searchCond) {
        final Integer searchSize = searchCond.getSearchSize();
        final List<PostOutline> postOutlineList = new ArrayList<>(searchSize);

        if (searchCond.shouldSearchForHotPosts()) {
            final List<PostOutline> hotPostOutlineList = hotPostSearchService
                    .getHotPostOutlineListByCategory(new HotPostSearchConditionByCategory(searchCond.getCategoryId()));

            postOutlineList.addAll(hotPostOutlineList);
        }

        final Integer deductedSearchSize = searchSize - postOutlineList.size();
        final PostOutlineSearchConditionByCategory renewedSearchCond = searchCond.setSearchSize(deductedSearchSize);

        postOutlineList.addAll(postRepository.findPostOutlineByCategory(renewedSearchCond));
        return Collections.unmodifiableList(postOutlineList);
    }

    public List<PostOutline> getPostOutlinesByWriter(final PostOutlineSearchConditionByWriter searchCond) {
        final List<PostOutline> postOutlineList = postRepository.findPostOutlineByWriter(searchCond);
        return Collections.unmodifiableList(postOutlineList);
    }

    @Cacheable(cacheNames = CacheNames.POST_DETAIL, key = "#postId", sync = true)
    public Optional<PostDetail> getPostDetail(final Long postId) {
        return postRepository.findPostDetail(postId);
    }
}
