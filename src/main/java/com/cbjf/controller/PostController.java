package com.cbjf.controller;

import com.cbjf.domain.entity.Post;
import com.cbjf.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판 컨트롤러
 */
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시판 목록 페이지
     */
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) Post.PostCategory category,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            postPage = postService.search(keyword, pageable);
            model.addAttribute("keyword", keyword);
        } else if (category != null) {
            postPage = postService.findByCategory(category, pageable);
            model.addAttribute("selectedCategory", category);
        } else {
            postPage = postService.findAll(pageable);
        }

        model.addAttribute("postPage", postPage);
        model.addAttribute("categories", Post.PostCategory.values());

        return "community/list";
    }

    /**
     * 게시글 상세 페이지
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "community/detail";
    }
}
