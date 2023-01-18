package com.example.market9.service;


import com.example.market9.dto.SalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import org.springframework.http.ResponseEntity;


public interface BoardService {


    CreateSalePostResponseDto createSalePost(SalePostRequestDto creatSalePostRequestDto);

             ResponseEntity<String> deleteSalePost(Long productId);

    public CreateSalePostResponseDto updateSalePost(Long id, SalePostRequestDto salePostRequestDto);

}
