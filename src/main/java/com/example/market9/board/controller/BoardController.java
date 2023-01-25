package com.example.market9.board.controller;


import com.example.market9.board.dto.CreateSalePostResponseDto;
import com.example.market9.board.dto.SalePostResponseDto;
import com.example.market9.board.dto.SalePostRequestDto;
import com.example.market9.board.dto.SalePostsListResponseDto;
import com.example.market9.security.UserDetailsImpl;
import com.example.market9.board.service.BoardService;
import com.example.market9.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/products")    //CRUD
public class BoardController {

     private final BoardService boardService;

     private final RequestService requestService;


    //판매 게시글 등록
     @PostMapping("/post")  //셀러만...
     public CreateSalePostResponseDto createSalePost(@RequestBody SalePostRequestDto salePostRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
         return boardService.createSalePost(salePostRequestDto,userDetails.getUser());
     }

    // 판매상품조회 ( 특정 판매자의 판매 게시물들 가져오기 )
    // 개선점 : Spring Security 활용해서, user 객체 가져와서 query 해와야함. 넵 !
    // 그러면 url이 겹치니까 .. PathBariable 삭제 하고 .. /profile
    //게시글은 게시글 번호 ...
    // 셀러아이디를 ?   // 판매자만.. 자기가 쓴글 보는거 .. !
    @GetMapping("/sellerPost")   //시큐리티에서 ... 인증가 인가를 진짜 잘 구분했다면 .
    public SalePostsListResponseDto<List<SalePostResponseDto>> getSalePosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page",required = false,defaultValue ="1") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
            @RequestParam(value = "isAsc",required = false,defaultValue = "false")Boolean isAsc,
            @RequestParam(value = "sortBy",required = false,defaultValue = "createdAt")String sortBy
    ) {
        Pageable pageRequest = getPageable(page, size, isAsc, sortBy);

        Long id = userDetails.getUser().getId();

        return boardService.getSalePosts(id,pageRequest);
    }

    // 판매상품조회 ( 모든 판매상품 조회 ) // ?고객+셀러+어드민
    @GetMapping("/")
    public SalePostsListResponseDto<List<SalePostResponseDto>> getAllSalePosts(
            @RequestParam(value = "page",required = false,defaultValue ="1") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
            @RequestParam(value = "isAsc",required = false,defaultValue = "false")Boolean isAsc,
            @RequestParam(value = "sortBy",required = false,defaultValue = "createdAt")String sortBy,
            @RequestParam(value = "search",required = false,defaultValue = "") String search
    ){

        Pageable pageRequest = getPageable(page, size, isAsc, sortBy);

        return boardService.getAllSalePosts(pageRequest,search);
    }

    private static Pageable getPageable(Integer page, Integer size, Boolean isAsc, String sortBy) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC:Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        if (page<0){
            page=1;
        }
        Pageable pageRequest = PageRequest.of(page -1, size,sort);
        return pageRequest;
    }

    //판매상품수정
    @PutMapping("/{productId}")
    public CreateSalePostResponseDto editSalePost(@PathVariable Long productId, @RequestBody SalePostRequestDto salePostRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
         return boardService.editSalePost(productId, salePostRequestDto,userDetails.getUser());
    }
    // CreateSalePostRequestDto, CreateSalePostResponseDto -> 공동으로 사용하게 Create 빼면 좋을 것 같아요.


    //판매상품삭제 /api/products/{productId
/*    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteSalePost(@PathVariable Long productId){
         boardService.deletesalePost(productId);
        return new ResponseEntity<> ("게시글삭제 완료했습니다",HttpStatus.OK); //지니어스 ?
    }*/

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteSalePost(@PathVariable Long productId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return  boardService.deleteSalePost(productId,userDetails.getUser()); //인증은 앞단에서..했다고 가정하니까....
    }
}

