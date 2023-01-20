package com.example.market9.service;

import com.example.market9.dto.*;
import com.example.market9.entity.*;
import com.example.market9.exception.CustomException;
import com.example.market9.exception.ExceptionStatus;
import com.example.market9.jwt.JwtUtil;
import com.example.market9.repository.AuthorityDemandRepository;
import com.example.market9.repository.ProfileRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
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

//    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional // 회원가입
    public void signUp(SignUpRequestDto signUpRequestDto) throws NullPointerException {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        String image = signUpRequestDto.getImage();
        String nickname = signUpRequestDto.getNickname();

        // 회원 중복 확인
        duplicateCheckByUsername(username);
        /*Optional<Users> found = userRepository.findByUsername(username);
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }*/

        // 사용자 Role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signUpRequestDto.isAdmin()){
            checkByAdminPassword(signUpRequestDto);
            role = UserRoleEnum.ADMIN;
        }
        /*if (signUpRequestDto.isAdmin()) {
            if (!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }*/


        Users user = new Users(username, password, nickname, role);
//        Users seller = new Users(username, password, nickname, UserRoleEnum.SELLER);
        Profile profile = new Profile(username, nickname, image);
        userRepository.save(user);
//        userRepository.save(seller);
        profileRepository.save(profile);
    }


    public void duplicateCheckByUsername(String username)throws RuntimeException{
        if(userRepository.findByUsername(username).isPresent())
            throw new CustomException(ExceptionStatus.DUPLICATED_USERNAME);
    }
    public void checkByAdminPassword(SignUpRequestDto signUpRequestDto)throws RuntimeException{
        if(!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new CustomException(ExceptionStatus.WRONGADMINTOKEN);
        }
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

    @Transactional // admin 에게 seller 권한 요청 보내기
    public String applySeller(SellerProfileRequestDto sellerProfileRequestDto) {
        String username = sellerProfileRequestDto.getUsername();
        String category = sellerProfileRequestDto.getCategory();
        String introduce = sellerProfileRequestDto.getIntroduce();

        Optional<AuthorityDemand> foundAuthorityDemand = authorityDemandRepository.findByUsername(username);
/*        Optional<Users> foundUser = userRepository.findByUsername(username);
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
        }*/
        checkAuthority(sellerProfileRequestDto);

        if (foundAuthorityDemand.isPresent()) {
            if (foundAuthorityDemand.get().getRole().equals(PermissionStatusEnum.REFUSE)) {
                foundAuthorityDemand.get().updateAuthorityDemand(username, category, introduce);
                return username;
            } else {
                checkDemand(sellerProfileRequestDto);
            }
        }
        AuthorityDemand authorityDemand = new AuthorityDemand(username, category, introduce);
        authorityDemandRepository.save(authorityDemand);
        return username;

    }
    public void checkAuthority(SellerProfileRequestDto sellerProfileRequestDto)throws RuntimeException{
        String username = sellerProfileRequestDto.getUsername();
        Optional<Users> foundUser = userRepository.findByUsername(username);
        UserRoleEnum role = foundUser.get().getRole();
        if (role == UserRoleEnum.SELLER) {
            throw new CustomException(ExceptionStatus.ALREADY_EXIST_SELLER);
        }
        else if (role == UserRoleEnum.ADMIN) {
            throw new CustomException(ExceptionStatus.ALREADY_EXIST_ADMIN);
        }
    }
    public void checkDemand(SellerProfileRequestDto sellerProfileRequestDto)throws RuntimeException{
        String username = sellerProfileRequestDto.getUsername();
        Optional<AuthorityDemand> foundAuthorityDemand = authorityDemandRepository.findByUsername(username);
        if (foundAuthorityDemand.get().getRole().equals(PermissionStatusEnum.WAITING)) {
            throw new CustomException(ExceptionStatus.ALREADY_EXIST_REQUEST);
        }
    }


}



