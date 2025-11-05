package com.cbjf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 엔티티
 * 강의 구매 주문 정보
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber; // 주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Column
    private LocalDateTime paidAt; // 결제 완료 시간

    @Column(length = 50)
    private String paymentMethod; // 결제 수단

    /**
     * 주문 상태 Enum
     */
    public enum OrderStatus {
        PENDING,    // 결제 대기
        PAID,       // 결제 완료
        CANCELLED   // 주문 취소
    }

    /**
     * 주문 항목 추가
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    /**
     * 총 금액 계산
     */
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .mapToInt(OrderItem::getPrice)
                .sum();
    }

    /**
     * 결제 완료 처리
     */
    public void completePaid() {
        this.status = OrderStatus.PAID;
        this.paidAt = LocalDateTime.now();
    }

    /**
     * 주문 취소 처리
     */
    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }
}
