package com.example.market9.service;

import com.example.market9.dto.*;
import com.example.market9.entity.*;
import com.example.market9.jwt.JwtUtil;
import com.example.market9.repository.AuthorityDemandRepository;
import com.example.market9.repository.ProfileRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final AuthorityDemandRepository authorityDemandRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional // 회원가입
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
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
//        Users seller = new Users(username, password, nickname, UserRoleEnum.SELLER);
        Profile profile = new Profile(username, nickname, image);
        userRepository.save(user);
//        userRepository.save(seller);
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
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Transactional // 유저 자신의 프로필 변경
    public Long changeUserProfile(ProfileRequestDto profileRequestDto, Users user) {
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("프로필이 존재하지 않습니다."));
        profile.updateUserProfile(profileRequestDto);
        profileRepository.save(profile);
        return user.getId();
    }

    @Transactional // 유저 자신의 정보 조회
    public ProfileResponseDto getMyProfile(Users user) {
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
        return new ProfileResponseDto(profile);
    }

    @Transactional // admin 에게 seller 권한 요청 보내기
    public String applySeller(SellerProfileRequestDto sellerProfileRequestDto, Users user) {
        String username = sellerProfileRequestDto.getUsername(); //user.getUsername() 을 할 것인가 sellerProfileRequestDto.getUsername() 을 할것인가..
        String category = sellerProfileRequestDto.getCategory();
        String introduce = sellerProfileRequestDto.getIntroduce();

        Optional<AuthorityDemand> foundAuthorityDemand = authorityDemandRepository.findByUsername(user.getUsername());
        Optional<Users> foundUser = userRepository.findByUsername(user.getUsername());
        UserRoleEnum role = foundUser.get().getRole();
        if (role == UserRoleEnum.SELLER) {
            throw new IllegalArgumentException("이미 판매자로 등록된 유저입니다.");
        }
        else if (role == UserRoleEnum.ADMIN) {
            throw new IllegalArgumentException("ADMIN 계정입니다. 요청이 불가합니다.");
        }

        if (foundAuthorityDemand.isPresent()) {
            if (foundAuthorityDemand.get().getRole().equals(PermissionStatusEnum.WAITING)) {
                throw new IllegalArgumentException("이미 전송된 요청입니다.");
            } else if(foundAuthorityDemand.get().getRole().equals(PermissionStatusEnum.REFUSE)) {
                foundAuthorityDemand.get().updateAuthorityDemand(username, category, introduce);
                return username;
            }
        }

        AuthorityDemand authorityDemand = new AuthorityDemand(username, category, introduce);
        authorityDemandRepository.save(authorityDemand);

        return username;

    }


}



