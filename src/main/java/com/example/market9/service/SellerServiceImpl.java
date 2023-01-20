package com.example.market9.service;

import com.example.market9.dto.*;
import com.example.market9.entity.Profile;
import com.example.market9.entity.UserRoleEnum;
import com.example.market9.entity.Users;
import com.example.market9.repository.ProfileRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Transactional // 전체 판매자 목록 조회
    public List<SellerResponseDto> getSellerList(Pageable requestPage) {
        List<Users> sellersList = userRepository.findAllByRole(UserRoleEnum.SELLER,requestPage);
        List<SellerResponseDto> sellerResponseDtoList = sellersList.stream().map(x -> new SellerResponseDto(x)).collect(Collectors.toList());

        return sellerResponseDtoList;
    }

    @Transactional // 판매자 정보 조회
    public SellerProfileResponseDto getSellerProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new SellerProfileResponseDto(profile);

    }

    @Transactional // 판매자 자신의 프로필 변경
    public Long changeSellerProfile(Long id, SellerProfileRequestDto sellerProfileRequestDto) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("프로필이 존재하지 않습니다."));
        profile.updateSelleProfile(sellerProfileRequestDto);
        profileRepository.save(profile);
        return id;
    }

}

