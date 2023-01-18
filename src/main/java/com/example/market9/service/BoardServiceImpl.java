package com.example.market9.service;


import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.SalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import com.example.market9.entity.Board;
import com.example.market9.entity.UserRequest;
import com.example.market9.repository.BoardRepository;
import com.example.market9.repository.PurchaseRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements  BoardService{

    private final BoardRepository boardRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    @Transactional
    @Override
    public CreateSalePostResponseDto createSalePost(SalePostRequestDto salePostRequestDto) {
        Board board = new Board(salePostRequestDto);
        boardRepository.save(board);

        return new CreateSalePostResponseDto(board);
    }

    // 나중에...예외처리 적용할때 .. 수정
    @Override
    public ResponseEntity<String> deleteSalePost(Long productId) {

        if(existsById(productId)) {
            boardRepository.deleteById(productId);
            purchaseRequestRepository.deleteByProductId(productId);
            return new ResponseEntity<>("게시글삭제 완료했습니다", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("게시글이 없습니다", HttpStatus.BAD_REQUEST); //@.....
        }
    }

    public boolean existsById(Long productId){
      return boardRepository.existsById(productId);
    }



    //판매상품수정
    @Transactional
    @Override
    public CreateSalePostResponseDto updateSalePost(Long productId, SalePostRequestDto salePostRequestDto){
        Board board = boardRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException(" 게시글이 존재하지 않습니다."));
        return new CreateSalePostResponseDto(board);
    }



    //(고객)판매자에게 요청폼 보내기
    @Override
    @Transactional
    public void requestSeller(Long productId, RequestSellerDto requestSellerDto/*, String name*/) {
        UserRequest userRequest = new UserRequest(requestSellerDto,productId/*,name*/);
        purchaseRequestRepository.save(userRequest);
    }
}
