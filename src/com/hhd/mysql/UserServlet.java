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
 * �ٶ���BAE MySQL����
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
			// ��ѯUSER
			List<HashMap<String, Object>> userList = queryUser(request);
			// ����LIST
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

		// ��request����ͷ��ȡ��IP �˿� �û��� ����
		String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
		String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
		String username = request.getHeader("BAE_ENV_AK");
		String password = request.getHeader("BAE_ENV_SK");

		// ���ݿ�����
		String dbName = "KAlxVgEGDlnoBHuxWvKT";
		// JDBC URL
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

		try {
			// MySQL Driver
			Class.forName("com.mysql.jdbc.Driver");
			// ��ȡ���ݿ�����
			Connection conn = DriverManager.getConnection(url, username,
					password);
			// �����ѯ����
			String sql = "select openId,phone from openUser";
			// ����PreparedStatement���󣨰����ѱ����SQL��䣩
			PreparedStatement ps = conn.prepareStatement(sql);
			// ִ�в�ѯ����ȡ�����
			ResultSet rs = ps.executeQuery();
			// ������ѯ�����
			while (rs.next()) {
				HashMap<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("openId", rs.getString("openId"));
				userMap.put("phone", rs.getInt("phone"));
				userList.add(userMap);
			}
			// �ر����ӣ��ͷ���Դ
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
