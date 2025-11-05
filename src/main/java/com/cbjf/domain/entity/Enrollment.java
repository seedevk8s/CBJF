package com.cbjf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 수강 등록 엔티티
 * 학생이 특정 강의를 수강 등록한 정보
 */
@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    @Column
    private LocalDateTime completedAt;

    @Column(nullable = false)
    @Builder.Default
    private Integer progress = 0; // 진도율 (0-100)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EnrollmentStatus status = EnrollmentStatus.IN_PROGRESS;

    /**
     * 수강 상태 Enum
     */
    public enum EnrollmentStatus {
        IN_PROGRESS,  // 수강 중
        COMPLETED,    // 수강 완료
        DROPPED       // 수강 중단
    }

    /**
     * 강의 완료 처리
     */
    public void complete() {
        this.status = EnrollmentStatus.COMPLETED;
        this.progress = 100;
        this.completedAt = LocalDateTime.now();
    }
}
