/*
package com.example.market9.controller;

import com.example.market9.dto.*;
import com.example.market9.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    // 1. 회원가입
    @PostMapping("/auth/signup")
    public HttpStatus signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        userServiceImpl.signup(signUpRequestDto);
        return HttpStatus.CREATED;
    }

    // 2. 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<SignUpResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        SignUpResponseDto data = userServiceImpl.login(loginRequestDto);
        return ResponseEntity.status(200).body(data);
    }

    // 3. 나의 정보 설정(프로필 변경)
    @PutMapping("/profile")
    public HttpStatus changeMyProfile(@Valid @RequestBody ProfileRequestDto profileRequestDto) {
        userServiceImpl.changeMyProfile(profileRequestDto);
        return HttpStatus.OK;
    }

    // 4. 나의 정보 조회
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getMyProfile(@AuthenticationPrincipal UserDetails userDetails){
        ProfileResponseDto data = userServiceImpl.getMyProfile(UserDetails.getUser().getId());
        return ResponseEntity.status(200).body(data);
    }

    // 5. 전체 판매자 목록 조회
    @GetMapping("/sellers")
    public List<SellerResponseDto> getSellerList() {
        return userServiceImpl.getSellerList();
    }

    // 6. 판매자 정보 조회
    @GetMapping("/sellers/{sellerId}")
    public ResponseEntity<ProfileResponseDto>> getSellerProfile(@PathVariable Long sellerId){
        return userServiceImpl.getSeller(sellerId);
    }



}

 */