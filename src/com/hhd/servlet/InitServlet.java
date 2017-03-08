package com.hhd.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhd.thread.TokenThread;
import com.hhd.util.WeiXinUtil;

public class InitServlet extends HttpServlet {
	private static final long seriaVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

	public void init() throws ServletException {
		// 获取web.xml中的配置参数
		TokenThread.appid = getInitParameter("appid");
		TokenThread.appsecret = getInitParameter("appsecret");

		log.info("weixin api appid:{}", TokenThread.appid);
		log.info("weixin api appsecret:{}", TokenThread.appsecret);

		// 未配置appid\appsecret是给出提示
		if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
			log.error("appid and appsecret configuration error, please check carefully.");
		} else {
			// 启动定时获取access_token的线程
			new Thread(new TokenThread()).start();
		}
	}
}
