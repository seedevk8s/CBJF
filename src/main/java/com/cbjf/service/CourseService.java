package com.cbjf.service;

import com.cbjf.domain.entity.Category;
import com.cbjf.domain.entity.Course;
import com.cbjf.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 강의 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * 전체 강의 조회 (페이징)
     */
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    /**
     * 공개 강의 조회 (페이징)
     */
    public Page<Course> findPublished(Pageable pageable) {
        return courseRepository.findByPublished(true, pageable);
    }

    /**
     * ID로 강의 조회
     */
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다: " + id));
    }

    /**
     * 카테고리별 강의 조회
     */
    public Page<Course> findByCategory(Category category, Pageable pageable) {
        return courseRepository.findByCategoryAndPublished(category, true, pageable);
    }

    /**
     * 인기 강의 조회
     */
    public List<Course> findPopular(int limit) {
        return courseRepository.findPopularCourses(PageRequest.of(0, limit));
    }

    /**
     * 최신 강의 조회
     */
    public List<Course> findLatest(int limit) {
        return courseRepository.findLatestCourses(PageRequest.of(0, limit));
    }

    /**
     * 높은 평점 강의 조회
     */
    public List<Course> findTopRated(int limit) {
        return courseRepository.findTopRatedCourses(PageRequest.of(0, limit));
    }

    /**
     * 강의 검색
     */
    public Page<Course> search(String keyword, Pageable pageable) {
        return courseRepository.searchByTitle(keyword, pageable);
    }

    /**
     * 강의 등록
     */
    @Transactional
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    /**
     * 강의 수정
     */
    @Transactional
    public Course update(Long id, Course updatedCourse) {
        Course course = findById(id);
        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());
        course.setDuration(updatedCourse.getDuration());
        course.setLevel(updatedCourse.getLevel());
        course.setThumbnailUrl(updatedCourse.getThumbnailUrl());
        return course;
    }

    /**
     * 강의 삭제
     */
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    /**
     * 강의 공개/비공개 전환
     */
    @Transactional
    public void togglePublish(Long id) {
        Course course = findById(id);
        course.setPublished(!course.getPublished());
    }
}
