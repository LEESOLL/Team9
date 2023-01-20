package com.example.market9.controller;


import com.example.market9.dto.*;
import com.example.market9.service.BoardService;
import com.example.market9.service.RequestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public GetSalePostsResponseDto<List<GetSalePostsDto>> getSalePosts(@PathVariable Long sellerId) {
         return boardService.getSalePosts(sellerId);
    }

    // 판매상품조회 ( 모든 판매상품 조회 )
    @GetMapping("/")
    public GetSalePostsResponseDto<List<GetSalePostsDto>> getAllSalePosts(
            @RequestParam(value = "page",required = false,defaultValue ="1") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
            @RequestParam(value = "isAsc",required = false,defaultValue = "false")Boolean isAsc,
            @RequestParam(value = "sortBy",required = false,defaultValue = "createdAt")String sortBy
    ){

        Sort.Direction direction = isAsc ? Sort.Direction.ASC:Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageRequest = PageRequest.of(page-1,size,sort);

         return boardService.getAllSalePosts(pageRequest);
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
}
