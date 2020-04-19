package com.dgut.community.service.impl;

import com.dgut.community.service.LikedService;
import com.dgut.community.service.RedisService;
import com.dgut.community.entity.UserLike;
import com.dgut.community.dto.LikedCountDTO;
import com.dgut.community.enums.LikedStatusEnum;
import com.dgut.community.redis.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RedisServiceimpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LikedService likedService;

    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId,String Question_ID) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId,Question_ID);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId,String Question_ID) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId,Question_ID);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId,String Question_ID) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId,Question_ID);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String Question_Id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, Question_Id, 1);
    }

    @Override
    public void decrementLikedCount(String Question_Id) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, Question_Id, -1);
    }

    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[1];
            String Question_ID = split[2];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            UserLike userLike = new UserLike(likedUserId, likedPostId, value,Question_ID);
            list.add(userLike);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }

        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, key);
        }
        return list;
    }
}

