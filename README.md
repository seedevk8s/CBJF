# 댕댕이샵 - 반려견 쇼핑몰

## 🐕 프로젝트 소개

**댕댕이샵**은 반려견을 위한 온라인 쇼핑몰입니다. 프리미엄 사료, 건강한 간식, 재미있는 장난감, 필수 용품, 건강관리 제품까지 반려견에게 필요한 모든 것을 한 곳에서 만나보세요.

Spring Boot를 활용한 풀스택 이커머스 플랫폼 + 반려인 커뮤니티로 구성되어 있으며, 웹 애플리케이션의 모든 요소를 포함하고 있습니다.

## 🎯 주요 기능

### 1. 메인 페이지
- 쇼핑몰 소개 및 통계 정보 (반려가족, 상품 수, 평점)
- 인기 상품 및 신상품 조회
- 공지사항 확인
- CTA(Call-to-Action) 섹션

### 2. 상품 관리
- 상품 목록 조회 (페이징, 정렬)
- 카테고리별 상품 필터링 (사료, 간식, 장난감, 용품, 건강관리)
- 상품 검색 기능
- 상품 상세 정보 (설명, 가격, 배송, 등급, 평점)
- 판매자 정보 표시
- 상품 후기 및 평점 시스템 (1-5점)

### 3. 장바구니 & 주문
- 장바구니에 상품 담기/제거
- 주문 요약 및 총 금액 계산
- 주문 프로세스 (데모)

### 4. 반려인 커뮤니티
- 게시판 (공지사항, Q&A, 자유게시판, 모임)
- 게시글 작성, 조회, 검색
- 댓글 시스템
- 좋아요 및 조회수 기능

### 5. 마이페이지
- 구매 내역 조회
- 배송 진행률 확인
- 주문 내역 조회
- 적립금 관리
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
- **MySQL 8.0+** - 관계형 데이터베이스
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
│   │   ├── CourseController.java        # 상품 관리
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
│       ├── courses/                     # 상품 페이지
│       ├── cart/                        # 장바구니
│       ├── community/                   # 커뮤니티
│       └── mypage/                      # 마이페이지
└── pom.xml                              # Maven 설정
```

## 🚀 실행 방법

### 1. 사전 요구사항
- JDK 17 이상
- Maven 3.6 이상
- **MySQL 8.0 이상**

### 2. MySQL 데이터베이스 설정

MySQL을 설치하고 데이터베이스를 생성합니다:

```sql
# MySQL 접속
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE cbjfdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 사용자 생성 및 권한 부여 (선택사항)
CREATE USER 'cbjfuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON cbjfdb.* TO 'cbjfuser'@'localhost';
FLUSH PRIVILEGES;

# 종료
EXIT;
```

**application.properties 설정 확인**

`src/main/resources/application.properties` 파일에서 데이터베이스 설정을 확인하고 필요시 수정:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cbjfdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. 프로젝트 클론
```bash
git clone https://github.com/seedevk8s/CBJF.git
cd CBJF
```

### 4. 빌드 및 실행
```bash
# Maven 빌드
mvn clean package

# 애플리케이션 실행
mvn spring-boot:run
```

### 5. 접속
- 메인: http://localhost:8080
- 데모 데이터가 자동으로 로드됩니다

## 📊 데모 데이터

애플리케이션 시작 시 자동으로 생성되는 데이터:

- **사용자**: 6명 (고객 3명, 판매자 2명, 관리자 1명)
- **카테고리**: 5개 (사료, 간식, 장난감, 용품, 건강관리)
- **상품**: 12개 (다양한 반려견 용품)
- **구매 내역**: 고객별 3-5개 상품
- **상품 후기**: 상품별 2-5개 (4-5점 평점)
- **게시글**: 8개 (공지 2, Q&A 2, 자유 2, 모임 2)
- **댓글**: 게시글별 0-5개

### 테스트 사용자

| 사용자명 | 역할 | 이름 | 이메일 |
|---------|------|------|--------|
| customer1 | 고객 | 김반려 | customer1@dogshop.com |
| seller1 | 판매자 | 댕댕펫샵 | seller1@dogshop.com |
| admin | 관리자 | 관리자 | admin@dogshop.com |

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
- 이커머스 형태의 장바구니/주문 시스템
- 커뮤니티 게시판 및 댓글
- 구매 관리 및 배송 진행률 추적
- 평점 및 리뷰 시스템

## 📈 향후 개선 방향

- [ ] Spring Security 인증/인가
- [ ] 실제 결제 시스템 연동 (PG)
- [ ] 파일 업로드 (상품 이미지, 리뷰 사진)
- [ ] 이메일 알림 (주문 확인, 배송 알림)
- [ ] 관리자 페이지 (상품 관리, 주문 관리)
- [ ] REST API 문서화 (Swagger)
- [ ] 테스트 코드 작성
- [ ] React/Vue 프론트엔드 분리

## 🌟 프로젝트 특징

✅ **실무 중심**: 실제 쇼핑몰 기능 구현
✅ **풀스택**: Backend + Frontend + Database 전체 스택
✅ **모범 사례**: 3계층 아키텍처, Entity 설계, 코드 품질
✅ **포트폴리오**: 취업 지원 시 활용 가능한 실전 프로젝트
✅ **확장 가능**: 추가 기능 구현 가능한 구조

## 📞 문의

- Email: contact@dogshop.com
- Tel: 02-1234-5678
- 주소: 서울시 강남구

---

**🐶 우리 아이에게 최고만을! 댕댕이샵에서 만나요!**
