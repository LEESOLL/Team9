package com.example.market9.repository;

import com.example.market9.entity.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequestRepository extends JpaRepository<UserRequest,Long> {
}
