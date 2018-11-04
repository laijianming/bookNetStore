<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'header.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/cus.css">
	

  </head>
  
  <body>
  	<img alt="" src="http://localhost:8080/day00_00NetStore/imgs/IBook.jpg" style="background-position: center; width : 800 ; height :130px;"><br>
  	<c:if test="${sessionScope.user!=null}">欢迎您！${sessionScope.user.userId}&nbsp;&nbsp;&nbsp;<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/servlet/CustomerServlet?open=logout">注销</a></c:if>
  	<c:if test="${sessionScope.user==null}"><a class="a_hover" style="text-decoration : blink;color : red;" href="${pageContext.request.contextPath }/customer/login.jsp">亲，请登入&nbsp;&nbsp;</a><a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/customer/regist.jsp">注册&nbsp;</a></c:if>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/customer/index.jsp"><span>IBook首页</span></a>
  	&nbsp;&nbsp;
  	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/customer/cusinfo.jsp">我的IBook</a>
  	&nbsp;&nbsp;
  	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/servlet/CustomerServlet?open=trolley">购物车</a>
  	&nbsp;&nbsp;
  	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/customer/order.jsp">我的订单</a>
  	&nbsp;&nbsp;
  	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/customer/order.jsp">联系客服</a>
  	&nbsp;&nbsp;
  	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/manage/manage.jsp">管理员</a>
  	
  	<hr>
  	<br>  <br>
  
  
  
  