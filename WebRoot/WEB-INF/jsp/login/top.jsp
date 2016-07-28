<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script language="javascript">
	//setTimeout("self.location.reload();",1000);
	window.setInterval('showTime()',1000);
	function showTime()
	{
		var today = new Date();
		var day; 
		var date;
		if(today.getDay()==0) day = "星期日";
		if(today.getDay()==1) day = "星期一";
		if(today.getDay()==2) day = "星期二";
		if(today.getDay()==3) day = "星期三";
		if(today.getDay()==4) day = "星期四";
		if(today.getDay()==5) day = "星期五";
		if(today.getDay()==6) day = "星期六";
		date = (today.getFullYear()) + "年" + (today.getMonth() + 1 ) + "月" + today.getDate() + "日 " + 
		day+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
		document.getElementById("time").innerHTML=date;
	}
</script>

</head>
<body onload=showTime();>

    <div style="height: 40px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px;
         color: #fff; font-family: Verdana, 微软雅黑,黑体;">
         <div align="center" style="height:22px;font-size: 30px; line-height: 33px;"><span><!-- 权限管理系统 --></span></div>
        <div align="right" style="height:18px; "><span id="time"></span></div>
       
    </div>
</body>
</html>