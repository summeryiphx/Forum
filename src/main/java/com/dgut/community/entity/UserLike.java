package com.dgut.community.entity;

import com.dgut.community.enums.LikedStatusEnum;
import lombok.Data;

/**
 * 用户点赞表
 */
@Data
public class UserLike {

    //主键id
    private Integer id;

    //文章ID
    private String Question_ID;

    //被点赞的用户的id
    private String likedUserId;

    //点赞的用户的id
    private String likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public UserLike() {
    }

    public UserLike(String likedUserId, String likedPostId, Integer status,String Question_ID) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
        this.Question_ID=Question_ID;
    }
}

