package com.example.market9.dto;

import com.example.market9.entity.SaleStatusEnum;
import lombok.Getter;

@Getter
public class SalePostRequestDto {

    private  Long id;

    private String title;
    private String productName;
    private String content;
    private Long price;
    private SaleStatusEnum saleStatusEnum;

// ”title” : “”,
// ”productName” : “” ,
// ”content” : “”,
// ”price” : 00000,
// ”status”: “..


}