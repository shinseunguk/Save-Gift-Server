package com.savegift.notification;

import java.sql.Date;

public class NotificationVO {
	String user_id;
	String device_model;
	String device_id;
	String push_token;
	int push_yn;
	int push30;
	int push7;
	int push1;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getPush_token() {
		return push_token;
	}
	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}
	public int getPush_yn() {
		return push_yn;
	}
	public void setPush_yn(int push_yn) {
		this.push_yn = push_yn;
	}
	public int getPush30() {
		return push30;
	}
	public void setPush30(int push30) {
		this.push30 = push30;
	}
	public int getPush7() {
		return push7;
	}
	public void setPush7(int push7) {
		this.push7 = push7;
	}
	public int getPush1() {
		return push1;
	}
	public void setPush1(int push1) {
		this.push1 = push1;
	}
	
	
}
