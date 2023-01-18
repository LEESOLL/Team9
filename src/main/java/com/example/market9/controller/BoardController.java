package com.example.market9.controller;


import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.SalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import com.example.market9.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/products")    //CRUD
public class BoardController {

     private final BoardService boardService;

    //판매 게시글 등록
     @PostMapping("/")
     public CreateSalePostResponseDto createSalePost(@RequestBody SalePostRequestDto salePostRequestDto /* @AuthenticationPrincipal UserDetailsImpl userDetails)*/) {

     return boardService.createSalePost(salePostRequestDto);
     }

    //판매상품조회

    //판매상품수정
    @PutMapping("/{productId}")
    public CreateSalePostResponseDto updateSalePost(@PathVariable Long productId, @RequestBody SalePostRequestDto salePostRequestDto){
         return boardService.updateSalePost(productId, salePostRequestDto);
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




    //(고객) 판매자에게 요청폼 보내기
    @PostMapping("/{productId}/request")
    public ResponseEntity<String> requestSeller(@PathVariable Long productId, @RequestBody RequestSellerDto requestSellerDto/*String username*/){

        boardService.requestSeller(productId,requestSellerDto/*,username*/);

        return  new ResponseEntity<> ("요청 완료 되었습니다", HttpStatus.OK);
    }
}
