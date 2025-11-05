package com.cbjf.controller;

import com.cbjf.domain.entity.Category;
import com.cbjf.domain.entity.Course;
import com.cbjf.service.CategoryService;
import com.cbjf.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 강의 컨트롤러
 */
@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    /**
     * 강의 목록 페이지
     */
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> coursePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            coursePage = courseService.search(keyword, pageable);
            model.addAttribute("keyword", keyword);
        } else if (categoryId != null) {
            Category category = categoryService.findById(categoryId);
            coursePage = courseService.findByCategory(category, pageable);
            model.addAttribute("selectedCategory", category);
        } else {
            coursePage = courseService.findPublished(pageable);
        }

        List<Category> categories = categoryService.findAll();

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("categories", categories);

        return "courses/list";
    }

    /**
     * 강의 상세 페이지
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        return "courses/detail";
    }
}
