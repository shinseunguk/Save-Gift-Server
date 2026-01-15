package spring.service;

import java.sql.Date;

public class SmsVO {

	private String phone_number;
	private String cert_number;
	private int count;
	private Date cert_date;
	private String device_id;
	
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getCert_number() {
		return cert_number;
	}
	public void setCert_number(String cert_number) {
		this.cert_number = cert_number;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getCert_date() {
		return cert_date;
	}
	public void setCert_date(Date cert_date) {
		this.cert_date = cert_date;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
}
