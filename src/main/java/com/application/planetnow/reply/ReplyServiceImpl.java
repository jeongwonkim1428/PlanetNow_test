package com.application.planetnow.reply;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDAO replyDAO;

    @Override
    public List<Map<String, Object>> getReplyList(Long mainTaskId) {
        List<Map<String, Object>> replyDTO= replyDAO.getReplyList(mainTaskId);

        return replyDTO;
    }



    @Override
    public ReplyDTO getReplyDetail(Long replyId) {
        ReplyDTO replyDTO = replyDAO.getReplyDetail(replyId);
        return replyDTO;
    }

    @Override
    public int getReplyCnt(Long mainTaskId) {
        int replyCnt =replyDAO.getReplyCnt(mainTaskId);
        return replyCnt;
    }


    @Override
    public void createReply(ReplyDTO replyDTO) {
        replyDAO.createReply(replyDTO);
    }




    @Override
    public void deleteReply(Long replyId) {
        replyDAO.deleteReply(replyId);
    }



    @Override
    public void updateReply(ReplyDTO replyDTO) {
        replyDAO.updateReply(replyDTO);
    }


}
