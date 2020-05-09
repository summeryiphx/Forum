package com.dgut.community.Quartz;

import com.dgut.community.entity.Notice;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.UserMapper;
import com.dgut.community.service.LikedService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class MailTask extends QuartzJobBean {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String from;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("MailTask-------- {}", sdf.format(new Date()));

        List<Notice> noticeList = noticeMapper.finddelay();
        for (Notice notice : noticeList){
            if (day(notice.getGmt_create())>=3){
                User user = userMapper.findByUserid(notice.getReceiver());
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from); // 邮件发送者
                message.setTo(user.getMail()); // 邮件接受者
                message.setSubject("莞工网上答疑平台"); // 主题
                message.setText(user.getUsername()+"教师您好，系统检测到有学生向您请教但长时间未收到您的回复，请有空后登录平台进行解答"); // 内容
                mailSender.send(message);
            }
        }
    }

    public static long day(long gmt){
        Date nowDate = new Date(System.currentTimeMillis());
        Date date = new Date(gmt);
        long l =  nowDate.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        return day;
    }
}
