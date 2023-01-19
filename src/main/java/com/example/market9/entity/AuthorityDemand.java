package com.example.market9.entity;

import com.example.market9.dto.SellerProfileRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AuthorityRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    private Profile profile;
    @Column
    private String username;
    @Column
    private String category;
    @Column
    private String introduce;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PermissionStatusEnum role;


    public AuthorityRequest(String username, String category, String introduce) {
        this.username = username;
        this.category = category;
        this.introduce = introduce;
        this.role = PermissionStatusEnum.WAITING;

    }

}

 