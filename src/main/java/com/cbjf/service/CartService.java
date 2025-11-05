package com.cbjf.service;

import com.cbjf.domain.entity.Cart;
import com.cbjf.domain.entity.CartItem;
import com.cbjf.domain.entity.Course;
import com.cbjf.domain.entity.User;
import com.cbjf.domain.repository.CartItemRepository;
import com.cbjf.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 장바구니 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CourseService courseService;

    /**
     * 사용자 장바구니 조회 또는 생성
     */
    @Transactional
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(cart);
                });
    }

    /**
     * 장바구니에 강의 추가
     */
    @Transactional
    public void addCourse(User user, Long courseId) {
        Cart cart = getOrCreateCart(user);
        Course course = courseService.findById(courseId);

        // 이미 장바구니에 있는지 확인
        if (cartItemRepository.existsByCartAndCourse(cart, course)) {
            return; // 이미 담긴 강의는 무시
        }

        CartItem item = CartItem.create(course);
        cart.addItem(item);
        cartItemRepository.save(item);
    }

    /**
     * 장바구니에서 강의 제거
     */
    @Transactional
    public void removeCourse(User user, Long courseId) {
        Cart cart = getOrCreateCart(user);
        Course course = courseService.findById(courseId);

        CartItem item = cartItemRepository.findByCartAndCourse(cart, course)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 강의가 없습니다"));

        cart.removeItem(item);
        cartItemRepository.delete(item);
    }

    /**
     * 장바구니 비우기
     */
    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cart.clear();
        cartItemRepository.deleteAll(cart.getItems());
    }
}
