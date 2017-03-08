package com.hhd.po;

public class po {
	private String openid;
	private String phone;
	private String password;
	public String getOpenid() {
		return openid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "po [openid=" + openid + ", phone=" + phone + ", password="
				+ password + ", getOpenid()=" + getOpenid()
				+ ", getPassword()=" + getPassword() + ", getPhone()="
				+ getPhone() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
