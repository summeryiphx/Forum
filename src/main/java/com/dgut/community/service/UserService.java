package com.dgut.community.service;

import com.dgut.community.entity.User;

import java.util.List;

public interface UserService {
    User findUser(String account_id, String password);

    User findByName(String username);

    User findById(Integer id);
}
