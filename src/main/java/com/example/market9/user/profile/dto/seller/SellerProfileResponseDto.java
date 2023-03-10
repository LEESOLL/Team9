package com.example.market9.user.profile.dto.seller;

import com.example.market9.user.profile.entity.Profile;
import com.example.market9.user.entity.Users;
import lombok.Getter;

@Getter
public class SellerProfileResponseDto {

    private String username;
    private String nickname;
    private String filename;  // url입니다 !
    private String introduce;
    private String category;




    public SellerProfileResponseDto(Users user, Profile profile) {  //포스 트 조회 반환 패대글 ...
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.filename = user.getFilename();
        this.introduce = profile.getIntroduce();
        this.category = profile.getCategory();

        //10만 동시 .....성 굴러만가자 ..일단 .....
    }//

    public SellerProfileResponseDto(Profile profile) {
        this.username = profile.getUsername();
        this.nickname = profile.getNickname();
        this.introduce = profile.getIntroduce();
        this.category = profile.getCategory();
        this.filename = profile.getFilename();
    }
}
