package com.example.market9.entity;

public class UserRequest {
    private Long id; //요청id
    private String requestContent;//

    private Long userId; /// path 유저id만 가지고오면 .>장점 @OneToMany피할 수 있습니다.... //유저 id 유저 객체 어떤게 좋을까요.....?!
}
