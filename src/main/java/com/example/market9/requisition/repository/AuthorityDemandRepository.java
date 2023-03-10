package com.example.market9.requisition.repository;

import com.example.market9.requisition.entity.AuthorityDemand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityDemandRepository extends JpaRepository<AuthorityDemand, Long> {

    Optional<AuthorityDemand> findByUsername(String username);

}
