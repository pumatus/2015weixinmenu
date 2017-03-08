package com.hhd.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.dao.OpenDao;
import com.hhd.po.WeiXinOauth2Token;
import com.hhd.po.po;
import com.hhd.util.AdvancedUtil;
import com.hhd.util.WeiXinUtil;

/**
 * Servlet implementation class openUserServlet
 * 
 * 此servlert有待代码优化
 */
public class openUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public openUserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();// 设置session属性

		OpenDao fm = new OpenDao();
		po p = new po();

		// 取出state 状态
		String state = request.getParameter("state");
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");

		String accessToken = "";
		String openId = "";

		if (!"authdeny".equals(code)) {
			// 获取网页授权 access_token
			WeiXinOauth2Token weixinOauth2Token = AdvancedUtil
					.getOauth2AccessToken(WeiXinUtil.APPID,
							WeiXinUtil.APPSECRET, code);
			// 网页授权接口访问凭证
			accessToken = weixinOauth2Token.getAccessToken();
			// 用户唯一标识
			openId = weixinOauth2Token.getOpenId();
		}
		request.setAttribute("openId", openId);
		request.setAttribute("accessToken", accessToken);
		request.setAttribute("code", code);
		// 根据状态 判断业务逻辑
		if ("baobiao".equals(state)) {
			boolean openyn = fm.getOpenId(openId);
			if (openyn == true) {
				p = fm.getOpenIdToUser(openId);
				String pOpenId = p.getOpenid();
				String pPhone = p.getPhone();// 把这电话给他作为参数
				String pPassword = p.getPassword();
				request.setAttribute("pOpenId", pOpenId);
				request.setAttribute("pPhone", pPhone);
				request.setAttribute("pPassword", pPassword);
				request.getRequestDispatcher("/baobiao.jsp").forward(request,
						response);
			} else {
				request.getSession().setAttribute("sessionOpenId", openId);
				request.getRequestDispatcher("/login.jsp").forward(request,
						response);
			}
		} else if ("hhd".equals(state)) {

			boolean openyn = fm.getOpenId(openId);
			if (openyn == true) {
				p = fm.getOpenIdToUser(openId);
				String pOpenId = p.getOpenid();
				String pPhone = p.getPhone();// 把这电话给他作为参数
				String pPassword = p.getPassword();
				request.setAttribute("pOpenId", pOpenId);
				request.setAttribute("pPhone", pPhone);
				request.setAttribute("pPassword", pPassword);
				request.getRequestDispatcher("/OK.jsp").forward(request,
						response);
			} else {
				request.getSession().setAttribute("sessionOpenId", openId);
				request.getRequestDispatcher("/login.jsp").forward(request,
						response);
			}
		}
		// po p = new po();
		//
		// List<po> list = fm.getAll();
		// System.out.println(list.size());

		// try {
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i).getOpenid());
		// System.out.println(list.get(i).getPhone());
		// }
		// request.setAttribute("openid0", list.get(0).getOpenid());
		// request.setAttribute("phone0", list.get(0).getPhone());
		// request.setAttribute("openid1", list.get(1).getOpenid());
		// request.setAttribute("phone1", list.get(1).getPhone());
		// request.getRequestDispatcher("/index.jsp").forward(request,
		// response);
		// System.out.println("----");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

}
