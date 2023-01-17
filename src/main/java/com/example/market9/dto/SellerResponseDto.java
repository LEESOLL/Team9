package com.example.market9.dto;

public class SellerResponseDto {
    private String name;
    private String nickname;

    public SellerResponseDto(User user) {
        this.name = user.getName();
        this.nickname = user.getNickName();
    }
}
