package com.example.market9.entity;

import com.example.market9.dto.ProfileRequestDto;
import com.example.market9.dto.SellerProfileRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users extends TimeStamp{  // User가  JPA 예약어로 등록되어있어서  오류 .. .

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
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @Column
    private String nickname;

    @Column
    private  String filename;

    @Column
    private String filepath;

    @Column
    private String category;

    @Column
    private String introduce;



    public Users(String username, String password, String nickname, String filename, String filepath, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.filename = filename;
        this.filepath = filepath;
        this.role = role;
    }

    public void changeRole(UserRoleEnum role) {
        this.role = role;
    }

    public void updateUserProfile(ProfileRequestDto profileRequestDto) {
        this.nickname = profileRequestDto.getNickname();
    }

    public void updateSellerProfile(SellerProfileRequestDto sellerProfileRequestDto) {
        this.nickname = sellerProfileRequestDto.getNickname();
        this.category = sellerProfileRequestDto.getCategory();
        this.introduce = sellerProfileRequestDto.getIntroduce();
    }

    public void updateSellerProfile(String category, String introduce) {
        this.category = category;
        this.introduce = introduce;
    }

}
