package com.example.market9.user.service.admin;

import com.example.market9.requisition.entity.AuthorityDemand;
import com.example.market9.requisition.entity.PermissionStatusEnum;
import com.example.market9.user.entity.UserRoleEnum;
import com.example.market9.user.entity.Users;
import com.example.market9.requisition.dto.AuthorityDemandDto;
import com.example.market9.user.dto.UserInfoListResponseDto;
import com.example.market9.exception.CustomException;
import com.example.market9.exception.ExceptionStatus;
import com.example.market9.requisition.repository.AuthorityDemandRepository;
import com.example.market9.user.profile.entity.Profile;
import com.example.market9.user.profile.repository.ProfileRepository;
import com.example.market9.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private final UserRepository userRepository;
    private final AuthorityDemandRepository authorityDemandRepository;
    private final ProfileRepository profileRepository;

    @Transactional // 유저 전체 목록 조회
    public List<UserInfoListResponseDto> getUserList(Pageable pageRequest) {
        Page<Users> users = userRepository.findAll(pageRequest);
        List<UserInfoListResponseDto> userListResponseDtoInfoList = users.stream().map(x -> new UserInfoListResponseDto(x)).collect(Collectors.toList());
        return userListResponseDtoInfoList;
    }

    @Transactional // 판매 권한 요청 유저 목록 조회
    public List<AuthorityDemandDto> getAuthorityDemandList() {
        List<AuthorityDemand> authorityDemands = authorityDemandRepository.findAll();
        List<AuthorityDemandDto> authorityDemandDtoList = authorityDemands.stream().map(x -> new AuthorityDemandDto(x)).collect(Collectors.toList());
        return authorityDemandDtoList;
    }

    @Transactional // 판매 권한 요청 수락
    public Long acceptAuthorityDemand(Long id) {
        AuthorityDemand authorityDemand = authorityDemandRepository.findById(id)
                .orElseThrow(()-> new CustomException(ExceptionStatus.WRONG_AUTHORITY_DEMAND));
        String username = authorityDemand.getUsername();
        Users users = userRepository.findByUsername(username).get();
        Profile profile = profileRepository.findByUsername(username).get();
        if(authorityDemand.getRole().equals(PermissionStatusEnum.WAITING)) {
            authorityDemand.changePermission(PermissionStatusEnum.ACCEPT);
            authorityDemandRepository.save(authorityDemand);
            users.changeRole(UserRoleEnum.SELLER);
            users.updateSellerProfile(authorityDemand.getCategory(), authorityDemand.getIntroduce());
            profile.updateSellerProfile(authorityDemand.getCategory(), authorityDemand.getIntroduce());
            userRepository.save(users);
            profileRepository.save(profile);

        } else if (authorityDemand.getRole().equals(PermissionStatusEnum.ACCEPT)||authorityDemand.getRole().equals(PermissionStatusEnum.REFUSE)) {
            throw new CustomException(ExceptionStatus.ALREADY_PROCESSED_REQUEST);
        }
        return id;
    }

    @Transactional // 판매 권한 요청 거절
    public Long refuseAuthorityDemand(Long id) {
        AuthorityDemand authorityDemand = authorityDemandRepository.findById(id)
                .orElseThrow(()-> new CustomException(ExceptionStatus.WRONG_AUTHORITY_DEMAND));
        if(authorityDemand.getRole().equals(PermissionStatusEnum.WAITING)) {
            authorityDemand.changePermission(PermissionStatusEnum.REFUSE);
            authorityDemandRepository.save(authorityDemand);
        } else if (authorityDemand.getRole().equals(PermissionStatusEnum.REFUSE)||authorityDemand.getRole().equals(PermissionStatusEnum.ACCEPT)) {
            throw new CustomException(ExceptionStatus.ALREADY_PROCESSED_REQUEST);
        }
        return id;
    }
}
