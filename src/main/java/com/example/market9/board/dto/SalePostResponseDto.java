package com.example.market9.board.dto;


import com.example.market9.board.entity.Board;
import com.example.market9.board.entity.SaleStatusEnum;
import lombok.Getter;

@Getter
public class SalePostResponseDto {

    private String title;

    private String productName;

    private Long price;

    private SaleStatusEnum status;

    public SalePostResponseDto(Board board){
        this.title = board.getTitle();
        this.productName = board.getProductName();
        this.price = board.getPrice();
        this.status = board.getStatus();
    }

}
