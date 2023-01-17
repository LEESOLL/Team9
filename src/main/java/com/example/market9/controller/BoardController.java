package com.example.market9.controller;


import com.example.market9.dto.CreateSalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import com.example.market9.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/products")    //CRUD
public class BoardController {

     private final BoardService boardService;

    //판매 게시글 등록
     @PostMapping("/")
     public CreateSalePostResponseDto createSalePost(@RequestBody CreateSalePostRequestDto createSalePostRequestDto /* @AuthenticationPrincipal UserDetailsImpl userDetails)*/) {
        return boardService.createSalePost(createSalePostRequestDto);
     }

    //판매상품조회

    //판매상품수정

    //판매상품삭제

    //고객요청 목록 조회





}
