package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Post;
import com.cbjf.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시글 Repository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 카테고리별 게시글 목록 조회 (페이징)
     */
    Page<Post> findByCategory(Post.PostCategory category, Pageable pageable);

    /**
     * 작성자별 게시글 목록 조회
     */
    List<Post> findByAuthor(User author);

    /**
     * 공지사항 조회
     */
    List<Post> findByNotice(Boolean notice);

    /**
     * 제목으로 게시글 검색 (페이징)
     */
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    Page<Post> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 인기 게시글 조회 (조회수 기준)
     */
    @Query("SELECT p FROM Post p ORDER BY p.viewCount DESC")
    List<Post> findPopularPosts(Pageable pageable);
}
