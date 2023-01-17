package com.example.market9.service;


import com.example.market9.dto.CreateSalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import com.example.market9.entity.Board;
import com.example.market9.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
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
}
