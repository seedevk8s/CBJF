package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Category;
import com.cbjf.domain.entity.Course;
import com.cbjf.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 강의 Repository
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * 카테고리별 강의 목록 조회
     */
    List<Course> findByCategory(Category category);

    /**
     * 강사별 강의 목록 조회
     */
    List<Course> findByInstructor(User instructor);

    /**
     * 공개된 강의 목록 조회 (페이징)
     */
    Page<Course> findByPublished(Boolean published, Pageable pageable);

    /**
     * 카테고리별 공개 강의 목록 조회 (페이징)
     */
    Page<Course> findByCategoryAndPublished(Category category, Boolean published, Pageable pageable);

    /**
     * 인기 강의 조회 (수강생 수 기준)
     */
    @Query("SELECT c FROM Course c WHERE c.published = true ORDER BY c.enrollmentCount DESC")
    List<Course> findPopularCourses(Pageable pageable);

    /**
     * 높은 평점 강의 조회
     */
    @Query("SELECT c FROM Course c WHERE c.published = true ORDER BY c.rating DESC")
    List<Course> findTopRatedCourses(Pageable pageable);

    /**
     * 제목으로 강의 검색
     */
    @Query("SELECT c FROM Course c WHERE c.published = true AND c.title LIKE %:keyword%")
    Page<Course> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 최신 강의 조회
     */
    @Query("SELECT c FROM Course c WHERE c.published = true ORDER BY c.createdAt DESC")
    List<Course> findLatestCourses(Pageable pageable);
}
