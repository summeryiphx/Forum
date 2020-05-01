package com.dgut.community.mapper;

import com.dgut.community.entity.QuestionCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionCollectMapper {

    int collect(@Param("user_id") Integer user_id, @Param("question_id") Integer id);

    int delcollect(@Param("user_id") Integer user_id, @Param("question_id") Integer id);

    QuestionCollect findByUserIDAndQID(@Param("user_id") Integer user_id, @Param("question_id") Integer id);

    List<QuestionCollect> findByUserID(@Param("user_id") Integer user_id);
}
