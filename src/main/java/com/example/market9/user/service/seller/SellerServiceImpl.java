package com.example.market9.user.service.seller;

import com.example.market9.user.dto.SellerInfoResponseDto;
import com.example.market9.user.profile.dto.seller.SellerProfileRequestDto;
import com.example.market9.user.profile.dto.seller.SellerProfileResponseDto;
import com.example.market9.user.profile.entity.Profile;
import com.example.market9.user.entity.UserRoleEnum;
import com.example.market9.user.entity.Users;
import com.example.market9.exception.CustomException;
import com.example.market9.exception.ExceptionStatus;
import com.example.market9.user.profile.repository.ProfileRepository;
import com.example.market9.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Transactional
    @Override
    public List<SellerInfoResponseDto> getSellerList(Pageable requestPage) {
        List<Users> sellersList = userRepository.findAllByRole(UserRoleEnum.SELLER,requestPage);
        List<SellerInfoResponseDto> sellerInfoResponseDtoList = sellersList.stream().map(x -> new SellerInfoResponseDto(x)).collect(Collectors.toList());
        return sellerInfoResponseDtoList;
    }

    @Transactional
    @Override// 판매자 정보 조회
    public SellerProfileResponseDto getSellerProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionStatus.WRONG_PROFILE));
        Users user = userRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionStatus.REQUEST_NOT_EXIST));

        if(!user.getRole().equals(UserRoleEnum.SELLER)) {
            throw new CustomException(ExceptionStatus.REQUEST_NOT_EXIST);
        }

        return new SellerProfileResponseDto(profile);
        
    }

    @Transactional
    @Override// 판매자 자신의 프로필 변경
    public Long changeSellerProfile(SellerProfileRequestDto sellerProfileRequestDto, Users user) {
        if(user.getRole().equals(UserRoleEnum.USER)) {
            throw new CustomException(ExceptionStatus.ACCESS_DENINED);
        }
        user.updateSellerProfile(sellerProfileRequestDto);
        userRepository.save(user);
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new CustomException(ExceptionStatus.WRONG_PROFILE));
        profile.updateSelleProfile(sellerProfileRequestDto);
        profileRepository.save(profile);
        return 1L;
    }

}

