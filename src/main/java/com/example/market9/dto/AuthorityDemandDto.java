package com.example.market9.dto;

import com.example.market9.entity.AuthorityRequest;
import com.example.market9.entity.PermissionStatusEnum;
import lombok.Getter;

@Getter
public class AuthorityRequestDto {

    private String username;
    private String category;
    private String introduce;
    private PermissionStatusEnum role;

    public AuthorityRequestDto(AuthorityRequest authorityRequest) {
        this.username = authorityRequest.getUsername();;
        this.category = authorityRequest.getCategory();
        this.introduce = authorityRequest.getIntroduce();
        this.role = authorityRequest.getRole();
    }
}
