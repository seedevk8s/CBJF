package com.cbjf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 엔티티
 * 사용자별 장바구니
 */
@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    /**
     * 장바구니에 상품 추가
     */
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    /**
     * 장바구니에서 상품 제거
     */
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }

    /**
     * 장바구니 비우기
     */
    public void clear() {
        items.clear();
    }

    /**
     * 장바구니 총 금액 계산
     */
    public int getTotalPrice() {
        return items.stream()
                .mapToInt(CartItem::getPrice)
                .sum();
    }
}
