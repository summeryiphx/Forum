package com.dgut.community.entity;

import lombok.Data;

@Data
public class User {
    private Integer user_id;
    private String account_id;
    private String username;
    private String password;
    private String mobile;
    private String address;
    private Integer role_type;
    private String avatar;
    private Role role;

}
