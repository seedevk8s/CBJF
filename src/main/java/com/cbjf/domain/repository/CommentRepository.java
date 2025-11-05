package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Comment;
import com.cbjf.domain.entity.Post;
import com.cbjf.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 Repository
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 게시글별 댓글 목록 조회
     */
    List<Comment> findByPost(Post post);

    /**
     * 작성자별 댓글 목록 조회
     */
    List<Comment> findByAuthor(User author);

    /**
     * 게시글의 삭제되지 않은 댓글 조회
     */
    List<Comment> findByPostAndDeleted(Post post, Boolean deleted);
}
