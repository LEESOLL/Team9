package com.example.market9.board.service;


import com.example.market9.board.dto.CreateSalePostResponseDto;
import com.example.market9.board.dto.SalePostResponseDto;
import com.example.market9.board.dto.SalePostRequestDto;
import com.example.market9.board.dto.SalePostsListResponseDto;
import com.example.market9.board.entity.Board;
import com.example.market9.board.entity.SaleStatusEnum;

import com.example.market9.service.RequestService;
import com.example.market9.user.entity.Users;

import com.example.market9.exception.CustomException;
import com.example.market9.exception.ExceptionStatus;


import com.example.market9.board.repository.BoardRepository;
import com.example.market9.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements  BoardService {

    private final BoardRepository boardRepository;
    private final RequestService requestService;
    private final UserRepository userRepository;

    @Override
    @Transactional  //업데이트 쿼리 날리는거 아니면 ... 더티채킹.......원... 값이 변경거는 비교 .. ~  //
    public CreateSalePostResponseDto createSalePost(SalePostRequestDto salePostRequestDto, Users users) {


       /* ///----------시큐리티 나오면 없어질 내용 ..... 유저 객체를  꺼내오기 위한 과정이다 -----//
        String userName = salePostRequestDto.getUserName();

        Users sampleUser = userRepository.findByUsername(userName).orElseThrow(()-> new IllegalArgumentException("유저없음"));
        ///------------------------------------------------------------------------------------------------------------///*/

        SaleStatusEnum status = SaleStatusEnum.SALE;
        Board board = new Board(salePostRequestDto, status,users);
        boardRepository.save(board);


        //UserRoleEnum role = UserRoleEnum.USE
        return new CreateSalePostResponseDto(board);
    }


    // 나중에...예외처리 적용할때 .. 수정
    @Override
    @Transactional
    public ResponseEntity<String> deleteSalePost(Long productId,Users users) {
        Board board = boardRepository.findById(productId).orElseThrow(()-> new CustomException(ExceptionStatus.BOARD_NOT_EXIST));
        // 존재하면 -> 게시글 삭제 완료.
        // 존재하지 않는 아이디에 대한 삭제 요청? => Client 쪽 문제.
        if (board.getUser().getId().equals(users.getId())) {
            boardRepository.deleteById(productId);
            requestService.deleteUserRequest(productId);
            return new ResponseEntity<>("게시글 삭제 완료했습니다", HttpStatus.OK);
        } else {
            throw new CustomException(ExceptionStatus.WRONG_SELLER_ID_T0_BOARD);

        }
    }

    public boolean existsById(Long productId) {
        return boardRepository.existsById(productId);
    }


    // 특정 판매자의 판매 상품 조회
    @Override
    public SalePostsListResponseDto<List<SalePostResponseDto>> getSalePosts(Long sellerId , Pageable pageRequest) {

        // Spring Security 활용해서, Controller 단에서 User 로 받아와야 함.
        Users user = userRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // Board 객체 뽑아오기
        List<Board> boards = boardRepository.findAllByUser(user,pageRequest);

        // Board 객체 리스트를, DTO 리스트로 변환
        List<SalePostResponseDto> getSalePostResponseDto = boards.stream()
                .map(SalePostResponseDto::new)
                .collect(Collectors.toList());

        return new SalePostsListResponseDto<>(getSalePostResponseDto.size(), getSalePostResponseDto);
    }



    // 모든 판매 상품 조회
    @Override
    public SalePostsListResponseDto<List<SalePostResponseDto>> getAllSalePosts(Pageable pageRequest, String search){

        Page<Board> boards = boardRepository.findAll(pageRequest);

        String productName =search;
        String title =search;
        String content =search;

        List<Board> searchBoards = boardRepository.findByProductNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(
                productName,title,content, pageRequest);

        List<Board> oneSearch = boardRepository.findByProductNameContainingIgnoreCase(search, pageRequest);

        List<SalePostResponseDto> getSalePostResponseDto = searchBoards.stream()
                .map(SalePostResponseDto::new)
                .collect(Collectors.toList());

        return new SalePostsListResponseDto<>(getSalePostResponseDto.size(), getSalePostResponseDto);
    }


    /*
    @GetMapping("/api/v2/members")
public Result memberV2(){
     List<Member> findMembers = memberService.findMembers();
    List<MemberDto> collect = findMembers.stream()
            .map(m-> new MemberDto(m.getName()))
            .collect(Collectors.toList());

    return new Result(collect.size(), collect);
}

    */

    //판매상품수정
    @Transactional
    @Override
    public CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto,Users users) {

        Board board = boardRepository.findById(productId).orElseThrow
                (() -> new CustomException(ExceptionStatus.BOARD_NOT_EXIST));
        if(board.getUser().getId().equals(users.getId())){
            board.editSalePost(productId,salePostRequestDto);
            return new CreateSalePostResponseDto(board);

        }throw new CustomException(ExceptionStatus.WRONG_SELLER_ID_T0_BOARD);

    }
}
