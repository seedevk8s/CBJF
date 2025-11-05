package com.cbjf.controller;

import com.cbjf.domain.entity.Cart;
import com.cbjf.domain.entity.User;
import com.cbjf.service.CartService;
import com.cbjf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 장바구니 컨트롤러
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    /**
     * 장바구니 페이지
     */
    @GetMapping
    public String cart(Model model) {
        // 임시로 첫 번째 사용자 사용 (실제로는 로그인 세션에서 가져와야 함)
        User user = userService.findById(1L);
        Cart cart = cartService.getOrCreateCart(user);

        model.addAttribute("cart", cart);
        return "cart/index";
    }

    /**
     * 장바구니에 강의 추가
     */
    @PostMapping("/add/{courseId}")
    public String addCourse(@PathVariable Long courseId) {
        // 임시로 첫 번째 사용자 사용
        User user = userService.findById(1L);
        cartService.addCourse(user, courseId);
        return "redirect:/cart";
    }

    /**
     * 장바구니에서 강의 제거
     */
    @PostMapping("/remove/{courseId}")
    public String removeCourse(@PathVariable Long courseId) {
        User user = userService.findById(1L);
        cartService.removeCourse(user, courseId);
        return "redirect:/cart";
    }

    /**
     * 장바구니 비우기
     */
    @PostMapping("/clear")
    public String clearCart() {
        User user = userService.findById(1L);
        cartService.clearCart(user);
        return "redirect:/cart";
    }
}
