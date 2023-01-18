package com.example.market9.dto;


import lombok.Getter;

@Getter
public class RequestSellerDto {

    private String requestContent;

    private String userName; //임시방편임 !! 스프링 시큐리티 적용하게 된다면 없어질 내용 !
}
