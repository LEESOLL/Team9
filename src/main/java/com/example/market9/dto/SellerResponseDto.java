package com.example.market9.dto;

import com.example.market9.entity.User;

public class SellerResponseDto {
    private String name;
    private String nickname;

    public SellerResponseDto(User user) {
        this.name = user.getName();
        this.nickname = user.getNickName();
    }
}
