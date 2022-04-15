package com.savegift.login;

public class LoginVO {
	private String user_id;
	private String user_password;
	private String name;
	private String phone_number;
	private int img_count;
	private int push_yn;
	private int email_yn;
	private int sms_yn;
	private String create_date;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public int getImg_count() {
		return img_count;
	}
	public void setImg_count(int img_count) {
		this.img_count = img_count;
	}
	public int getPush_yn() {
		return push_yn;
	}
	public void setPush_yn(int push_yn) {
		this.push_yn = push_yn;
	}
	public int getEmail_yn() {
		return email_yn;
	}
	public void setEmail_yn(int email_yn) {
		this.email_yn = email_yn;
	}
	public int getSms_yn() {
		return sms_yn;
	}
	public void setSms_yn(int ems_yn) {
		this.sms_yn = ems_yn;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
}
