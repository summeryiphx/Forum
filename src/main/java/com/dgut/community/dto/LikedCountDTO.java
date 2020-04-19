package com.dgut.community.dto;

import com.dgut.community.enums.LikedStatusEnum;
import lombok.Data;

@Data
public class LikedCountDTO {
    //问题id
    private String qusetion_id;

    //被点赞的用户的id
    private String likedUserId;

    //点赞的用户的id
    private String likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    private String key;
    private Integer count;

    public LikedCountDTO(String qusetion_id,Integer count){
        this.qusetion_id=qusetion_id;
        this.count =count;
    }
    public LikedCountDTO(){};
}
