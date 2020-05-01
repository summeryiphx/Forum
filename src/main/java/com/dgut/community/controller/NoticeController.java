package com.dgut.community.controller;

import com.dgut.community.entity.Comment;
import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.CommentMapper;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import com.dgut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    NoticeMapper noticeMapper;

    @RequestMapping("/notice")
    public String notice(Integer q_id,Integer n_id, Model model, HttpSession session){
        Question question=questionMapper.findById(q_id);
        User q_user = userMapper.findByName(question.getCreator());
        question.setUser(q_user);
        List<Question> relateQuestions = questionService.selectRelate(question);  //查找相关问题

        //查找评论
        List<Comment> commentList = commentMapper.findAllcomments(q_id);
        List<Comment> commentList1 = new ArrayList<>();
        for (Comment comment : commentList){
            User user=userMapper.findByUserid(comment.getCommentator());
            comment.setUser(user);
            commentList1.add(comment);
        }

        //+阅读数
        User user =(User) session.getAttribute("user");
        if (!user.getUsername().equals(question.getCreator())){
            questionMapper.incViewcount(q_id);
        }

        noticeMapper.read(n_id);

        int noticecount = noticeMapper.noticecount(user.getUser_id());
        session.setAttribute("noticecount",noticecount);

        model.addAttribute("question",question);
        model.addAttribute("commentList",commentList1);
        model.addAttribute("relateQuestions",relateQuestions);
        return "question-view";
    }







}
