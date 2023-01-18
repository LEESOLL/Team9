package com.example.market9.dto;

import com.example.market9.entity.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerProfileRequestDto {
    private String username;
    private String nickname;
    private String image;
    private String category;
    private String introduce;
}
