package com.example.market9.dto;

import com.example.market9.entity.UserRoleEnum;
import com.example.market9.entity.Users;
import lombok.Getter;

@Getter
public class UserListResponseDto {

    private String username;
    private String nickname;
    private UserRoleEnum role;


    public UserListResponseDto(Users users) {
        this.username = users.getUsername();
        this.nickname = users.getNickname();
        this.role = users.getRole();
    }
}
