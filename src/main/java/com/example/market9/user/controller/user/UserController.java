
package com.example.market9.user.controller.user;

import com.example.market9.security.UserDetailsImpl;
import com.example.market9.user.dto.LoginRequestDto;
import com.example.market9.user.dto.SignUpRequestDto;
import com.example.market9.user.profile.dto.seller.SellerProfileRequestDto;
import com.example.market9.user.profile.dto.user.UserProfileRequestDto;
import com.example.market9.user.profile.dto.user.UserProfileResponseDto;
import com.example.market9.user.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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

    //3. 나의 정보 설정(프로필 변경)
    @PutMapping("/profile")
    public Long changeUserProfile(@RequestBody UserProfileRequestDto userProfileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userServiceImpl.changeUserProfile(userProfileRequestDto, userDetails.getUser());
    }

    // 4. 나의 정보 조회
    @GetMapping("/profile")
    public UserProfileResponseDto getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userServiceImpl.getMyProfile(userDetails.getUser());
    }


    // 5. 판매자 등록 요청 보내기
    @PostMapping("/auth/seller")
    public String applySeller(@RequestBody SellerProfileRequestDto sellerProfileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userServiceImpl.applySeller(sellerProfileRequestDto, userDetails.getUser());
        return "요청 완료";
    }

}
