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

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private SaleStatusEnum status; //? 이넘으로 하는으로 하는

    private String userName;







    public Board(SalePostRequestDto salePostRequestDto, SaleStatusEnum status) {
        this.id = salePostRequestDto.getId();
        this.productName = salePostRequestDto.getProductName();
        this.title = salePostRequestDto.getTitle();
        this.content = salePostRequestDto.getContent();
        this.price = salePostRequestDto.getPrice();
        this.status = status;

        //-----------userName은 시큐리티에서 받아오는걸로 바꾸궈야함------------//
        this.userName =salePostRequestDto.getUserName();
        //-----------userName은 시큐리티에서 받아오는걸로 바꾸궈야함------------//

    }


    // update 메소드
    public void editSalePost(Long id, SalePostRequestDto salePostRequestDto){
        this.productName = salePostRequestDto.getProductName();
        this.title = salePostRequestDto.getTitle();
        this.content = salePostRequestDto.getContent();
        this.price = salePostRequestDto.getPrice();
        //this.status = salePostRequestDto.getSaleStatusEnum();
    }


}