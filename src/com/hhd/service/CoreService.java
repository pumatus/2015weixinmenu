package com.hhd.service;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.hhd.po.TextMessage;
import com.hhd.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author hehongda
 * @date 2015-10-31
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "未知的消息类型！";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.xmlToMap(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 发送的消息
			String content = requestMap.get("Content");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
			textMessage.setContent(content);

			// 文本消息
			if (msgType.equals(MessageUtil.MESSAGE_TEXT)) {
				if ("1".equals(content)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.firstMenu());
					return respXml;
				} else if ("2".equals(content)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.secondMenu());
					return respXml;
				} else if ("?".equals(content) || "？".equals(content)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
					return respXml;
				} else if ("我要看图文消息".equals(content)) {
					respXml = MessageUtil.initNewsMessage(toUserName,
							fromUserName);
					return respXml;
				} else {
					respContent = "您发送的是文本消息！" + content;
					// 设置文本消息的内容
					textMessage.setContent(respContent);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.textMessageToXml(textMessage);
					return respXml;
				}
			} else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				// 点击菜单拉取消息时的事件推送 事件类型，CLICK
				String eventType = requestMap.get("Event");
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
					return respXml;
				} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
					return respXml;
				} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
					// 点击菜单跳转链接时的事件推送 事件KEY值，设置的跳转URL
					String url = requestMap.get("EventKey");
					respXml = MessageUtil.initText(toUserName, fromUserName,
							url);
					return respXml;
				} else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
					String key = requestMap.get("EventKey");
					respXml = MessageUtil.initText(toUserName, fromUserName,
							key);
					return respXml;
				}
			} else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
				String label = requestMap.get("Label");
				respXml = MessageUtil.initText(toUserName, fromUserName, label);
				return respXml;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respContent;
	}
}
