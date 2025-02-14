package com.application.planetnow.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {

		String finalMessage = HtmlUtils.htmlEscape(message.getNickname()) + ":" + HtmlUtils.htmlEscape(message.getName());

		return new Greeting(finalMessage);
	}

	@MessageMapping("/welcome")
	@SendTo("/topic/greetings")
	public Greeting Hello(String nickname) throws Exception {
		String welcome = HtmlUtils.htmlEscape(nickname) +"님이 채팅방에 접속했습니다";
		return new Greeting(welcome);
	}

	@MessageMapping("/bye")
	@SendTo("/topic/greetings")
	public Greeting bye(String nickname) throws Exception {
		String welcome = HtmlUtils.htmlEscape(nickname) +"님이 채팅방을 나갔습니다";
		return new Greeting(welcome);
	}

}
