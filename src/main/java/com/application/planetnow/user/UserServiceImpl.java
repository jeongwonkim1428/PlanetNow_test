package com.application.planetnow.user;


import com.application.planetnow.mainTask.MainTaskDTO;
import com.application.planetnow.user.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final LevelDAO levelDAO;
    private final UserPointDAO userPointDAO;
    private final PointDAO pointDAO;
    private final UserDAO userDAO;
    @Value("${file.repo.path}")
    private String fileRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean validEmailResult(String email) {
        if (userDAO.validEmailResult(email) == null) {
            return true;
        }else {
            return false;
        }
    }


    @Override
    public boolean validNicknameResult(String nickname) {
        if (userDAO.validNicknameResult(nickname) == null) {
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void updateUserResult(MultipartFile myProfile, UserDTO userDTO) throws IOException {
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            userDTO.setPassword(null);  // 비밀번호가 빈 값일 경우 null로 설정
        }else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }


        if (!myProfile.isEmpty()){
            new File(fileRepo + userDTO.getProfileUuid()).delete();

            String originalFilename = myProfile.getOriginalFilename();
            userDTO.setProfileOriginalName(originalFilename);

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String uploadFile = UUID.randomUUID() + extension;
            userDTO.setProfileUuid(uploadFile);

            myProfile.transferTo(new File(fileRepo + uploadFile));
            userDAO.update(userDTO);
        }
        else {


            userDAO.update(userDTO);
        }
    }


    @Override
    public UserDTO getUserDetailById(Long userId) {
        UserDTO userDTO = userDAO.getUserDetailById(userId);
        return userDTO;
    }


    @Override
    public List<Map<String, Object>> searchUser(String name) {
        return userDAO.searchUser(name);


    }

    @Override
    public double getProgress(Long userId) {
        List<MainTaskDTO> userTaskList = userDAO.getUserProgressRate(userId);
        if (userTaskList.isEmpty()){
            return (double) 0;
        }
        Integer userTaskCount = userTaskList.size();
        Integer userCompleted =userTaskList.stream()
                .filter((task) -> task.getTaskStatusId().equals(3))
                .toList().size();


        return (double) Math.round((double) userCompleted / userTaskCount * 10000) / 100 ;
    }

    @Override
    public Long getFollowerCount(Long userId) {
        return userDAO.getFollowerCount(userId);

    }

    @Override
    public Long getFollowingCount(Long userId) {
        return userDAO.getFollowingCount(userId);
    }


    @Override
    public void signUp(MultipartFile myProfile, UserDTO userDTO) throws IOException {
        if (!myProfile.isEmpty()){
            String originalFilename = myProfile.getOriginalFilename();
            userDTO.setProfileOriginalName(originalFilename);


            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));


            String uuidFile = UUID.randomUUID() + extension;
            userDTO.setProfileUuid(uuidFile);


            myProfile.transferTo(new File(fileRepo + uuidFile));
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setLevelId(1L);
        userDTO.setTotalPoint(0L);
        userDTO.setRole("USER");
        userDAO.saveUser(userDTO);
    }


    @Override
    public boolean loginResult(UserDTO userDTO) {
            UserDTO loginUser = userDAO.loginResult(userDTO);

        if (loginUser != null){
            if( passwordEncoder.matches(userDTO.getPassword(),loginUser.getPassword()) ){
                //포인트 리스트 조회
                //로그인 포인트 조회
                PointDTO loginPointDTO = getPoint();
                //UserPointDTO 생성
                UserPointDTO userPointDTO = UserPointDTO.of(loginUser.getUserId(),loginPointDTO.getPointId());
                log.info("유저 포인트 DTO 객체 : " +userPointDTO);


                //UserPoint 객체 DB에 저장
                userPointDAO.userPointSave(userPointDTO);
                //유저가 가지고 있는 포인트 조회
                Long userTotalPoint = getUserTotalPoint(loginUser.getUserId());
                log.info("유저 총포인트 : "+ userTotalPoint);
                //레벨 리스트 조회
                List<LevelDTO> levelDTOList = levelList();
                Long userLevel = levelDTOList.stream()
                        .filter(level -> userTotalPoint >= level.getLevelValue())  // 포인트가 levelValue보다 크거나 같은 레벨만 필터링
                        .max(Comparator.comparingLong(LevelDTO::getLevelId))  // levelId가 가장 큰 값을 선택
                        .map(LevelDTO::getLevelId)  // 해당 LevelDTO의 levelId만 추출
                        .orElse(1L);
                //변경사항 저장
                loginUser.setLevelId(userLevel);
                loginUser.setTotalPoint(userTotalPoint);
                userDAO.updateUser(loginUser);
                //로그인 완료
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    public PointDTO getPoint(){
        //포인트 리스트 조회
        List<PointDTO> pointList = pointDAO.getPointList();
        return pointList.stream()
                .filter((p)->p.getAction().equals("로그인"))
                .findFirst()
                .orElseThrow(()-> new NotFoundException("해당 포인트를 찾을 수 없습니다."));
    }


    public Long getUserTotalPoint(Long userId){
        Long userTotalPoint =  userPointDAO.getUserTotalPoint(userId);
        log.info( "유저 총 포인트 : " + userTotalPoint);
        return userTotalPoint;
    }

    public List<LevelDTO> levelList(){
        return levelDAO.getLevelList();
    }


    @Override
    public UserDTO getUserDetail(String email) {
        return userDAO.getUserDetail(email);
    }


    @Override
    public boolean userRemoveResult(UserDTO userDTO) {
        UserDTO user = userDAO.loginResult(userDTO);
        if (passwordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            userDAO.removeUserResult(userDTO.getEmail());
            return true;
        }
        return false;




    }


    @Override
    public UserDTO getUserFromSession(HttpServletRequest request) {


        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return null;
        }
        return getUserDetail(email);


    }
    @Override
    public boolean getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        log.info("email : " + email);
        if (email== null){
            return true;
        }
        return false;
    }


}



