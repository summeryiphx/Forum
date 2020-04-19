package com.dgut.community.service;

import com.dgut.community.entity.UserLike;


import java.util.List;

public interface LikedService {

    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
    Integer save(UserLike userLike);

    /**
     * 更新点赞记录
     * @param userLike
     * @return
     */
    Integer update(UserLike userLike);

    /**
     * 批量保存或修改
     * @param list
     */
    List<UserLike> saveAll(List<UserLike> list);


    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedUserId 被点赞人的id
     * @return
     */
    List<UserLike> getLikedListByLikedUserId(String likedUserId);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param likedPostId
     * @return
     */
    List<UserLike> getLikedListByLikedPostId(String likedPostId);

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    UserLike getByLikedUserIdAndLikedPostIdQusetionID(String likedUserId, String likedPostId,String Question_ID);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

}

