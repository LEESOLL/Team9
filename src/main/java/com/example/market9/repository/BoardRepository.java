package com.example.market9.repository;

import com.example.market9.entity.Board;
import com.example.market9.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {


    List<Board> findAllByUser(Users user,Pageable pageable);

    List<Board> findByProductNameContainingIgnoreCase(String productName,Pageable pageable);
    List<Board> findByProductNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(
            String productName,
            String title,
            String content,
            Pageable pageable);

}

