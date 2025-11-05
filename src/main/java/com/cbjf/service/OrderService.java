package com.cbjf.service;

import com.cbjf.domain.entity.*;
import com.cbjf.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 주문 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final EnrollmentService enrollmentService;

    /**
     * 사용자 주문 목록 조회
     */
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    /**
     * 주문 생성 (장바구니에서)
     */
    @Transactional
    public Order createOrder(User user) {
        Cart cart = cartService.getOrCreateCart(user);

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어있습니다");
        }

        // 주문 생성
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .build();

        // 장바구니 항목을 주문 항목으로 변환
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.create(cartItem.getCourse());
            order.addItem(orderItem);
        }

        order.calculateTotalPrice();
        Order savedOrder = orderRepository.save(order);

        // 장바구니 비우기
        cartService.clearCart(user);

        return savedOrder;
    }

    /**
     * 결제 완료 처리
     */
    @Transactional
    public void completePaid(Long orderId, String paymentMethod) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));

        order.setPaymentMethod(paymentMethod);
        order.completePaid();

        // 주문 항목의 강의를 수강 등록
        for (OrderItem item : order.getItems()) {
            enrollmentService.enroll(order.getUser(), item.getCourse().getId());
        }
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다"));

        if (order.getStatus() == Order.OrderStatus.PAID) {
            throw new IllegalArgumentException("이미 결제 완료된 주문은 취소할 수 없습니다");
        }

        order.cancel();
    }

    /**
     * 주문번호 생성
     */
    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.valueOf((int) (Math.random() * 10000));
        return "ORD" + timestamp + random;
    }
}
