package com.example.market9.dto;

import com.example.market9.entity.Board;
import lombok.Getter;

@Getter
public class CreateSalePostResponseDto {

/*    private String */

// 대박이빈다....
    private  Board board;

    
    public CreateSalePostResponseDto(Board board) {
        this.board = board;
    }
}
