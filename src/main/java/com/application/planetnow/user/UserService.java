package com.application.planetnow.user;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    boolean validEmailResult(String email);

    boolean validNicknameResult(String nickname);

    void signUp(MultipartFile myProfile, UserDTO userDTO) throws IOException;
}
