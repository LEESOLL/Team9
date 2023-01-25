package com.example.market9.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalePostsListResponseDto<T> {
    private int count;
    private T boards; // 리스트의 값

}
