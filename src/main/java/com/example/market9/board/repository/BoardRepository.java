package com.example.market9.board.repository;

import com.example.market9.board.entity.Board;
import com.example.market9.user.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {


    List<Board> findAllByUser(Users user,Pageable pageable);




    List<Board> findByProductNameContainingIgnoreCase(String productName,Pageable pageable);
    List<Board> findByProductNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(
            String productName,
            String title,
            String content,
            Pageable pageable);

}

