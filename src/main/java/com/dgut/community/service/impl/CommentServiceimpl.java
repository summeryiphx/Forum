package com.dgut.community.service.impl;

import com.dgut.community.entity.Comment;
import com.dgut.community.mapper.CommentMapper;
import com.dgut.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceimpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public int insert(Comment comment) {
        return commentMapper.insert(comment);
    }
}
