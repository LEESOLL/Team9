package com.example.market9.entity;

import javax.persistence.Entity;

@Entity
public class User {

    private Long id;
    private String username;
    private String password;
    private RoleType roleType;

    private Profile profile;

    private String nickname;
    private String image;
}
