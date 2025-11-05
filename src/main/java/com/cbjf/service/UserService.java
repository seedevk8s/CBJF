package com.cbjf.service;

import com.cbjf.domain.entity.User;
import com.cbjf.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 전체 사용자 조회
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * ID로 사용자 조회
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
    }

    /**
     * 사용자명으로 사용자 조회
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + username));
    }

    /**
     * 역할별 사용자 조회
     */
    public List<User> findByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    /**
     * 사용자 등록
     */
    @Transactional
    public User register(User user) {
        // 중복 체크
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + user.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public User update(Long id, User updatedUser) {
        User user = findById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        return user;
    }

    /**
     * 포인트 추가
     */
    @Transactional
    public void addPoints(Long userId, Integer points) {
        User user = findById(userId);
        user.setPoints(user.getPoints() + points);
    }

    /**
     * 포인트 차감
     */
    @Transactional
    public void deductPoints(Long userId, Integer points) {
        User user = findById(userId);
        if (user.getPoints() < points) {
            throw new IllegalArgumentException("포인트가 부족합니다");
        }
        user.setPoints(user.getPoints() - points);
    }

    /**
     * 사용자 비활성화
     */
    @Transactional
    public void deactivate(Long userId) {
        User user = findById(userId);
        user.setEnabled(false);
    }

    /**
     * 사용자 활성화
     */
    @Transactional
    public void activate(Long userId) {
        User user = findById(userId);
        user.setEnabled(true);
    }
}
