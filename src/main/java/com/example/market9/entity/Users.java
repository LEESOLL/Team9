package com.example.market9.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Users {  //     User가  JPA 예약어로 등록되어있어서  오류 .. .

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

   /* @Column
    private Profile profile;*//*  지워야한다 ..! 이유는 엔티티가 엔티를 가지고있는거니까 ! //?*//*
    *//*유저엔티티가 프로필엔티를 연관 .......아예 안들고있던가 .... */
    @Column
    private String nickname;


    @Column
    private String images;

    public Users( String username, String password, RoleType roleType, String nickname, String image) {
        this.username = username;
        this.password = password;
        this.roleType = roleType;
        this.nickname = nickname;
        this.images = image;
    }
}
