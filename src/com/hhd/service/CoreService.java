package com.hhd.service;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.hhd.po.TextMessage;
import com.hhd.util.MessageUtil;

/**
 * ���ķ�����
 * 
 * @author hehongda
 * @date 2015-10-31
 */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml��ʽ����Ϣ����
		String respXml = null;
		// Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "δ֪����Ϣ���ͣ�";
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.xmlToMap(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");
			// ���͵���Ϣ
			String content = requestMap.get("Content");

			// �ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
			textMessage.setContent(content);

			// �ı���Ϣ
			if (msgType.equals(MessageUtil.MESSAGE_TEXT)) {
				if ("1".equals(content)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.firstMenu());
					return respXml;
				} else if ("2".equals(content)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.secondMenu());
					return respXml;
				} else if ("?".equals(content) || "��".equals(content)) {
					respXml = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
					return respXml;
				} else if ("��Ҫ��ͼ����Ϣ".equals(content)) {
					respXml = MessageUtil.initNewsMessage(toUserName,
							fromUserName);
					return respXml;
				} else {
					respContent = "�����͵����ı���Ϣ��" + content;
					// �����ı���Ϣ������
					textMessage.setContent(respContent);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.textMessageToXml(textMessage);
					return respXml;
				}
			} else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				// ����˵���ȡ��Ϣʱ���¼����� �¼����ͣ�CLICK
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
					// ����˵���ת����ʱ���¼����� �¼�KEYֵ�����õ���תURL
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
