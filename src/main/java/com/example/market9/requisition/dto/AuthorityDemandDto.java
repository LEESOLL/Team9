package com.example.market9.requisition.dto;

import com.example.market9.requisition.entity.AuthorityDemand;
import com.example.market9.requisition.entity.PermissionStatusEnum;
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
