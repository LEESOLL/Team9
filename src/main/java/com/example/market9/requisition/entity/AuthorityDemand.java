package com.example.market9.requisition.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AuthorityDemand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //뭔차이가 있다고 들었는데 ..기억이 ..
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


    public AuthorityDemand(String username, String category, String introduce) {
        this.username = username;
        this.category = category;
        this.introduce = introduce;
        this.role = PermissionStatusEnum.WAITING;

    }
    public void changePermission(PermissionStatusEnum role) {
        this.role = role;
    }
    public void updateAuthorityDemand(String username, String category, String introduce) {
        this.username = username;
        this.category = category;
        this.introduce = introduce;
        this.role = PermissionStatusEnum.WAITING;
    }

}

 