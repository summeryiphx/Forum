package com.dgut.community.entity;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String content;
    private String category;
    private String teacher;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
