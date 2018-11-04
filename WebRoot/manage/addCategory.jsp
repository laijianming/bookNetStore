<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>



	<form action="${pageContext.request.contextPath}/servlet/BackstageServlet?open=addCategory" method="post" id="form1" >
		<table  id="tb1">
    		<tr>
    			<td style="background-color: #EE9A00 " >分类名称：</td>
    			<td style="background-color: #c3f3c3 " >
    				<input type="text" name="name" id="categoryName"  >
    				<span id="if" style="color : red"> </span>
    			</td>
    		</tr>
    		<tr>
    			<td style="background-color: #EE9A00 " >分类描述：</td>
    			<td style="background-color: #c3f3c3 ">
    				<textarea rows="3" cols="38" name="description"  ></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2" style="text-align:center">
    				<input type="button" value="                                        保              存                                       " id="save" />
    			</td>
    		</tr>
    	</table>
	</form>
<script type="text/javascript">

	window.onload=function(){
		document.getElementById("save").onclick=function(){
			console.log(document.getElementsByName('name')[0].value); //可以得到值
			if(document.getElementsByName('name')[0].value != "" && document.getElementsByName('name')[0].value != null ){
				this.form.submit();	 	
				console.log("不等于空");
			}else{
				document.getElementById("if").innerHTML="分类名称不能为空";
				console.log("等于空");
			}
		}

	}
	
	
</script>
  </body>
</html>

<!-- <script type="text/javascript" src="http://localhost:8080/day00_00NetStore/util.js" ></script>
<script type="text/javascript">

		window.onload=function(){
			
			
			document.getElementById("save").onclick=function(){
				
				var xhr = getXmlHttpRequest();
		      
				xhr.onreadystatechange=function(){
					if(xhr.readyState==4){
						if(xhr.status==200){
							//在div中显示商品的信息
							document.getElementById("if").innerHTML=xhr.responseText;
						}
					}
				}
		 //POST传送数据到后台服务器，需要设置请求头，这是与GET不同的地方
    	 xhr.open("POST","http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?time="+new Date().getTime());
    	 
	     xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    	 // var formdata = "name="+data+"&password=123456";
    	 xhr.send("name="+document.getElementById("categoryName").value);
    }
} -->

<!-- /*  
//全局变量声明XMLHTTPRequsert对象，方便各方法使用
var xmlHTTP;

//创建XMLHTTPRequest对象
function createXMLHTTPRequest() {
    if(window.ActiveXObject){
        //IE5、6对XMLHTTPRequest的实现
        xmlHTTP=new ActiveXObject("Microsoft.XMLHTTP");
    }else{
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlHTTP=new XMLHttpRequest();
    }
}

//进行Ajax操作，将用户名异步发送到服务器
function doAjax(data) {
	
	console.log(formdata);
    //创建XMLHTTPRequest对象
    createXMLHTTPRequest();
    //建立连接（方式、URL、是否异步）
    xmlHTTP.open("post","http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?time="+new Date().getTime(),true);
    //POST传送数据到后台服务器，需要设置请求头，这是与GET不同的地方
    xmlHTTP.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    //对该对象进行监听
    xmlHTTP.onreadystatechange=function () {
        //对readyState和status进行判断，如果响应成功则获取后台返回的数据
         if(xmlHTTP.readyState==4&&xmlHTTP.status==200){
            //获取响应数据
            var value=xmlHTTP.responseText;
            var messageshow=document.getElementById("messageshow");
            if(value=="0"){
               messageshow.style.color="red";
               messageshow.innerText="用户名存在";
            }else{
                messageshow.style.color="green";
                messageshow.innerText="用户名可用";
            }

        } 
    }
    //post方式在send中以键值对的方式发送数据，后台则以getParameter()方法获取用户发送过去的数据
    var formdata = "name="+data+"&password=123456";
    xmlHTTP.send(formdata);
    
}
window.onload=function(){
	document.getElementById("save").onclick=function(){
		var data = document.getElementById("categoryName").value;
		doAjax(data);
	}
} */
</script> -->