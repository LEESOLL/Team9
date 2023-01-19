package com.example.market9.repository;

import com.example.market9.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {


    /**
     * 셀러(판매글작성자)가 작성한 게시글을 모두 뽑아내는것,..!
     * @param userName 시큐리티에서 받아오는 값을 이용해야함...적용되면 바뀜!
     * @return 그러면 [게시글1,게시글3,게시글4.....]이렇게 뽑힘
     */
    List<Board> findAllByUserName(String userName);

}

