package com.eva.EvaSupportAgent.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eva.EvaSupportAgent.product.model.OrderHistory;


@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findByUserId(Long userId);

    List<OrderHistory> findByProductId(Long productId);
}

