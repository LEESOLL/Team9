package com.example.market9.user.profile.dto.seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerProfileRequestDto {
    private String category;
    private String introduce;
    private String nickname;
}
