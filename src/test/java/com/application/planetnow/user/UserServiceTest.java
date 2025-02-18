package com.application.planetnow.user;

import com.application.planetnow.mainTask.MainTaskDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserPointDAO userPointDAO;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO user;
    private List<MainTaskDTO> mainTaskDTOS;


    @BeforeEach
    void setUp(){
        user = new UserDTO();
        user.setUserId(1000L);
        user.setName("이름");
        user.setEmail("lll@naver.com");
        user.setNickname("닉네임");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setBirthTimestamp(LocalDate.now());
        user.setSex("남");
        user.setPhoneNumber("01012341234");
        user.setZipcode("123");
        user.setRoadAddress("주소");
        user.setJibunAddress("주소");
        user.setEtcAddress("나머지");
        user.setProfileOriginalName("오리지널");
        user.setProfileUuid("uuid");
        user.setAmbitionQuote("화이팅하면서 목표 달성");
        user.setLevelId(1L);
        user.setTotalPoint(2000L);
        user.setRole("USER");

        MainTaskDTO main1 =new MainTaskDTO();
        MainTaskDTO main2 =new MainTaskDTO();
        main1.setTaskStatusId(3);
        main2.setTaskStatusId(1);
        mainTaskDTOS = Arrays.asList(main1,main2);

    }
    @Test
    @Order(1)
    @DisplayName("이메일 중복확인")
    public void emailCheckTest(){
        //Given
        String email = "lhh@gmail.com";
        when(userDAO.validEmailResult(email)).thenReturn(null);
        //When
        boolean result = userService.validEmailResult(email);
        //Then
        assertThat(result).isTrue();
        verify(userDAO,times(1)).validEmailResult(email);

    }
    @Test
    @Order(2)
    @DisplayName("닉네임 중복확인")
    public void nicknameCheckTest(){
        //Given
        String nickname = "닉네임";
        when(userDAO.validNicknameResult(nickname)).thenReturn(null);
        //When
        boolean result = userService.validNicknameResult(nickname);
        //Then
        assertThat(result).isTrue();
        verify(userDAO,times(1)).validNicknameResult(nickname);

    }
    @Test
    @Order(3)
    @DisplayName("유저 상세조회")
    public void userUpdateTest(){
        //Given
        long userId = 1;
        when(userDAO.getUserDetailById(userId)).thenReturn(user);
        //When
        UserDTO userDTO =userService.getUserDetailById(userId);
        //Then
        assertThat(userDTO.getEmail())
                .isEqualTo(user.getEmail());
        verify(userDAO,times(1)).getUserDetailById(userId);
    }
    @Test
    @Order(4)
    @DisplayName("유저가 작성한 게시글 리스트 조회")
    public void userWritePostTest(){
        //Given
        long userId =1;
        when(userDAO.getUserProgressRate(userId)).thenReturn(mainTaskDTOS);

        //When
        double result =userService.getProgress(userId);

        Integer userTaskCount = mainTaskDTOS.size();
        Integer userCompleted = mainTaskDTOS.stream().filter((mt)->mt.getTaskStatusId().equals(3)).toList().size();
        double result2 = (double) Math.round((double) userCompleted / userTaskCount * 10000) / 100 ;
        //Then
        assertThat(result).isEqualTo(result2);
        verify(userDAO,times(1)).getUserProgressRate(userId);
    }
    @Test
    @Order(5)
    @DisplayName("유저가 팔로우 하고 있는 유저 수")
    public void followCnt(){
        //Given
        Long userId = 1L;
        Long followerCount = 3L;
        when(userDAO.getFollowerCount(userId)).thenReturn(followerCount);
        //When
        Long followerCountResult = userService.getFollowerCount(userId);
        //Then
        assertThat(followerCountResult)
                .isEqualTo(followerCount);
        verify(userDAO,times(1)).getFollowerCount(userId);
    }
    @Test
    @Order(6)
    @DisplayName("유저를 팔로우 하고 있는 유저 수")
    public void followeeCnt(){
        //Given
        Long userId = 1L;
        Long followeeCount = 3L;
        when(userDAO.getFollowingCount(userId)).thenReturn(followeeCount);
        //When
        Long followingCountResult = userService.getFollowingCount(userId);
        //Then
        assertThat(followingCountResult)
                .isEqualTo(followeeCount);
        verify(userDAO,times(1)).getFollowingCount(userId);
    }

    @Test
    @Order(7)
    @DisplayName("유저 포인트 조회")
    public void userTotalPointTest(){
        //Given
        Long userId = 1L;
        when(userPointDAO.getUserTotalPoint(userId)).thenReturn(2000L);
        //when
        Long result = userService.getUserTotalPoint(userId);
        //then
        assertThat(result).isEqualTo(user.getTotalPoint());
        verify(userPointDAO,times(1)).getUserTotalPoint(userId);
    }




}
