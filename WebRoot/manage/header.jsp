<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + ":/"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>后台管理首页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/my.css" />


</head>



	<body>
	<a href=" ${pageContext.request.contextPath }/customer/index.jsp">
  	<img style=" position:absolute; left:0px; top:0px; width : 100px; height : 40px;" alt="" src="${pageContext.request.contextPath }/imgs/20.png">
  	<br>
  		<font style=" position:absolute; left:0px; top:40px;font-size: 15px; ">返回IBook首页</font>
  </a>
		<br>
		<br>
		<h2>
			<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/manage/welcome.jsp">后台管理页面</a>
		</h2>
		<br>
		<br>
		<span style="text-align : right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		管理员 : ${sessionScope.name}&nbsp;</span><a class="a_hover" href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=logout" >注销</a>
		<div id="d1">
			<a class="a_hover"  href="${pageContext.request.contextPath}/manage/addCategory.jsp">添加分类
				&nbsp;</a>
			<a class="a_hover"  href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllCategory">查询分类
				&nbsp;</a>
			<a class="a_hover"  href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=addBook">添加书籍 &nbsp;</a>
			<a class="a_hover"  href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllBooks">查询书籍 &nbsp;</a>
			<a class="a_hover"  href="">待处理订单 &nbsp;</a>
			<a class="a_hover"  href="">已处理订单 &nbsp;</a>
		</div>
		<br>
		<br>
		<br>
