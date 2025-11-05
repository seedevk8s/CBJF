# CBJF (Cloud-Based Java Fullstack) IT 교육 플랫폼

## 📚 프로젝트 소개

**CBJF**는 IT 학원 모집을 위한 데모 웹사이트로, **6개월 자바 풀스택 과정을 수료하면 이런 사이트를 만들 수 있다**는 것을 보여주는 실전 프로젝트입니다.

온라인 IT 교육 플랫폼 + 커뮤니티 형태로 구성되어 있으며, Spring Boot를 활용한 풀스택 웹 애플리케이션의 모든 요소를 포함하고 있습니다.

## 🎯 주요 기능

### 1. 메인 페이지
- 학원 소개 및 통계 정보 (수강생, 강의, 취업률)
- 인기 강의 및 최신 강의 조회
- 공지사항 확인
- CTA(Call-to-Action) 섹션

### 2. 강의 관리
- 강의 목록 조회 (페이징, 정렬)
- 카테고리별 강의 필터링 (Java, Spring, Frontend, Database, DevOps)
- 강의 검색 기능
- 강의 상세 정보 (설명, 가격, 기간, 난이도, 평점)
- 강사 정보 표시
- 수강 후기 및 평점 시스템 (1-5점)

### 3. 장바구니 & 결제
- 장바구니에 강의 담기/제거
- 주문 요약 및 총 금액 계산
- 결제 프로세스 (데모)

### 4. 커뮤니티
- 게시판 (공지사항, Q&A, 자유게시판, 스터디)
- 게시글 작성, 조회, 검색
- 댓글 시스템
- 좋아요 및 조회수 기능

### 5. 마이페이지
- 내 수강 강의 조회
- 학습 진도율 확인 (0-100%)
- 주문 내역 조회
- 포인트 관리
- 사용자 정보 표시

## 🛠 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** - ORM 및 데이터 접근
- **Spring Web** - RESTful 웹 서비스
- **Spring Validation** - 데이터 검증
- **Lombok** - 보일러플레이트 코드 감소

### Frontend
- **Thymeleaf** - 서버 사이드 템플릿 엔진
- **Bootstrap 5.3** - 반응형 UI 프레임워크
- **Bootstrap Icons** - 아이콘 라이브러리

### Database
- **H2 Database** - 인메모리 데이터베이스 (개발용)
- **Hibernate** - JPA 구현체

### Build Tool
- **Maven** - 의존성 관리 및 빌드 도구

## 📁 프로젝트 구조

```
CBJF/
├── src/main/java/com/cbjf/
│   ├── CbjfApplication.java              # 메인 애플리케이션
│   ├── config/
│   │   ├── JpaConfig.java               # JPA Auditing 설정
│   │   └── DataLoader.java              # 데모 데이터 로더
│   ├── controller/                       # 컨트롤러 레이어
│   │   ├── HomeController.java          # 메인 페이지
│   │   ├── CourseController.java        # 강의 관리
│   │   ├── CartController.java          # 장바구니
│   │   ├── PostController.java          # 커뮤니티
│   │   └── MyPageController.java        # 마이페이지
│   ├── domain/
│   │   ├── entity/                      # 엔티티 (11개)
│   │   └── repository/                  # Repository (11개)
│   └── service/                         # 서비스 레이어 (7개)
├── src/main/resources/
│   ├── application.properties           # 설정 파일
│   ├── static/css/style.css            # 커스텀 CSS
│   └── templates/                       # Thymeleaf 템플릿
│       ├── index.html                   # 메인 페이지
│       ├── layout/                      # 공통 레이아웃
│       ├── courses/                     # 강의 페이지
│       ├── cart/                        # 장바구니
│       ├── community/                   # 커뮤니티
│       └── mypage/                      # 마이페이지
└── pom.xml                              # Maven 설정
```

## 🚀 실행 방법

### 1. 사전 요구사항
- JDK 17 이상
- Maven 3.6 이상

### 2. 프로젝트 클론
```bash
git clone https://github.com/seedevk8s/CBJF.git
cd CBJF
```

### 3. 빌드 및 실행
```bash
# Maven 빌드
mvn clean package

# 애플리케이션 실행
mvn spring-boot:run
```

