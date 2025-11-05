package com.cbjf.config;

import com.cbjf.domain.entity.*;
import com.cbjf.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 데모 데이터 로더
 * 애플리케이션 시작 시 샘플 데이터를 생성합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final Random random = new Random();

    @Override
    @Transactional
    public void run(String... args) {
        log.info("========================================");
        log.info("데모 데이터 로딩 시작...");
        log.info("========================================");

        // 데이터가 이미 존재하는지 확인
        if (userRepository.count() > 0) {
            log.info("데모 데이터가 이미 존재합니다. 데이터 로딩을 건너뜁니다.");
            log.info("========================================");
            return;
        }

        // 사용자 생성
        List<User> users = createUsers();
        log.info("✓ 사용자 {} 명 생성 완료", users.size());

        // 카테고리 생성
        List<Category> categories = createCategories();
        log.info("✓ 카테고리 {} 개 생성 완료", categories.size());

        // 강의 생성
        List<Course> courses = createCourses(categories, users);
        log.info("✓ 강의 {} 개 생성 완료", courses.size());

        // 수강 등록 생성
        List<Enrollment> enrollments = createEnrollments(users, courses);
        log.info("✓ 수강 등록 {} 개 생성 완료", enrollments.size());

        // 수강 후기 생성
        List<Review> reviews = createReviews(users, courses);
        log.info("✓ 수강 후기 {} 개 생성 완료", reviews.size());

        // 게시글 생성
        List<Post> posts = createPosts(users);
        log.info("✓ 게시글 {} 개 생성 완료", posts.size());

        // 댓글 생성
        List<Comment> comments = createComments(users, posts);
        log.info("✓ 댓글 {} 개 생성 완료", comments.size());

        log.info("========================================");
        log.info("데모 데이터 로딩 완료!");
        log.info("========================================");
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        // 학생 사용자
        users.add(User.builder()
                .username("student1")
                .password("password")
                .name("김학생")
                .email("student1@cbjf.com")
                .phone("010-1111-1111")
                .role(User.UserRole.STUDENT)
                .enabled(true)
                .points(5000)
                .build());

        users.add(User.builder()
                .username("student2")
                .password("password")
                .name("이학생")
                .email("student2@cbjf.com")
                .phone("010-2222-2222")
                .role(User.UserRole.STUDENT)
                .enabled(true)
                .points(3000)
                .build());

        users.add(User.builder()
                .username("student3")
                .password("password")
                .name("박학생")
                .email("student3@cbjf.com")
                .phone("010-3333-3333")
                .role(User.UserRole.STUDENT)
                .enabled(true)
                .points(2000)
                .build());

        // 강사 사용자
        users.add(User.builder()
                .username("instructor1")
                .password("password")
                .name("최강사")
                .email("instructor1@cbjf.com")
                .phone("010-4444-4444")
                .role(User.UserRole.INSTRUCTOR)
                .enabled(true)
                .points(0)
                .build());

        users.add(User.builder()
                .username("instructor2")
                .password("password")
                .name("정강사")
                .email("instructor2@cbjf.com")
                .phone("010-5555-5555")
                .role(User.UserRole.INSTRUCTOR)
                .enabled(true)
                .points(0)
                .build());

        // 관리자
        users.add(User.builder()
                .username("admin")
                .password("password")
                .name("관리자")
                .email("admin@cbjf.com")
                .phone("010-0000-0000")
                .role(User.UserRole.ADMIN)
                .enabled(true)
                .points(0)
                .build());

        return userRepository.saveAll(users);
    }

    private List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();

        categories.add(Category.builder()
                .name("Java")
                .description("Java 프로그래밍 과정")
                .build());

        categories.add(Category.builder()
                .name("Spring")
                .description("Spring Framework 과정")
                .build());

        categories.add(Category.builder()
                .name("Frontend")
                .description("웹 프론트엔드 과정")
                .build());

        categories.add(Category.builder()
                .name("Database")
                .description("데이터베이스 과정")
                .build());

        categories.add(Category.builder()
                .name("DevOps")
                .description("DevOps & 클라우드 과정")
                .build());

        return categoryRepository.saveAll(categories);
    }

    private List<Course> createCourses(List<Category> categories, List<User> users) {
        List<Course> courses = new ArrayList<>();
        List<User> instructors = users.stream()
                .filter(u -> u.getRole() == User.UserRole.INSTRUCTOR)
                .toList();

        // Java 과정
        courses.add(Course.builder()
                .title("Java 기초부터 실전까지")
                .description("Java의 기초 문법부터 객체지향 프로그래밍, 컬렉션 프레임워크까지 학습하는 완전 기초 과정입니다. 프로그래밍을 처음 시작하는 분들도 쉽게 따라올 수 있도록 구성되었습니다.")
                .category(categories.get(0))
                .instructor(instructors.get(0))
                .price(250000)
                .duration("2개월")
                .level("초급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/4CAF50/FFFFFF?text=Java+Basic") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(156)
                .rating(4.8)
                .build());

        courses.add(Course.builder()
                .title("Java 고급 프로그래밍")
                .description("멀티스레딩, 네트워크 프로그래밍, 람다 표현식, 스트림 API 등 Java의 고급 기능을 심도있게 다룹니다.")
                .category(categories.get(0))
                .instructor(instructors.get(0))
                .price(300000)
                .duration("2개월")
                .level("고급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/FF9800/FFFFFF?text=Java+Advanced") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(89)
                .rating(4.9)
                .build());

        // Spring 과정
        courses.add(Course.builder()
                .title("Spring Boot 완전정복")
                .description("Spring Boot를 활용한 웹 애플리케이션 개발을 처음부터 끝까지 학습합니다. REST API, JPA, Security 등 실무에 필요한 모든 것을 다룹니다.")
                .category(categories.get(1))
                .instructor(instructors.get(1))
                .price(400000)
                .duration("3개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/2196F3/FFFFFF?text=Spring+Boot") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(234)
                .rating(4.9)
                .build());

        courses.add(Course.builder()
                .title("Spring Cloud 마이크로서비스")
                .description("Spring Cloud를 활용한 마이크로서비스 아키텍처 구축을 학습합니다. 실전 프로젝트를 통해 MSA를 완벽하게 이해할 수 있습니다.")
                .category(categories.get(1))
                .instructor(instructors.get(1))
                .price(500000)
                .duration("3개월")
                .level("고급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/9C27B0/FFFFFF?text=Spring+Cloud") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(67)
                .rating(4.7)
                .build());

        // Frontend 과정
        courses.add(Course.builder()
                .title("HTML/CSS/JavaScript 기초")
                .description("웹 개발의 기초인 HTML, CSS, JavaScript를 체계적으로 학습합니다. 반응형 웹 디자인과 모던 JavaScript 문법을 익힙니다.")
                .category(categories.get(2))
                .instructor(instructors.get(0))
                .price(200000)
                .duration("2개월")
                .level("초급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/F44336/FFFFFF?text=Web+Basic") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(312)
                .rating(4.6)
                .build());

        courses.add(Course.builder()
                .title("React 완벽 마스터")
                .description("React를 활용한 SPA 개발을 학습합니다. Hooks, Redux, TypeScript와 함께 실무 프로젝트를 진행합니다.")
                .category(categories.get(2))
                .instructor(instructors.get(1))
                .price(350000)
                .duration("2개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/00BCD4/FFFFFF?text=React") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(178)
                .rating(4.8)
                .build());

        courses.add(Course.builder()
                .title("Vue.js 3.0 실전 가이드")
                .description("Vue.js 3.0의 Composition API를 활용한 모던 웹 개발을 학습합니다.")
                .category(categories.get(2))
                .instructor(instructors.get(0))
                .price(320000)
                .duration("2개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/4CAF50/FFFFFF?text=Vue.js") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(95)
                .rating(4.5)
                .build());

        // Database 과정
        courses.add(Course.builder()
                .title("MySQL 데이터베이스 마스터")
                .description("MySQL의 기초부터 고급 쿼리 작성, 인덱싱, 성능 튜닝까지 데이터베이스의 모든 것을 학습합니다.")
                .category(categories.get(3))
                .instructor(instructors.get(1))
                .price(280000)
                .duration("2개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/FF5722/FFFFFF?text=MySQL") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(145)
                .rating(4.7)
                .build());

        courses.add(Course.builder()
                .title("MongoDB NoSQL 완전정복")
                .description("NoSQL 데이터베이스인 MongoDB를 활용한 데이터 모델링과 쿼리 작성을 학습합니다.")
                .category(categories.get(3))
                .instructor(instructors.get(0))
                .price(300000)
                .duration("2개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/4CAF50/FFFFFF?text=MongoDB") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(78)
                .rating(4.6)
                .build());

        // DevOps 과정
        courses.add(Course.builder()
                .title("Docker & Kubernetes 실전")
                .description("컨테이너 기술인 Docker와 오케스트레이션 도구 Kubernetes를 실전 프로젝트로 학습합니다.")
                .category(categories.get(4))
                .instructor(instructors.get(1))
                .price(450000)
                .duration("3개월")
                .level("고급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/2196F3/FFFFFF?text=Docker+K8s") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(112)
                .rating(4.8)
                .build());

        courses.add(Course.builder()
                .title("AWS 클라우드 마스터")
                .description("AWS의 주요 서비스(EC2, S3, RDS, Lambda 등)를 활용한 클라우드 인프라 구축을 학습합니다.")
                .category(categories.get(4))
                .instructor(instructors.get(0))
                .price(420000)
                .duration("3개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/FF9800/FFFFFF?text=AWS") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(203)
                .rating(4.9)
                .build());

        courses.add(Course.builder()
                .title("CI/CD 파이프라인 구축")
                .description("Jenkins, GitLab CI, GitHub Actions를 활용한 자동화된 배포 파이프라인을 구축합니다.")
                .category(categories.get(4))
                .instructor(instructors.get(1))
                .price(380000)
                .duration("2개월")
                .level("중급")
                // .thumbnailUrl("https://via.placeholder.com/400x200/9C27B0/FFFFFF?text=CI+CD") // 로컬 placeholder 사용
                .published(true)
                .enrollmentCount(134)
                .rating(4.7)
                .build());

        return courseRepository.saveAll(courses);
    }

    private List<Enrollment> createEnrollments(List<User> users, List<Course> courses) {
        List<Enrollment> enrollments = new ArrayList<>();
        List<User> students = users.stream()
                .filter(u -> u.getRole() == User.UserRole.STUDENT)
                .toList();

        // 각 학생마다 랜덤하게 3-5개 강의 수강
        for (User student : students) {
            int courseCount = random.nextInt(3) + 3; // 3-5개
            List<Course> selectedCourses = new ArrayList<>();

            while (selectedCourses.size() < courseCount) {
                Course course = courses.get(random.nextInt(courses.size()));
                if (!selectedCourses.contains(course)) {
                    selectedCourses.add(course);

                    Enrollment enrollment = Enrollment.builder()
                            .user(student)
                            .course(course)
                            .enrolledAt(LocalDateTime.now().minusDays(random.nextInt(60)))
                            .progress(random.nextInt(101)) // 0-100%
                            .status(Enrollment.EnrollmentStatus.IN_PROGRESS)
                            .build();

                    enrollments.add(enrollment);
                }
            }
        }

        return enrollmentRepository.saveAll(enrollments);
    }

    private List<Review> createReviews(List<User> users, List<Course> courses) {
        List<Review> reviews = new ArrayList<>();
        List<User> students = users.stream()
                .filter(u -> u.getRole() == User.UserRole.STUDENT)
                .toList();

        String[] reviewContents = {
                "정말 유익한 강의였습니다! 실무에 바로 적용할 수 있는 내용들이 많아서 좋았어요.",
                "강사님의 설명이 아주 명쾌하고 이해하기 쉬웠습니다. 추천합니다!",
                "초보자도 쉽게 따라갈 수 있도록 구성되어 있어서 좋았습니다.",
                "실전 프로젝트가 포함되어 있어서 실력 향상에 큰 도움이 되었습니다.",
                "기대 이상의 강의였습니다. 6개월 과정 수료 후 취업에 성공했습니다!",
                "체계적인 커리큘럼과 친절한 강의로 실력이 많이 늘었습니다.",
                "이론과 실습의 균형이 잘 맞춰져 있어서 학습하기 좋았습니다."
        };

        // 각 강의마다 랜덤하게 2-5개 리뷰 생성
        for (Course course : courses) {
            int reviewCount = random.nextInt(4) + 2; // 2-5개

            for (int i = 0; i < reviewCount && i < students.size(); i++) {
                Review review = Review.builder()
                        .course(course)
                        .user(students.get(i))
                        .rating(random.nextInt(2) + 4) // 4-5점
                        .content(reviewContents[random.nextInt(reviewContents.length)])
                        .likes(random.nextInt(20))
                        .build();

                reviews.add(review);
            }
        }

        return reviewRepository.saveAll(reviews);
    }

    private List<Post> createPosts(List<User> users) {
        List<Post> posts = new ArrayList<>();

        // 공지사항
        posts.add(Post.builder()
                .author(users.get(5)) // 관리자
                .category(Post.PostCategory.NOTICE)
                .title("CBJF 플랫폼 오픈을 환영합니다!")
                .content("안녕하세요. CBJF(Cloud Based Java Fullstack) 교육 플랫폼이 새롭게 오픈했습니다.\n\n6개월 완성 IT 교육 과정으로 취업까지 책임지겠습니다.\n많은 관심과 참여 부탁드립니다.")
                .notice(true)
                .viewCount(523)
                .likeCount(45)
                .build());

        posts.add(Post.builder()
                .author(users.get(5))
                .category(Post.PostCategory.NOTICE)
                .title("2025년 상반기 수강생 모집 안내")
                .content("2025년 상반기 수강생을 모집합니다.\n\n모집 기간: 2025.01.01 ~ 2025.01.31\n교육 기간: 2025.02.01 ~ 2025.07.31 (6개월)\n\n지금 바로 신청하세요!")
                .notice(true)
                .viewCount(412)
                .likeCount(38)
                .build());

        // Q&A
        posts.add(Post.builder()
                .author(users.get(0))
                .category(Post.PostCategory.QNA)
                .title("Spring Boot JPA 연관관계 질문드립니다")
                .content("JPA에서 @OneToMany와 @ManyToOne 연관관계 설정 시 주의할 점이 있을까요?\n양방향 매핑 시 무한 루프 문제를 어떻게 해결하나요?")
                .viewCount(89)
                .likeCount(5)
                .build());

        posts.add(Post.builder()
                .author(users.get(1))
                .category(Post.PostCategory.QNA)
                .title("Docker 컨테이너 네트워크 설정 문제")
                .content("Docker Compose로 여러 컨테이너를 실행할 때 네트워크 설정을 어떻게 해야 하나요?")
                .viewCount(67)
                .likeCount(3)
                .build());

        // 자유게시판
        posts.add(Post.builder()
                .author(users.get(2))
                .category(Post.PostCategory.FREE)
                .title("6개월 과정 수료 후기")
                .content("드디어 6개월 과정을 수료했습니다!\n처음엔 막막했는데 강사님들의 도움과 열정적인 수업 덕분에 취업에 성공했습니다.\n정말 감사합니다!")
                .viewCount(234)
                .likeCount(28)
                .build());

        posts.add(Post.builder()
                .author(users.get(0))
                .category(Post.PostCategory.FREE)
                .title("개발자 취업 준비 팁 공유합니다")
                .content("제가 취업 준비하면서 도움이 되었던 것들을 공유합니다.\n1. 포트폴리오 프로젝트는 최소 2개 이상\n2. 코딩 테스트 준비는 필수\n3. 기술 면접 대비도 중요합니다!")
                .viewCount(178)
                .likeCount(15)
                .build());

        // 스터디
        posts.add(Post.builder()
                .author(users.get(1))
                .category(Post.PostCategory.STUDY)
                .title("알고리즘 스터디 멤버 모집")
                .content("매주 토요일 오전 10시에 진행하는 알고리즘 스터디 멤버를 모집합니다.\n관심있으신 분들은 댓글 남겨주세요!")
                .viewCount(123)
                .likeCount(12)
                .build());

        posts.add(Post.builder()
                .author(users.get(2))
                .category(Post.PostCategory.STUDY)
                .title("Spring Boot 프로젝트 스터디원 구합니다")
                .content("같이 Spring Boot 프로젝트를 진행할 스터디원을 구합니다.\n포트폴리오 목적으로 실전 프로젝트를 만들어봐요!")
                .viewCount(156)
                .likeCount(18)
                .build());

        return postRepository.saveAll(posts);
    }

    private List<Comment> createComments(List<User> users, List<Post> posts) {
        List<Comment> comments = new ArrayList<>();

        String[] commentContents = {
                "좋은 정보 감사합니다!",
                "저도 같은 문제를 겪었는데 도움이 되었습니다.",
                "참여하고 싶습니다!",
                "축하드립니다!",
                "유익한 글 감사합니다.",
                "저도 관심있습니다!",
                "좋은 의견이네요!"
        };

        // 각 게시글마다 랜덤하게 0-5개 댓글 생성
        for (Post post : posts) {
            if (post.getNotice()) continue; // 공지사항은 댓글 스킵

            int commentCount = random.nextInt(6); // 0-5개

            for (int i = 0; i < commentCount && i < users.size(); i++) {
                Comment comment = Comment.builder()
                        .post(post)
                        .author(users.get(i))
                        .content(commentContents[random.nextInt(commentContents.length)])
                        .deleted(false)
                        .build();

                comments.add(comment);
            }
        }

        return commentRepository.saveAll(comments);
    }
}
