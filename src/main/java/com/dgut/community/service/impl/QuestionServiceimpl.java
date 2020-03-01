package com.dgut.community.service.impl;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import com.dgut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionServiceimpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    public List<Question> list(){
        return questionMapper.list();
    }
}
