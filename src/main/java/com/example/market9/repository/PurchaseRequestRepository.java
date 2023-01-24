package com.example.market9.repository;

import com.example.market9.entity.UserRequest;
import com.example.market9.entity.Users;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequestRepository extends JpaRepository<UserRequest,Long> {


    Optional<UserRequest> deleteByProductId (Long productId);  //게시글삭제되면 한 요청도 삭제...

   List<UserRequest> findAllByProductId(Long productId);


  /* List<UserRequest> findAllBySellerName(String sellerName , Pageable pageRequest);*/
   List<UserRequest> findAllBySeller(Users seller , Pageable pageRequest);

   //지금 든 생각 1
    // 1.유저이름으로 보드레포 들려서 해당 유저가쓴 게시글 다 리스트로 가져옴
    // 2. 이걸 for-each돌려서 getID해서 Long타입을 꺼내 ~
    // 3 그러면 꺼낸 id를 이용해서 요청포지토리에 들려서 list<요청> 이렇게 담아옴
    // 4.list<요청> 담아온걸 다시 List<list<요청>> 에 담아줘서 반환해줌 !! 일단 이렇게 다시 구현해보자 ..!
}
