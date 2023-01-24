package com.example.market9.dto;


import com.example.market9.entity.UserRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestSellerListResponseDto {

   //고객 요청한거 조회 ..
    //----버전 1. 셀러가 자기가쓴 1번 게시글 을 들어가서 해당 게시글의 요청만 조회--------//

    List<UserRequest> sellerRequestList;
    ///객체 json... 컬럼을...

    public RequestSellerListResponseDto(List<UserRequest> sellerRequestList) {
        this.sellerRequestList = sellerRequestList;
    }

    //----버전 2. 셀러가 자기가 1신발,2목도리,3패딩 게시글에대한  모든 요청 조회--------//
   /* List<List<UserRequest>> requestByPost;
    public RequestSellerListResponseDto(List<List<UserRequest>> requestByPost){

      this.requestByPost = requestByPost;
    }*/

}
