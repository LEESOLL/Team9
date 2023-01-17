package com.example.market9.entity;


import com.example.market9.dto.CreateSalePostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    public Board(CreateSalePostRequestDto createSalePostRequestDto) {
        this.productName = createSalePostRequestDto.getProductName();
        this.title = createSalePostRequestDto.getTitle();
        this.content = createSalePostRequestDto.getContent();
        this.price = createSalePostRequestDto.getPrice();
        this.status = createSalePostRequestDto.getStatus();
    }
}