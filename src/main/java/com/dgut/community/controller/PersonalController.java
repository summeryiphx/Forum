package com.dgut.community.controller;

import com.dgut.community.dto.NoticeDTO;
import com.dgut.community.entity.Notice;
import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonalController {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/personal/{action}")
    public String Personal(@PathVariable(name = "action") String action, Model model,HttpSession session,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        if ("question".equals(action)){
            model.addAttribute("option", "question");
            model.addAttribute("optionName", "我的提问");
            User user = (User) session.getAttribute("user");
            PageHelper.startPage(pageNum,10);
            List<Question> questionList = questionMapper.findByName(user.getUsername());
            PageInfo pageInfo = new PageInfo<>(questionList,5);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("questionList", questionList);
            return "personal";
        }
        if ("reply".equals(action)){
            model.addAttribute("option","reply");
            model.addAttribute("optionName","回复");
            User user = (User) session.getAttribute("user");

            PageHelper.startPage(pageNum,5);
            List<Notice> noticeList = noticeMapper.findNotice(user.getUser_id());
            PageInfo pageInfo = new PageInfo<>(noticeList,5);

            List<Notice> noticDTOList = new ArrayList<>();
            for ( Notice notice : noticeList){
                User user1 = userMapper.findByUserid(notice.getNotifier());
                Question question = questionMapper.findById(notice.getOuterId());
                notice.setUser(user1);
                notice.setQuestion(question);
                noticDTOList.add(notice);
            }

            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("noticeList", noticeList);
            model.addAttribute("noticDTOList",noticDTOList);

            return "personal";
        }
        return "personal";
    }
}
