
package com.example.market9.user.dto;

import com.example.market9.user.entity.Users;
import lombok.Getter;

@Getter
public class SellerInfoResponseDto {
    private String username;
    private String nickname;


    public SellerInfoResponseDto(Users user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
