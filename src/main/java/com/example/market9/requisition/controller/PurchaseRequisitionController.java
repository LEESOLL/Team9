package com.example.market9.requisition.controller;

import com.example.market9.requisition.dto.PurchaseRequisitionDto;
import com.example.market9.requisition.dto.PurchaseRequisitionListResponseDto;
import com.example.market9.user.entity.Users;
import com.example.market9.security.UserDetailsImpl;
import com.example.market9.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 유저가 셀러에게 요청을 보내는걸 처리하는 컨트롤러입니다..!
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class PurchaseRequisitionController {


    private final RequestService requestService;



    /**
     * 고객이 셀러에게 요청폼을 보내기 .. 후에 사고싶은 금액도 추가했으면 좋겠다는 생각 .. 경매처럼..
     * @param productId  pathBariable로 받아오는 게시글 아이디
     * @param purchaseRequisitionDto  요청메세지가 담겨있다 이때 요청하는 유저의 이름도 담겨잇는데 이건 추후 시큐리티에서 해결
     * @return ResponseEntitiy 활용해서 메세지와 status 상태표시
     */
    @PostMapping("/user/{productId}/request")//고객
    public ResponseEntity<String> requestSeller(@PathVariable Long productId, @RequestBody PurchaseRequisitionDto purchaseRequisitionDto, @AuthenticationPrincipal UserDetailsImpl userDetails){




        /*new ResponseEntity<> ("요청 완료 되었습니다", HttpStatus.OK);*/
        return requestService.requestSeller(productId, purchaseRequisitionDto, userDetails.getUser());
    }

    /**
     * 게시글에 들어온 요청만 보기 ..해당 게시글은 쓴 셀러만 볼 수 있다
     * @param productId  해당 게시글의 id값
     * @return PurchaseRequestRepository 에서 게시글 id에 대항하는 요청을 반환
     */
    @GetMapping("/seller/{productId}/request") //게시글에 들어온 요청보기//셀러
    public List<PurchaseRequisitionListResponseDto> getRequestSellerList(@PathVariable Long productId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return  requestService.getRequestSellerList(productId,userDetails.getUser());
    }

    /**
     * 셀러가 쓴 게시글에 대한 모든 요청을 보는것..
     * @return PurchaseRequestRepository 에서  유저네임으로 조회 되는 값을 반환
     */
    @GetMapping("/seller/request")  //전체요청보기 ..,
    public List<PurchaseRequisitionListResponseDto> getRequestAllSellerList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page",required = false,defaultValue ="1") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
            @RequestParam(value = "isAsc",required = false,defaultValue = "false")Boolean isAsc,
            @RequestParam(value = "sortBy",required = false,defaultValue = "createdAt")String sortBy)
    {
        //시큐리티 적용시 바뀔사항...유저네임꺼내기 !

        Sort.Direction direction = isAsc ? Sort.Direction.ASC:Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageRequest = PageRequest.of(page-1,size,sort);

        Users seller = userDetails.getUser();

       /* return  requestService.getRequestAllSellerList(seller, pageRequest);*/
        return  requestService.getRequestAllSellerList(seller,pageRequest);
    }


    /**
     * 1/2/3/4    > 1,
     * 여러개의 요청들 중에서 하나를 선택해서 ..거래수락을 함
     * 그러면 .. status 가 true로 바뀌고
     * 보드에서 판매완료로 바뀌게 해줌 ...
     * @param requestId  이건 구매요청폼의 아이디이다..
     * @return 거래가 완료 되었다는 표시와  status OK 표시...
     */
    @PutMapping("/seller/request/{requestId}") //셀러만// 이상황은 .. 이미 셀러마다 들어온 요청 검증이 끝난 상황..? 셀러가 가입한 아이디 !
    public ResponseEntity<String> purchaseConfirmation(@PathVariable Long requestId ,@AuthenticationPrincipal UserDetailsImpl userDetails){

      return requestService.purchaseConfirmation(requestId,userDetails.getUser());
    }

}

