package com.dgut.community.service.impl;

import com.dgut.community.entity.User;
import com.dgut.community.mapper.UserMapper;
import com.dgut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUser(String account_id, String password) {
        return  userMapper.findUser(account_id,password);
    }

    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findByUserid(id);
    }
}
