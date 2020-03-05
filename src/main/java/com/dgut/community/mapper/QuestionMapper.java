package com.dgut.community.mapper;

import com.dgut.community.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    void CreateQuestion(Question question);

    List<Question> list();

    List<Question> findAll();

    List<Question> findByName(String username);

    Question findById(@Param("id") Integer id);

    int updatepublish(Question question);
}
