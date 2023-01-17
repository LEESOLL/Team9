package com.example.market9.dto;

import lombok.Getter;

@Getter
public class CreateSalePostRequestDto {


    private String title;
    private String productName;
    private String content;
    private Long price;
    private String status;

// ”title” : “”,
// ”productName” : “” ,
// ”content” : “”,
// ”price” : 00000,
// ”status”: “”

}