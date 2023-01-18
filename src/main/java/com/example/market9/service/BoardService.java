package com.example.market9.service;


import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.SalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import org.springframework.http.ResponseEntity;


public interface BoardService {


    CreateSalePostResponseDto createSalePost(SalePostRequestDto creatSalePostRequestDto);

             ResponseEntity<String> deleteSalePost(Long productId);

    CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto);

     void requestSeller(Long productId, RequestSellerDto requestSellerDto/*,String name*/);
}
