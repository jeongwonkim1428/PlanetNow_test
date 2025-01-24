package com.application.planetnow.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
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
        log.info("암호화 패스워드: " + loginUser.getPassword());
        log.info("내 비밀번호: " + userDTO.getPassword());
        if (loginUser != null){
            if( passwordEncoder.matches(userDTO.getPassword(),loginUser.getPassword()) ){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }


}
