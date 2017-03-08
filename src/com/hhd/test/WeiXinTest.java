package com.hhd.test;

import net.sf.json.JSONObject;

import com.hhd.po.AccessToken;
import com.hhd.util.WeiXinUtil;

public class WeiXinTest {
	public static void main(String[] args) {
		try {
			/* ���ڴ�ӡ�������ʱƱ�� �� ��Чʱ�� */
			AccessToken token = WeiXinUtil.getAccessToken();
			System.out.println("Ʊ�ݣ�" + token.getToken());
			System.out.println("��Чʱ��:" + token.getExpiresIn());

			/* ����΢�ź�̨�����˵� */
			String menu = JSONObject.fromObject(WeiXinUtil.initMenu())
					.toString();
			int result = WeiXinUtil.createMenu(token.getToken(), menu);
			if (result == 0) {
				System.out.println("�����˵��ɹ�");
			} else {
				System.out.println("������:" + result);
			}

			/* ����΢�ź�̨��ѯ�˵� */
			// JSONObject jsonObject = WeiXinUtil.queryMenu(token.getToken());
			// System.out.println(jsonObject);

			/* ����΢�ź�̨ɾ���˵� */
//			 int result = WeiXinUtil.deleteMenu(token.getToken());
//			 if (result == 0) {
//			 System.out.println("�˵�ɾ���ɹ���");
//			 } else {
//			 System.out.println(result);
//			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
