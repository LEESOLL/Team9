package com.example.market9.requisition.dto;

import lombok.Getter;

@Getter
public class PurchaseRequisitionListResponseDto {
    String requestContent ;
    String userName ;
    Long postId ;

    public PurchaseRequisitionListResponseDto(String requestContent, String userName, Long postId) {
        this.requestContent = requestContent;
        this.userName = userName;
        this.postId = postId;
    }
}
