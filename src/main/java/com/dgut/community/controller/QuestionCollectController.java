package com.dgut.community.controller;

import com.dgut.community.entity.QuestionCollect;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.QuestionCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionCollectController {
    @Autowired
    QuestionCollectMapper questionCollectMapper;

    @ResponseBody
    @RequestMapping("/collection")
    public String collection(Integer id, HttpSession session){
        User user =(User) session.getAttribute("user");
        int row = questionCollectMapper.collect(user.getUser_id(),id);
        if (row>0){
            return "收藏成功~";
        }else {
            return "收藏失败~发生了一点小错误~";
        }
    }

    @ResponseBody
    @RequestMapping("/cancelcollectioned")
    public String cancelcollectioned(Integer id,HttpSession session){
        User user =(User) session.getAttribute("user");
       int row = questionCollectMapper.delcollect(user.getUser_id(),id);
        if (row>0){
            return "已取消收藏~";
        }else{
            return "发生了一点小错误~请稍后再试";
        }
    }





}
