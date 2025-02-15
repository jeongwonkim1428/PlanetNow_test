package com.application.planetnow.websocket;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HelloMessage {

	private String content;
	private String userId;
	private String nickname;


	public HelloMessage(String content, String userId, String nickname) {
		this.content = content;
		this.userId = userId;
		this.nickname = nickname;
	}

}
