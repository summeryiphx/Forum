package com.dgut.community.controller;

import com.dgut.community.entity.User;
import com.dgut.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String account_id, String password , HttpSession session, HttpServletRequest request, Model model){
        User user=userMapper.findUser(account_id,password);
        if (user != null){
            session.setAttribute("user",user);
            return "redirect:/";
        }else{
            return "redirect:/to_login";
        }

    }

    @RequestMapping("to_login")
    public String to_login(){
        return "login";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


}
