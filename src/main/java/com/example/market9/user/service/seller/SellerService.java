package com.example.market9.user.service.seller;

import com.example.market9.user.profile.dto.seller.SellerProfileRequestDto;
import com.example.market9.user.profile.dto.seller.SellerProfileResponseDto;
import com.example.market9.user.dto.SellerInfoResponseDto;
import com.example.market9.user.entity.Users;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerService {
    List<SellerInfoResponseDto> getSellerList(Pageable requestPage);
    SellerProfileResponseDto getSellerProfile(Long id);
    Long changeSellerProfile(SellerProfileRequestDto sellerProfileRequestDto, Users user);

}