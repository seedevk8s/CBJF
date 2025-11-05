package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Course;
import com.cbjf.domain.entity.Review;
import com.cbjf.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 수강 후기 Repository
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * 강의별 후기 목록 조회
     */
    List<Review> findByCourse(Course course);

    /**
     * 사용자별 후기 목록 조회
     */
    List<Review> findByUser(User user);

    /**
     * 사용자와 강의로 후기 조회
     */
    Optional<Review> findByUserAndCourse(User user, Course course);

    /**
     * 후기 작성 여부 확인
     */
    boolean existsByUserAndCourse(User user, Course course);
}
