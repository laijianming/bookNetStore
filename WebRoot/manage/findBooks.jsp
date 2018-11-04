<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp" %>



<table id="tb5" >
			<%-- varStatus是<c:forEach>jstl循环标签的一个属性，varStatus属性。
			 varStatus=“status”事实上定义了一个status名的对象作为varStatus的绑定值。
			 该绑定值也就是status封装了当前遍历的状态，比如，可以从该对象上查看是遍历到了第几个元素：${status.count} --%>
			
		
		<c:forEach items="${books.records}" var="b" varStatus="vs"> 
			<tr>
				<td rowspan="5" style="width : 150px ;height : 200px">
					<img alt="" src="${pageContext.request.contextPath}/${b.imgPath}" style="width : 150px ;height : 200px">
				</td>
				<th>书名 : </th>
				<td>${b.name}</td>
			</tr>
			<tr>
				<th>作者 : </th>
				<td>${b.author}</td>
			</tr>
			<tr>
				<th>&nbsp;￥ :</th>
				<td>${b.price}</td>
			</tr>
			<tr>
				<th>简介 : </th>
				<td><textarea readonly="readonly" rows="3" cols="38"  >${b.description}</textarea></td>
				<!--  readonly="readonly" 设置只读属性，不可修改其内容  -->
			</tr>
			<tr>
				<th>种类 : </th>
				<td>${b.category}</td>
			</tr>
			<tr>
				<td style="text-align:center" colspan="3"><a href="${pageContext.request.contextPath }/servlet/BackstageServlet?oper=modifyBook&id=${b.id}">修改</a> <a href="${pageContext.request.contextPath }/servlet/BackstageServlet?oper=deleteBook&id=${b.id}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
	
	
	第${books.currentPageNum}页/共${books.totalPageNum}页 &nbsp; &nbsp;
	<%-- <a href="">第${ }页/共${ }页</a> --%>
	<a href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllBooks&num=1">首页&nbsp;</a>
	<a href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllBooks&num=${books.currentPageNum}&turnPage=prePageNum">上一页&nbsp;</a>
	<a href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllBooks&num=${books.currentPageNum}&turnPage=nextPageNum">下一页&nbsp;</a>
	<a href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllBooks&num=${books.totalPageNum}">尾页&nbsp;</a>
	 &nbsp; &nbsp;
	 <input type="text" size="3" id="num" name="num" /><input type="button" id="bt1" value="跳转" onclick="jump();"/>
	 
	 <script type="text/javascript">
		function jump(){
			var numValue = document.getElementById("num").value;
			console.log(num);
			//验证
			if(!/^[1-9][0-9]*$/.test(numValue)){
				alert("请输入正确的页码");
				return;
			}
			if(numValue > ${books.totalPageNum}){
				alert("页码不能超过最大页数");
				return;
			}
			window.location.href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllBooks&num="+numValue+""; //转发的路径
		}
	</script>



</body>
</html>
