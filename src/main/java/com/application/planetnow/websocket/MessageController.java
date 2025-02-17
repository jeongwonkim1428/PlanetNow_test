package com.application.planetnow.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {


	@MessageMapping("/hello")
	@SendTo("/topic/allChat")
	public Message allChat(@Payload HelloMessage message) throws Exception {

		String finalMessage = " " + message.getNickname() + ": " + message.getContent();

		return new Message(finalMessage);
	}

	@MessageMapping("/welcome")
	@SendTo("/topic/allChat")
	public Message Hello(@Payload String nickname) throws Exception {
		String welcome = " ---- " + nickname +"님이 채팅방에 접속했습니다 ----";
		return new Message(welcome);
	}

	@MessageMapping("/bye")
	@SendTo("/topic/allChat")
	public Message bye(@Payload String nickname) throws Exception {
		String welcome = " ---- " + nickname +"님이 채팅방을 나갔습니다 ----";
		return new Message(welcome);
	}

}
