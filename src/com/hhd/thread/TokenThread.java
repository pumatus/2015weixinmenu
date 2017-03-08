package com.hhd.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhd.po.AccessToken;
import com.hhd.util.WeiXinUtil;

public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	// �������û�Ψһƾ֤
	public static String appid = "";
	// �������û�Ψһƾ֤��Կ
	public static String appsecret = "";
	public static AccessToken accessToken = null;

	@Override
	public void run() {
		while (true) {
			try {
				accessToken = WeiXinUtil.getAccessTokenHTTPS(appid, appsecret);
				if (null != accessToken) {
					log.info("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}",
							accessToken.getExpiresIn(), accessToken.getToken());
					// ����7000��
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// ���access_tokenΪnull��60����ٻ�ȡ
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
