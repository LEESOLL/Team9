package com.example.market9.service;


import com.example.market9.dto.*;
import com.example.market9.entity.Board;
import com.example.market9.entity.SaleStatusEnum;
import com.example.market9.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements  BoardService {

    private final BoardRepository boardRepository;
    private final RequestService requestService;

    @Transactional
    @Override
    public CreateSalePostResponseDto createSalePost(SalePostRequestDto salePostRequestDto) {

        SaleStatusEnum status = SaleStatusEnum.SALE;
        Board board = new Board(salePostRequestDto, status);
        boardRepository.save(board);
        
        //UserRoleEnum role = UserRoleEnum.USE
        return new CreateSalePostResponseDto(board);
    }


    // 나중에...예외처리 적용할때 .. 수정
    @Override
    @Transactional
    public ResponseEntity<String> deleteSalePost(Long productId) {

        if (existsById(productId)) {
            boardRepository.deleteById(productId);
            requestService.deleteUserRequest(productId);
            return new ResponseEntity<>("게시글삭제 완료했습니다", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("게시글이 없습니다", HttpStatus.BAD_REQUEST); //@.....
        }
    }


    public boolean existsById(Long productId) {
        return boardRepository.existsById(productId);
    }

    //판매상품수정
    @Transactional
    @Override
    public CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto) {
        Board board = boardRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException(" 게시글이 존재하지 않습니다."));
        board.editSalePost(productId,salePostRequestDto);
        return new CreateSalePostResponseDto(board);
    }
}
