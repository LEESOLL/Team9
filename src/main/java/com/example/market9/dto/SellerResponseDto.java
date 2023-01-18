
package com.example.market9.dto;

import com.example.market9.entity.Users;
import lombok.Getter;

@Getter
public class SellerResponseDto {
    private String username;
    private String nickname;


    public SellerResponseDto(Users user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
