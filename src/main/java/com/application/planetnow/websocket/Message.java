package com.application.planetnow.websocket;

import lombok.Getter;

@Getter
public class Message {

	private String content;

	public Message(String content) {
		this.content = content;
	}

}
