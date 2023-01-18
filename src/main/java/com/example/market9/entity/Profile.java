package com.example.market9.entity;

import com.example.market9.dto.ProfileRequestDto;
import com.example.market9.dto.SellerProfileRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Profile {
    // 모든 사용자(유저 + 판매자)의 프로필을 의미하는 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long id;
    private String username;
    private String nickname;

    private String introduce;

    private String category;

    private String image;



    //
    // 유저네임 어떻게 받아오냐면 ~ 컨트롤러단에서 @AuthenticationPrincipal UserDetailsImpl userDetails

    // userDetails.getUser() 이러면 ~ 유저 객체가 꺼내지고 여기서또 getUsername  즉 userDetails.getUser().getUserName


    //회원가입과 동시에 생성되는 프로필
    public Profile(String username, String nickname, String image){
        this.username = username;
        this.nickname = nickname;
        this.image = image;
    }

    //seller 요청시 작성할 프로필
    public Profile(String username, String nickname, String introduce, String category, String image) {
        this.username = username;
        this.nickname = nickname;
        this.introduce = introduce;
        this.category = category;
        this.image = image;
    }

    //유저 프로필 변경, username 은 변경 불가
    public void updateUserProfile(ProfileRequestDto profileRequestDto) {
        this.nickname = profileRequestDto.getNickname();
        this.image = profileRequestDto.getImage();
    }

    //판매자 프로필 변경
    public void updateSelleProfile(SellerProfileRequestDto sellerProfileRequestDto) {
        this.nickname = sellerProfileRequestDto.getNickname();
        this.image = sellerProfileRequestDto.getImage();
        this.introduce = sellerProfileRequestDto.getIntroduce();
        this.category = sellerProfileRequestDto.getCategory();
    }
}