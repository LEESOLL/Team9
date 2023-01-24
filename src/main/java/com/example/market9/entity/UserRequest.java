package com.example.market9.entity;

import com.example.market9.dto.RequestSellerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserRequest extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Request_Id")
    private Long id; //요청id
    private String requestContent;//

    private Long productId;
    private String userName; /// path 유저id만 가지고오면 .>장점 @OneToMany피할 수 있습니다.... //유저 id 유저 객체 어떤게 좋을까요.....?!

    private boolean status;
//  private String sellerName; //회원 가입시 적는 아이디  중복 X ...*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users seller;//seller < 받을수 있다 여러개의 요청을 ... 요청 기준으로 매티 투 원 ..


    public UserRequest(RequestSellerDto requestSellerDto, Long productId, String userName, Boolean status ,Users seller) {
        this.requestContent = requestSellerDto.getRequestContent();
        this.productId = productId;
        this.userName = userName;
        this.status = status;
        this.seller = seller;
    }

    /**
     * 거래 요청 승낙시 true로 바꿔주기 위한 메소드
     * @param status false->true로 바뀔것..
     */
    public void acceptDeal(boolean status){
        this.status =status;
    }
}
   //[C-S-R]
/*
진재혁 1 ,2 ,3  MAP    [key > 게시글아이디> value 요청리스트]

[[요청,요청],[요청,여청],[,ㅊ영쳐여쳥ㅊ] < 고민한흔적

게시글 id
id 욤ㄴㅇ리ㅏㅁㅇ너리ㅏㅁㄴ어개첵객리스트 ..*/
