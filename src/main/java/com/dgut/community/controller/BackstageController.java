package com.dgut.community.controller;

import com.dgut.community.entity.Comment;
import com.dgut.community.entity.Message;
import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.CommentMapper;
import com.dgut.community.mapper.MessageMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BackstageController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    MessageMapper messageMapper;

    @RequestMapping("/back")
    public String back(){
        return "b-index";
    }

    @RequestMapping("/to_welcome")
    public String towelcome(Model model){
        int qcount = questionMapper.count();
        int ucount = userMapper.count();
        int ccount = commentMapper.count();
        model.addAttribute("qcount",qcount);
        model.addAttribute("ucount",ucount);
        model.addAttribute("ccount",ccount);
        return "b-welcome";
    }

    @RequestMapping("/stumember")
    public String stumember(Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<User> userList = userMapper.findByrole(1,14);
        PageInfo pageInfo = new PageInfo<>(userList,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("userList",userList);
        return "b-studentmember";
    }

    @RequestMapping("/tecmember")
    public String tecmember(Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<User> userList = userMapper.findByrole(2,24);
        PageInfo pageInfo = new PageInfo<>(userList,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("userList",userList);
        return "b-teachermember";
    }

    @RequestMapping("/changerole")
    @ResponseBody
    public String stopuser(Integer id, Integer role){
        userMapper.changerole(id,role);
        return null;
    }

    @RequestMapping("/deluser")
    @ResponseBody
    public String deluser(Integer id){
        userMapper.deluser(id);
        return null;
    }

    @RequestMapping("/que/{action}")
    public String que(@PathVariable(name = "action") String action, Model model, HttpSession session, @RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<Question> questionList = questionMapper.findByCategory(action);
        PageInfo pageInfo = new PageInfo<>(questionList,5);
        model.addAttribute("action",action);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("questionList",questionList);
        return "b-question-list";
    }

    @RequestMapping("/stusearch")
    public String stumembersearch (String username,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<User> userList = userMapper.findByNameList(username);
        PageInfo pageInfo = new PageInfo<>(userList,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("userList",userList);
        return "b-studentmember";
    }
    @RequestMapping("/tecsearch")
    public String tecmembersearch (String username,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<User> userList = userMapper.findByNameList(username);
        PageInfo pageInfo = new PageInfo<>(userList,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("userList",userList);
        return "b-teachermember";
    }

    @RequestMapping("/quesearch")
    public String quesearch (String title,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<Question> questionList = questionMapper.findBytitle(title);
        PageInfo pageInfo = new PageInfo<>(questionList,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("questionList",questionList);
        return "b-question-list";
    }

    @RequestMapping("/getmessage")
    public String getMessage(Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<Message> messageList = messageMapper.findAll();
        PageInfo pageInfo = new PageInfo<>(messageList,5);
        List<Message> messageList1 = new ArrayList<>();
        for (Message message : messageList){
            User user = userMapper.findByUserid(message.getUser_id());
            message.setUser(user);
            messageList1.add(message);
        }
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("messageList",messageList1);
        return "b-message-list";
    }
}
