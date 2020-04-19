package com.dgut.community.mapper;

import com.dgut.community.entity.UserLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserLikeMapper {
    Integer save(UserLike userLike);
    Integer update(UserLike userLike);
    List<UserLike> saveAll(List<UserLike> list);
    List<UserLike> findByLikedUserIdAndStatus(@Param("likedUserId") String likedUserId, @Param("code") Integer code);
    List<UserLike> findByLikedPostIdAndStatus(@Param("likedPostId") String likedPostId,@Param("code") Integer code);
    UserLike findByLikedUserIdAndLikedPostIdQusetionID(@Param("likedUserId") String likedUserId, @Param("likedPostId") String likedPostId,@Param("Question_ID") Integer Question_ID);

    Integer updateQuestionID(@Param("id") Integer id,@Param("question_id") Integer question_id);
}
