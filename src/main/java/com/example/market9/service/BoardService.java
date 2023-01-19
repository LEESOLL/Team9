package com.example.market9.service;


import com.example.market9.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BoardService {


    CreateSalePostResponseDto createSalePost(SalePostRequestDto creatSalePostRequestDto);


    GetSalePostsResponseDto<List<GetSalePostsDto>> getSalePosts(Long sellerId);

    GetSalePostsResponseDto<List<GetSalePostsDto>> getAllSalePosts();


    ResponseEntity<String> deleteSalePost(Long productId);


    CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto);



    /*void requestSeller(Long productId, RequestSellerDto requestSellerDto*//*,String name*//*);*/

 /*   RequestSellerListResponseDto getRequestSellerList(Long productId);
    RequestSellerListResponseDto getRequestAllSellerList(String userName);*/

}
