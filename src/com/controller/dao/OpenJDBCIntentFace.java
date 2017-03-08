package com.controller.dao;

import java.util.List;

import com.hhd.po.po;


public interface OpenJDBCIntentFace {
	
	public List<po> getAll();
	public boolean getUser(String phone, String password);
	public boolean getOpenId(String openid);
	public po getOpenIdToUser(String openid);
	public boolean updateOpenId(String openid,String phone);
}
