package com.example.market9.user.service.user;

import com.example.market9.user.dto.LoginRequestDto;
import com.example.market9.user.dto.SignUpRequestDto;
import com.example.market9.user.entity.Users;
import com.example.market9.user.profile.dto.seller.SellerProfileRequestDto;
import com.example.market9.user.profile.dto.user.UserProfileRequestDto;
import com.example.market9.user.profile.dto.user.UserProfileResponseDto;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
//    void signUp(SignUpRequestDto signUpRequestDto, MultipartFile file);
    void login(LoginRequestDto loginRequestDto, HttpServletResponse response);
    void duplicateCheckByUsername(String username);
    void checkByAdminPassword(SignUpRequestDto signUpRequestDto);
    Long changeUserProfile(UserProfileRequestDto userProfileRequestDto, Users user);
    UserProfileResponseDto getMyProfile(Users user);
    String applySeller(SellerProfileRequestDto sellerProfileRequestDto, Users user);
    void checkAuthority(String username);
    void checkDemand(String username);
}
