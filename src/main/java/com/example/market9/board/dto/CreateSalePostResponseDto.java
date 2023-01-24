package com.example.market9.board.dto;

import com.example.market9.board.entity.Board;
import lombok.Getter;

@Getter
public class CreateSalePostResponseDto {
    private String title;
    private Long userId;
    
    public CreateSalePostResponseDto(Board board) {
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
    }

}
