package com.dgut.community.dto;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import lombok.Data;

@Data
public class NoticeDTO {
    private Integer notifier;
    private String notifier_name;
    private Integer receiver;
    private Integer outerId;
    private String title;
    private Integer type;
    private Long gmt_create;
    private Integer status;
    private User user;
    private Question question;
}
