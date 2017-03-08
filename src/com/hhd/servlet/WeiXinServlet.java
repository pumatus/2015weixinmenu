package com.hhd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.hhd.service.CoreService;
import com.hhd.util.CheckUtil;

public class WeiXinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 加密/校验流程如下：
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		// 新加的
		out.close();
		out = null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// req.setCharacterEncoding("UTF-8");
		// resp.setCharacterEncoding("UTF-8");
		// PrintWriter out = resp.getWriter();
		// try {
		// Map<String, String> map = MessageUtil.xmlToMap(req);
		// String fromUserName = map.get("FromUserName");
		// String toUserName = map.get("ToUserName");
		// String msgType = map.get("MsgType");
		// String content = map.get("Content");
		// // String createTime = map.get("CreateTime");
		// String message = null;
		// if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
		// if ("1".equals(content)) {
		// message = MessageUtil.initText(toUserName, fromUserName,
		// MessageUtil.firstMenu());
		// } else if ("2".equals(content)) {
		// message = MessageUtil.initText(toUserName, fromUserName,
		// MessageUtil.secondMenu());
		// } else if ("?".equals(content) || "？".equals(content)) {
		// message = MessageUtil.initText(toUserName, fromUserName,
		// MessageUtil.menuText());
		// }
		// } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
		// String eventType = map.get("Event");
		// if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
		// message = MessageUtil.initText(toUserName, fromUserName,
		// MessageUtil.menuText());
		// }
		// }
		// System.out.println(message);
		// out.print(message);
		// } catch (DocumentException e) {
		// e.printStackTrace();
		// } finally {
		// out.close();
		// }

		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		// 接收参数微信加密签名、 时间戳、随机数
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");

		PrintWriter out = resp.getWriter();
		// 请求校验
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			// 调用核心服务类接收处理请求
			String respXml = CoreService.processRequest(req);
			out.print(respXml);
		}
		out.close();
		out = null;
	}
}
