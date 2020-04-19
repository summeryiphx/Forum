package com.dgut.community.service;

import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;

import java.util.List;

public interface QuestionService {
    List<Question> list();
    List<Question> selectRelate(Question question);

    Question findById(Integer qusetion_id);

    int updateLikeNum(Question question);

}
