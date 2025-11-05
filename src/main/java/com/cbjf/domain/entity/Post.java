package com.cbjf.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 엔티티
 * 커뮤니티 게시판의 게시글
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostCategory category;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0; // 조회수

    @Column(nullable = false)
    @Builder.Default
    private Integer likeCount = 0; // 좋아요 수

    @Column(nullable = false)
    @Builder.Default
    private Boolean notice = false; // 공지사항 여부

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    /**
     * 게시글 카테고리 Enum
     */
    public enum PostCategory {
        NOTICE,     // 공지사항
        QNA,        // 질문답변
        FREE,       // 자유게시판
        STUDY       // 스터디
    }

    /**
     * 조회수 증가
     */
    public void increaseViewCount() {
        this.viewCount++;
    }

    /**
     * 좋아요 증가
     */
    public void increaseLikeCount() {
        this.likeCount++;
    }
}
