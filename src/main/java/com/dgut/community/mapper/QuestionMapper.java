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

    Question findById(Integer id);

    int updatepublish(Question question);

    int incViewcount(Integer id);

    int incCommentcount(Integer id);

    List<Question> selectRelate(Question question);

    //搜索有空格
    List<Question> search2(String search);

    //搜索没有空格
    List<Question> search1(String search);

    int del(Integer id);

    int reduceCommentcount(Integer id);

    String findTag(Integer id);

    int updateLikeNum(Question question);
}
