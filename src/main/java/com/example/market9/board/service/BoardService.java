package com.example.market9.board.service;


import com.example.market9.board.dto.CreateSalePostResponseDto;
import com.example.market9.board.dto.SalePostResponseDto;
import com.example.market9.board.dto.SalePostRequestDto;
import com.example.market9.board.dto.SalePostsListResponseDto;
import com.example.market9.user.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BoardService {


    CreateSalePostResponseDto createSalePost(SalePostRequestDto creatSalePostRequestDto, Users users);


    SalePostsListResponseDto<List<SalePostResponseDto>> getSalePosts(Long sellerId, Pageable pageRequest);

    SalePostsListResponseDto<List<SalePostResponseDto>> getAllSalePosts(Pageable pageRequest, String search);


    ResponseEntity<String> deleteSalePost(Long productId,Users users);


    CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto,Users users);



    /*void requestSeller(Long productId, RequestSellerDto requestSellerDto*//*,String name*//*);*/

 /*   RequestSellerListResponseDto getRequestSellerList(Long productId);
    RequestSellerListResponseDto getRequestAllSellerList(String userName);*/

}

