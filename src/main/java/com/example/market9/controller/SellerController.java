package com.example.market9.controller;

import com.example.market9.dto.InfoRequestDTo;
import com.example.market9.dto.SellerProfileRequestDto;
import com.example.market9.dto.SellerProfileResponseDto;
import com.example.market9.dto.SellerResponseDto;
import com.example.market9.security.UserDetailsImpl;
import com.example.market9.service.SellerService;
import com.example.market9.service.SellerServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SellerController {

    private final SellerServiceImpl sellerServiceImpl;

    // 1. 전체 판매자 목록 조회   //이거 어드민계정 기능 같은데 왜 ... 요기 있는지 살짝 의문..을 남겨봅니다...
    @GetMapping("/sellers")
    public List<SellerResponseDto> getSellerList(
            @RequestParam(value = "page",required = false,defaultValue ="1") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
            @RequestParam(value = "isAsc",required = false,defaultValue = "false")Boolean isAsc,
            @RequestParam(value = "sortBy",required = false,defaultValue = "createdAt")String sortBy
    ) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC:Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageRequest = PageRequest.of(page-1,size,sort);

        return sellerServiceImpl.getSellerList(pageRequest);
    }

    // 2. 판매자 정보 조회
    @GetMapping("/sellers/{id}")
    public SellerProfileResponseDto getSellerProfile(@PathVariable Long id) {
        return sellerServiceImpl.getSellerProfile(id);
    }

    // 3. 판매자 자신의 프로필 변경
    @PutMapping("/sellers/{id}")
    public Long changeSellerProfile(@PathVariable Long id, @RequestBody SellerProfileRequestDto sellerProfileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return sellerServiceImpl.changeSellerProfile(sellerProfileRequestDto, userDetails.getUser());
    }

}
