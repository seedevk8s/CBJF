package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Course;
import com.cbjf.domain.entity.Enrollment;
import com.cbjf.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 수강 등록 Repository
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * 사용자별 수강 목록 조회
     */
    List<Enrollment> findByUser(User user);

    /**
     * 강의별 수강 목록 조회
     */
    List<Enrollment> findByCourse(Course course);

    /**
     * 사용자와 강의로 수강 정보 조회
     */
    Optional<Enrollment> findByUserAndCourse(User user, Course course);

    /**
     * 사용자의 수강 상태별 목록 조회
     */
    List<Enrollment> findByUserAndStatus(User user, Enrollment.EnrollmentStatus status);

    /**
     * 수강 여부 확인
     */
    boolean existsByUserAndCourse(User user, Course course);
}
