package com.example.market9.board.dto;

import lombok.Getter;

@Getter
public class SalePostRequestDto {

    private String title;
    private String productName;
    private String content;
    private Long price;

}