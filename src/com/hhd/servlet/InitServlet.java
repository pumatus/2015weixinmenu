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
		// ��ȡweb.xml�е����ò���
		TokenThread.appid = getInitParameter("appid");
		TokenThread.appsecret = getInitParameter("appsecret");

		log.info("weixin api appid:{}", TokenThread.appid);
		log.info("weixin api appsecret:{}", TokenThread.appsecret);

		// δ����appid\appsecret�Ǹ�����ʾ
		if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
			log.error("appid and appsecret configuration error, please check carefully.");
		} else {
			// ������ʱ��ȡaccess_token���߳�
			new Thread(new TokenThread()).start();
		}
	}
}
