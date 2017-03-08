<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表</title>
</head>
<body>报表界面
<%
		out.println(request.getAttribute("pOpenId"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("pPhone"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("pPassword"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("update"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("sessionOpenId"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("accessToken"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("code"));
		out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		out.println(request.getAttribute("openId"));
	%>
</body>
</html>