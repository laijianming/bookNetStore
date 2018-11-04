<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>


	<table id="tb2" >
		<tr>
			<th style="color:#FFFFFF ">选择</th>
			<th style="color:#FFFFFF ">分类名称</th>
			<th style="color:#FFFFFF ">分类描述</th>
			<th style="color:#FFFFFF ">操作</th>
		</tr>
		<c:forEach items="${cs.records}" var="c" varStatus="vs">
			<tr class="${vs.index%2==0?'odd':'even' }">
				<td><input type="checkbox" name="ids" value="${c.id}" /></td>
				<td style=" width : 300px;">${c.name}</td>
				<td><textarea readonly="readonly" rows="3" cols="38">${c.description}</textarea><br></td>
				<td style=" width : 300px; "><a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/servlet/BackstageServlet?oper=modifyCategory&id=${c.id}">修改</a> <a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath }/servlet/BackstageServlet?oper=deleteCategory&id=${c.id}">删除</a></td>
			</tr>
		</c:forEach>
	
	</table>
	
	
	第${cs.currentPageNum}页/共${cs.totalPageNum}页 &nbsp; &nbsp;
	<%-- <a href="">第${ }页/共${ }页</a> --%>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllCategory&num=1">首页&nbsp;</a>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllCategory&num=${cs.currentPageNum}&turnPage=prePageNum">上一页&nbsp;</a>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllCategory&num=${cs.currentPageNum}&turnPage=nextPageNum">下一页&nbsp;</a>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllCategory&num=${cs.totalPageNum}">尾页&nbsp;</a>
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
			if(numValue > ${cs.totalPageNum}){
				alert("页码不能超过最大页数");
				return;
			}
			window.location.href="${pageContext.request.contextPath}/servlet/BackstageServlet?open=findAllCategory&num="+numValue+""; //转发的路径
		}
	</script>



</body>
</html>
