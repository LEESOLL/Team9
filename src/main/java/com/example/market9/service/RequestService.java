package com.example.market9.service;

import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.RequestSellerListResponseDto;
import com.example.market9.entity.UserRequest;
import com.example.market9.entity.Users;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<UserRequest> getAllByProductId(Long boardId);

   Optional<UserRequest> deleteUserRequest(Long productId);

    //(고객)판매자에게 요청폼 보내기

    ResponseEntity<String> requestSeller(Long productId, RequestSellerDto requestSellerDto, Users users) ;


    RequestSellerListResponseDto getRequestSellerList(Long productId,Users users);


    RequestSellerListResponseDto getRequestAllSellerList(Users seller, Pageable pageRequest);



    /* RequestSellerListResponseDto getRequestSellerList(Long productId);*/
    ResponseEntity<String> purchaseConfirmation(Long requestId , Users seller);
}