### 4. 접속
- 메인: http://localhost:8080
- H2 콘솔: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:cbjfdb`
  - Username: `sa`
  - Password: (비워두기)

## 📊 데모 데이터

애플리케이션 시작 시 자동으로 생성되는 데이터:

- **사용자**: 6명 (학생 3명, 강사 2명, 관리자 1명)
- **카테고리**: 5개 (Java, Spring, Frontend, Database, DevOps)
- **강의**: 12개 (다양한 IT 교육 과정)
- **수강 등록**: 학생별 3-5개 강의
- **수강 후기**: 강의별 2-5개 (4-5점 평점)
- **게시글**: 8개 (공지 2, Q&A 2, 자유 2, 스터디 2)
- **댓글**: 게시글별 0-5개

### 테스트 사용자

| 사용자명 | 역할 | 이름 | 이메일 |
|---------|------|------|--------|
| student1 | 학생 | 김학생 | student1@cbjf.com |
| instructor1 | 강사 | 최강사 | instructor1@cbjf.com |
| admin | 관리자 | 관리자 | admin@cbjf.com |

*비밀번호: `password` (모든 계정 동일)*

## 💡 학습 포인트

### Backend
- Spring Boot 3계층 아키텍처 (Controller-Service-Repository)
- JPA 엔티티 설계 및 연관관계 매핑
- JPQL을 활용한 복잡한 쿼리
- 트랜잭션 관리
- 페이징 및 정렬 구현

### Frontend
- Thymeleaf 템플릿 엔진
- Bootstrap 반응형 디자인
- Fragment를 활용한 레이아웃 재사용
- 동적 데이터 렌더링

### Database
- JPA Entity 설계 (11개 엔티티)
- 연관관계 (@OneToMany, @ManyToOne, @OneToOne)
- BaseEntity를 활용한 공통 필드 관리
- 데이터 검증 (Validation)

### 실전 기능
- 쇼핑몰 형태의 장바구니/주문 시스템
- 커뮤니티 게시판 및 댓글
- 수강 관리 및 진도율 추적
- 평점 및 리뷰 시스템

## 🎓 6개월 과정 로드맵

| 기간 | 주제 | 내용 |
|------|------|------|
| 1-2개월 | Java 기초 | 문법, OOP, 컬렉션, 예외처리 |
| 3-4개월 | Spring & DB | Spring Boot, JPA, SQL, REST API |
| 5개월 | Frontend | HTML/CSS/JS, Thymeleaf, Bootstrap |
| 6개월 | 프로젝트 | **CBJF 같은 실전 프로젝트** |

## 📸 스크린샷

### 메인 페이지
- 통계 정보, 인기 강의, 최신 강의, 공지사항

### 강의 목록
- 카테고리 필터, 검색, 페이징

### 강의 상세
- 상세 정보, 강사 정보, 수강 후기, 장바구니 담기

### 커뮤니티
- 게시판, 게시글 상세, 댓글

### 마이페이지
- 내 수강 강의, 진도율, 주문 내역

## 📈 향후 개선 방향

- [ ] Spring Security 인증/인가
- [ ] 실제 결제 시스템 연동
- [ ] 파일 업로드 (강의 자료, 썸네일)
- [ ] 이메일 알림
- [ ] 관리자 페이지
- [ ] REST API 문서화 (Swagger)
- [ ] 테스트 코드 작성
- [ ] React/Vue 프론트엔드 분리

## 🌟 프로젝트 특징

✅ **실무 중심**: 실제 IT 교육 플랫폼 기능 구현
✅ **풀스택**: Backend + Frontend + Database 전체 스택
✅ **모범 사례**: 3계층 아키텍처, Entity 설계, 코드 품질
✅ **포트폴리오**: 취업 지원 시 활용 가능한 실전 프로젝트
✅ **확장 가능**: 추가 기능 구현 가능한 구조

## 📞 문의

- Email: contact@cbjf.com
- Tel: 02-1234-5678
- 주소: 서울시 강남구

---

**🎉 6개월이면 당신도 이런 사이트를 만들 수 있습니다!**
