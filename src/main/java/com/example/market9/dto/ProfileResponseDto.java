package com.example.market9.dto;

import com.example.market9.entity.Profile;
import com.example.market9.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    private String username;
    private String nickname;
    private String image;

    public ProfileResponseDto(Profile profile) {
        this.username = profile.getUsername();
        this.nickname = profile.getNickname();
        this.image = profile.getImage();
    }

}
