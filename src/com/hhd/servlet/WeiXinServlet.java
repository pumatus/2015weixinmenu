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

		// ����/У���������£�
		// 1. ��token��timestamp��nonce�������������ֵ�������
		// 2. �����������ַ���ƴ�ӳ�һ���ַ�������sha1����
		// 3. �����߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��

		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		// �¼ӵ�
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
		// } else if ("?".equals(content) || "��".equals(content)) {
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

		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		// ���ղ���΢�ż���ǩ���� ʱ����������
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");

		PrintWriter out = resp.getWriter();
		// ����У��
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			// ���ú��ķ�������մ�������
			String respXml = CoreService.processRequest(req);
			out.print(respXml);
		}
		out.close();
		out = null;
	}
}
