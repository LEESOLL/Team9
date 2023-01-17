package com.example.market9.service;

import com.example.market9.dto.InfoRequestDTo;
import com.example.market9.dto.SellerProfileResponseDto;
import com.example.market9.entity.Profile;
import com.example.market9.entity.Users;
import com.example.market9.repository.ProfileRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;///
    private final ProfileRepository profileRepository;


    @Transactional
    @Override  // 조회도 상관없습니다 !//   ~~~ user삭제할때 ... profile 들려서 같이 삭제....
    public SellerProfileResponseDto getSellerProfile(InfoRequestDTo infoRequestDTo) {

        System.out.println("UserName : " + infoRequestDTo.getUserName());

        Users user = userRepository.findByUsername(infoRequestDTo.getUserName()).orElseThrow(()-> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        Profile profile = profileRepository.findByUsername(infoRequestDTo.getUserName()).orElseThrow(() ->new IllegalArgumentException("사용자 상세 프로필이 존재하지 않습니다"));

        // 1. user 객체에다가, db에 id값으로 쿼리 날려서 유저 데이터 뽑아옴.
        // 2. profile 객체에다가, db에 id값으로 쿼리 날려서 유저 데이터 뽑아옴.
        // 1,2 번 가공해서, sellerprofiledto 만들어서 리턴해 줌.

        return new SellerProfileResponseDto(user,profile);
    }
    // H2 학습 ........ //오.눈물 ......

}

