package com.cbjf.controller;

import com.cbjf.domain.entity.Enrollment;
import com.cbjf.domain.entity.Order;
import com.cbjf.domain.entity.User;
import com.cbjf.service.EnrollmentService;
import com.cbjf.service.OrderService;
import com.cbjf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 마이페이지 컨트롤러
 */
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;
    private final EnrollmentService enrollmentService;
    private final OrderService orderService;

    /**
     * 마이페이지 메인
     */
    @GetMapping
    public String myPage(Model model) {
        // 임시로 첫 번째 사용자 사용 (실제로는 로그인 세션에서 가져와야 함)
        User user = userService.findById(1L);
        model.addAttribute("user", user);
        return "mypage/index";
    }

    /**
     * 내 수강 목록
     */
    @GetMapping("/enrollments")
    public String enrollments(Model model) {
        User user = userService.findById(1L);
        List<Enrollment> enrollments = enrollmentService.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("enrollments", enrollments);
        return "mypage/enrollments";
    }

    /**
     * 주문 내역
     */
    @GetMapping("/orders")
    public String orders(Model model) {
        User user = userService.findById(1L);
        List<Order> orders = orderService.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "mypage/orders";
    }
}
