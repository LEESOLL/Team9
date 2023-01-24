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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Board_Id")
    private Long id;
    private String productName; //상품명
    private String title; // "~팝니다."
    private String content; //상품소개
    private Long price;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private SaleStatusEnum status; //? 이넘으로 하는으로 하는

/*    private String userName;*/


    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;





    public Board(SalePostRequestDto salePostRequestDto, SaleStatusEnum status ,Users users) {
        this.productName = salePostRequestDto.getProductName();
        this.title = salePostRequestDto.getTitle();
        this.content = salePostRequestDto.getContent();
        this.price = salePostRequestDto.getPrice();
        this.status = status;
        //왜 Dto를 여기서 get하면 안되는지 아는가?
        //?

        //-----------userName은 시큐리티에서 받아오는걸로 바꾸궈야함------------//
        /*this.userName =salePostRequestDto.getUserName();*/
        this.user = users;
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

    public void soldOut(SaleStatusEnum status){
        this.status = status;
    }


}