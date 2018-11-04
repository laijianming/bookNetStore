<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/customer/header.jsp" %>



<table id="tb1" >
			<%-- varStatus是<c:forEach>jstl循环标签的一个属性，varStatus属性。
			 varStatus=“status”事实上定义了一个status名的对象作为varStatus的绑定值。
			 该绑定值也就是status封装了当前遍历的状态，比如，可以从该对象上查看是遍历到了第几个元素：${status.count} --%>
		<c:forEach items="${books.records}" var="bs" varStatus="vs"> 
			<tr>
				<c:forEach items="${bs}" var="b" varStatus="vs"> 
					
					<td>
						<a class="a1_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/CustomerServlet?open=detailedInfo&id=${b.id}">
							<img alt="" src="${pageContext.request.contextPath}/${b.imgPath}" style="width : 150px ;height : 200px"><br>
							书名 : ${b.name}<br>
							作者 : ${b.author}<br>
							&nbsp;￥ : ${b.price}<br>
							简介 : <textarea readonly="readonly" rows="3" cols="38" style="width : 150px;">${b.description}</textarea><br>
							<!--  readonly="readonly" 设置只读属性，不可修改其内容  -->
							种类 : ${b.category}<br>
						</a>
					</td>
					
					<td style="width : 50px"></td>
				</c:forEach>
			</tr>
		</c:forEach>
		
	</table>
	
	
	第${books.currentPageNum}页/共${books.totalPageNum}页 &nbsp; &nbsp;
	<%-- <a href="">第${ }页/共${ }页</a> --%>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/CustomerServlet?open=findAllBooks&num=1">首页&nbsp;</a>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/CustomerServlet?open=findAllBooks&num=${books.currentPageNum}&turnPage=prePageNum">上一页&nbsp;</a>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/CustomerServlet?open=findAllBooks&num=${books.currentPageNum}&turnPage=nextPageNum">下一页&nbsp;</a>
	<a class="a_hover" style="text-decoration : blink;" href="${pageContext.request.contextPath}/servlet/CustomerServlet?open=findAllBooks&num=${books.totalPageNum}">尾页&nbsp;</a>
	 &nbsp; &nbsp;
	 <input type="text" size="3" id="num" name="num" /><input class="a_hover" style="text-decoration : blink;" type="button" id="bt1" value="跳转" onclick="jump();"/>
	 
	 <!-- 跳转页面 -->
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
			window.location.href="${pageContext.request.contextPath}/servlet/CustomerServlet?open=findAllBooks&num="+numValue+""; //转发的路径
		}
	</script>
</body>
</html>

