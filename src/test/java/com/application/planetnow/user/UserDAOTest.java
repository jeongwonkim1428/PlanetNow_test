package com.application.planetnow.user;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserDTO user;
    @BeforeEach
    public void setUp(){
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
        userDAO.saveUser(user);

    }
    @Test @Order(1) @DisplayName("유저 생성(회원가입)")
    public void creatUserTest(){
        // When
        UserDTO userDTO = userDAO.getUserDetail(user.getEmail());
        // Then
        assertThat(userDTO)
                .isNotNull();
    }
    @Test@Order(2)@DisplayName("유저 삭제(회원탈퇴)")
    public void deleteUserTest(){
        userDAO.removeUserResult(user.getEmail());
        UserDTO userDTO = userDAO.getUserDetail(user.getEmail());
        assertThat(userDTO)
                .isNull();
    }
    @Test@Order(3)@DisplayName("유저 업데이트(회원수정)")
    public void userUpdateTest(){

        UserDTO before =userDAO.getUserDetail(user.getEmail());
        before.setNickname("수정된 닉네임");
        before.setPhoneNumber("수정된 폰번호");
        before.setZipcode("수정된 주소");
        before.setRoadAddress("수정된 도로주소");
        before.setJibunAddress("수정된 jibun");
        userDAO.update(before);
        UserDTO after = userDAO.getUserDetail(user.getEmail());
        assertThat(after)
                .extracting(UserDTO::getNickname)
                .isEqualTo("수정된 닉네임");
    }
    @Test@Order(4)@DisplayName("유저 검색(유저 조회)")
    public void searchUserTest(){
        String keyword = "닉";
        List<Map<String,Object>> searchUser =userDAO.searchUser(keyword);

        assertThat(searchUser)
                .isNotEmpty();
    }
}
