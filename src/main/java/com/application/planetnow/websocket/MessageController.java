package com.application.planetnow.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {


	@MessageMapping("/hello")
	@SendTo("/topic/messages")
	public Message greeting(HelloMessage message) throws Exception {

		String finalMessage = " " + HtmlUtils.htmlEscape(message.getNickname()) + ": " + HtmlUtils.htmlEscape(message.getContent());

		return new Message(finalMessage);
	}

	@MessageMapping("/welcome")
	@SendTo("/topic/messages")
	public Message Hello(String nickname) throws Exception {
		String welcome = " ---- " + HtmlUtils.htmlEscape(nickname) +"님이 채팅방에 접속했습니다 ----";
		return new Message(welcome);
	}

	@MessageMapping("/bye")
	@SendTo("/topic/messages")
	public Message bye(String nickname) throws Exception {
		String welcome = " ---- " + HtmlUtils.htmlEscape(nickname) +"님이 채팅방을 나갔습니다 ----";
		return new Message(welcome);
	}

}
