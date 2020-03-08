package com.dgut.community.mapper;

import com.dgut.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper {

    int insert(Comment comment);
}
