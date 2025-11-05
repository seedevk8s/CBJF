package com.cbjf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 주문 항목 엔티티
 * 주문에 포함된 개별 강의
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer price; // 구매 시점의 가격

    /**
     * 주문 항목 생성
     */
    public static OrderItem create(Course course) {
        return OrderItem.builder()
                .course(course)
                .price(course.getPrice())
                .build();
    }
}
