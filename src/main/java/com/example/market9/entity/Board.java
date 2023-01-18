package com.example.market9.entity;


import com.example.market9.dto.SalePostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *Product 엔티티
 *
 */
@Getter
@Entity
@NoArgsConstructor
public class Board extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Board_Id")
    private Long id;
    private String productName; //상품명
    private String title; // "~팝니다."
    private String content; //상품소개
    private Long price;
    private String status; //? 이넘으로 하는


    public Board(SalePostRequestDto salePostRequestDto) {
        this.productName = salePostRequestDto.getProductName();
        this.title = salePostRequestDto.getTitle();
        this.content = salePostRequestDto.getContent();
        this.price = salePostRequestDto.getPrice();
        this.status = salePostRequestDto.getStatus();
    }


    // update 메소드
    public void editSalePost(SalePostRequestDto salePostRequestDto){
        this.productName = salePostRequestDto.getProductName();
        this.title = salePostRequestDto.getTitle();
        this.content = salePostRequestDto.getContent();
        this.price = salePostRequestDto.getPrice();
        this.status = salePostRequestDto.getStatus();
        //id도 가져와야 하나요?
    }


}