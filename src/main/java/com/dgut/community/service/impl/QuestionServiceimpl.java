package com.dgut.community.service.impl;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.entity.UserLike;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserLikeMapper;
import com.dgut.community.mapper.UserMapper;
import com.dgut.community.redis.RedisKeyUtils;
import com.dgut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionServiceimpl implements QuestionService{
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserLikeMapper userLikeMapper;

    public List<Question> list(){
        return questionMapper.list();
    }

    @Override
    public List<Question> selectRelate(Question question) {
        if (StringUtils.isEmpty(question.getTag())) {
            return new ArrayList<>();
        }

        if (!question.getTag().contains(",")){
            String regexpTag = question.getTag();
            Question question1 = new Question();
            question1.setId(question.getId());
            question1.setTag(regexpTag);
            List<Question> questionList = questionMapper.selectRelate(question1);
            return questionList;
        }else {
            String[] tags = StringUtils.split(question.getTag(), ",");
            String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
            Question question1 = new Question();
            question1.setId(question.getId());
            question1.setTag(regexpTag);
            List<Question> questionList = questionMapper.selectRelate(question1);
            return questionList;
        }


    }

    @Override
    public Question findById(Integer qusetion_id) {
        return questionMapper.findById(qusetion_id);
    }

    @Override
    public int updateLikeNum(Question question) {
        return questionMapper.updateLikeNum(question);
    }


}
