package com.dgut.community.controller;

import com.dgut.community.entity.Question;
import com.dgut.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class QuestionController {
    @Autowired
    QuestionMapper questionMapper;

    @RequestMapping("/getquestion")
    public String GetQuestion(Integer id, Model model){
        Question question=questionMapper.findById(id);
        questionMapper.incViewcount(id);
        model.addAttribute("question",question);
        return "question-view";
    }

    @RequestMapping("to_questionview")
    public String to_questionview(){
        return "question-view";
    }
}
