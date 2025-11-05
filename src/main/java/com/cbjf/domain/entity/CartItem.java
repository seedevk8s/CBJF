package com.cbjf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 장바구니 항목 엔티티
 * 장바구니에 담긴 개별 강의
 */
@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer price; // 담은 시점의 가격

    /**
     * 장바구니 항목 생성
     */
    public static CartItem create(Course course) {
        return CartItem.builder()
                .course(course)
                .price(course.getPrice())
                .build();
    }
}
