package com.example.market9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetSalePostsResponseDto<T> {
    private int count;
    private T boards; // 리스트의 값

}

/*

response 예시

{
    count : 3,
    boards : [
        {
            "title"
            . . .
        },
        {

        },
    ]

}


 */
