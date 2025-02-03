package com.application.planetnow.user;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


public interface UserService {
    boolean validEmailResult(String email);


    boolean validNicknameResult(String nickname);


    void signUp(MultipartFile myProfile, UserDTO userDTO) throws IOException;


    boolean loginResult(UserDTO userDTO);


    UserDTO getUserDetail(String email);


    boolean userRemoveResult(UserDTO userDTO);




    UserDTO getUserFromSession(HttpServletRequest request);


    void updateUserResult(MultipartFile myProfile, UserDTO userDTO) throws IOException;




    UserDTO getUserDetailById(Long userId);


    List<UserDTO> searchUser(String name);
}

