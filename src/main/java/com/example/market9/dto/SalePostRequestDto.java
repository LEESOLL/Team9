package com.example.market9.dto;

import com.example.market9.entity.SaleStatusEnum;
import lombok.Getter;

@Getter
public class SalePostRequestDto {

//    private  Long id;

    private String title;
    private String productName;
    private String content;
    private Long price;
//    private SaleStatusEnum saleStatusEnum;


    private String userName; //나중가서 시큐리티쪽에서 뽑아오면 삭제해야함 !
// ”title” : “”,
// ”productName” : “” ,
// ”content” : “”,
// ”price” : 00000,
// ”status”: “..


}