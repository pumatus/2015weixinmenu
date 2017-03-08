package com.hhd.mysql;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 百度云BAE MySQL连接
 * @author Administrator
 *
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1196941092414541883L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			PrintWriter out = response.getWriter();
			// 查询USER
			List<HashMap<String, Object>> userList = queryUser(request);
			// 遍历LIST
			for (HashMap<String, Object> map : userList) {
				request.setAttribute("1", map.get("openId"));
				request.setAttribute("2", map.get("phone"));
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
				//out.println(map.get("openId") + " " + map.get("phone"));
			}
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	private static List<HashMap<String, Object>> queryUser(
			HttpServletRequest request) {
		List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();

		// 从request请求头中取出IP 端口 用户名 密码
		String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
		String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
		String username = request.getHeader("BAE_ENV_AK");
		String password = request.getHeader("BAE_ENV_SK");

		// 数据库名称
		String dbName = "KAlxVgEGDlnoBHuxWvKT";
		// JDBC URL
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

		try {
			// MySQL Driver
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			Connection conn = DriverManager.getConnection(url, username,
					password);
			// 定义查询语言
			String sql = "select openId,phone from openUser";
			// 创建PreparedStatement对象（包含已编译的SQL语句）
			PreparedStatement ps = conn.prepareStatement(sql);
			// 执行查询并获取结果集
			ResultSet rs = ps.executeQuery();
			// 遍历查询结果集
			while (rs.next()) {
				HashMap<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("openId", rs.getString("openId"));
				userMap.put("phone", rs.getInt("phone"));
				userList.add(userMap);
			}
			// 关闭连接，释放资源
			rs.close();
			ps.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
}
