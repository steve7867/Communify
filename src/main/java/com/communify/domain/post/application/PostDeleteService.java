package com.communify.domain.post.application;

import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostDeleteRequest;
import com.communify.domain.post.error.exception.InvalidPostAccessException;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;
    private final PostAttachmentService postAttachmentService;

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#request.postId")
    public void deletePost(final PostDeleteRequest request) {
        final Long postId = request.getPostId();
        final Long memberId = request.getRequesterId();

        final boolean canDelete = postRepository.isWrittenBy(postId, memberId);
        if (!canDelete) {
            throw new InvalidPostAccessException(postId, memberId);
        }

        postAttachmentService.deleteFiles(postId);

        postRepository.deletePost(request);
    }
}
