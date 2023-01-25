package com.example.market9.requisition.repository;

import com.example.market9.requisition.entity.PurchaseRequisition;
import com.example.market9.user.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Long> {

    Optional<PurchaseRequisition> deleteByProductId(Long productId);

    List<PurchaseRequisition> findAllByProductId(Long productId);

    List<PurchaseRequisition> findBySeller(Users seller, Pageable pageable);
}
