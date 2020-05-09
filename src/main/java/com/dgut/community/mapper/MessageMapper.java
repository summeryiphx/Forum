package com.dgut.community.mapper;

import com.dgut.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {
    int insert(Message message);

    List<Message> findAll();

    int del(Integer id);
}
