package com.example.market9.repository;

import com.example.market9.entity.Profile;
import com.example.market9.entity.UserRoleEnum;
import com.example.market9.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String userName);
    List<Users> findAllByRole(UserRoleEnum role, Pageable requestPage);
    List<Users> findAll();

}
