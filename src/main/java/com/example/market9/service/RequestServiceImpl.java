package com.example.market9.service;

import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.RequestSellerListResponseDto;
import com.example.market9.entity.Board;
import com.example.market9.entity.UserRequest;
import com.example.market9.repository.BoardRepository;
import com.example.market9.repository.PurchaseRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    private final BoardRepository boardRepository;


    public List<UserRequest> getAllByProductId(Long boardId) {

        return purchaseRequestRepository.findAllByProductId(boardId);
    }

    public Optional<UserRequest> deleteUserRequest(Long productId) {
        return purchaseRequestRepository.deleteByProductId(productId);
    }

    //(고객)판매자에게 요청폼 보내기
    @Transactional
    public void requestSeller(Long productId, RequestSellerDto requestSellerDto/*, String name*/) {
        Boolean status = false;
        Board board = boardRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("게시글없음"));
        String sellerName = board.getUserName();
        UserRequest userRequest = new UserRequest(requestSellerDto, productId/*,name*/, status, sellerName);
        purchaseRequestRepository.save(userRequest);
    }


    @Override
    @Transactional
    public RequestSellerListResponseDto getRequestSellerList(Long productId) {
        List<UserRequest> userRequests = purchaseRequestRepository.findAllByProductId(productId);
        return new RequestSellerListResponseDto(userRequests);
    }

    @Override
    @Transactional
    public RequestSellerListResponseDto getRequestAllSellerList(String sellerName) {
        List<UserRequest> allByUserName = purchaseRequestRepository.findAllBySellerName(sellerName);
        return new RequestSellerListResponseDto(allByUserName);
    }

}
