<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/customer/header.jsp" %>

   <h1>购物车</h1>
  	<table id="tb2">
  	
  	<c:forEach items="${trolley}" var="t" varStatus="vs"> 
  		<tr>
  			<td  rowspan="2">
  				<input type="checkbox" id="id" name="id" value="${t.id }">
  			</td>
  			<td rowspan="2">
  				<img alt="" src="${pageContext.request.contextPath}/${t.imgPath}" style="width:80px; height:100px;">
  			</td>
  			<td colspan="4">
  				${t.name}
  			</td>
  		</tr>
  		<tr>
  			<td style="color: red;">
  				￥：${t.totalPrice}
  			</td>
  			<td>
  				<input type="button" id="reduse" value="-" style="min-width: 15px;min-height: 15px;">
  				<input type="text" id="num" name="num"  style="width :30px;text-align: center;" value="${t.num }">
  				<input type="button" id="increase" value="+" style="min-width: 15px;min-height: 15px;">
  			</td>
  			<td >&nbsp;&nbsp;&nbsp;<input type="button" id="delete" value="删除" ></td>
  			
  		</tr>
  		<tr><td style="height:20"></td></tr>
  		</c:forEach>
  	</table>
  
  </body>
  <script type="text/javascript">
  	window.onload=function(){
  			var num = document.getElementById("num").value;
  			console.log("开始js");
  		document.getElementById("reduse").onclick=function(){
  			if(num == 1){
  			}else{
  				num--;
  				document.getElementById("num").value= num;
  				console.log("num-1");
  			}
  		}
  		document.getElementById("increase").onclick=function(){
  			if(num == 99){
  			}else{
  				num++;
  				document.getElementById("num").value= num;
  				console.log("num+1");
  			}
  		}
  		document.getElementById("delete").onclick=function(){
  		var id = document.getElementById("id").value;
  		window.location.href= "servlet/CustomerServlet?open=deleteTrolley&id="+id;
  		}
  	}
  </script>

</html>