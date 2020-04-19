package com.dgut.community.controller;

import com.dgut.community.dto.CommentDTO;
import com.dgut.community.entity.Comment;
import com.dgut.community.entity.Notice;
import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.CommentMapper;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoticeMapper noticeMapper;

    //评论
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object postcomment(@RequestBody CommentDTO commentDTO, HttpSession session){
        int row;
        User user=(User)session.getAttribute("user");
        if(user == null){
            return row=0;
        }
        Comment comment = new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(user.getUser_id());
        row = commentMapper.insert(comment);
        questionMapper.incCommentcount(commentDTO.getParent_id());

        //通知
        Notice notice = new Notice();
        notice.setNotifier(user.getUser_id());
        notice.setNotifier_name(user.getUsername());
        Question question = questionMapper.findById(commentDTO.getParent_id());
        User user1 = userMapper.findByName(question.getCreator());
        notice.setTitle(question.getTitle());
        notice.setReceiver(user1.getUser_id());
        notice.setOuterId(commentDTO.getParent_id());
        notice.setType(1);
        notice.setGmt_create(System.currentTimeMillis());
        notice.setStatus(0);
        noticeMapper.insert(notice);
        return row;
    }

    //获取二级评论
    @RequestMapping(value = "/getseccomments/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> getseccomments(@PathVariable(name = "id") Integer id){
        List<Comment> comments = commentMapper.findSecondComments(id);
        List<Comment> comments1 =new ArrayList<>();
        for (Comment comment : comments){
            User user=userMapper.findByUserid(comment.getCommentator());
            comment.setUser(user);
            comments1.add(comment);
        }
        return comments1;
    }

    //二级评论
    @RequestMapping(value = "/secondcomment",method = RequestMethod.POST)
    @ResponseBody
    public Object secondcomment(@RequestBody CommentDTO commentDTO, HttpSession session){
        int row;
        User user=(User)session.getAttribute("user");
        if (user == null){
            return row=0;
        }
        Comment comment =new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(user.getUser_id());
        comment.setSecomment_id(commentDTO.getSecomment_id());
        row = commentMapper.insertsecomment(comment);
        commentMapper.incsecomment_count(commentDTO.getSecomment_id());

        //通知
        Notice notice = new Notice();
        notice.setNotifier(user.getUser_id());
        notice.setNotifier_name(user.getUsername());
        //Question question = questionMapper.findById(commentDTO.getParent_id());
        Comment comment1 = commentMapper.findById(commentDTO.getSecomment_id());
        notice.setReceiver(comment1.getCommentator());
        notice.setTitle(comment1.getContent());
        notice.setOuterId(commentDTO.getParent_id());
        notice.setType(2);
        notice.setGmt_create(System.currentTimeMillis());
        notice.setStatus(0);
        noticeMapper.insert(notice);

        return row;
    }
    @RequestMapping(value = "/commentdelete",method = RequestMethod.POST)
    @ResponseBody
    public int delete(Integer cid,Integer qid){
        int row = commentMapper.del(cid);
        questionMapper.reduceCommentcount(qid);
        return row;
    }

}
