package com.application.planetnow.reply;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyDAO {

    public List<Map<String,Object>> getReplyList(Long mainTaskId);
    public ReplyDTO getReplyDetail(Long replyId);
    public int getReplyCnt(Long mainTaskId);
    public void createReply(ReplyDTO replyDTO);
    public void deleteReply(Long replyId);
    public void updateReply(ReplyDTO replyDTO);
}
