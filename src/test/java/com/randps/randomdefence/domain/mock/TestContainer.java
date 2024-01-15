package com.randps.randomdefence.domain.mock;


import com.randps.randomdefence.domain.board.mock.FakeBoardRepository;
import com.randps.randomdefence.domain.board.service.BoardService;
import com.randps.randomdefence.domain.board.service.port.BoardRepository;
import com.randps.randomdefence.domain.boolshit.mock.FakeBoolshitRepository;
import com.randps.randomdefence.domain.boolshit.service.BoolshitService;
import com.randps.randomdefence.domain.boolshit.service.port.BoolshitRepository;
import com.randps.randomdefence.domain.comment.mock.FakeCommentRepository;
import com.randps.randomdefence.domain.comment.service.CommentService;
import com.randps.randomdefence.domain.comment.service.port.CommentRepository;
import com.randps.randomdefence.domain.event.mock.FakeEventPointRepository;
import com.randps.randomdefence.domain.event.service.EventPointService;
import com.randps.randomdefence.domain.event.service.port.EventPointRepository;
import com.randps.randomdefence.domain.image.mock.FakeBoardImageRepository;
import com.randps.randomdefence.domain.image.mock.FakeImageRepository;
import com.randps.randomdefence.domain.image.service.ImageService;
import com.randps.randomdefence.domain.image.service.port.BoardImageRepository;
import com.randps.randomdefence.domain.image.service.port.ImageRepository;
import com.randps.randomdefence.domain.item.mock.FakeItemRepository;
import com.randps.randomdefence.domain.item.mock.FakeUserItemRepository;
import com.randps.randomdefence.domain.item.service.ItemSaveService;
import com.randps.randomdefence.domain.item.service.RandomStreakFreezeItemUseServiceImpl;
import com.randps.randomdefence.domain.item.service.port.ItemRepository;
import com.randps.randomdefence.domain.item.service.port.UserItemRepository;
import com.randps.randomdefence.domain.log.mock.FakePointLogRepository;
import com.randps.randomdefence.domain.log.mock.FakeWarningLogRepository;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.log.service.WarningLogSaveService;
import com.randps.randomdefence.domain.log.service.port.PointLogRepository;
import com.randps.randomdefence.domain.log.service.port.WarningLogRepository;
import com.randps.randomdefence.domain.problem.mock.FakeProblemRepository;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.problem.service.port.ProblemRepository;
import com.randps.randomdefence.domain.recommendation.service.RecommendationService;
import com.randps.randomdefence.domain.statistics.mock.FakeUserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.mock.FakeUserStatisticsRepository;
import com.randps.randomdefence.domain.statistics.service.UserProblemStatisticsService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.statistics.service.port.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.service.port.UserStatisticsRepository;
import com.randps.randomdefence.domain.team.mock.FakeTeamRepository;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.team.service.port.TeamRepository;
import com.randps.randomdefence.domain.user.mock.FakeUserAlreadySolvedRepository;
import com.randps.randomdefence.domain.user.mock.FakeUserGrassRepository;
import com.randps.randomdefence.domain.user.mock.FakeUserRandomStreakRepository;
import com.randps.randomdefence.domain.user.mock.FakeUserRepository;
import com.randps.randomdefence.domain.user.mock.FakeUserSolvedProblemRepository;
import com.randps.randomdefence.domain.user.service.PrincipalDetailsService;
import com.randps.randomdefence.domain.user.service.UserAlreadySolvedService;
import com.randps.randomdefence.domain.user.service.UserAuthService;
import com.randps.randomdefence.domain.user.service.UserDeleteService;
import com.randps.randomdefence.domain.user.service.UserGrassService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserService;
import com.randps.randomdefence.domain.user.service.UserSolvedJudgeService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
import com.randps.randomdefence.domain.user.service.port.UserAlreadySolvedRepository;
import com.randps.randomdefence.domain.user.service.port.UserGrassRepository;
import com.randps.randomdefence.domain.user.service.port.UserRandomStreakRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import com.randps.randomdefence.global.aws.s3.service.S3Service;
import com.randps.randomdefence.global.aws.s3.service.port.AmazonS3ClientPort;
import com.randps.randomdefence.global.component.parser.Parser;
import com.randps.randomdefence.global.component.parser.SolvedacParser;
import com.randps.randomdefence.global.jwt.component.JwtRefreshUtil;
import com.randps.randomdefence.global.jwt.component.port.RefreshTokenRepository;
import com.randps.randomdefence.global.jwt.mock.FakeRefreshTokenRepository;
import com.randps.randomdefence.global.mock.FakeAmazonS3Client;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestContainer {

    public final UserRepository userRepository;
    public final UserRandomStreakRepository userRandomStreakRepository;
    public final UserGrassRepository userGrassRepository;
    public final UserSolvedProblemRepository userSolvedProblemRepository;
    public final ProblemRepository problemRepository;
    public final UserAlreadySolvedRepository userAlreadySolvedRepository;
    public final PointLogRepository pointLogRepository;
    public final TeamRepository teamRepository;
    public final UserStatisticsRepository userStatisticsRepository;
    public final UserProblemStatisticsRepository userProblemStatisticsRepository;
    public final EventPointRepository eventPointRepository;
    public final UserItemRepository userItemRepository;
    public final ItemRepository itemRepository;
    public final WarningLogRepository warningLogRepository;

    public final RefreshTokenRepository refreshTokenRepository;

    public final BoolshitRepository boolshitRepository;

    public final CommentRepository commentRepository;

    public final BoardRepository boardRepository;

    public final ImageRepository imageRepository;

    public final BoardImageRepository boardImageRepository;

    public final AmazonS3ClientPort amazonS3Client;

    public final Parser parserHolder;
    public final SolvedacParser solvedacParserHolder;

    public final JwtRefreshUtil jwtUtil;

    public final UserAlreadySolvedService userAlreadySolvedService;
    public final UserSolvedJudgeService userSolvedJudgeService;
    public final UserGrassService userGrassService;
    public final RecommendationService recommendationService;
    public final ProblemService problemService;
    public final PointLogSaveService pointLogSaveService;
    public final TeamService teamService;
    public final UserProblemStatisticsService userProblemStatisticsService;
    public final UserStatisticsService userStatisticsService;
    public final EventPointService eventPointService;
    public final UserSolvedProblemService userSolvedProblemService;
    public final RandomStreakFreezeItemUseServiceImpl randomStreakFreezeItemUseService;
    public final UserRandomStreakService userRandomStreakService;
    public final WarningLogSaveService warningLogSaveService;
    public final UserInfoService userInfoService;
    public final UserService userService;
    public final UserDeleteService userDeleteService;

    public final PrincipalDetailsService principalDetailsService;

    public final UserAuthService userAuthService;

    public final BoolshitService boolshitService;

    public final ItemSaveService itemSaveService;

    public final ImageService imageService;

    public final BoardService boardService;

    public final S3Service s3Service;

    public final CommentService commentService;

    @Builder
    public TestContainer(Parser parser, SolvedacParser solvedacParser, BCryptPasswordEncoder passwordEncoder) {
        userRepository = new FakeUserRepository();
        userRandomStreakRepository = new FakeUserRandomStreakRepository();
        userGrassRepository = new FakeUserGrassRepository();
        userSolvedProblemRepository = new FakeUserSolvedProblemRepository();
        problemRepository = new FakeProblemRepository();
        userAlreadySolvedRepository = new FakeUserAlreadySolvedRepository();
        pointLogRepository = new FakePointLogRepository();
        teamRepository = new FakeTeamRepository();
        userStatisticsRepository = new FakeUserStatisticsRepository();
        userProblemStatisticsRepository = new FakeUserProblemStatisticsRepository();
        eventPointRepository = new FakeEventPointRepository();
        userItemRepository = new FakeUserItemRepository();
        itemRepository = new FakeItemRepository();
        warningLogRepository = new FakeWarningLogRepository();
        refreshTokenRepository = new FakeRefreshTokenRepository();
        boolshitRepository = new FakeBoolshitRepository(userRepository);
        commentRepository = new FakeCommentRepository();
        imageRepository = new FakeImageRepository();
        boardImageRepository = new FakeBoardImageRepository();
        boardRepository = new FakeBoardRepository(userRepository, commentRepository, imageRepository, boardImageRepository);
        amazonS3Client = new FakeAmazonS3Client(); // TODO: mock객체로 변경
        parserHolder = parser;
        solvedacParserHolder = solvedacParser;
        userAlreadySolvedService = UserAlreadySolvedService.builder()
                .userRepository(userRepository)
                .userAlreadySolvedRepository(userAlreadySolvedRepository)
                .bojProfileParser(parserHolder)
                .build();
        userSolvedJudgeService = UserSolvedJudgeService.builder()
                .userSolvedProblemRepository(userSolvedProblemRepository)
                .userAlreadySolvedService(userAlreadySolvedService)
                .build();
        userGrassService = UserGrassService.builder()
                .userRepository(userRepository)
                .userGrassRepository(userGrassRepository)
                .userRandomStreakRepository(userRandomStreakRepository)
                .build();
        recommendationService = RecommendationService.builder()
                .build();
        problemService = ProblemService.builder()
                .problemRepository(problemRepository)
                .userSolvedJudgeService(userSolvedJudgeService)
                .build();
        pointLogSaveService = PointLogSaveService.builder()
                .userRepository(userRepository)
                .pointLogRepository(pointLogRepository)
                .build();
        teamService = TeamService.builder()
                .userRepository(userRepository)
                .pointLogSaveService(pointLogSaveService)
                .teamRepository(teamRepository)
                .build();
        userProblemStatisticsService = UserProblemStatisticsService.builder()
                .userProblemStatisticsRepository(userProblemStatisticsRepository)
                .build();
        userStatisticsService = UserStatisticsService.builder()
                .userRepository(userRepository)
                .userSolvedProblemRepository(userSolvedProblemRepository)
                .userRandomStreakRepository(userRandomStreakRepository)
                .userStatisticsRepository(userStatisticsRepository)
                .userProblemStatisticsRepository(userProblemStatisticsRepository)
                .userProblemStatisticsService(userProblemStatisticsService)
                .problemService(problemService)
                .build();
        eventPointService = EventPointService.builder()
                .userRepository(userRepository)
                .eventPointRepository(eventPointRepository)
                .pointLogSaveService(pointLogSaveService)
                .build();
        userSolvedProblemService = UserSolvedProblemService.builder()
                .userRandomStreakRepository(userRandomStreakRepository)
                .userRepository(userRepository)
                .userSolvedProblemRepository(userSolvedProblemRepository)
                .problemService(problemService)
                .pointLogSaveService(pointLogSaveService)
                .teamService(teamService)
                .bojParser(parserHolder)
                .userStatisticsService(userStatisticsService)
                .userAlreadySolvedService(userAlreadySolvedService)
                .eventPointService(eventPointService)
                .build();
        randomStreakFreezeItemUseService = RandomStreakFreezeItemUseServiceImpl.builder()
                .userRepository(userRepository)
                .userGrassRepository(userGrassRepository)
                .userRandomStreakRepository(userRandomStreakRepository)
                .userItemRepository(userItemRepository)
                .itemRepository(itemRepository)
                .userGrassService(userGrassService)
                .build();
        userRandomStreakService = UserRandomStreakService.builder()
                .userRandomStreakRepository(userRandomStreakRepository)
                .userRepository(userRepository)
                .userGrassRepository(userGrassRepository)
                .recommendationService(recommendationService)
                .problemService(problemService)
                .userSolvedProblemService(userSolvedProblemService)
                .userGrassService(userGrassService)
                .pointLogSaveService(pointLogSaveService)
                .userStatisticsService(userStatisticsService)
                .teamService(teamService)
                .randomStreakFreezeItemUseService(randomStreakFreezeItemUseService)
                .eventPointService(eventPointService)
                .build();
        warningLogSaveService = WarningLogSaveService.builder()
                .userRepository(userRepository)
                .warningLogRepository(warningLogRepository)
                .build();
        userInfoService = UserInfoService.builder()
                .userRepository(userRepository)
                .userRandomStreakService(userRandomStreakService)
                .warningLogSaveService(warningLogSaveService)
                .userSolvedProblemService(userSolvedProblemService)
                .solvedacParser(solvedacParserHolder)
                .bojParser(parserHolder)
                .build();
        userService = UserService.builder()
                .passwordEncoder(passwordEncoder)
                .userRepository(userRepository)
                .userRandomStreakRepository(userRandomStreakRepository)
                .userGrassService(userGrassService)
                .userInfoService(userInfoService)
                .userRandomStreakService(userRandomStreakService)
                .userSolvedProblemService(userSolvedProblemService)
                .build();
        principalDetailsService = PrincipalDetailsService.builder()
                .userRepository(userRepository)
                .build();
        jwtUtil = new JwtRefreshUtil(principalDetailsService, refreshTokenRepository, "secret");
        userAuthService = UserAuthService.builder()
                .passwordEncoder(passwordEncoder)
                .userRepository(userRepository)
                .refreshTokenRepository(refreshTokenRepository)
                .jwtUtil(jwtUtil)
                .build();
        boolshitService = BoolshitService.builder()
                .boolshitRepository(boolshitRepository)
                .userRepository(userRepository)
                .build();
        itemSaveService = ItemSaveService.builder()
                .userItemRepository(userItemRepository)
                .userRepository(userRepository)
                .itemRepository(itemRepository)
                .pointLogSaveService(pointLogSaveService)
                .build();
        imageService = ImageService.builder()
                .imageRepository(imageRepository)
                .build();
        s3Service = new S3Service(amazonS3Client, imageService, "test");
        commentService = CommentService.builder()
                .commentRepository(commentRepository)
                .build();
        boardService = BoardService.builder()
                .boardRepository(boardRepository)
                .imageRepository(imageRepository)
                .commentService(commentService)
                .imageService(imageService)
                .s3Service(s3Service)
                .boardImageRepository(boardImageRepository)
                .build();
        userDeleteService = UserDeleteService.builder()
                .userRepository(userRepository)
                .userRandomStreakService(userRandomStreakService)
                .userSolvedProblemService(userSolvedProblemService)
                .userStatisticsService(userStatisticsService)
                .userAuthService(userAuthService)
                .boolshitService(boolshitService)
                .pointLogSaveService(pointLogSaveService)
                .warningLogSaveService(warningLogSaveService)
                .itemSaveService(itemSaveService)
                .boardService(boardService)
                .commentService(commentService)
                .build();
    }


}
