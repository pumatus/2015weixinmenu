package com.hhd.util;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhd.po.WeiXinOauth2Token;

public class AdvancedUtil {
	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
	
	/**
	 * 获取网页授权凭证
	 * @param appId 票据
	 * @param appSecret  密钥
	 * @param code
	 * @return WeiXinOauth2Token
	 */
	public static WeiXinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		WeiXinOauth2Token wat = null;
		String requestUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		JSONObject jsonObject = WeiXinUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeiXinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}
}
