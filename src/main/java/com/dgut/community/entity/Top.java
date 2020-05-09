package com.dgut.community.entity;

import lombok.Data;

@Data
public class Top {
    private Integer id;
    private String title;
    private String content;
    private Long gmtCreate;
}
