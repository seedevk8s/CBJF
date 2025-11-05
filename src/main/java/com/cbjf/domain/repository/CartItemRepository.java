package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Cart;
import com.cbjf.domain.entity.CartItem;
import com.cbjf.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 장바구니 항목 Repository
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * 장바구니와 강의로 항목 조회
     */
    Optional<CartItem> findByCartAndCourse(Cart cart, Course course);

    /**
     * 장바구니 항목 존재 여부 확인
     */
    boolean existsByCartAndCourse(Cart cart, Course course);
}
