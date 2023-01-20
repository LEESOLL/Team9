package com.example.market9.dto;

import com.example.market9.entity.Board;
import lombok.Getter;

@Getter
public class CreateSalePostResponseDto {

/*    private String */

// 대박이빈다....
   /* private Board board;*/

    private String title;
    private Long userId;

    // Security 사용.
/*    private Long sellerId;*/

    
    public CreateSalePostResponseDto(Board board) {
        this.userId = board.getUser().getId();
        this.title = board.getTitle();

    /*    this.board = board;*/
    }

}
