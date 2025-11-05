package com.cbjf.domain.repository;

import com.cbjf.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 카테고리 Repository
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 이름으로 카테고리 조회
     */
    Optional<Category> findByName(String name);

    /**
     * 카테고리명 존재 여부 확인
     */
    boolean existsByName(String name);
}
