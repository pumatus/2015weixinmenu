package com.controller.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {

	// 驱动器 路径 用户名 密码
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://61.50.114.230:3306/test";
	private static final String USERNAME = "test1";
	private static final String PASSWORD = "test1";

	// 3个接口
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * 获得连接
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	
	/**
	 * 关闭连接
	 */
	public static void freeDB(Connection con,PreparedStatement st,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(st!=null){
				st.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
