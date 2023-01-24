package com.example.market9.user.controller.seller;

import com.example.market9.user.profile.dto.seller.SellerProfileRequestDto;
import com.example.market9.user.profile.dto.seller.SellerProfileResponseDto;
import com.example.market9.user.dto.SellerInfoResponseDto;
import com.example.market9.security.UserDetailsImpl;
import com.example.market9.user.service.seller.SellerServiceImpl;
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

    // 1. 전체 판매자 목록 조회
    @GetMapping("/sellers")
    public List<SellerInfoResponseDto> getSellerList(
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
    @PutMapping("/sellers")
    public Long changeSellerProfile(@RequestBody SellerProfileRequestDto sellerProfileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return sellerServiceImpl.changeSellerProfile(sellerProfileRequestDto, userDetails.getUser());
    }
}
