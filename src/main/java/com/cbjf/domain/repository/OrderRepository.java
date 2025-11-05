package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Order;
import com.cbjf.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 주문 Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 주문번호로 주문 조회
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * 사용자별 주문 목록 조회
     */
    List<Order> findByUser(User user);

    /**
     * 사용자의 주문 상태별 목록 조회
     */
    List<Order> findByUserAndStatus(User user, Order.OrderStatus status);

    /**
     * 주문번호 존재 여부 확인
     */
    boolean existsByOrderNumber(String orderNumber);
}
