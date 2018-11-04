<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/customer/header.jsp" %>

	<table id="tb2">
		<tr>
			<td colspan="3">种类 : ${book.category}<input type="hidden" name="id" id="id" value="${book.id }"></td>
			
		</tr>
		<tr>
			<td colspan="4"><hr></td>
		</tr>
		<tr>
		    <td rowspan="5"><img alt="" src="${pageContext.request.contextPath}/${book.imgPath}" style="width : 200px ;height : 300px"></td>
		    <td style="width : 50px"></td>
		    <th style="font-size: 18px;">${book.name}</th>
			 <%--    <td colspan="2" >${book.name}</td> --%>
	 	</tr>
	 	<tr>
	 		<td style="width : 50px"></td>
		  	<th>
		  		作者 :  <span>${book.author}</span>
		  	</th>
		    <td>
		    	
		    </td>
	 	</tr>
	 	<tr style="background-color: #F0F0F0 ">
	 		<td style="width : 50px;background-color: 	#FFFFFF"></td>
		    <th colspan="2">
		    	IBook价 : <span style="color : red;text-align: left;font-size: 30px;">${book.price}</span>￥
		    </th>
		</tr>
		<tr>
			<td style="width : 50px"></td>
		  	<th>
		  		书籍描述 :  
		  	</th>
		    <td>
		    	<textarea readonly="readonly" rows="5" cols="38" style="width : 350px;">${book.description}</textarea>
		    </td>
		</tr>
		<tr style=" height:50px">
			
		</tr>
	 	<tr>
	 		<td></td>
	 		<td>
  				<input type="button" id="reduse" value="-" style="min-width: 15px;min-height: 15px;">
  				<input type="text" id="num" name="num"  style="width :30px;text-align: center;" value="1">
  				<input type="button" id="increase" value="+" style="min-width: 15px;min-height: 15px;">
  			</td>
  			<td></td>
  			<td >
  				
  				<input id="add" type="button" class="a3_hover" style="background-color:red;text-decoration : blink;font-size: 26px;" value="&nbsp;&nbsp;加入购物车&nbsp;">
  				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				<a class="a2_hover" style="border: 1px solid red;color : red;text-decoration : blink;font-size: 26px;" href="">立即购买</a>
  			</td>
	  		
	 	</tr>
	 	<tr>
			<td colspan="4"><hr></td>
		</tr>
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
  		
  		document.getElementById("add").onclick=function(){
	  		var id = document.getElementById("id").value;
	  		var num = document.getElementById("num").value;
	  		window.location.href= "servlet/CustomerServlet?open=addTrolley&id="+id+"&num="+num;
  		}
	 	
  	
	 
  	
  }
  
  </script>
</html>

