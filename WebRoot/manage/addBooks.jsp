<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp" %>

<!-- enctype="multipart/form-data" -->
	<form action="${pageContext.request.contextPath}/servlet/BackstageServlet?oper=saveBook" enctype="multipart/form-data"  method="post">
		<table id="tb4">
			<tr>
				<th>书名 : </th>
				<td>
					<input type="text" name="name" >
					<span id="if1" style="color : red"> </span>				
				</td>
			</tr>
			<tr>
				<th>作者 : </th>
				<td>
					<input type="text" name="author" >
					<span id="if2" style="color : red"> </span>	
				</td>
			</tr>
			<tr>
				<th>单价 : </th>
				<td>
					<input type="text" name="price" >
					<span id="if3" style="color : red"> </span>	
				</td>
			</tr>
			<tr>
				<th>图片 : </th>
				<td><input type="file" name="img"  value="选择图片"></td>
			</tr>
			<tr>
				<th>描述 : </th>
				<td>
					<textarea  rows="3" cols="38" name="description"></textarea>   <!-- 这个标签可以把输入文本框扩大 -->
				</td>
			</tr>
			<tr>
				<th>所属分类 : </th>
				<td>
					<select name="category">  <!-- 下拉式选择按钮 -->
    					<c:forEach items="${cs}" var="c">
    						<option  value="${c.name}" >${c.name}</option>
    					</c:forEach>
    				</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" ><!-- colspan 表格单元横跨两列的表 -->
					<input type="button" value="                                             保              存                                                 " id="save">
				</td>
				
			</tr>
			
		</table>
	
	
	
	</form>
	<script type="text/javascript">

	window.onload=function(){
		document.getElementById("save").onclick=function(){
			console.log(document.getElementsByName('name')[0].value); //可以得到值
			var flag = 3;
			//判断书的name是否为空
			if(document.getElementsByName('name')[0].value != "" && document.getElementsByName('name')[0].value != null ){
				console.log("不等于空");
			}else{
				flag--;
				document.getElementById("if1").innerHTML="分类名称不能为空";
				console.log("等于空");
			}
			//判断作者author是否为空
			if(document.getElementsByName('author')[0].value != "" && document.getElementsByName('author')[0].value != null ){
				console.log("不等于空");
			}else{
				flag--;
				document.getElementById("if2").innerHTML="作者名称不能为空";
				console.log("等于空");
			}
			//判断单价price 是否为空
			if(document.getElementsByName('price')[0].value != "" && document.getElementsByName('price')[0].value != null ){
				console.log("不等于空");
			}else{
				flag--;
				document.getElementById("if3").innerHTML="单价不能为空";
				console.log("等于空");
			}
			console.log(flag);
			if(flag == 3)
				this.form.submit();
		}

	}
	
	
</script>

  </body>
</html>
