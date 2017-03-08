package com.hhd.test;

import net.sf.json.JSONObject;

import com.hhd.po.AccessToken;
import com.hhd.util.WeiXinUtil;

public class WeiXinTest {
	public static void main(String[] args) {
		try {
			/* 用于打印服务号暂时票据 与 有效时间 */
			AccessToken token = WeiXinUtil.getAccessToken();
			System.out.println("票据：" + token.getToken());
			System.out.println("有效时间:" + token.getExpiresIn());

			/* 用于微信后台创建菜单 */
			String menu = JSONObject.fromObject(WeiXinUtil.initMenu())
					.toString();
			int result = WeiXinUtil.createMenu(token.getToken(), menu);
			if (result == 0) {
				System.out.println("创建菜单成功");
			} else {
				System.out.println("错误码:" + result);
			}

			/* 用于微信后台查询菜单 */
			// JSONObject jsonObject = WeiXinUtil.queryMenu(token.getToken());
			// System.out.println(jsonObject);

			/* 用于微信后台删除菜单 */
//			 int result = WeiXinUtil.deleteMenu(token.getToken());
//			 if (result == 0) {
//			 System.out.println("菜单删除成功！");
//			 } else {
//			 System.out.println(result);
//			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
