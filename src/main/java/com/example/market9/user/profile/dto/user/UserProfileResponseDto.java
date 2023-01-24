package com.example.market9.user.profile.dto.user;

import com.example.market9.user.profile.entity.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserProfileResponseDto {

    private String username;
    private String nickname;
    private String filename;

    public UserProfileResponseDto(Profile profile) {
        this.username = profile.getUsername();
        this.nickname = profile.getNickname();
        this.filename = profile.getFilename();
    }

}
