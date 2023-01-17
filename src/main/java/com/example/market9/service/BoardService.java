package com.example.market9.service;


import com.example.market9.dto.CreateSalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;


public interface BoardService {


    public CreateSalePostResponseDto createSalePost(CreateSalePostRequestDto creatSalePostRequestDto);

}
