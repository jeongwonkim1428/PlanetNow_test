package com.application.planetnow.reply;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyDTO {
    private Long replyId;
    private Long userId;
    private Long mainTaskId;
    private String content;
    private LocalDate enrolledAt;
    private LocalDate modifiedAt;

}
