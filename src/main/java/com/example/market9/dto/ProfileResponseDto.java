package com.example.market9.dto;

import com.example.market9.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    private String username;
    private String nickname;
    private String images;

    public ProfileResponseDto(Users user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.images = user.getImages();
    }
    public static ProfileResponseDto of(Users user) {
        return new ProfileResponseDto(user);
    }

}
