package com.dgut.community.redis;

public class RedisKeyUtils {

    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
//    //保存用户被点赞数量的key
//    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    //保存问题被点赞数量的key
    public static final String MAP_KEY_QUESTION_LIKED_COUNT = "MAP_QUESTION_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId, String likedPostId,String Question_ID){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        builder.append("::");
        builder.append(Question_ID);
        return builder.toString();
    }
}

