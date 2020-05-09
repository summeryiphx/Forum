package com.dgut.community.entity;

import lombok.Data;

@Data
public class User {
    private Integer user_id;
    private String account_id;
    private String username;
    private String name;
    private String password;
    private String mobile;
    private String address;
    private Integer role_type;
    private String avatar;
    private String sex;
    private String grade;
    private String profession;
    private String college;
    private String category;
    private String mail;
    private Role role;

}
