package com.example.market9.service;


import com.example.market9.dto.*;
import com.example.market9.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BoardService {


    CreateSalePostResponseDto createSalePost(SalePostRequestDto creatSalePostRequestDto, Users users);


    GetSalePostsResponseDto<List<GetSalePostsDto>> getSalePosts(Long sellerId,Pageable pageRequest);

    GetSalePostsResponseDto<List<GetSalePostsDto>> getAllSalePosts( Pageable pageRequest, String search);


    ResponseEntity<String> deleteSalePost(Long productId,Users users);


    CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto,Users users);



    /*void requestSeller(Long productId, RequestSellerDto requestSellerDto*//*,String name*//*);*/

 /*   RequestSellerListResponseDto getRequestSellerList(Long productId);
    RequestSellerListResponseDto getRequestAllSellerList(String userName);*/

}
