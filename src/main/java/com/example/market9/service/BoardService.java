package com.example.market9.service;


import com.example.market9.dto.CreateSalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import org.springframework.http.ResponseEntity;


public interface BoardService {


    CreateSalePostResponseDto createSalePost(CreateSalePostRequestDto creatSalePostRequestDto);

             ResponseEntity<String> deleteSalePost(Long productId);

}
