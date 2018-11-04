<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
	<form action="${pageContext.request.contextPath}/servlet/BackstageServlet?oper=modify" method="post">
	<table id="tb3" >
		<tr>
			<th style="color:#FFFFFF ">选择</th>
			<th style="color:#FFFFFF ">分类名称</th>
			<th style="color:#FFFFFF ">分类描述</th>
			<th style="color:#FFFFFF ">操作</th>
		</tr>
		<tr>
					<td><input type="checkbox" name="id" value="${c.id}"  checked="checked"/></td>
					<td><input type="text" name="name"  value="${c.name}"/></td>
					<td><input type="text" name="description" value="${c.description}"/></td>
					<td><input type="button" value="保存" id="save"/></td>
				
		</tr>
	</table>
	</form>
<script type="text/javascript">

	window.onload=function(){
		document.getElementById("save").onclick=function(){
			console.log(document.getElementsByName("name")[0].value); //可以得到值
			if(document.getElementsByName("name")[0].value != "" && document.getElementsByName("name")[0].value != null ){
				this.form.submit();
				console.log("不等于空");
			}else{
				alert("分类名称不能为空");
				console.log("等于空");
			}
		}

	}
	
	
</script>


</body>
</html>

