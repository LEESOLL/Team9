package com.example.market9.dto;

import com.example.market9.entity.Board;
import lombok.Getter;

@Getter
public class CreateSalePostResponseDto {

/*    private String */

// 대박이빈다....
    private Board board;    // board 객체를 바로 반환하는 것 보다 DTO 반환이 더 나을 것 같습니다.

    // Security 사용.
//    private Long sellerId;

    
    public CreateSalePostResponseDto(Board board) {
        this.board = board;
    }
}
