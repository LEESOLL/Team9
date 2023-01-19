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

    private String sellerName;

    public UserRequest(RequestSellerDto requestSellerDto, Long productId/*, String userName*/,Boolean status ,String sellerName) {
        this.requestContent = requestSellerDto.getRequestContent();
        this.productId = productId;
        this.userName = requestSellerDto.getUserName();
        this.status = status;
        this.sellerName = sellerName;
    }
}
   //[C-S-R]
/*
진재혁 1 ,2 ,3  MAP    [key > 게시글아이디> value 요청리스트]

[[요청,요청],[요청,여청],[,ㅊ영쳐여쳥ㅊ] < 고민한흔적

게시글 id
id 욤ㄴㅇ리ㅏㅁㅇ너리ㅏㅁㄴ어개첵객리스트 ..*/
