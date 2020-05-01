package com.dgut.community.controller;

import com.dgut.community.entity.User;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public int login(String account_id, String password , HttpSession session, HttpServletRequest request, Model model){
        User user=userMapper.findUser(account_id,password);
        if (user != null){
            session.setAttribute("user",user);
            int noticecount = noticeMapper.noticecount(user.getUser_id());
            session.setAttribute("noticecount",noticecount);

            return 1;
        }else{
            return -1;
        }

    }

    @RequestMapping("to_index")
    public String to_login(){
        return "index";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:to_index";
    }


}
