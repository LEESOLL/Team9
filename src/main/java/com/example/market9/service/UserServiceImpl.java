package com.example.market9.service;

import com.example.market9.dto.*;
import com.example.market9.entity.Profile;
import com.example.market9.entity.RoleType;
import com.example.market9.entity.User;
import com.example.market9.repository.ProfileRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    @Override // 회원가입
    public void signUp(SignUpRequestDto signUpRequestDto) {
        checkByUserDuplicated(SignUpRequestDto.getUsername());
        User user = SignUpRequestDto.toEntity(passwordEncoder.encode(SignUpRequestDto.getPassword()));
        userRepository.save(user);
    }

    @Override // 유저 중복 체크
    public void checkByUserDuplicated(String username) throws RuntimeException {
        if (UserRepository.findByUsername(username).isPresent())
            throw new CustomException(ExceptionStatus.USER_IS_EXIST);
    }

    @Override
    @Transactional // 로그인
    public SignUpResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(IllegalArgumentException::new);
        checkByUserPassword(loginRequestDto, user);
        user.updateRefreshToken(jwtTokenProvider.createRefreshToken());
        return SignUpResponseDto.of(user, jwtTokenProvider.createToken(user.getUsername()));
    }

    @Override // 비밀번호 체크
    public void checkByUserPassword(LoginRequestDto loginRequestDto, User user) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new RuntimeException(" 비밀번호가 틀렸습니다 다시한번 확인해주세요.");
    }

    @Override // 프로필 변경
    public void changeMyProfile(Long profileId, ProfileRequestDto profileRequestDto, User user) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionStatus.PROFILE_IS_NOT_EXIST));
        profile.checkUser(profile, user);
        profile.updateProfile(profileRequestDto);
        profileRepository.save(profile);
    }

    @Override // 나의 정보 조회
    public ProfileResponseDto getMyProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ProfileResponseDto.of(user);
    }

    @Override // 전체 판매자 목록 조회
    public List<SellerResponseDto> getSellerList() {
        List<User> users = userRepository.findByRoleType("ROLE_SELLER");
        List<SellerResponseDto> sellers = users.stream().map(x -> new SellerResponseDto(x)).toList();
        return sellers;
    }

    @Override // 판매자 정보 조회
    public ProfileResponseDto getSellerProfile(String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        return ProfileResponseDto.of(seller);

    }

}



