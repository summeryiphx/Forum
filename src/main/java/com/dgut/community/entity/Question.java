package com.dgut.community.entity;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String content;
    private String category;
    private String teacher_name;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
