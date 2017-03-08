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
 * XML转为MAP集合
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
	 * 扩展xstream使其支持CDATA   XML格式中的标签
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
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
	 * 文本消息解析为XML格式
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文消息解析为XML格式
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
	 * 图文消息 可以是集合
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
		news.setTitle("创新农厂");
		news.setDescription("描述创新农厂");
		news.setPicUrl("http://1234567.duapp.com/WieXin/image/ifarms.jpg");
		news.setUrl("www.innofarms.com");

		newsList.add(news);

		newsMessage.setToUserName(fromUserName);// 开发者账号
		newsMessage.setFromUserName(toUserName);// 接收方帐号（收到的OpenID）
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 微信客户端发送的内容
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
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注,请按照菜单提示进行操作:\n\n");
		sb.append("1.创新农厂文字\n");
		sb.append("2.创新农厂网页\n\n");
		sb.append("回复? 调出此菜单。");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("您选的是文字");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"http://www.baidu.com\">您选的是网页</a>");
		return sb.toString();
	}
}
