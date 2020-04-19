package com.dgut.community.service.impl;

import com.dgut.community.entity.Question;
import com.dgut.community.mapper.UserLikeMapper;
import com.dgut.community.service.LikedService;
import com.dgut.community.entity.UserLike;
import com.dgut.community.dto.LikedCountDTO;
import com.dgut.community.enums.LikedStatusEnum;
import com.dgut.community.service.QuestionService;
import com.dgut.community.service.RedisService;
import com.dgut.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LikedServiceimpl implements LikedService {

    @Autowired
    UserLikeMapper userLikeMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Override
    @Transactional
    public Integer save(UserLike userLike) {
        return userLikeMapper.save(userLike);
    }

    @Override
    public Integer update(UserLike userLike) {
        return userLikeMapper.update(userLike);
    }

    @Override
    @Transactional
    public List<UserLike> saveAll(List<UserLike> list) {
        return userLikeMapper.saveAll(list);
    }

    @Override
    public List<UserLike> getLikedListByLikedUserId(String likedUserId) {
        return userLikeMapper.findByLikedUserIdAndStatus(likedUserId,LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public List<UserLike> getLikedListByLikedPostId(String likedPostId) {
        return userLikeMapper.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public UserLike getByLikedUserIdAndLikedPostIdQusetionID(String likedUserId, String likedPostId,String Question_ID) {
        int qusetion_id = Integer.parseInt(Question_ID);
        return userLikeMapper.findByLikedUserIdAndLikedPostIdQusetionID(likedUserId, likedPostId,qusetion_id);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        for (UserLike like : list) {
            UserLike ul = getByLikedUserIdAndLikedPostIdQusetionID(like.getLikedUserId(), like.getLikedPostId(),like.getQuestion_ID());
            if (ul == null){
                //没有记录，直接存入
                save(like);
            }else{
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                update(ul);
            }
        }
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {
            int qusetion_id = Integer.parseInt(dto.getQusetion_id());
            Question question = questionService.findById(qusetion_id);
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (question != null){
                Integer likeCount = question.getLikeCount() + dto.getCount();
                question.setLikeCount(likeCount);
                //更新点赞数量
                questionService.updateLikeNum(question);

            }
        }
    }
}
