package com.application.planetnow;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.application.planetnow.follow.FollowDAO;
import com.application.planetnow.follow.FollowDTO;
import com.application.planetnow.follow.FollowServiceImpl;
import com.application.planetnow.mainTask.LikeDAO;
import com.application.planetnow.mainTask.LikeDTO;
import com.application.planetnow.mainTask.LikeServiceImpl;
import com.application.planetnow.reply.ReplyDAO;
import com.application.planetnow.reply.ReplyDTO;
import com.application.planetnow.reply.ReplyServiceImpl;
import com.application.planetnow.user.LevelDAO;
import com.application.planetnow.user.LevelDTO;
import com.application.planetnow.user.PointDAO;
import com.application.planetnow.user.PointDTO;
import com.application.planetnow.user.UserDAO;
import com.application.planetnow.user.UserDTO;
import com.application.planetnow.user.UserPointDAO;
import com.application.planetnow.user.UserPointDTO;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanetNowApplicationServiceTest {

	@Mock
	private UserDAO userDAO;
	
	@Mock
	private FollowDAO followDAO;
	
	@Mock
	private ReplyDAO replyDAO;
	
	@Mock
	private LikeDAO likeDAO;
	
	@Mock
	private PointDAO pointDAO;
	
	@Mock
	private UserPointDAO userPointDAO;
	
	@Mock
	private LevelDAO levelDAO; 
	
	@InjectMocks
	private FollowServiceImpl followService;
	
	@InjectMocks
	private ReplyServiceImpl replyService;
	
	@InjectMocks
	private LikeServiceImpl likeService;
	
	@Test
	@Order(1)
	@DisplayName("팔로우 리스트 조회")
	void testGetFollowerList() {
		
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("followeeId", 4L);
		temp.put("searchFollower", "44");
		List<Map<String, Object>> followerList = new ArrayList<Map<String,Object>>();
		
		when(followDAO.getFollowerList(temp)).thenReturn(followerList);
		
		List<Map<String, Object>> result = followService.getFollowerList(temp);
		
		assertThat(result)
		.isNotNull();
		
		verify(followDAO, times(1)).getFollowerList(temp);
		
	}
	
	@Test
	@Order(2)
	@DisplayName("게시글수 조회")
	void testFollowMainCnt() {
		
		// 팔로우 리스트
		Long followeeId = 4L;
		Long followerMainCnt = 0L;
		when(followDAO.followerMainCnt(followeeId)).thenReturn(followerMainCnt);
		
		Long result1 = followService.followerMainCnt(followeeId);
		
		assertThat(result1)
		.isNotNull();
		
		verify(followDAO, times(1)).followerMainCnt(followeeId);
		
		// 팔로잉 리스트
		Long followerId = 2L;
		Long followingMainCnt = 0L;
		when(followDAO.followingMainCnt(followerId)).thenReturn(followingMainCnt);
		
		Long result2 = followService.followingMainCnt(followerId);
		
		assertThat(result2)
		.isNotNull();
		
		verify(followDAO, times(1)).followingMainCnt(followerId);
		
	}
	
	@Test
	@Order(3)
	@DisplayName("댓글 수 조회")
	void testFollowReplyCnt() {
		
		// 팔로우 리스트
		Long followeeId = 4L;
		Long followerReplyCnt = 0L;
		when(followDAO.followerReplyCnt(followeeId)).thenReturn(followerReplyCnt);
		
		Long result1 = followService.followerReplyCnt(followeeId);
		
		assertThat(result1)
		.isNotNull();
		
		verify(followDAO, times(1)).followerReplyCnt(followeeId);
		
		// 팔로잉 리스트
		Long followerId = 2L;
		Long followingReplyCnt = 0L;
		when(followDAO.followingReplyCnt(followerId)).thenReturn(followingReplyCnt);
		
		Long result2 = followService.followingReplyCnt(followerId);
		
		assertThat(result2)
		.isNotNull();
		
		verify(followDAO, times(1)).followingReplyCnt(followerId);
		
	}
	
	@Test
	@Order(4)
	@DisplayName("팔로우 수 조회")
	void testFollowerCnt() {
		
		Long id = 4L;
		Long followerCnt = 0L;
		when(followDAO.followerCnt(id)).thenReturn(followerCnt);
		
		Long result = followService.followerCnt(id);
		
		assertThat(result)
		.isNotNull();
		
		verify(followDAO, times(1)).followerCnt(id);
		
	}
	
	@Test
	@Order(5)
	@DisplayName("팔로잉 리스트 조회")
	void testGetRFollowingList() {
		
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("followerId", 6L);
		temp.put("searchFollower", "11");
		List<Map<String, Object>> followingList = new ArrayList<Map<String,Object>>();
		
		when(followDAO.getFollowingList(temp)).thenReturn(followingList);
		
		List<Map<String, Object>> results = followService.getFollowingList(temp);
		
		assertThat(results)
		.isNotNull();
		
		verify(followDAO, times(1)).getFollowingList(temp);
		
	}
	
	@Test
	@Order(6)
	@DisplayName("팔로잉 수 조회")
	void testFollowingCnt() {
		
		Long id = 8L;
		Long followingCnt = 0L;
		
		when(followDAO.followingCnt(id)).thenReturn(followingCnt);
		
		Long result = followService.followingCnt(id);
		
		assertThat(result)
		.isNotNull();
		
		verify(followDAO, times(1)).followingCnt(id);
		
	}
	
	@Test
	@Order(7)
	@DisplayName("팔로우")
	void testCreateFollow() {
		
		
		FollowDTO followDTO = new FollowDTO();
		followDTO.setFollowerId(1L);
		followDTO.setFolloweeId(5L);

		List<PointDTO> pointList = new ArrayList<PointDTO>();
		
		UserPointDTO userPointDTO = UserPointDTO.of(followDTO.getFollowerId(), 2L);
		userPointDAO.userPointSave(userPointDTO);
		
		UserDTO userDTO = new UserDTO();
		
		when(pointDAO.getPointList()).thenReturn(pointList);
		when(userDAO.getUserDetailById(followDTO.getFollowerId())).thenReturn(userDTO);
		
		followService.createFollow(followDTO);
		
		assertThat(pointList).isNotNull();
		assertThat(userDTO).isNotNull();
		
		verify(followDAO, times(1)).createFollow(followDTO);
		verify(pointDAO, times(1)).getPointList();
		verify(userDAO, times(1)).getUserDetailById(followDTO.getFollowerId());
		
	}
	
	@Test
	@Order(8)
	@DisplayName("팔로우 취소")
	void testDeleteFollow() {
		
		Long followerId = 2L;
		Long followeeId = 8L;
		Map<String, Long> temp = new HashMap<String, Long>();
		temp.put("followerId", followerId);
		temp.put("followeeId", followeeId);
		
		followService.deleteFollow(followeeId, followerId);		
		
		verify(followDAO, times(1)).deleteFollow(temp);
		
	}
	
	@Test
	@Order(9)
	@DisplayName("유저별 팔로우 상태")
	void testFollowCheck() {
		
		FollowDTO followDTO = new FollowDTO();
		followDTO.setFollowerId(1L);
		followDTO.setFolloweeId(5L);
		
		int check = followDAO.check(followDTO);
		
		when(check).thenReturn(check);
		
		int result = followService.check(followDTO.getFolloweeId(), followDTO.getFollowerId());
		
		assertThat(result).isNotNull();
		
		verify(followDAO, times(1)).check(followDTO);
		
	}
	
	@Test
	@Order(10)
	@DisplayName("댓글 리스트 조회")
	void testGetReplyList() {
		
		Long mainId = 1L;
		List<Map<String, Object>> replyList = new ArrayList<Map<String,Object>>();
		
		when(replyDAO.getReplyList(mainId)).thenReturn(replyList);
		
		List<Map<String, Object>> results = replyService.getReplyList(mainId);
		
		assertThat(results)
		.isNotNull();
		
		verify(replyDAO, times(1)).getReplyList(mainId);
		
	}
	
	@Test
	@Order(11)
	@DisplayName("댓글 상세 조회")
	void testGetReplyDetail() {
		
		Long replyId = 2L;
		ReplyDTO replyDTO = new ReplyDTO();
		
		when(replyDAO.getReplyDetail(replyId)).thenReturn(replyDTO);
		
		ReplyDTO result = replyService.getReplyDetail(replyId);
		
		assertThat(result).isNotNull();
		
		verify(replyDAO, times(1)).getReplyDetail(replyId);
		
	}
	
	@Test
	@Order(12)
	@DisplayName("댓글 생성")
	void testCreateReply() {
		
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setMainTaskId(1L);
		replyDTO.setUserId(2L);
		replyDTO.setReplyId(3L);
		replyDTO.setContent("588885");
		
		PointDTO pointDTO = new PointDTO();
		
		UserPointDTO userPointDTO = UserPointDTO.of(replyDTO.getUserId(), 2L);
		userPointDAO.userPointSave(userPointDTO);
		
		UserDTO userDTO = new UserDTO();
		
		when(pointDAO.getReplyPoint()).thenReturn(pointDTO);
		when(userDAO.getUserDetailById(replyDTO.getUserId())).thenReturn(userDTO);
		
		replyService.createReply(replyDTO);
		
		assertThat(pointDTO).isNotNull();
		assertThat(userDTO).isNotNull();
		
		verify(replyDAO, times(1)).createReply(replyDTO);
		verify(pointDAO, times(1)).getReplyPoint();
		verify(userDAO, times(1)).getUserDetailById(replyDTO.getUserId());
		
		
	}
	
	@Test
	@Order(13)
	@DisplayName("댓글 수정")
	void testUpdaterReply() {
		
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setMainTaskId(1L);
		replyDTO.setUserId(2L);
		replyDTO.setReplyId(3L);
		replyDTO.setContent("588885");
		
		replyService.updateReply(replyDTO);
		
		verify(replyDAO, times(1)).updateReply(replyDTO);
		
	}
	
	@Test
	@Order(14)
	@DisplayName("댓글 삭제")
	void testDeleteReply() {
		
		Long replyId = 2L;
		
		replyService.deleteReply(replyId);
		
		verify(replyDAO, times(1)).deleteReply(replyId);
		
	}
	
	@Test
	@Order(15)
	@DisplayName("좋아요 상태")
	void testGetLikeStatus() {

		Long mainId = 1L;
		Long userId = 2L;
		LikeDTO like = new LikeDTO();
		like.setMainTaskId(mainId);
		like.setUserId(userId);
		
		LikeDTO likeDTO = new LikeDTO();
		
		when(likeDAO.getLikeStatus(like)).thenReturn(likeDTO);
		
		boolean result = likeService.getLikeStatus(mainId, userId);
		
		assertThat(result).isNotNull();
		verify(likeDAO, times(1)).getLikeStatus(like);
		
	}
	
	// 다시
	@Test
	@Order(16)
	@DisplayName("좋아요")
	void testCreateHeart() {
		
		Long mainId = 1L;
		Long userId = 2L;
		LikeDTO like = new LikeDTO();
		like.setMainTaskId(mainId);
		like.setUserId(userId);
		
		PointDTO pointDTO = new PointDTO();
		
		UserPointDTO userPointDTO = UserPointDTO.of(userId, 2L);
		userPointDAO.userPointSave(userPointDTO);
		
		UserDTO userDTO = new UserDTO();
		
		when(pointDAO.getLikePoint()).thenReturn(pointDTO);
		when(userDAO.getUserDetailById(userId)).thenReturn(userDTO);
		
		likeService.createHeart(like);
		
		assertThat(pointDTO).isNotNull();
		assertThat(userDTO).isNotNull();
		
		verify(likeDAO, times(1)).createHeart(like);
		verify(pointDAO, times(1)).getLikePoint();
		verify(userDAO, times(1)).getUserDetailById(userId);
		
	}
	
	@Test
	@Order(17)
	@DisplayName("좋아요 취소")
	void testDeleteHeart() {
		
		Long mainId = 1L;
		Long userId = 2L;
		LikeDTO like = new LikeDTO();
		like.setMainTaskId(mainId);
		like.setUserId(userId);
		
		likeService.deleteHeart(like);
		
		verify(likeDAO, times(1)).deleteHeart(like);
		
	}
	
	@Test
	@Order(18)
	@DisplayName("좋아요 수 조회")
	void testGetLikeCnt() {
		Long mainId = 2L;
		int likeCnt = likeDAO.getLikeCnt(mainId);
		when(likeCnt).thenReturn(likeCnt);
		
		int result = likeService.getLikeCnt(mainId);
		
		assertThat(result).isNotNull();
		verify(likeDAO, times(1)).getLikeCnt(mainId);
	}
	
}
