package com.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hhd.po.po;

/**
 * 数据库操作
 * @author Administrator
 *
 */
public class OpenDao extends BaseDao implements OpenJDBCIntentFace {

	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
//所有
	@Override
	public List<po> getAll() {
		List<po> li = new ArrayList<po>();
		try {
			conn = getConnection();
			String sql = "select openid,phone from openUser";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				po dd = new po();
				dd.setOpenid(rs.getString("openid"));
				dd.setPhone(rs.getString("phone"));
				li.add(dd);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeDB(conn, pst, rs);
		}
		return li;
	}
//查询是否有此用户
	@Override
	public boolean getUser(String phone, String password) {
		boolean yn = false;
		try {
			conn = getConnection();
			String sql = "select phone,password from openUser where phone=? and password=? ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, phone);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("登陆成功");
				yn = true;
			} else {
				System.out.println("登陆失败");
				yn = false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeDB(conn, pst, rs);
		}
		return yn;
	}
//查询是否有此openid
	@Override
	public boolean getOpenId(String openid) {
		boolean yn = false;
		try {
			conn = getConnection();
			String sql = "select * from openUser where openid=? ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, openid);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("有了");
				yn = true;
			} else {
				System.out.println("没有");
				yn = false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeDB(conn, pst, rs);
		}
		return yn;
	}
//查询此openid返回的用户记录
	@Override
	public po getOpenIdToUser(String openid) {
		po dd = new po();
		try {
			conn = getConnection();
			String sql = "select * from openUser where openid=? ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, openid);
			rs = pst.executeQuery();
			while (rs.next()) {
				dd.setOpenid(rs.getString("openid"));
				dd.setPhone(rs.getString("phone"));
				dd.setPassword(rs.getString("password"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeDB(conn, pst, rs);
		}
		return dd;
	}
//根据用户电话绑定微信账户
	@Override
	public boolean updateOpenId(String openid, String phone) {
		boolean yn = false;
		try {
			conn = getConnection();
			String sql = "update openUser set openid= ?  where phone= ? ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, openid);
			pst.setString(2, phone);
			int count = pst.executeUpdate();
			if (count==1) {
				System.out.println("能改");
				yn = true;
			} else {
				System.out.println("不能改");
				yn = false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeDB(conn, pst, rs);
		}
		return yn;
	}

}
