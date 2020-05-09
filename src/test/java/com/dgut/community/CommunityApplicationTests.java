package com.dgut.community;

import cn.hutool.extra.mail.MailUtil;
import com.dgut.community.Utils.MdUtil;
import com.dgut.community.entity.Comment;
import com.dgut.community.entity.Question;
import com.dgut.community.mapper.CommentMapper;
import com.dgut.community.mapper.QuestionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommunityApplicationTests {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CommentMapper commentMapper;

    @Value("${spring.mail.username}")
    private String from;
    @Test
    public void sendSimpleMail() throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 邮件发送者
        message.setTo("839456939@qq.com"); // 邮件接受者
        message.setSubject("莞工网上答疑平台"); // 主题
        message.setText("您好，有学生向您请教但长时间未收到您的回复，请空余后登录平台进行解答"); // 内容

        mailSender.send(message);
    }

    @Test
    public void mdtohtml(){
       List<Comment> commentList = commentMapper.findAll();
       for (Comment comment : commentList){
           comment.setContent(MdUtil.MdToHtml(comment.getContent()));
           commentMapper.updatecontent(comment.getId(),comment.getContent());
       }
    }

    @Test
    public void test(){
        System.out.println(System.currentTimeMillis());

    }




}
