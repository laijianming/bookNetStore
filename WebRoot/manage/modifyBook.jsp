<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp" %>




	<form action="${pageContext.request.contextPath}/servlet/BackstageServlet?oper=modifyBookInfo&oldImg=/${book.imgPath}" enctype="multipart/form-data" method="post">
		
		<table id="tb6" >
				<tr>
					<td colspan="3">
						<input type="hidden" name="id" value="${book.id}">
					</td>
				</tr>
				<tr>
					<td rowspan="5"  style="width : 150px ;height : 200px">
							<img id="img"  alt="图片加载失败" src="${pageContext.request.contextPath}/${book.imgPath}" style="width : 150px;height : 200px" >
					</td>
					<th><div>书名 : </div></th>
					<td>
							<input name="name" value="${book.name}" >
							<span id="if1" style="color : red"></span>	
					</td>
				</tr>
				<tr>
					<th>作者 : </th>
					<td>
						<input name="author" value="${book.author}"/>
						<span id="if2" style="color : red"> </span>
					</td>
				</tr>
				<tr>
					<th>&nbsp;￥ :</th>
					<td>
						<input name="price" value="${book.price}"/>
						<span id="if3" style="color : red"> </span>	
					</td>
				</tr>
				<tr>
					<th>简介 : </th>
					<td><textarea name="description" rows="3" cols="38"  >${book.description}</textarea></td>
				</tr>
				<tr>
					<!-- <td style="width : 100;">
						<input type="file" name="newImg" value="更换图片">
					</td> -->
					<th>种类 : </th>
					<td>
						<select name="category">  <!-- 下拉式选择按钮 -->
	    					<c:forEach items="${cs}" var="c">
	    						<option  value="${c.name}" >${c.name}</option>
	    					</c:forEach>
	    				</select>
					</td>
				</tr>
				<tr>
					<td >
						<input type="file" name="ImgPath" value="更换图片">
					</td>
					<td style="text-align:center" colspan="2" ><input type="button" value="保存" id="save"/></td>
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

