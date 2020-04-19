package com.dgut.community.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer secomment_id;
    private Integer secomment_count;
    private Integer parent_id;
    private Integer type;
    private Integer commentator;
    private String content;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private User user;
}
