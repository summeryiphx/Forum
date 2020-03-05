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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/")
    public String Index( HttpSession session,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
//        question1是带有User question2是不带有User
        PageHelper.startPage(pageNum,15);
        List<Question> questionList2=questionMapper.findAll();
        PageInfo pageInfo = new PageInfo<>(questionList2,5);

        List<Question> questionList1=new ArrayList<>();
        for (Question question:questionList2){
            User user=userMapper.findByName(question.getCreator());
            question.setUser(user);
            questionList1.add(question);
        }
        model.addAttribute("question2",questionList2);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("question1",questionList1);
        return "index";
    }

}
