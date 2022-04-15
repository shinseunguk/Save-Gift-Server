package com.savegift.giftcon;

import java.sql.Date;

public class GiftVO {
	private int seq;
	private String user_id;
	private String img_rul;
	private String brand;
	private String barcode_number;
	private Date expiration_period;
	private Date registration_date;
	private int use_yn;
	private String device_id;
	private String registrant;
	private String product_name;
	private String img_local_url;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getImg_rul() {
		return img_rul;
	}
	public void setImg_rul(String img_rul) {
		this.img_rul = img_rul;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBarcode_number() {
		return barcode_number;
	}
	public void setBarcode_number(String barcode_number) {
		this.barcode_number = barcode_number;
	}
	public Date getExpiration_period() {
		return expiration_period;
	}
	public void setExpiration_period(Date expiration_period) {
		this.expiration_period = expiration_period;
	}
	public Date getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	public int getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(int use_yn) {
		this.use_yn = use_yn;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getRegistrant() {
		return registrant;
	}
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getImg_local_url() {
		return img_local_url;
	}
	public void setImg_local_url(String img_local_url) {
		this.img_local_url = img_local_url;
	}
	
	
	
}
