package com.example.market9.controller;

import com.example.market9.service.SellerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
/*import org.springframework.security.core.annotation.AuthenticationPrincipal;*/
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api")
public class SellerController {

    private final SellerService sellerService;

    // 선택한 셀러 프로필 조회(셀러,,,,방향을 )  ///.........어,,,

    /**
     *  검증로직이 필요함 !
     *
     * @return   서비스 이용 !
     */

    // 선택한 셀러 프로필 조회  /로그인 jwt +시큐리티(필터 ~ 인증 , 인가)
//    @GetMapping("/profile")    //url패스베리어블 ㅠㅠㅠ   ,  ,  ? ,,,username = " "리퀘스트파람
//    public SellerProfileResponseDto  getSellerProfile(@RequestBody InfoRequestDTo infoRequestDTo/*@AuthenticationPrincipal UserDetailsImpl userDetails*/) {
//        return sellerService.getSellerProfile(infoRequestDTo); //대박 입니다 ..! 맞습니다 !
//    }

    // 선택한 셀러 프로필 수정 .....다시내려받고...


}
