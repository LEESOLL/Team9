//package com.example.market9.service;
//
//
//import com.example.market9.dto.*;
//import com.example.market9.entity.Board;
//import com.example.market9.entity.SaleStatusEnum;
//
//import com.example.market9.entity.UserRequest;
//import com.example.market9.entity.Users;
//
//import com.example.market9.exception.CustomException;
//import com.example.market9.exception.ExceptionStatus;
//
//
//import com.example.market9.repository.BoardRepository;
//import com.example.market9.repository.PurchaseRequestRepository;
//import com.example.market9.repository.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class BoardServiceImpl implements  BoardService {
//
//    private final BoardRepository boardRepository;
//    private final RequestService requestService;
//    private final UserRepository userRepository;
//
//
//
//
//    @Override
//    @Transactional(readOnly = true)  //업데이트 쿼리 날리는거 아니면 ... 더티채킹.......원... 값이 변경거는 비교 .. ~  //
//    public CreateSalePostResponseDto createSalePost(SalePostRequestDto salePostRequestDto) {
//
//
//        ///----------시큐리티 나오면 없어질 내용 ..... 유저 객체를  꺼내오기 위한 과정이다 -----//
//        String userName = salePostRequestDto.getUserName();
//
//        Users sampleUser = userRepository.findByUsername(userName).orElseThrow(()-> new IllegalArgumentException("유저없음"));
//        ///------------------------------------------------------------------------------------------------------------///
//
//        SaleStatusEnum status = SaleStatusEnum.SALE;
//        Board board = new Board(salePostRequestDto, status,sampleUser);
//        boardRepository.save(board);
//
//        //UserRoleEnum role = UserRoleEnum.USE
//        return new CreateSalePostResponseDto(board);
//    }
//
//
//    // 나중에...예외처리 적용할때 .. 수정
//    @Override
//    @Transactional
//    public ResponseEntity<String> deleteSalePost(Long productId) {
//
//
//        // 존재하면 -> 게시글 삭제 완료.
//        // 존재하지 않는 아이디에 대한 삭제 요청? => Client 쪽 문제.
//        if (existsById(productId)) {
//            boardRepository.deleteById(productId);
//            requestService.deleteUserRequest(productId);
//            return new ResponseEntity<>("게시글 삭제 완료했습니다", HttpStatus.OK);
//        } else {
//            throw new CustomException(ExceptionStatus.BOARD_NOT_EXIST);
//
//        }
//    }
//
//    public boolean existsById(Long productId) {
//        return boardRepository.existsById(productId);
//    }
//
//
//    // 특정 판매자의 판매 상품 조회
//    @Override
//    public GetSalePostsResponseDto<List<GetSalePostsDto>> getSalePosts(Long sellerId , Pageable pageRequest) {
//
//        // Spring Security 활용해서, Controller 단에서 User 로 받아와야 함.
//        Users user = userRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
//
//        // Board 객체 뽑아오기
//        List<Board> boards = boardRepository.findAllByUser(user,pageRequest);
//
//        // Board 객체 리스트를, DTO 리스트로 변환
//        List<GetSalePostsDto> getSalePostsDto = boards.stream()
//                .map(GetSalePostsDto::new)
//                .collect(Collectors.toList());
//
//        return new GetSalePostsResponseDto<>(getSalePostsDto.size(), getSalePostsDto);
//    }
//
//
//
//    // 모든 판매 상품 조회
//    @Override
//    public GetSalePostsResponseDto<List<GetSalePostsDto>> getAllSalePosts(Pageable pageRequest,String search){
//
//        Page<Board> boards = boardRepository.findAll(pageRequest);
//
//        String productName =search;
//        String title =search;
//        String content =search;
//
//        List<Board> searchBoards = boardRepository.findByProductNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(
//                productName,title,content, pageRequest);
//
//        List<Board> oneSearch = boardRepository.findByProductNameContainingIgnoreCase(search, pageRequest);
//
//        List<GetSalePostsDto> getSalePostsDto = searchBoards.stream()
//                .map(GetSalePostsDto::new)
//                .collect(Collectors.toList());
//
//        return new GetSalePostsResponseDto<>(getSalePostsDto.size(), getSalePostsDto);
//    }
//
//
//    /*
//    @GetMapping("/api/v2/members")
//public Result memberV2(){
//     List<Member> findMembers = memberService.findMembers();
//    List<MemberDto> collect = findMembers.stream()
//            .map(m-> new MemberDto(m.getName()))
//            .collect(Collectors.toList());
//
//    return new Result(collect.size(), collect);
//}
//<<<<<<< HEAD
//    */
//
//    //판매상품수정
//    @Transactional
//    @Override
//    public CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto) {
//        Board board = boardRepository.findById(productId).orElseThrow
//                (() -> new CustomException(ExceptionStatus.BOARD_NOT_EXIST));
//        board.editSalePost(productId,salePostRequestDto);
//        return new CreateSalePostResponseDto(board);
//    }
//}
