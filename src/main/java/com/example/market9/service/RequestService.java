package com.example.market9.service;

import com.example.market9.requisition.dto.PurchaseRequisitionDto;
import com.example.market9.requisition.dto.PurchaseRequisitionListResponseDto;
import com.example.market9.requisition.entity.PurchaseRequisition;
import com.example.market9.user.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<PurchaseRequisition> getAllByProductId(Long boardId);

   Optional<PurchaseRequisition> deleteUserRequest(Long productId);

    //(고객)판매자에게 요청폼 보내기

    ResponseEntity<String> requestSeller(Long productId, PurchaseRequisitionDto purchaseRequisitionDto, Users users) ;


    List<PurchaseRequisitionListResponseDto> getRequestSellerList(Long productId, Users users);


    List<PurchaseRequisitionListResponseDto> getRequestAllSellerList(Users seller, Pageable pageable);



    /* RequestSellerListResponseDto getRequestSellerList(Long productId);*/
    ResponseEntity<String> purchaseConfirmation(Long requestId , Users seller);
}

