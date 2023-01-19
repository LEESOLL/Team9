package com.example.market9.controller;


import com.example.market9.dto.*;
import com.example.market9.service.BoardService;
import com.example.market9.service.RequestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/products")    //CRUD
public class BoardController {

     private final BoardService boardService;

     private final RequestService requestService;

    //판매 게시글 등록
     @PostMapping("/")
     public CreateSalePostResponseDto createSalePost(@RequestBody SalePostRequestDto salePostRequestDto /* @AuthenticationPrincipal UserDetailsImpl userDetails)*/) {
        return boardService.createSalePost(salePostRequestDto);
     }

    // 판매상품조회 ( 특정 판매자의 판매 게시물들 가져오기 )
    // 개선점 : Spring Security 활용해서, user 객체 가져와서 query 해와야함.
    @GetMapping("/{sellerId}")
    public GetSalePostsResponseDto<List<GetSalePostsDto>> getSalePosts(@PathVariable Long sellerId){
         return boardService.getSalePosts(sellerId);
    }

    // 판매상품조회 ( 모든 판매상품 조회 )
    @GetMapping("/")
    public GetSalePostsResponseDto<List<GetSalePostsDto>> getAllSalePosts(){
         return boardService.getAllSalePosts();
    }



    //판매상품수정
    @PutMapping("/{productId}")
    public CreateSalePostResponseDto editSalePost(@PathVariable Long productId, @RequestBody SalePostRequestDto salePostRequestDto){
         return boardService.editSalePost(productId, salePostRequestDto);
    }
    // CreateSalePostRequestDto, CreateSalePostResponseDto -> 공동으로 사용하게 Create 빼면 좋을 것 같아요.


    //판매상품삭제 /api/products/{productId
/*    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteSalePost(@PathVariable Long productId){
         boardService.deletesalePost(productId);
        return new ResponseEntity<> ("게시글삭제 완료했습니다",HttpStatus.OK); //지니어스 ?
    }*/
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteSalePost(@PathVariable Long productId) {

    return  boardService.deleteSalePost(productId); //인증은 앞단에서..했다고 가정하니까....
    }
    //고객요청 목록 조회




    //(고객) 판매자에게 요청폼 보내기  //1번게시글에서 ..
    @PostMapping("/{productId}/request")
    public ResponseEntity<String> requestSeller(@PathVariable Long productId, @RequestBody RequestSellerDto requestSellerDto/*String username*/){

        requestService.requestSeller(productId,requestSellerDto/*,username*/);

        return  new ResponseEntity<> ("요청 완료 되었습니다", HttpStatus.OK);
    }

    @GetMapping("/{productId}/request") //게시글에 들어온 요청보기
    public RequestSellerListResponseDto getRequestSellerList(@PathVariable Long productId){

        return  requestService.getRequestSellerList(productId);
    }
    @GetMapping("/request")  //전체요청보기 ..,
    public RequestSellerListResponseDto getRequestAllSellerList(@RequestBody OnlyUserNameDto userName){  //시큐리티 적용시 바뀔사항...유저네임꺼내기 !

        return  requestService.getRequestAllSellerList(userName.getUserName());
    }
}
