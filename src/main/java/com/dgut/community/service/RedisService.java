package com.dgut.community.service;

import com.dgut.community.entity.UserLike;
import com.dgut.community.dto.LikedCountDTO;

import java.util.List;

public interface RedisService {

    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLiked2Redis(String likedUserId, String likedPostId,String Question_ID);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId,String Question_ID);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId,String Question_ID);

    /**
     * 该问题的点赞数加1
     * @param Question_Id
     */
    void incrementLikedCount(String Question_Id);

    /**
     * 该问题的点赞数减1
     * @param Question_Id
     */
    void decrementLikedCount(String Question_Id);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();

}

