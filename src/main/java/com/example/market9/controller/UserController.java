
package com.example.market9.controller;

import com.example.market9.dto.*;
import com.example.market9.entity.Users;
import com.example.market9.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    // 1. 회원가입
    @PostMapping("/auth/signup")
    public String signUp(@RequestPart("signUpRequestDto") @Valid SignUpRequestDto signUpRequestDto, @RequestPart("file") MultipartFile file) throws IOException {
        userServiceImpl.signUp(signUpRequestDto, file);
        return "sign up success";
    }

    // 2. 로그인
    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userServiceImpl.login(loginRequestDto, response);
        return "login success";
    }

    // 3. 나의 정보 설정(프로필 변경)
    @PutMapping("/profile/{id}")
    public Long changeUserProfile(@PathVariable Long id, @RequestBody ProfileRequestDto profileRequestDto) {
        return userServiceImpl.changeUserProfile(id, profileRequestDto);
    }

    // 4. 나의 정보 조회
    @GetMapping("/profile/{id}")
    public ProfileResponseDto getMyProfile(@PathVariable Long id) {
        return userServiceImpl.getMyProfile(id);
    }


    // 5. 판매자 등록 요청 보내기
    @PostMapping("/auth/seller")
    public String applySeller(@RequestBody SellerProfileRequestDto sellerProfileRequestDto) {
        userServiceImpl.applySeller(sellerProfileRequestDto);
        return "요청 완료";
    }

}
