package com.example.market9.entity;

import com.example.market9.dto.ProfileRequestDto;
import com.example.market9.dto.SellerProfileRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public void updateUserProfile(ProfileRequestDto profileRequestDto) {
        this.nickname = profileRequestDto.getNickname();

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