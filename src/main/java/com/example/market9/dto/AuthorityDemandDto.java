package com.example.market9.dto;

import com.example.market9.entity.AuthorityDemand;
import com.example.market9.entity.PermissionStatusEnum;
import lombok.Getter;

@Getter
public class AuthorityDemandDto {

    private String username;
    private String category;
    private String introduce;
    private PermissionStatusEnum role;

    public AuthorityDemandDto(AuthorityDemand authorityDemand) {
        this.username = authorityDemand.getUsername();;
        this.category = authorityDemand.getCategory();
        this.introduce = authorityDemand.getIntroduce();
        this.role = authorityDemand.getRole();
    }

}
