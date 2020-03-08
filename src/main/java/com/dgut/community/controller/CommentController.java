package com.dgut.community.controller;

import com.dgut.community.dto.CommentDTO;
import com.dgut.community.entity.Comment;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.CommentMapper;
import com.dgut.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.beans.Transient;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object postcomment(@RequestBody CommentDTO commentDTO, HttpSession session){
        User user=(User)session.getAttribute("user");
        Comment comment = new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(user.getUser_id());
        int row = commentMapper.insert(comment);
        questionMapper.incCommentcount(commentDTO.getParent_id());
        return row;
    }

}
