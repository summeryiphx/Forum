package com.dgut.community.controller;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;

//    @GetMapping("/publish")
//    public ModelAndView publish(String creator, Model model){
//        System.out.println(creator);
//        model.addAttribute("creator",creator);
//        return new ModelAndView("/publish","model",model);
//    }
    @RequestMapping("/publish")
    public String publish(String creator,Model model){
        model.addAttribute("creator",creator);
        return "publish";
    }

    @GetMapping("/cque")
    public String CreateQuestion(Question question, HttpSession session){
//        User user=(User)session.getAttribute("user");
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.CreateQuestion(question);
        return "redirect:/";
    }
}
