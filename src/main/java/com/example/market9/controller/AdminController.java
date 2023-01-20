package com.example.market9.controller;


import com.example.market9.dto.AuthorityDemandDto;
import com.example.market9.dto.UserListResponseDto;
import com.example.market9.entity.AuthorityDemand;
import com.example.market9.service.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    // 판매자 권한 요청 수락
    @PutMapping("/auth/seller/accept/{id}")
    public Long acceptAuthorityDemand(@PathVariable Long id){
        return adminServiceImpl.acceptAuthorityDemand(id);
    }

    // 판매자 권한 요청 거절
    @PutMapping("/auth/seller/refuse/{id}")
    public Long refuseAuthorityDemand(@PathVariable Long id){
        return adminServiceImpl.refuseAuthorityDemand(id);
    }

    // 전체 유저 정보 조회
    @GetMapping("/users")
    public List<UserListResponseDto> getUserList(
            @RequestParam(value = "page",required = false,defaultValue ="1") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
            @RequestParam(value = "isAsc",required = false,defaultValue = "false")Boolean isAsc,
            @RequestParam(value = "sortBy",required = false,defaultValue = "createdAt")String sortBy
    ) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC:Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageRequest = PageRequest.of(page-1,size,sort);

        return adminServiceImpl.getUserList(pageRequest);
    }

    // 판매자 등록 요청목록 조회
    @GetMapping("/auth/seller")
    public List<AuthorityDemandDto> getAuthorityDemandList() {
        return adminServiceImpl.getAuthorityDemandList();
    }
}
