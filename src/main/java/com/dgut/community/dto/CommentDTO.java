package com.dgut.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer parent_id;
    private Integer type;
    private String content;
    private Integer secomment_id;
}
