package com.hhd.po;

public class WeiXinOauth2Token {
	/**
	 * Õ¯“≥ ⁄»®–≈œ¢
	 */
	private String accessToken;
	private int expiresIn;
	private String refreshToken;
	private String openId;
	private String scope;

	@Override
	public String toString() {
		return "WeiXinOauth2Token [accessToken=" + accessToken + ", expiresIn="
				+ expiresIn + ", refreshToken=" + refreshToken + ", openId="
				+ openId + ", scope=" + scope + ", getAccessToken()="
				+ getAccessToken() + ", getExpiresIn()=" + getExpiresIn()
				+ ", getRefreshToken()=" + getRefreshToken() + ", getOpenId()="
				+ getOpenId() + ", getScope()=" + getScope() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
