package com.example.market9.service;


import com.example.market9.dto.GetSalePostsDto;
import com.example.market9.dto.GetSalePostsResponseDto;
import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.SalePostRequestDto;
import com.example.market9.dto.CreateSalePostResponseDto;
import com.example.market9.entity.Board;
import com.example.market9.entity.UserRequest;
import com.example.market9.entity.Users;
import com.example.market9.repository.BoardRepository;
import com.example.market9.repository.PurchaseRequestRepository;
import com.example.market9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements  BoardService{

    private final BoardRepository boardRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;

    private final UserRepository userRepository;
    @Transactional
    @Override
    public CreateSalePostResponseDto createSalePost(SalePostRequestDto salePostRequestDto) {
        Board board = new Board(salePostRequestDto);
        boardRepository.save(board);
        return new CreateSalePostResponseDto(board);
    }


    // 나중에...예외처리 적용할때 .. 수정
    @Override
    @Transactional
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



    // 특정 판매자의 판매 상품 조회
    @Override
    public GetSalePostsResponseDto<List<GetSalePostsDto>> getSalePosts(Long sellerId) {

        // Spring Security 활용해서, Controller 단에서 User 로 받아와야 함.
        Users user = userRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // Board 객체 뽑아오기
        List<Board> boards = boardRepository.findAllByUser(user);

        // Board 객체 리스트를, DTO 리스트로 변환
        List<GetSalePostsDto> getSalePostsDto = boards.stream()
                .map(GetSalePostsDto::new)
                .collect(Collectors.toList());

        return new GetSalePostsResponseDto<>(getSalePostsDto.size(), getSalePostsDto);
    }



    // 모든 판매 상품 조회
    @Override
    public GetSalePostsResponseDto<List<GetSalePostsDto>> getAllSalePosts(){

        List<Board> boards = boardRepository.findAll();

        // Board 객체 리스트를, DTO 리스트로 변환
        List<GetSalePostsDto> getSalePostsDto = boards.stream()
                .map(GetSalePostsDto::new)
                .collect(Collectors.toList());

        return new GetSalePostsResponseDto<>(getSalePostsDto.size(), getSalePostsDto);
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
    public CreateSalePostResponseDto editSalePost(Long productId, SalePostRequestDto salePostRequestDto){
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
