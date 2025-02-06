package com.application.planetnow.user;


import com.application.planetnow.mainTask.MainTaskDTO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;
import java.util.Objects;


@Mapper
public interface UserDAO {
    String validEmailResult(String email);
    String validNicknameResult(String nickname);


    void saveUser(UserDTO userDTO);


    UserDTO loginResult(UserDTO userDTO);


    void updateUser(UserDTO loginUser);


    UserDTO getUserDetail(String email);


    void removeUserResult(String email);


    void update(UserDTO userDTO);


    UserDTO getUserDetailById(Long userId);


    List<Map<String, Object>> searchUser(String name);

    List<MainTaskDTO> getUserProgressRate(Long userId);

    Long getFollowerCount(Long userId);

    Long getFollowingCount(Long userId);
}

