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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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
    public void signUp(SignUpRequestDto signUpRequestDto, MultipartFile file) throws NullPointerException, IOException {
        String filepath = System.getProperty("user.dir")+"/src/main/resources/static/files";
        UUID uuid = UUID.randomUUID();
        String filename = uuid+"_"+file.getOriginalFilename();
        File savefile = new File(filepath, filename);
        file.transferTo(savefile);
        signUpRequestDto.setFilename(filename);
        signUpRequestDto.setFilepath("/files"+filename);
        String username = signUpRequestDto.getUsername();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        String image = signUpRequestDto.getImage();
        String nickname = signUpRequestDto.getNickname();

        // 회원 중복 확인
        duplicateCheckByUsername(username);

        // 사용자 Role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signUpRequestDto.isAdmin()){
            checkByAdminPassword(signUpRequestDto);
            role = UserRoleEnum.ADMIN;
        }

        Users user = new Users(username, password, image, nickname, filepath, filename, role);
        Profile profile = new Profile(username, nickname, image, filepath, filename);
        userRepository.save(user);
        profileRepository.save(profile);
    }

    // 회원 중복 확인
    public void duplicateCheckByUsername(String username) throws RuntimeException{
        if(userRepository.findByUsername(username).isPresent())
            throw new CustomException(ExceptionStatus.DUPLICATED_USERNAME);
    }
    // 어드민 비밀번호 확인
    public void checkByAdminPassword(SignUpRequestDto signUpRequestDto) throws RuntimeException{
        if(!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new CustomException(ExceptionStatus.WRONG_ADMINTOKEN);
        }
    }

    @Transactional // 로그인
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ExceptionStatus.WRONG_USERNAME)
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){

            throw new CustomException(ExceptionStatus.WRONG_PASSWORD);
        }


        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Transactional // 유저 자신의 프로필 변경
    public Long changeUserProfile(ProfileRequestDto profileRequestDto, Users user) {
        Users users = userRepository.findById(user.getId()).orElseThrow(() -> new CustomException(ExceptionStatus.WRONG_USERNAME));
        users.updateUser(profileRequestDto);
        userRepository.save(users);

        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new CustomException(ExceptionStatus.WRONG_PROFILE));
        profile.updateUserProfile(profileRequestDto);
        profileRepository.save(profile);
        return 1L;
    }

    @Transactional // 유저 자신의 정보 조회
    public ProfileResponseDto getMyProfile(Users user) {
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new CustomException(ExceptionStatus.WRONG_PROFILE));
        return new ProfileResponseDto(profile);
    }

    @Transactional // admin 에게 seller 권한 요청 보내기
    public String applySeller(SellerProfileRequestDto sellerProfileRequestDto, Users user) {
//        String username = sellerProfileRequestDto.getUsername();
        String category = sellerProfileRequestDto.getCategory();
        String introduce = sellerProfileRequestDto.getIntroduce();

        Optional<AuthorityDemand> foundAuthorityDemand = authorityDemandRepository.findByUsername(user.getUsername());

        checkAuthority(user.getUsername());

        if (foundAuthorityDemand.isPresent()) {
            if (foundAuthorityDemand.get().getRole().equals(PermissionStatusEnum.REFUSE)) {
                foundAuthorityDemand.get().updateAuthorityDemand(user.getUsername(), category, introduce);
                return user.getUsername();
            } else {
                checkDemand(user.getUsername());
            }
        }
        AuthorityDemand authorityDemand = new AuthorityDemand(user.getUsername(), category, introduce);
        authorityDemandRepository.save(authorityDemand);
        return user.getUsername();

    }
    public void checkAuthority(String username) throws RuntimeException{
        Optional<Users> foundUser = userRepository.findByUsername(username);
        UserRoleEnum role = foundUser.get().getRole();
        if (role == UserRoleEnum.SELLER) {
            throw new CustomException(ExceptionStatus.ALREADY_EXIST_SELLER);
        }
        else if (role == UserRoleEnum.ADMIN) {
            throw new CustomException(ExceptionStatus.ALREADY_EXIST_ADMIN);
        }
    }
    public void checkDemand(String username) throws RuntimeException{
        Optional<AuthorityDemand> foundAuthorityDemand = authorityDemandRepository.findByUsername(username);
        if (foundAuthorityDemand.get().getRole().equals(PermissionStatusEnum.WAITING)) {
            throw new CustomException(ExceptionStatus.ALREADY_EXIST_REQUEST);
        }
    }

}



