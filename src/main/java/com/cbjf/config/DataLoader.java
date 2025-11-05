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
        log.info("반려견 쇼핑몰 데모 데이터 로딩 시작...");
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

        // 상품 생성
        List<Course> courses = createCourses(categories, users);
        log.info("✓ 상품 {} 개 생성 완료", courses.size());

        // 주문 내역 생성
        List<Enrollment> enrollments = createEnrollments(users, courses);
        log.info("✓ 주문 내역 {} 개 생성 완료", enrollments.size());

        // 상품 후기 생성
        List<Review> reviews = createReviews(users, courses);
        log.info("✓ 상품 후기 {} 개 생성 완료", reviews.size());

        // 게시글 생성
        List<Post> posts = createPosts(users);
        log.info("✓ 게시글 {} 개 생성 완료", posts.size());

        // 댓글 생성
        List<Comment> comments = createComments(users, posts);
        log.info("✓ 댓글 {} 개 생성 완료", comments.size());

        log.info("========================================");
        log.info("반려견 쇼핑몰 데모 데이터 로딩 완료!");
        log.info("========================================");
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        // 고객 사용자
        users.add(User.builder()
                .username("customer1")
                .password("password")
                .name("김반려")
                .email("customer1@dogshop.com")
                .phone("010-1111-1111")
                .role(User.UserRole.STUDENT)
                .enabled(true)
                .points(5000)
                .build());

        users.add(User.builder()
                .username("customer2")
                .password("password")
                .name("이멍멍")
                .email("customer2@dogshop.com")
                .phone("010-2222-2222")
                .role(User.UserRole.STUDENT)
                .enabled(true)
                .points(3000)
                .build());

        users.add(User.builder()
                .username("customer3")
                .password("password")
                .name("박강아지")
                .email("customer3@dogshop.com")
                .phone("010-3333-3333")
                .role(User.UserRole.STUDENT)
                .enabled(true)
                .points(2000)
                .build());

        // 판매자 사용자
        users.add(User.builder()
                .username("seller1")
                .password("password")
                .name("댕댕펫샵")
                .email("seller1@dogshop.com")
                .phone("010-4444-4444")
                .role(User.UserRole.INSTRUCTOR)
                .enabled(true)
                .points(0)
                .build());

        users.add(User.builder()
                .username("seller2")
                .password("password")
                .name("애견용품마트")
                .email("seller2@dogshop.com")
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
                .email("admin@dogshop.com")
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
                .name("사료")
                .description("건강한 식사를 위한 프리미엄 사료")
                .build());

        categories.add(Category.builder()
                .name("간식")
                .description("영양 간식 및 트릿")
                .build());

        categories.add(Category.builder()
                .name("장난감")
                .description("재미있는 놀이 시간을 위한 장난감")
                .build());

        categories.add(Category.builder()
                .name("용품")
                .description("산책 및 생활 필수 용품")
                .build());

        categories.add(Category.builder()
                .name("건강관리")
                .description("건강 보조제 및 케어 제품")
                .build());

        return categoryRepository.saveAll(categories);
    }

    private List<Course> createCourses(List<Category> categories, List<User> users) {
        List<Course> courses = new ArrayList<>();
        List<User> sellers = users.stream()
                .filter(u -> u.getRole() == User.UserRole.INSTRUCTOR)
                .toList();

        // 사료 카테고리
        courses.add(Course.builder()
                .title("프리미엄 연어 사료 3kg")
                .description("신선한 연어와 야채로 만든 프리미엄 사료입니다. 오메가-3가 풍부하여 피부와 모질 개선에 도움을 줍니다. 소화가 잘되는 저알러지 레시피로 모든 견종에 적합합니다.")
                .category(categories.get(0))
                .instructor(sellers.get(0))
                .price(45000)
                .duration("무료배송")
                .level("전연령")
                .published(true)
                .enrollmentCount(324)
                .rating(4.8)
                .build());

        courses.add(Course.builder()
                .title("유기농 닭고기 사료 5kg")
                .description("유기농 닭고기를 주원료로 한 건강 사료입니다. 곡물 프리로 소화가 잘되며, 관절 건강에 좋은 글루코사민이 함유되어 있습니다.")
                .category(categories.get(0))
                .instructor(sellers.get(0))
                .price(62000)
                .duration("무료배송")
                .level("성견")
                .published(true)
                .enrollmentCount(267)
                .rating(4.9)
                .build());

        // 간식 카테고리
        courses.add(Course.builder()
                .title("수제 육포 간식 500g")
                .description("100% 국내산 소고기로 만든 수제 육포입니다. 인공 첨가물 없이 자연 건조 방식으로 제조하여 안심하고 급여할 수 있습니다. 단백질이 풍부합니다.")
                .category(categories.get(1))
                .instructor(sellers.get(1))
                .price(28000)
                .duration("2-3일")
                .level("전연령")
                .published(true)
                .enrollmentCount(456)
                .rating(4.9)
                .build());

        courses.add(Course.builder()
                .title("치즈 트릿 200g")
                .description("칼슘이 풍부한 치즈로 만든 건강 간식입니다. 작은 사이즈로 훈련용으로 적합하며, 소화가 잘되는 유산균이 함유되어 있습니다.")
                .category(categories.get(1))
                .instructor(sellers.get(0))
                .price(15000)
                .duration("2-3일")
                .level("전연령")
                .published(true)
                .enrollmentCount(389)
                .rating(4.7)
                .build());

        // 장난감 카테고리
        courses.add(Course.builder()
                .title("노즈워크 매트")
                .description("후각 자극 놀이를 위한 노즈워크 매트입니다. 간식을 숨겨 두고 찾는 놀이로 스트레스 해소와 집중력 향상에 도움이 됩니다. 세탁 가능합니다.")
                .category(categories.get(2))
                .instructor(sellers.get(0))
                .price(32000)
                .duration("2-3일")
                .level("전연령")
                .published(true)
                .enrollmentCount(198)
                .rating(4.6)
                .build());

        courses.add(Course.builder()
                .title("튼튼한 로프 장난감")
                .description("질긴 면 소재로 만든 로프 장난감입니다. 이빨 청소 효과가 있으며, 터그 놀이에 적합합니다. 대형견도 사용 가능한 튼튼한 제품입니다.")
                .category(categories.get(2))
                .instructor(sellers.get(1))
                .price(18000)
                .duration("2-3일")
                .level("중대형견")
                .published(true)
                .enrollmentCount(276)
                .rating(4.8)
                .build());

        courses.add(Course.builder()
                .title("삑삑이 봉제 인형")
                .description("귀여운 동물 모양의 봉제 장난감입니다. 삑삑이 소리가 나서 강아지들이 좋아합니다. 안전한 소재로 만들어져 안심하고 사용할 수 있습니다.")
                .category(categories.get(2))
                .instructor(sellers.get(0))
                .price(12000)
                .duration("2-3일")
                .level("소형견")
                .published(true)
                .enrollmentCount(342)
                .rating(4.5)
                .build());

        // 용품 카테고리
        courses.add(Course.builder()
                .title("프리미엄 목줄 세트")
                .description("편안한 착용감의 목줄과 리드줄 세트입니다. 반사 소재로 야간 산책 시 안전하며, 조절 가능한 디자인으로 모든 견종에 사용 가능합니다.")
                .category(categories.get(3))
                .instructor(sellers.get(1))
                .price(35000)
                .duration("무료배송")
                .level("전체")
                .published(true)
                .enrollmentCount(412)
                .rating(4.7)
                .build());

        courses.add(Course.builder()
                .title("스테인레스 식기 세트")
                .description("위생적인 스테인레스 재질의 식기와 물그릇 세트입니다. 미끄럼 방지 바닥으로 안정적이며, 식기세척기 사용 가능합니다.")
                .category(categories.get(3))
                .instructor(sellers.get(0))
                .price(22000)
                .duration("2-3일")
                .level("전체")
                .published(true)
                .enrollmentCount(298)
                .rating(4.6)
                .build());

        // 건강관리 카테고리
        courses.add(Course.builder()
                .title("관절 건강 영양제")
                .description("글루코사민과 MSM이 함유된 관절 건강 보조제입니다. 노령견과 대형견의 관절 건강 유지에 도움을 줍니다. 츄어블 타입으로 급여가 쉽습니다.")
                .category(categories.get(4))
                .instructor(sellers.get(1))
                .price(38000)
                .duration("2-3일")
                .level("노령견")
                .published(true)
                .enrollmentCount(187)
                .rating(4.8)
                .build());

        courses.add(Course.builder()
                .title("피부 건강 오메가3")
                .description("고농축 오메가3 영양제로 피부와 모질 개선에 도움을 줍니다. 알레르기 완화와 면역력 향상 효과가 있으며, 캡슐 형태로 보관이 편리합니다.")
                .category(categories.get(4))
                .instructor(sellers.get(0))
                .price(42000)
                .duration("2-3일")
                .level("전연령")
                .published(true)
                .enrollmentCount(234)
                .rating(4.9)
                .build());

        courses.add(Course.builder()
                .title("덴탈 케어 세트")
                .description("구강 건강을 위한 칫솔과 치약 세트입니다. 치석 제거와 구취 예방에 효과적이며, 강아지 전용 성분으로 안전합니다.")
                .category(categories.get(4))
                .instructor(sellers.get(1))
                .price(25000)
                .duration("2-3일")
                .level("전연령")
                .published(true)
                .enrollmentCount(156)
                .rating(4.7)
                .build());

        return courseRepository.saveAll(courses);
    }

    private List<Enrollment> createEnrollments(List<User> users, List<Course> courses) {
        List<Enrollment> enrollments = new ArrayList<>();
        List<User> customers = users.stream()
                .filter(u -> u.getRole() == User.UserRole.STUDENT)
                .toList();

        // 각 고객마다 랜덤하게 3-5개 상품 구매
        for (User customer : customers) {
            int courseCount = random.nextInt(3) + 3; // 3-5개
            List<Course> selectedCourses = new ArrayList<>();

            while (selectedCourses.size() < courseCount) {
                Course course = courses.get(random.nextInt(courses.size()));
                if (!selectedCourses.contains(course)) {
                    selectedCourses.add(course);

                    Enrollment enrollment = Enrollment.builder()
                            .user(customer)
                            .course(course)
                            .enrolledAt(LocalDateTime.now().minusDays(random.nextInt(60)))
                            .progress(random.nextInt(101)) // 배송 진행률
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
                "우리 강아지가 정말 좋아해요! 배송도 빠르고 품질도 최고입니다.",
                "가격 대비 품질이 훌륭합니다. 재구매 의사 100%입니다!",
                "처음 구매했는데 만족스러워요. 우리 멍멍이가 잘 먹네요.",
                "신선하고 좋은 제품입니다. 포장도 꼼꼼하게 되어 왔어요.",
                "기대 이상의 제품이었습니다. 친구들에게도 추천했어요!",
                "품질 좋고 가격도 합리적입니다. 계속 구매할게요.",
                "우리 강아지가 너무 좋아해서 또 주문했습니다. 강추!"
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
                .title("댕댕이샵 오픈을 환영합니다!")
                .content("안녕하세요. 댕댕이샵이 새롭게 오픈했습니다.\n\n프리미엄 반려견 용품을 합리적인 가격에 만나보세요.\n건강하고 행복한 반려생활을 위해 최선을 다하겠습니다.\n많은 관심과 사랑 부탁드립니다.")
                .notice(true)
                .viewCount(523)
                .likeCount(45)
                .build());

        posts.add(Post.builder()
                .author(users.get(5))
                .category(Post.PostCategory.NOTICE)
                .title("신규 회원 가입 이벤트")
                .content("신규 회원 가입 시 5,000원 적립금을 드립니다!\n\n이벤트 기간: 2025.01.01 ~ 2025.01.31\n첫 구매 시 추가 10% 할인까지!\n\n지금 바로 가입하세요!")
                .notice(true)
                .viewCount(412)
                .likeCount(38)
                .build());

        // Q&A
        posts.add(Post.builder()
                .author(users.get(0))
                .category(Post.PostCategory.QNA)
                .title("강아지 사료 추천 부탁드려요")
                .content("3개월 된 골든 리트리버를 키우고 있는데 어떤 사료가 좋을까요?\n소화가 잘되고 영양가 높은 사료 추천 부탁드립니다!")
                .viewCount(89)
                .likeCount(5)
                .build());

        posts.add(Post.builder()
                .author(users.get(1))
                .category(Post.PostCategory.QNA)
                .title("배송 기간이 얼마나 걸리나요?")
                .content("주문한 상품이 언제쯤 도착할까요?\n빠른 배송 부탁드립니다!")
                .viewCount(67)
                .likeCount(3)
                .build());

        // 자유게시판
        posts.add(Post.builder()
                .author(users.get(2))
                .category(Post.PostCategory.FREE)
                .title("우리 강아지 1살 생일 축하해요!")
                .content("드디어 우리 댕댕이가 1살이 되었어요!\n댕댕이샵에서 구매한 간식과 장난감으로 생일 파티 했습니다.\n정말 좋아하네요. 감사합니다!")
                .viewCount(234)
                .likeCount(28)
                .build());

        posts.add(Post.builder()
                .author(users.get(0))
                .category(Post.PostCategory.FREE)
                .title("강아지 건강 관리 팁 공유합니다")
                .content("제가 강아지 키우면서 도움이 되었던 것들을 공유합니다.\n1. 정기적인 건강검진은 필수\n2. 양치질은 매일 해주세요\n3. 산책은 하루 2회 이상!")
                .viewCount(178)
                .likeCount(15)
                .build());

        // 스터디
        posts.add(Post.builder()
                .author(users.get(1))
                .category(Post.PostCategory.STUDY)
                .title("강남 지역 산책 모임 멤버 모집")
                .content("매주 토요일 오전 10시에 강남 근처에서 반려견 산책 모임을 진행합니다.\n강아지 친구들도 만들고 정보도 공유해요!\n관심있으신 분들은 댓글 남겨주세요!")
                .viewCount(123)
                .likeCount(12)
                .build());

        posts.add(Post.builder()
                .author(users.get(2))
                .category(Post.PostCategory.STUDY)
                .title("대형견 보호자 모임 구합니다")
                .content("대형견을 키우시는 분들과 정보 공유하고 싶어요.\n사료, 훈련, 건강 관리 등 함께 이야기 나눠요!")
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
