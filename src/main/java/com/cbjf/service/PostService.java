package com.cbjf.service;

import com.cbjf.domain.entity.Post;
import com.cbjf.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시글 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    /**
     * 전체 게시글 조회 (페이징)
     */
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * 카테고리별 게시글 조회
     */
    public Page<Post> findByCategory(Post.PostCategory category, Pageable pageable) {
        return postRepository.findByCategory(category, pageable);
    }

    /**
     * ID로 게시글 조회
     */
    @Transactional
    public Post findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
        post.increaseViewCount(); // 조회수 증가
        return post;
    }

    /**
     * 공지사항 조회
     */
    public List<Post> findNotices() {
        return postRepository.findByNotice(true);
    }

    /**
     * 인기 게시글 조회
     */
    public List<Post> findPopular(int limit) {
        return postRepository.findPopularPosts(PageRequest.of(0, limit));
    }

    /**
     * 게시글 검색
     */
    public Page<Post> search(String keyword, Pageable pageable) {
        return postRepository.searchByTitle(keyword, pageable);
    }

    /**
     * 게시글 작성
     */
    @Transactional
    public Post create(Post post) {
        return postRepository.save(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Post update(Long id, Post updatedPost) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setCategory(updatedPost.getCategory());
        return post;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    /**
     * 좋아요 증가
     */
    @Transactional
    public void increaseLike(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
        post.increaseLikeCount();
    }
}
