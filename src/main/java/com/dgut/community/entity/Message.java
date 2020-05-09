package com.dgut.community.entity;

import lombok.Data;

@Data
public class Message {
    private Integer id;
    private Integer user_id;
    private String title;
    private String content;
    private Long gmtCreate;
    private User user;
}
