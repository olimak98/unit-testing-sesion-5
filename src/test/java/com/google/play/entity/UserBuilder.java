package com.google.play.entity;

public class UserBuilder {

	private User user;
	
	private UserBuilder() {}
	
	public static UserBuilder getUser() {
		UserBuilder builder = new UserBuilder();
		builder.user = new User();
		builder.user.setName("Dilanino");
		builder.user.setTypeDocument("CC");
		builder.user.setNumDocument(10059442);
		return builder;
		
	}
	
	public UserBuilder highRisk() {
		this.user.setNumDocument(100594423);
		return this;
	}
	
	public UserBuilder otherName(String name) {
		this.user.setName(name);
		return this;
	}
	
	
	public User now() {
		return user;
	}
	
	
}
