package com.example.market9.service;


import com.example.market9.dto.CreateSalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import com.example.market9.entity.Board;
import com.example.market9.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements  BoardService{

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public CreateSalePostResponseDto createSalePost(CreateSalePostRequestDto createSalePostRequestDto) {
        Board board = new Board(createSalePostRequestDto);
        boardRepository.save(board);

        return new CreateSalePostResponseDto(board);
    }

    @Override
    public ResponseEntity<String> deleteSalePost(Long productId) {
        if(existsById(productId)) {
            boardRepository.deleteById(productId);
            return new ResponseEntity<>("게시글삭제 완료했습니다", HttpStatus.PARTIAL_CONTENT);
        }else{
            return new ResponseEntity<>("게시글이 없습니다", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean existsById(Long productId){
      return boardRepository.existsById(productId);
    }
}
