package com.dgut.community.controller;

import com.dgut.community.Utils.MdUtil;
import com.dgut.community.entity.Message;
import com.dgut.community.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MessageController {
    @Autowired
    MessageMapper messageMapper;

    @RequestMapping("/to_message")
    public String tomessage(){
        return "message";
    }

    @RequestMapping("/message")
    public String Message(Message message){
        message.setGmtCreate(System.currentTimeMillis());
        message.setContent(MdUtil.MdToHtml(message.getContent()));
        int row = messageMapper.insert(message);
        return "redirect:/";
    }

    @RequestMapping("/deletemess")
    @ResponseBody
    public String delete(Integer id){
        int row = messageMapper.del(id);
        return null;
    }


}
