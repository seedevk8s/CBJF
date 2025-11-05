package com.cbjf.service;

import com.cbjf.domain.entity.Category;
import com.cbjf.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 카테고리 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 전체 카테고리 조회
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * ID로 카테고리 조회
     */
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + id));
    }

    /**
     * 카테고리 생성
     */
    @Transactional
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public Category update(Long id, Category updatedCategory) {
        Category category = findById(id);
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        return category;
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
