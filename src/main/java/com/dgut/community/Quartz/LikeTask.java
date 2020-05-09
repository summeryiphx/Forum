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

/**
 * 点赞的定时任务
 */
@Slf4j
public class LikeTask extends QuartzJobBean {
    @Autowired
    LikedService likedService;

    @Value("${spring.mail.username}")
    private String from;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("LikeTask-------- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        likedService.transLikedFromRedis2DB();
        likedService.transLikedCountFromRedis2DB();

    }



}
