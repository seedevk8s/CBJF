package com.cbjf.service;

import com.cbjf.domain.entity.Course;
import com.cbjf.domain.entity.Enrollment;
import com.cbjf.domain.entity.User;
import com.cbjf.domain.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 수강 등록 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;

    /**
     * 사용자별 수강 목록 조회
     */
    public List<Enrollment> findByUser(User user) {
        return enrollmentRepository.findByUser(user);
    }

    /**
     * 수강 등록
     */
    @Transactional
    public Enrollment enroll(User user, Long courseId) {
        Course course = courseService.findById(courseId);

        // 이미 수강 중인지 확인
        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new IllegalArgumentException("이미 수강 중인 강의입니다");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .progress(0)
                .status(Enrollment.EnrollmentStatus.IN_PROGRESS)
                .build();

        // 강의 수강생 수 증가
        course.increaseEnrollmentCount();

        return enrollmentRepository.save(enrollment);
    }

    /**
     * 수강 진도 업데이트
     */
    @Transactional
    public void updateProgress(Long enrollmentId, Integer progress) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("수강 정보를 찾을 수 없습니다"));

        enrollment.setProgress(progress);

        if (progress >= 100) {
            enrollment.complete();
        }
    }

    /**
     * 수강 완료
     */
    @Transactional
    public void complete(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("수강 정보를 찾을 수 없습니다"));
        enrollment.complete();
    }
}
