package com.dgut.community.mapper;

import com.dgut.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    int insert(Comment comment);

    List<Comment> findAllcomments(Integer id);

    List<Comment> findSecondComments(Integer id);

    int insertsecomment(Comment comment);

    int incsecomment_count(Integer id);

    Comment findById(Integer id);

    int del(Integer id);
}
