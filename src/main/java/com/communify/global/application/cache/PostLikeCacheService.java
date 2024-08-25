package com.communify.global.application.cache;

import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostLikeCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheLike(final LikeRequest request) {
        final String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, request.getPostId());
        final Long likerId = request.getLikerId();
        redisTemplate.opsForZSet().addIfAbsent(cacheKey, likerId, Double.valueOf(likerId));
    }

    public Map<Long, List<Long>> getPostLikeCacheAsMapAndClear() {
        final ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_LIKE + "*")
                .count(300)
                .build();

        try (final Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            final List<String> keyList = cursor.stream().toList();

            final Map<Long, List<Long>> postLikeMap = new HashMap<>(keyList.size());

            for (String cacheKey : keyList) {
                final List<Object> result = redisTemplate.execute(new SessionCallback<>() {

                    @Override
                    public List<Object> execute(final RedisOperations operations) throws DataAccessException {
                        operations.multi();

                        operations.opsForZSet().range(cacheKey, 0L, Long.MAX_VALUE);
                        operations.delete(cacheKey);

                        return operations.exec();
                    }
                });

                final Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));
                final List<Long> likerIdList = ((Set<Integer>) result.get(0))
                        .stream()
                        .map(Integer::longValue)
                        .toList();

                postLikeMap.put(postId, likerIdList);
            }

            return postLikeMap;
        }
    }
}
