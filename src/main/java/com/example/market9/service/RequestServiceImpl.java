package com.example.market9.service;

import com.example.market9.dto.RequestSellerDto;
import com.example.market9.dto.RequestSellerListResponseDto;
import com.example.market9.dto.Response;
import com.example.market9.entity.Board;
import com.example.market9.entity.SaleStatusEnum;
import com.example.market9.entity.UserRequest;
import com.example.market9.entity.Users;
import com.example.market9.exception.CustomException;
import com.example.market9.exception.ExceptionStatus;
import com.example.market9.repository.BoardRepository;
import com.example.market9.repository.PurchaseRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    private final BoardRepository boardRepository;

    /**
     * 고객이 판매자에게 구매요청을 보내는 메소드
      * @param productId  패스베리어블로 받아오는 게시판 아이디 .. ~
     * @param requestSellerDto  요청하는 내용이 들어감 ...
     */
    @Transactional
    @Override
    public ResponseEntity<String> requestSeller(Long productId, RequestSellerDto requestSellerDto, Users users) {


        boolean present = boardRepository.findById(productId).isPresent();

        if (!present){
           throw new CustomException(ExceptionStatus.BOARD_NOT_EXIST);
        }

        List<UserRequest> userAllRequests = getUserRequestList(productId);

        ResponseEntity<String> BAD_REQUEST = getResponse(userAllRequests);

        if (BAD_REQUEST != null){
            return BAD_REQUEST;
        }
        Board board = boardRepository.findById(productId).orElseThrow(() -> new CustomException(ExceptionStatus.BOARD_NOT_EXIST));

        Boolean status = false;
       /* String sellerName = board.getUserName();*/
        UserRequest userRequests = new UserRequest(requestSellerDto, productId,users.getUsername(), status,board.getUser());
        purchaseRequestRepository.save(userRequests);

        return new ResponseEntity<>("요청 완료 되었습니다", HttpStatus.OK);
    }



    /**
     * 판매자가 게시글 별로 요청 조회시 ..오직 글 작성자만 접근가능
     * @param productId  게시글 id이다
     * @return 게시글로 들어온 요청을 반환해줌 ..
     */
    @Override
    @Transactional
    public List<Response> getRequestSellerList(Long productId, Users user) {
        Board board = boardRepository.findById(productId).orElseThrow(()-> new CustomException(ExceptionStatus.BOARD_NOT_EXIST));

        if(board.getUser().getId().equals(user.getId())) {
            List<UserRequest> userRequests = getUserRequestList(productId);
            List<Response> response = new ArrayList<>();

            for (UserRequest request : userRequests) {
                String requestContent = request.getRequestContent();
                String userName = request.getUserName();
                Long postId = request.getProductId();

                Response response1 = new Response(requestContent,userName,postId);

                response.add(response1);

            }
            // 요청내용/요청한사람아이디=이름/무슨게시물에 요청왔나///


            return  response;
        }throw new CustomException(ExceptionStatus.WRONG_SELLER_ID_T0_BOARD);
    }

    /**
     * 판매자에게 온 모든 요청을 보여주는 메소드
     *
     * @param pageRequest  페이관리 객체
     * @return 리스트를 담아서 반환
     */
    @Override
    @Transactional
    public List<UserRequest> getRequestAllSellerList(Users seller , Pageable pageable) {

        List<UserRequest> allBySellerName = purchaseRequestRepository.findBySeller(seller,pageable);
        /*return new RequestSellerListResponseDto(allBySellerName);*/
        return allBySellerName;
    }
///!
    /**
     * 요청이 들어온것 중 .. 맘에드는 요청을 수락하는 메소드 거래완료다
     * @param requestId 요청의 id값이
     */
    @Override
    @Transactional
    public ResponseEntity<String> purchaseConfirmation(Long requestId,Users seller) { //메소드는 하나의 일만 해야한다,, 나머지는 시키면된다..//

        UserRequest userRequest = getUserRequest(requestId);

        if(!userRequest.getSeller().getId().equals(seller.getId())){
            throw new CustomException(ExceptionStatus.WRONG_SELLER_ID_TO_USER_REQUEST);
        }

        Long boardId = getBoardId(userRequest);

        List<UserRequest> userAllRequests = getUserRequestList(boardId);
        ResponseEntity<String> BAD_REQUEST = getResponse(userAllRequests);

        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        userRequest.acceptDeal(true);
        Board board = getBoard(boardId);
        board.soldOut(SaleStatusEnum.SOLD_OUT);

        return new ResponseEntity<>("거래 완료",HttpStatus.OK);
    }

    private static ResponseEntity<String> getResponse(List<UserRequest> userAllRequests) {
        for (UserRequest request : userAllRequests) {
            if(request.isStatus()){
                return new ResponseEntity<>("이미 판매된 게시글입니다.", HttpStatus.BAD_REQUEST);
            }
        }
        return null;  //null 값 .. 이대로 괜찮은가 ..!?
    }

    //------------------메소드 추출--------------------------------------------//

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ExceptionStatus.BOARD_NOT_EXIST));
    }

    private static Long getBoardId(UserRequest userRequest) {
        return userRequest.getProductId();
    }

    private UserRequest getUserRequest(Long requestId) {
        return purchaseRequestRepository.findById(requestId).orElseThrow(() -> new CustomException(ExceptionStatus.REQUEST_NOT_EXIST));
    }


    public List<UserRequest> getAllByProductId(Long boardId) {
        return getUserRequestList(boardId);
    }

    public Optional<UserRequest> deleteUserRequest(Long productId) {
        return purchaseRequestRepository.deleteByProductId(productId);
    }
    private List<UserRequest> getUserRequestList(Long productId) {
        return purchaseRequestRepository.findAllByProductId(productId);
    }


}
