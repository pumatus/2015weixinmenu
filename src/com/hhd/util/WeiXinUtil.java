package com.hhd.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhd.menu.Button;
import com.hhd.menu.ClickButton;
import com.hhd.menu.Menu;
import com.hhd.menu.ViewButton;
import com.hhd.po.AccessToken;

public class WeiXinUtil {
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
	public static final String APPID = "wxfbf3ba234576a177";// 应用ID
	public static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";// 应用密钥
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url, String outStr)
			throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * 获取access_token GET POST
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		// JSONObject jsonObject = doGetStr(url);
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}

	/**
	 * 获取 access_token HTTPS
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessTokenHTTPS(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		try {
			if (null != jsonObject) {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			}
		} catch (Exception e) {
			accessToken = null;
			// 获取token失败
			log.error("获取token失败 errcode:{} errmsg:{}",
					jsonObject.getInt("errcode"),
					jsonObject.getString("errmsg"));
		}
		return accessToken;
	}

	/**
	 * 发起https请求获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（get post）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject （通过JSONObject.get(key)的方式获取json对象的属性值）
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer sb = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式get post
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式 防止中文错乱
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(sb.toString());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 获取微信网页授权 OPENID
	 * 
	 * @param code
	 * @return
	 */
	public static String getOpenid(String code) {
		String openid = null;
		// String appid = "";
		// String appsecret = "";
		String openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		String requestUrl = openid_url.replace("APPID", APPID)
				.replace("APPSECRET", APPSECRET).replace("CODE", code);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				openid = jsonObject.getString("openid");
			} catch (Exception e) {
				openid = null;
				// 获取openid失败
				log.error("获取openid失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return openid;
	}

	/**
	 * 组长菜单
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ClickButton button1 = new ClickButton();
		button1.setName("周边");
		button1.setType("click");
		button1.setKey("11");

		ViewButton button2 = new ViewButton();
		button2.setName("我的账户");
		button2.setType("view");
		button2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfbf3ba234576a177&redirect_uri=http://1234567.duapp.com/WieXin/openUserServlet&response_type=code&scope=snsapi_base&state=hhd#wechat_redirect");

		ViewButton button22 = new ViewButton();
		button22.setName("我的报表");
		button22.setType("view");
		button22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfbf3ba234576a177&redirect_uri=http://1234567.duapp.com/WieXin/openUserServlet&response_type=code&scope=snsapi_base&state=baobiao#wechat_redirect");

		ClickButton button3 = new ClickButton();
		button3.setName("扫码菜单");
		button3.setType("scancode_push");
		button3.setKey("33");

		ClickButton button4 = new ClickButton();
		button4.setName("地理位置");
		button4.setType("location_select");
		button4.setKey("44");

		Button button = new Button();
		button.setName("咨询");
		button.setSub_button(new Button[] { button3, button4 });

		Button mybutton = new Button();
		mybutton.setName("我的");
		mybutton.setSub_button(new Button[] { button2, button22 });

		menu.setButton(new Button[] { button1, mybutton, button });
		return menu;
	}

	/**
	 * 创建接口菜单 用POST方式
	 * 
	 * @param token
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String token, String menu)
			throws ParseException, IOException {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	/**
	 * 查询菜单接口 GET
	 * 
	 * @param token
	 * @return
	 */
	public static JSONObject queryMenu(String token) {
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}

	/**
	 * 删除菜单接口 GET
	 * 
	 * @param token
	 * @return
	 */
	public static int deleteMenu(String token) {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
