# Pull Request 정보

## 제목
IT 학원 모집용 온라인 교육 플랫폼 구현 (MySQL 버전)

## Base Branch
main

## Head Branch
claude/recruitment-demo-website-011CUp4NMnDp6cMNwdjqNsEF

## PR 본문

## 📚 프로젝트 개요

IT 학원 모집을 위한 **6개월 자바 풀스택 과정 수료 후 제작 가능한 실전 프로젝트**

온라인 IT 교육 플랫폼 + 커뮤니티 형태의 완전한 웹 애플리케이션입니다.

## 🎯 주요 기능

### 1. 강의 관리 시스템
- ✅ 강의 목록/상세 페이지 (페이징, 정렬)
- ✅ 카테고리별 필터링 (Java, Spring, Frontend, Database, DevOps)
- ✅ 강의 검색 기능
- ✅ 수강 후기 및 평점 시스템 (1-5점)

### 2. 장바구니 & 주문
- ✅ 장바구니 담기/제거
- ✅ 주문 시스템 (데모)
- ✅ 주문 내역 조회

### 3. 커뮤니티
- ✅ 게시판 (공지, Q&A, 자유, 스터디)
- ✅ 게시글 CRUD
- ✅ 댓글 시스템
- ✅ 좋아요 및 조회수

### 4. 마이페이지
- ✅ 수강 강의 조회
- ✅ 학습 진도율 관리 (0-100%)
- ✅ 주문 내역
- ✅ 포인트 시스템

## 🛠 기술 스택

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- Bootstrap 5.3
- Bootstrap Icons

### Database
- **MySQL 8.0+** (프로덕션 환경 대응)

## 📁 프로젝트 구조

- **11개 엔티티**: User, Course, Category, Enrollment, Review, Cart, CartItem, Order, OrderItem, Post, Comment
- **11개 Repository**: JPA 기반 데이터 접근
- **7개 Service**: 비즈니스 로직 처리
- **5개 Controller**: 웹 요청 처리
- **반응형 Thymeleaf 템플릿**: Bootstrap 기반 UI

## 📊 데모 데이터

애플리케이션 시작 시 자동 생성:
- 사용자 6명 (학생 3, 강사 2, 관리자 1)
- 강의 12개 (5개 카테고리)
- 수강 등록, 리뷰, 게시글, 댓글

## 🔄 주요 변경사항

### Commit 1: 초기 구현
- Spring Boot 프로젝트 구조 생성
- 11개 엔티티 및 Repository 구현
- 7개 Service 레이어 구현
- 5개 Controller 구현
- Bootstrap 기반 Thymeleaf 템플릿
- 데모 데이터 자동 로딩

### Commit 2: MySQL 전환
- H2 인메모리 DB → MySQL 8.0+
- `hibernate.ddl-auto`: create-drop → update (데이터 보존)
- MySQL 설치 및 설정 가이드 추가

## 💡 학습 포인트

이 프로젝트를 통해 다음을 학습할 수 있습니다:
- Spring Boot 3계층 아키텍처
- JPA 엔티티 설계 및 연관관계 매핑
- Thymeleaf 템플릿 엔진
- Bootstrap 반응형 디자인
- 쇼핑몰 시스템 구현
- 커뮤니티 기능 구현

## 🚀 실행 방법

### MySQL 데이터베이스 생성
```sql
CREATE DATABASE cbjfdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### application.properties 설정
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 빌드 및 실행
```bash
mvn clean package
mvn spring-boot:run
```

### 접속
http://localhost:8080

## 📈 통계

- **55개 파일** 생성
- **4,256줄** 코드 작성
- **완전한 풀스택 웹 애플리케이션**

## ✅ 체크리스트

- [x] Spring Boot 프로젝트 구조
- [x] 도메인 엔티티 (11개)
- [x] Repository 레이어 (11개)
- [x] Service 레이어 (7개)
- [x] Controller 레이어 (5개)
- [x] Thymeleaf 템플릿
- [x] Bootstrap UI
- [x] 데모 데이터 로더
- [x] MySQL 데이터베이스 설정
- [x] README 문서화

---

**🎉 6개월 자바 풀스택 과정의 결과물을 확인하세요!**
