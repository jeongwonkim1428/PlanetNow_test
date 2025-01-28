package com.application.planetnow.user;

import org.apache.ibatis.annotations.Mapper;

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
}
