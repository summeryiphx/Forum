package com.dgut.community.controller;

import com.dgut.community.dto.LikedCountDTO;
import com.dgut.community.entity.UserLike;
import com.dgut.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/like")
    @ResponseBody
    public Object like(@RequestBody LikedCountDTO likedCountDTO){
        redisService.saveLiked2Redis(likedCountDTO.getLikedUserId(),likedCountDTO.getLikedPostId(),likedCountDTO.getQusetion_id());
        redisService.incrementLikedCount(likedCountDTO.getQusetion_id());
        return 1;
    }
    @RequestMapping("/cancellike")
    @ResponseBody
    public Object cancellike(@RequestBody LikedCountDTO likedCountDTO){
        redisService.unlikeFromRedis(likedCountDTO.getLikedUserId(),likedCountDTO.getLikedPostId(),likedCountDTO.getQusetion_id());
        redisService.decrementLikedCount(likedCountDTO.getQusetion_id());
        return 1;
    }

}
