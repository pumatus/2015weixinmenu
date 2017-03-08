package com.hhd.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hhd.po.News;
import com.hhd.po.NewsMessage;
import com.hhd.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XMLתΪMAP����
 * 
 * @author hehongda
 * 
 */
public class MessageUtil {

	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";

	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws DocumentException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		InputStream ins = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();

		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		ins.close();
		ins = null;
		return map;
	}

	/**
	 * ��չxstreamʹ��֧��CDATA   XML��ʽ�еı�ǩ
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// ������xml�ڵ��ת��������CDATA���
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * �ı���Ϣ����ΪXML��ʽ
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * ͼ����Ϣ����ΪXML��ʽ
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * ͼ����Ϣ �����Ǽ���
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("����ũ��");
		news.setDescription("��������ũ��");
		news.setPicUrl("http://1234567.duapp.com/WieXin/image/ifarms.jpg");
		news.setUrl("www.innofarms.com");

		newsList.add(news);

		newsMessage.setToUserName(fromUserName);// �������˺�
		newsMessage.setFromUserName(toUserName);// ���շ��ʺţ��յ���OpenID��
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * ΢�ſͻ��˷��͵�����
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName,
			String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * ���˵�
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע,�밴�ղ˵���ʾ���в���:\n\n");
		sb.append("1.����ũ������\n");
		sb.append("2.����ũ����ҳ\n\n");
		sb.append("�ظ�? �����˲˵���");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ѡ��������");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"http://www.baidu.com\">��ѡ������ҳ</a>");
		return sb.toString();
	}
}
