<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员登入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath }/css/my.css">
	

  </head>
  <a href=" ${pageContext.request.contextPath }/customer/index.jsp">
  	<img style=" position:absolute; left:0px; top:0px; width : 100px; height : 40px;" alt="" src="${pageContext.request.contextPath }/imgs/20.png">
  	<br>
  		<font style=" position:absolute; left:0px; top:40px;font-size: 15px; ">返回IBook首页</font>
  </a>
  <br>  <br>  <br>
  <h1 style="text-align : center;">后台管理系统界面</h1>
  <br>  <br>
  <body background="../imgs/9.jpg">
  		<table id="tb7"  align="center">
  			<tr >
  				<td colspan="2" style="text-align : center;color : #EEC900;">管理员登入</td>
  			</tr>
  			<tr>
  				<td>
  					账号 : 
  				</td>
  				<td style="width : 340px;">
  					<input type="text" name="name"  id="name"><span style="color : red; text-size: 18px;" id="if1"></span>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					密码 : 
  				</td>
  				<td style="width : 340px;">
  					<input type="password" name="password"  id="password"><span style="color : red; text-size: 18px;"  id="if2"></span>
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2"  style="text-align : center;">
  					<input type="button" id="login"  value="                 登入                 ">
  				</td>
  			</tr>
  		
  		</table>
  <script type="text/javascript" src="http://localhost:8080/day00_00NetStore/util.js" ></script>
  <script type="text/javascript">

	window.onload=function(){
		document.getElementById("login").onclick=function(){
			var flag = true;
			console.log(document.getElementsByName('name')[0].value); //可以得到值
			if(document.getElementsByName('name')[0].value != "" && document.getElementsByName('name')[0].value != null ){
				console.log("不等于空");
			}else{
				document.getElementById("if1").innerHTML="账号不能为空";
				console.log("等于空");
				flag = false;
			}
			if(document.getElementsByName('password')[0].value != "" && document.getElementsByName('name')[0].value != null ){
				console.log("不等于空");
			}else{
				document.getElementById("if2").innerHTML="密码不能为空";
				console.log("等于空");
				flag = false;
			}
				if(flag == true){
				var name = document.getElementById("name").value;
				var password = document.getElementById("password").value;
				
				var xhr = getXmlHttpRequest();
		      
				xhr.onreadystatechange=function(){
					if(xhr.readyState==4){
						if(xhr.status==200){
							var msg = xhr.responseText; 
							if(msg == "用户名或密码错误"){
								alert(msg);
							}else{
							 window.location.href=  msg;
							}
						}
					}
				}
			 //POST传送数据到后台服务器，需要设置请求头，这是与GET不同的地方
	    	 xhr.open("POST","http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?time="+new Date().getTime()+"&open=login");
	    	 
		     xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	 // var formdata = "name="+data+"&password=123456";
	    	 xhr.send("name="+name+"&password="+password);
	    	
		
		}
	/* 发出异步请求 */
}	
}
 

		
</script>	
  
			


  </body>

</html>
