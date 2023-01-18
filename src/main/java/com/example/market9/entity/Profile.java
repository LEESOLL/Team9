package com.example.market9.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELLER_ID")
    private Long id;

    private String introduce;

    private String category;

    private String username;    //컨트롤러 ~



    //
    // 유저네임 어떻게 받아오냐면 ~ 컨트롤러단에서 @AuthenticationPrincipal UserDetailsImpl userDetails

    // userDetails.getUser() 이러면 ~ 유저 객체가 꺼내지고 여기서또 getUsername  즉 userDetails.getUser().getUserName


    public Profile(String introduce, String category, String username) {
        this.introduce = introduce;
        this.category = category;
        this.username = username;
    }

}