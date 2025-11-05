package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Cart;
import com.cbjf.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 장바구니 Repository
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * 사용자별 장바구니 조회
     */
    Optional<Cart> findByUser(User user);
}
