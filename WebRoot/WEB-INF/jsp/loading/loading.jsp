<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload=showTime();>
<!-- filter:  -->
<div id="sending" style="position:absolute; top:30%; left:44%;  width: 180px; height: 81px; line-height:80px; background-color: #eeeeee; float: left;border: 3px solid #B2D0F7; visibility: 
hidden;">
	
		<span class="loading-indicator">&nbsp;</span> 正在登录, 请稍候...
	
</div>
</body>
</html>


<%-- <!-- 所有包含这个页面的页面，将会实现页面正在加载的提示信息，注意必须放在页面的最前面 author:kiral--> 
<%@ page contentType="text/html;charset=UTF-8"%>
<%String ctx=request.getContextPath();%>

<div id="loading">
	<div class="loading-indicator">
		页面正在加载中...
	</div>
</div>
<script type="text/javascript">
//判断页面是否加载完毕，如果加载完毕，就删除加载信息的DIV
function document.onreadystatechange()
{
	try
	{
		if (document.readyState == "complete") 
		{
	     	delNode("loading");
	    }
    }
    catch(e)
    {
    	alert("页面加载失败");
    }
}

//删除指定的DIV
function  delNode(nodeId)
{   
  try
  {   
	  var div =document.getElementById(nodeId);  
	  if(div !==null)
	  {
		  div.parentNode.removeChild(div);   
		  div=null;    
		  CollectGarbage(); 
	  }  
  }
  catch(e)
  {   
  	   alert("删除ID为"+nodeId+"的节点出现异常");
  }   
}
</script>

 --%>








