package com.dgut.community.mapper;

import com.dgut.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User findUser(@Param("account_id") String account_id, @Param("password") String password);

    User findByName(@Param("username") String username);

    User findByUserid(@Param("user_id") Integer user_id);

    int updateAvatar(@Param("user_id") Integer user_id,@Param("avatar") String avatar);
}
