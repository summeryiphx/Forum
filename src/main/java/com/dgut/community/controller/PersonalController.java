package com.dgut.community.controller;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.QuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PersonalController {
    @Autowired
    QuestionMapper questionMapper;

    @RequestMapping("/personal/{action}")
    public String Personal(@PathVariable(name = "action") String action, Model model,HttpSession session,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        if ("question".equals(action)){
            model.addAttribute("option", "question");
            model.addAttribute("optionName", "我的提问");
            User user = (User) session.getAttribute("user");
            PageHelper.startPage(pageNum,5);
            List<Question> questionList = questionMapper.findByName(user.getUsername());
            PageInfo pageInfo = new PageInfo<>(questionList,5);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("questionList", questionList);
            return "personal-question";
        }
        if ("reply".equals(action)){
            model.addAttribute("option","reply");
            model.addAttribute("optionName","回复");
            return "personal-reply";
        }
        return "personal-question";
    }
}
