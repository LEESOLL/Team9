package com.example.market9.dto;


import com.example.market9.entity.Board;
import com.example.market9.entity.SaleStatusEnum;
import lombok.Getter;

@Getter
public class GetSalePostsDto {

    // GetSalePostsResponseDto

    private String title;

    private String productName;

    private Long price;

    private SaleStatusEnum status;

    public GetSalePostsDto(Board board){
        this.title = board.getTitle();
        this.productName = board.getProductName();
        this.price = board.getPrice();
//        this.status = board.getStatus();
    }

}
