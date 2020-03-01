package com.dgut.community.controller;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
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
    public String Index(HttpSession session,Model model){
        List<Question> questionList=questionMapper.list();
        List<Question> questionList1=new ArrayList<>();
        for (Question question:questionList){
            User user=userMapper.findByName(question.getCreator());
            question.setUser(user);
            questionList1.add(question);
        }
        model.addAttribute("questions",questionList1);
        return "index";
    }

}
