package com.cbjf.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 강의 엔티티
 * IT 교육 과정 정보
 */
@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer price;

    @Column(length = 50)
    private String duration; // 수강 기간 (예: 6개월, 3개월)

    @Column(length = 20)
    private String level; // 난이도 (초급, 중급, 고급)

    @Column(length = 500)
    private String thumbnailUrl; // 썸네일 이미지 URL

    @Column(nullable = false)
    @Builder.Default
    private Integer enrollmentCount = 0; // 수강생 수

    @Column(nullable = false)
    @Builder.Default
    private Boolean published = true; // 공개 여부

    @Column(columnDefinition = "double default 0.0")
    @Builder.Default
    private Double rating = 0.0; // 평점

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Enrollment> enrollments = new ArrayList<>();

    /**
     * 수강생 수 증가
     */
    public void increaseEnrollmentCount() {
        this.enrollmentCount++;
    }

    /**
     * 평점 계산 (리뷰 기반)
     */
    public void updateRating() {
        if (reviews.isEmpty()) {
            this.rating = 0.0;
        } else {
            double sum = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .sum();
            this.rating = sum / reviews.size();
        }
    }
}
