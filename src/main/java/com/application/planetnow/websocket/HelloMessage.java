package com.application.planetnow.websocket;

public class HelloMessage {

	private String name;
	private String userId;
	private String nickname;

	public HelloMessage() {
	}

	public HelloMessage(String name, String userId, String nickname) {
		this.name = name;
		this.userId = userId;
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
