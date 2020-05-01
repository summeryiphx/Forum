package com.dgut.community.controller;


import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TagController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/findtag")
    public String findtag(String tag, Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,15);
        List<Question> questionList = questionMapper.findByTag(tag);
        PageInfo pageInfo = new PageInfo<>(questionList,5);

        List<Question> questionList1=new ArrayList<>();
        for (Question question:questionList){
            User user=userMapper.findByName(question.getCreator());
            question.setUser(user);
            questionList1.add(question);
        }
        model.addAttribute("question1",questionList1);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("tag",tag);
        return "tag";
    }










}
