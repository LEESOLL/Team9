package com.example.market9.service;

import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.RequestSellerListResponseDto;
import com.example.market9.entity.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<UserRequest> getAllByProductId(Long boardId);

   Optional<UserRequest> deleteUserRequest(Long productId);

    //(고객)판매자에게 요청폼 보내기

    ResponseEntity<String> requestSeller(Long productId, RequestSellerDto requestSellerDto/*, String name*/) ;


    RequestSellerListResponseDto getRequestSellerList(Long productId);


    RequestSellerListResponseDto getRequestAllSellerList(String userName);



    /* RequestSellerListResponseDto getRequestSellerList(Long productId);*/
    ResponseEntity<String> purchaseConfirmation(Long requestId);
}
