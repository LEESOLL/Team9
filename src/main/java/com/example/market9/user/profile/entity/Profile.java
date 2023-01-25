package com.example.market9.user.profile.entity;

import com.example.market9.user.profile.dto.user.UserProfileRequestDto;
import com.example.market9.user.profile.dto.seller.SellerProfileRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long id;
    private String username;
    private String nickname;
    private String introduce;
    private String category;
    private String filename;
    private String filepath;

    public Profile(String username, String nickname, String filename, String filepath){
        this.username = username;
        this.nickname = nickname;
        this.filename = filename;
        this.filepath = filepath;
    }

    public void updateUserProfile(UserProfileRequestDto userProfileRequestDto) {
        this.nickname = userProfileRequestDto.getNickname();

    }

    public void updateSelleProfile(SellerProfileRequestDto sellerProfileRequestDto) {
        this.nickname = sellerProfileRequestDto.getNickname();
        this.introduce = sellerProfileRequestDto.getIntroduce();
        this.category = sellerProfileRequestDto.getCategory();
    }

    public void updateSellerProfile(String category, String introduce) {
        this.category = category;
        this.introduce = introduce;
    }
}