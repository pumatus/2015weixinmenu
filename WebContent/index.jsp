<%@page import="com.hhd.util.WeiXinUtil,java.util.*,com.hhd.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="image/H_Letter.ico">
<title>微信测试</title>
</head>
<body>
	<H1>呵呵哒</H1>
</body>
<H1>
	<%
		out.println(request.getAttribute("openid0"));
		out.println(request.getAttribute("phone0"));
		out.println(request.getAttribute("openid1"));
		out.println(request.getAttribute("phone1"));
	%>
</H1>
<form name="myForm" method="post"
	action="com.hhd.servlet/openUserServlet">
	<table border="1">
		<!-- <tr>  
           <td>openid   </td>  
           <td><input name="openid"type="text" /></td>         
        </tr>   -->
		<tr>
			<td>phone</td>
			<td><input name="phone" type="text" /></td>
		</tr>
		<tr>
			<td>passwrd</td>
			<td><input name="password" type="text" /></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Submit" /></td>
		</tr>
	</table>
</form>
</html>