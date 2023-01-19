package com.example.market9.service;

import com.example.market9.dto.*;
import com.example.market9.entity.Profile;
import com.example.market9.entity.UserRoleEnum;
import com.example.market9.entity.Users;
import com.example.market9.jwt.JwtUtil;
import com.example.market9.repository.ProfileRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

//    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional // 회원가입
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        String image = signUpRequestDto.getImage();

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        String nickname = signUpRequestDto.getNickname();
        // 사용자 Role 확인
        UserRoleEnum role = UserRoleEnum.USER;//

        if (signUpRequestDto.isAdmin()) {
            if (!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        Users user = new Users(username, password, nickname, role);
        Users seller = new Users(username, password, nickname, UserRoleEnum.SELLER);
        Profile profile = new Profile(username, nickname, image);
        userRepository.save(user);
        userRepository.save(seller);
        profileRepository.save(profile);

    }

    @Transactional // 로그인
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
//    public SignUpResponseDto login(LoginRequestDto loginRequestDto) {
//        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(IllegalArgumentException::new);
//        checkByUserPassword(loginRequestDto, user);
//        user.updateRefreshToken(jwtTokenProvider.createRefreshToken());
//        return SignUpResponseDto.of(user, jwtTokenProvider.createToken(user.getUsername()));
//    }

//    @Override // 비밀번호 체크
//    public void checkByUserPassword(LoginRequestDto loginRequestDto, User user) {
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
//            throw new RuntimeException(" 비밀번호가 틀렸습니다 다시한번 확인해주세요.");
//    }

    @Transactional // 유저 자신의 프로필 변경
    public Long changeUserProfile(Long id, ProfileRequestDto profileRequestDto) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("프로필이 존재하지 않습니다."));
        profile.updateUserProfile(profileRequestDto);
        profileRepository.save(profile);
        return id;
    }

    @Transactional // 유저 자신의 정보 조회
    public ProfileResponseDto getMyProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new ProfileResponseDto(profile);
    }

}



