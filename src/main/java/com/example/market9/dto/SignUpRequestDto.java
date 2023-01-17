package com.example.market9.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {


    private String username;
    private String password;
    private String nickname;
//    private String image;
    // 기본이미지 ? 를 설정해놔야?

}
