package com.dgut.community.mapper;

import com.dgut.community.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {

    List<Teacher> selectcatgegory();

    List<Teacher> selectBycatgegory(String category);
}
