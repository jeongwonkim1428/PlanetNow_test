package com.application.planetnow;

import com.application.planetnow.follow.FollowDAO;
import com.application.planetnow.follow.FollowDTO;
import com.application.planetnow.mainTask.CategoryDTO;
import com.application.planetnow.mainTask.LikeDAO;
import com.application.planetnow.mainTask.LikeDTO;
import com.application.planetnow.mainTask.MainTaskDAO;
import com.application.planetnow.mainTask.MainTaskDTO;
import com.application.planetnow.reply.ReplyDAO;
import com.application.planetnow.reply.ReplyDTO;
import com.application.planetnow.user.LevelDAO;
import com.application.planetnow.user.LevelDTO;
import com.application.planetnow.user.PointDAO;
import com.application.planetnow.user.PointDTO;
import com.application.planetnow.user.UserPointDAO;
import com.application.planetnow.user.UserPointDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class PlanetNowApplicationTests {

    @Autowired
    MainTaskDAO mainTaskDAO;
    
    @Autowired
    FollowDAO followDAO;
    
    @Autowired
    ReplyDAO replyDAO;

    @Autowired
    LikeDAO likeDAO;
    
    @Autowired
    PointDAO pointDAO;
    
    @Autowired
    UserPointDAO userPointDAO;
    
    @Autowired
    LevelDAO levelDAO;

    @Test
    void contextLoads() {
    }

//    @Test
//    void searchMainTask() {
//
//        List<Map<String, Object>> getMainTaskList = mainTaskDAO.getMainTaskList();
//        for (Map<String, Object> map : getMainTaskList) {
//            System.out.println(map);
//        }
//    }
//
//    @Test
//    void getCategoryList() {
//
//        List<CategoryDTO> getCategoryList = mainTaskDAO.getCategoryList();
//        for (CategoryDTO map : getCategoryList) {
//            System.out.println(map);
//        }
//
//    }
//
//    @Test
//    void getMainTaskListById() {
//        List<Map<String, Object>> getMAinTaskListById = mainTaskDAO.getMainTaskListById(1L);
//        for (Map<String, Object> map : getMAinTaskListById) {
//            System.out.println(map);
//        }
//    }
//    
    // DAO
    // follow
    @Test
    @Order(1)
    void followerList() {
    	Map<String, Object> temp = new HashMap<String, Object>();
    	temp.put("followeeId", 4L);
    	followDAO.getFollowerList(temp);
    	temp.put("searchFollower", "11");
    	
    	assertThat(followDAO.getFollowerList(temp)).isNotNull();
    }
    
    @Test
    @Order(2)
    void mainCnt() {
    	assertThat(followDAO.followerMainCnt(4L)).isNotNull();
    	
    	assertThat(followDAO.followingMainCnt(4L)).isNotNull();
    }
    
    @Test
    @Order(3)
    void replyCnt() {
    	assertThat(followDAO.followerReplyCnt(6L)).isNotNull();
    	
    	assertThat(followDAO.followingReplyCnt(6L)).isNotNull();
    	
    	assertThat(replyDAO.getReplyCnt(3L)).isNotNull();		// replyMapper error; wher -> where 
    }
    
    @Test
    @Order(4)
    void followerCnt() {
    	assertThat(followDAO.followerCnt(6L)).isNotNull();
    }
    
    @Test
    @Order(5)
    void followingList() {
    	Map<String, Object> temp = new HashMap<String, Object>();
    	temp.put("followerId", 4L);
    	temp.put("searchFollower", "11");
    	followDAO.getFollowingList(temp);
    	assertThat(followDAO.getFollowingList(temp)).isNotNull();
    }
    
    @Test
    @Order(6)
    void followeeCnt() {
    	assertThat(followDAO.followingCnt(6L)).isNotNull();
    }
    
    @Test
    @Order(7)
    void check() {
    	FollowDTO followDTO = new FollowDTO();
    	followDTO.setFollowerId(4L);
    	followDTO.setFolloweeId(5L);
    	assertThat(followDAO.check(followDTO)).isNotNull();
    }
    
    @Test
    @Order(8)
    void createFollow() {
    	FollowDTO followDTO = new FollowDTO();
    	followDTO.setFollowerId(5L);
    	followDTO.setFolloweeId(6L);
    	
    	followDAO.createFollow(followDTO);
    	List<PointDTO> pointList = pointDAO.getPointList();
    	UserPointDTO userPointDTO = UserPointDTO.of(followDTO.getFollowerId(), 6L);
    	userPointDAO.userPointSave(userPointDTO);
    	Long totalPoint = userPointDAO.getUserTotalPoint(followDTO.getFollowerId());
    	List<LevelDTO> levelList = levelDAO.getLevelList();
    	
    	assertThat(followDAO.check(followDTO)).isNotNull();
    	assertThat(pointList).isNotNull();
    	assertThat(userPointDTO).isNotNull();
    	assertThat(totalPoint).isNotNull();
    	assertThat(totalPoint).isNotNull();
    	assertThat(levelList).isNotNull();
    	
    }
    
    @Test
    @Order(9)
    void deleteFollow() {
    	Map<String, Long> temp = new HashMap<String, Long>();
    	temp.put("followeeId", 3L);
		temp.put("followerId", 4L);
		assertThat(temp).isNotNull();
		followDAO.deleteFollow(temp);
		
		FollowDTO followDTO = new FollowDTO();
    	followDTO.setFollowerId(4L);
    	followDTO.setFolloweeId(3L);
		assertThat(followDAO.check(followDTO)).isNotNull();
    }

    // Reply
    @Test
    @Order(10)
    void replyList() {
    	assertThat(replyDAO.getReplyList(3L)).isNotNull();
    }
    
    @Test
    @Order(11)
    void replyDetail() {
    	assertThat(replyDAO.getReplyDetail(2L)).isNotNull();
    }
    
    @Test
    @Order(12)
    void createReply() {
    	ReplyDTO replyDTO = new ReplyDTO();
    	replyDTO.setUserId(3L);
    	replyDTO.setMainTaskId(4L);
    	replyDTO.setContent("1111");
    	replyDAO.createReply(replyDTO);
    	
    	ReplyDTO dto = replyDAO.getReplyDetail(3L);
    	List<PointDTO> pointList = pointDAO.getPointList();
    	UserPointDTO userPointDTO = UserPointDTO.of(replyDTO.getUserId(), 4L);
    	userPointDAO.userPointSave(userPointDTO);
    	Long totalPoint = userPointDAO.getUserTotalPoint(replyDTO.getUserId());
    	List<LevelDTO> levelList = levelDAO.getLevelList();
    	
    	assertThat(dto).isNotNull();
    	assertThat(pointList).isNotNull();
    	assertThat(userPointDTO).isNotNull();
    	assertThat(totalPoint).isNotNull();
    	assertThat(levelList).isNotNull();
    	
    }
    
    @Test
    @Order(13)
    void updateReply() {
    	ReplyDTO replyDTO = new ReplyDTO();
    	replyDTO.setUserId(3L);
    	replyDTO.setMainTaskId(4L);
    	replyDTO.setContent("1111");
    	replyDAO.createReply(replyDTO);
    	
    	ReplyDTO dto = new ReplyDTO();
    	dto.setUserId(3L);
    	dto.setMainTaskId(4L);
    	dto.setContent("1122");
    	replyDAO.updateReply(dto);
    	
    	assertThat(dto.getContent()).isEqualTo("1122");
    }
    
    @Test
    @Order(14)
    void deleteReply() {
    	ReplyDTO replyDTO = new ReplyDTO();
    	replyDTO.setUserId(3L);
    	replyDTO.setMainTaskId(4L);
    	replyDTO.setContent("1111");
    	replyDAO.createReply(replyDTO);
    	
    	replyDAO.deleteReply(3L);
    	ReplyDTO dto = replyDAO.getReplyDetail(3L);
    	assertThat(dto).isNull();
    }
    
    // Like
    @Test
    @Order(15)
    void likeStatus() {
    	LikeDTO likeDTO = new LikeDTO();
    	likeDTO.setMainTaskId(4L);
    	likeDTO.setUserId(6L);
    	LikeDTO dto = likeDAO.getLikeStatus(likeDTO);
    	assertThat(dto).isNotNull();
    }
    
    @Test
    @Order(16)
    void createLike() {
    	LikeDTO likeDTO = new LikeDTO();
    	likeDTO.setMainTaskId(4L);
    	likeDTO.setUserId(5L);
    	likeDAO.createHeart(likeDTO);
    	
    	LikeDTO dto = likeDAO.getLikeStatus(likeDTO);
    	List<PointDTO> pointList = pointDAO.getPointList();
    	UserPointDTO userPointDTO = UserPointDTO.of(likeDTO.getUserId(), 4L);
    	userPointDAO.userPointSave(userPointDTO);
    	Long totalPoint = userPointDAO.getUserTotalPoint(likeDTO.getUserId());
    	List<LevelDTO> levelList = levelDAO.getLevelList();
    	
    	assertThat(dto).isNotNull();
    	assertThat(pointList).isNotNull();
    	assertThat(userPointDTO).isNotNull();
    	assertThat(totalPoint).isNotNull();
    	assertThat(levelList).isNotNull();
    	
    }
    
    @Test
    @Order(17)
    void deleteLike() {
    	LikeDTO likeDTO = new LikeDTO();
    	likeDTO.setMainTaskId(4L);
    	likeDTO.setUserId(5L);
    	likeDAO.createHeart(likeDTO);
    	
    	likeDAO.deleteHeart(likeDTO);
    	LikeDTO dto = likeDAO.getLikeStatus(likeDTO);
    	assertThat(dto).isNull();
    }
    
    @Test
    @Order(18)
    void likeCnt() {
    	int likeCnt = likeDAO.getLikeCnt(3L);
    	assertThat(likeCnt).isNotNull();
    }

}
