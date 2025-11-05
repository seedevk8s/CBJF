package com.cbjf.controller;

import com.cbjf.domain.entity.Course;
import com.cbjf.domain.entity.Post;
import com.cbjf.service.CourseService;
import com.cbjf.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 홈 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseService courseService;
    private final PostService postService;

    /**
     * 메인 페이지
     */
    @GetMapping("/")
    public String home(Model model) {
        // 인기 강의 조회
        List<Course> popularCourses = courseService.findPopular(6);

        // 최신 강의 조회
        List<Course> latestCourses = courseService.findLatest(6);

        // 공지사항 조회
        List<Post> notices = postService.findNotices();

        model.addAttribute("popularCourses", popularCourses);
        model.addAttribute("latestCourses", latestCourses);
        model.addAttribute("notices", notices);

        return "index";
    }
}
