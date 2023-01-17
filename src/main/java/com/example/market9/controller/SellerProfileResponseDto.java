package com.example.market9.controller;

import com.example.market9.entity.Profile;
import com.example.market9.entity.Users;
import lombok.Getter;

@Getter
public class SellerProfileResponseDto {

    private String username;
    private String nickname;
    private String image;  // url입니다 !
    private String introduce;
    private String category;




    public SellerProfileResponseDto(Users user, Profile profile) {  //포스 트 조회 반환 패대글 ...
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.image = user.getImages();

        this.introduce = profile.getIntroduce();
        this.category = profile.getCategory();

        //10만 동시 .....성 굴러만가자 ..일단 .....
    }//

/*

 JSON...
”username”:
”nickname;”
”image”;*/
}
