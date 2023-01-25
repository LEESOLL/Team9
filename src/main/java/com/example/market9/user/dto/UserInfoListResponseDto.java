package com.example.market9.user.dto;

import com.example.market9.user.entity.UserRoleEnum;
import com.example.market9.user.entity.Users;
import lombok.Getter;

@Getter
public class UserInfoListResponseDto {

    private String username;
    private String nickname;
    private UserRoleEnum role;


    public UserInfoListResponseDto(Users users) {
        this.username = users.getUsername();
        this.nickname = users.getNickname();
        this.role = users.getRole();
    }
}
