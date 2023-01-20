package com.example.market9.service;

import com.example.market9.dto.AuthorityDemandDto;
import com.example.market9.dto.UserListResponseDto;
import com.example.market9.entity.AuthorityDemand;
import com.example.market9.entity.PermissionStatusEnum;
import com.example.market9.entity.UserRoleEnum;
import com.example.market9.entity.Users;
import com.example.market9.repository.AuthorityDemandRepository;
import com.example.market9.repository.UserRepository;
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

    @Transactional // 유저 전체 목록 조회
    public List<UserListResponseDto> getUserList(Pageable pageRequest) {
        Page<Users> users = userRepository.findAll(pageRequest);
        List<UserListResponseDto> userListResponseDtoList = users.stream().map(x -> new UserListResponseDto(x)).collect(Collectors.toList());
        return userListResponseDtoList;
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
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 판매자 변경 신청서입니다."));
        String username = authorityDemand.getUsername();
        Users users = userRepository.findByUsername(username).get();

        if(authorityDemand.getRole().equals(PermissionStatusEnum.WAITING)) {
            authorityDemand.changePermission(PermissionStatusEnum.ACCEPT);
            authorityDemandRepository.save(authorityDemand);
            users.changeRole(UserRoleEnum.SELLER);
            userRepository.save(users);
        }
        return id;
    }

    @Transactional // 판매 권한 요청 거절
    public Long refuseAuthorityDemand(Long id) {
        AuthorityDemand authorityDemand = authorityDemandRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 판매자 변경 신청서입니다."));
        if(authorityDemand.getRole().equals(PermissionStatusEnum.WAITING)) {
            authorityDemand.changePermission(PermissionStatusEnum.REFUSE);
            authorityDemandRepository.save(authorityDemand);
        }
        return id;
    }
}
