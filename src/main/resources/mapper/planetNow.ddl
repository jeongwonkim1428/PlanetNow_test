CREATE DATABASE PLANET_NOW;


CREATE TABLE LEVEL (
                       LEVEL_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                       LEVEL_VALUE BIGINT NOT NULL
);

CREATE TABLE POINT (
                       POINT_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                       ACTION VARCHAR(255) NOT NULL,
                       POINT_VALUE BIGINT NOT NULL
);

CREATE TABLE USER (
                      USER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                      NAME VARCHAR(20) NOT NULL,
                      EMAIL VARCHAR(255) NOT NULL,
                      NICKNAME VARCHAR(30) NOT NULL,
                      PASSWORD VARCHAR(255) NOT NULL,
                      BIRTH_TIMESTAMP TIMESTAMP NOT NULL,
                      SEX CHAR(1) NOT NULL,
                      PHONE_NUMBER CHAR(11) NOT NULL,
                      ZIPCODE VARCHAR(255) NULL,
                      ROAD_ADDRESS VARCHAR(255) NULL,
                      JIBUN_ADDRESS VARCHAR(255) NULL,
                      ETC_ADDRESS VARCHAR(255) NULL,
                      PROFILE_ORIGINAL_NAME VARCHAR(255) NOT NULL,
                      PROFILE_UUID VARCHAR(255) NOT NULL,
                      AMBITION_QUOTE VARCHAR(50) NOT NULL,
                      LEVEL_ID BIGINT NOT NULL ,
                      TOTAL_POINT BIGINT NOT NULL,
                      ENROLLED_AT TIMESTAMP DEFAULT NOW(),
                      MODIFIED_AT TIMESTAMP DEFAULT NOW(),
                      ROLE VARCHAR(5) NOT NULL,
                      FOREIGN KEY (LEVEL_ID) REFERENCES LEVEL (LEVEL_ID)
);

CREATE TABLE USER_POINT (
                            USER_POINT_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                            USER_ID BIGINT NOT NULL,
                            POINT_ID BIGINT NOT NULL,
                            FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE,
                            FOREIGN KEY (POINT_ID) REFERENCES POINT (POINT_ID)
);

CREATE TABLE CATEGORY (
                          CATEGORY_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                          CATEGORY_NM VARCHAR(255) NOT NULL
);

CREATE TABLE TASK_STATUS (
                             TASK_STATUS_ID INT AUTO_INCREMENT PRIMARY KEY,
                             TASK_STATUS_VALUE ENUM('진행전', '진행중', '완료', '실패') DEFAULT '진행전'
);



CREATE TABLE MAIN_TASK (
                           MAIN_TASK_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                           USER_ID BIGINT NOT NULL,
                           CATEGORY_ID BIGINT NOT NULL,
                           MAIN_SUBJECT VARCHAR(40) NOT NULL,
                           START_TIMESTAMP TIMESTAMP NOT NULL,
                           END_TIMESTAMP TIMESTAMP NOT NULL,
                           MEMO VARCHAR(255) NOT NULL,
                           ENROLLED_AT TIMESTAMP DEFAULT NOW(),
                           MODIFIED_AT TIMESTAMP DEFAULT NOW(),
                           TASK_STATUS_ID INT  NOT NULL,
                           VIEW_CNT BIGINT NOT NULL DEFAULT 0,

                           FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE,
                           FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (CATEGORY_ID),
                           FOREIGN KEY (TASK_STATUS_ID) REFERENCES TASK_STATUS (TASK_STATUS_ID)
);

CREATE TABLE SUB_TASK (
                          SUB_TASK_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                          MAIN_TASK_ID BIGINT NOT NULL,
                          SUB_SUBJECT VARCHAR(40) NOT NULL,
                          ENROLLED_AT TIMESTAMP DEFAULT NOW(),
                          MODIFIED_AT TIMESTAMP DEFAULT NOW(),
                          TASK_STATUS_ID INT NOT NULL,
                          FOREIGN KEY (MAIN_TASK_ID) REFERENCES MAIN_TASK (MAIN_TASK_ID) ON DELETE CASCADE,
                          FOREIGN KEY (TASK_STATUS_ID) REFERENCES TASK_STATUS (TASK_STATUS_ID)
);

CREATE TABLE RECOMMENDED_TASK (
                                  RECOMMENDED_TASK_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  MAIN_TASK_ID BIGINT NOT NULL,
                                  USER_ID BIGINT NOT NULL,
                                  SUB_SUBJECT VARCHAR(40) NOT NULL,
                                  ENROLLED_AT TIMESTAMP DEFAULT NOW(),
                                  FOREIGN KEY (MAIN_TASK_ID) REFERENCES MAIN_TASK (MAIN_TASK_ID) ON DELETE CASCADE,
                                  FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE
);

CREATE TABLE LIKES (
                       LIKE_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                       USER_ID BIGINT NOT NULL,
                       MAIN_TASK_ID BIGINT NOT NULL,
                       FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE,
                       FOREIGN KEY (MAIN_TASK_ID) REFERENCES MAIN_TASK (MAIN_TASK_ID) ON DELETE CASCADE
);

CREATE TABLE REPLY (
                       REPLY_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                       USER_ID BIGINT NOT NULL,
                       MAIN_TASK_ID BIGINT NOT NULL,
                       CONTENT VARCHAR(100) NOT NULL,
                       ENROLLED_AT TIMESTAMP DEFAULT NOW(),
                       MODIFIED_AT TIMESTAMP DEFAULT NOW(),
                       FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE,
                       FOREIGN KEY (MAIN_TASK_ID) REFERENCES MAIN_TASK (MAIN_TASK_ID) ON DELETE CASCADE
);

CREATE TABLE FOLLOW (
                        FOLLOW_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        FOLLOWER_ID BIGINT NOT NULL,
                        FOLLOWEE_ID BIGINT NOT NULL,
                        FOREIGN KEY (FOLLOWER_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE,
                        FOREIGN KEY (FOLLOWEE_ID) REFERENCES USER (USER_ID) ON DELETE CASCADE
);



