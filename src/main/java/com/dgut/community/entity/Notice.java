package com.dgut.community.entity;

import lombok.Data;

@Data
public class Notice {
    private Integer id;
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
