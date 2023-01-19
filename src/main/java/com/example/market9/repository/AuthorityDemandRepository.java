package com.example.market9.repository;

import com.example.market9.entity.AuthorityDemand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRequestRepository extends JpaRepository<AuthorityDemand, Long> {

    Optional<AuthorityDemand> findByUsername(String username);
}
