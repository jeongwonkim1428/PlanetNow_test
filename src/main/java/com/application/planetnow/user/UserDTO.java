package com.application.planetnow.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String nickname;
    private String password;
    private LocalDate birthTimestamp;
    private String sex;
    private String phoneNumber;
    private String zipcode;
    private String roadAddress;
    private String jibunAddress;
    private String etcAddress;
    private String profileOriginalName;
    private String profileUuid;
    private String ambitionQuote;
    private Long levelId;
    private Long totalPoint;
    private LocalDate enrolledAt;
    private LocalDate modifiedAt;
    private String role;

}
