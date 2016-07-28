<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<link rel="icon" href="${ctx}/js.ico" type="image/x-icon" />
<TITLE>登录界面</TITLE> 
<link rel="stylesheet" href="${ctx}/UI/css/login.css" />
<link rel="stylesheet" href="${ctx}/UI/css/loading/loading.css" />
</HEAD>
<BODY style="background: url(${ctx}/UI/css/images/bg-login.jpg); background-size:cover;">
<jsp:include page="../loading/loading.jsp"></jsp:include>
<jsp:include page="top.jsp"></jsp:include>
<DIV id=logo ></DIV>
<DIV id=login>
<FORM id=loginform method=post>
<P id=info >请输入用户名和密码</P>

<DIV class=control-group>
	<IMG class=icon src="${ctx}/UI/css/images/bg-loginName.png"><INPUT type=text id=loginName name=loginName placeholder="LoginName" onkeydown = 'Enter_Login(event)'>
</DIV>
<DIV class=control-group>
	<IMG class=icon src="${ctx}/UI/css/images/bg-loginPwd.png"><INPUT type=password id=passWord name=passWord placeholder="Password" onkeydown = 'Enter_Login(event)'> 
</DIV>

<DIV class=control-group>
	<IMG class=icon src="${ctx}/UI/css/images/bg-loginCaptcha.png"><INPUT type=text id=captcha name="captcha" placeholder="Captcha" style="width: 76px" size="4" onkeydown = 'Enter_Login(event)' onkeyup='validateCode(event)'> 
	<span id="codeImg"><img id="codeImge" src="code.do?captchaKey=${CAPTCHAKEY}"></span>
	<a style="color: #000000;" href="#" id="reloadCode">换一张</a> <a id="flag"> &nbsp; &nbsp;&nbsp;</a><span id="flagImage"></span>
</DIV>
<DIV class=login-btn>
	<INPUT id=login-btn value="登 录" type="button" name=login> 
	<INPUT id=reset-btn value="重置" type="reset" name=reset>
</DIV>
<div align="right" style="height:18px; CURSOR: pointer">
	<a target="_blank" href="<%=request.getContextPath() %>/ClientSetup.exe" >客户端软件下载</a>
</div>

</FORM>
</DIV>
<DIV id=login-copyright>Copyright © 2015 JIESI .All Rights Reserved</DIV>
<input type="hidden" value="${targetUrl}" id="targetUrl" />
<input type="hidden" value="${CAPTCHAKEY}" id="captchaKey" />
</BODY>
<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>


<SCRIPT>
	var cp="${ctx}";
function validateCode(event){
	var captcha = $("#captcha").val();
	var captchaKey = $("#captchaKey").val();
	if(event.keyCode != 9 && event.keyCode != 13){
		if(captcha==''){
			$("#flag").html(" &nbsp; &nbsp;&nbsp;");
			$("#flagImage").text("");
			return;
		}
		var param = {"captcha":captcha,"captchaKey":captchaKey};
		$.ajax({
			type:"post",
			url:"checkCode.do",
			dataType:"json",
			data:param,
		    async:true,
			success:function(data){
				if(data.result){
					$("#flag").text("");
					$("#flagImage").html("&nbsp;<img src='${ctx}/UI/css/images/success.png' />");
				}else{
					$("#flag").text("");
					$("#flagImage").html("&nbsp;<img src='${ctx}/UI/css/images/error.png' />");
				}
			},
			error:function(){
				alert('checkCode fail。。。');
			}
		}); 
		
	}
	
	
}


function Enter_Login(event){
	if(event.keyCode == 13){
		$("#login-btn").click();
		event.returnVallue = false;
	}
}

function disabledButton(){
	$("#login-btn").attr("disabled", "disabled");
	$("#reset-btn").attr("disabled", "disabled");
}

function enabledButton(){
	$("#login-btn").removeAttr("disabled"); //使按钮能够被点击
	$("#reset-btn").removeAttr("disabled");
}


$(function() {
	
	//定位光标
	$("input[name='loginName']").focus();
	enabledButton();
	//更换验证码图片
	$("#reloadCode").click(function(){
		var a=$.getRandom(1000);
		$("#codeImge").get(0).src='code.do?rd='+a+'&captchaKey=${CAPTCHAKEY}';
	});
	
	$("#login_dow").click(function(){
		window.open(cp+"/downLoad/getFileName.do?name=ClientSetup.exe", "客户端软件下载");
	}) 
   
	//登录进行验证、并获取用户登录信息。
	$("#login-btn").click(function(){
	 	var loginName = $("#loginName");
		var passWord = $("#passWord");
		var captcha = $("#captcha");
		if(loginName.val() == ""){
			alert("请输入用户名!");
			loginName.focus();
			return false;
		}
		if(passWord.val() == ""){
			alert("请输入密码!");
			passWord.focus();
			return false;
		}
		if(captcha.val() == ""){
			alert("请输入验证码!");
			captcha.focus();
			return false;
		}
		var targetUrl = $("#targetUrl").val();
		var captchaKey = $("#captchaKey").val();
		var post_data = {"loginName": loginName.val(), "passWord":passWord.val(), "captcha":captcha.val(), "targetUrl":targetUrl, "captchaKey":captchaKey};
		
		disabledButton();
		$.ajax({
			type:"post",
			url:"loginCheck.do?",
			dataType:"json",
			data:post_data,
		    async:false,
			success:function(data){
			    if(!data.result){
			    	if(data.message!='' && data.message!=null){
			    		alert(data.message);
			    	}
					if(data.message == '验证码不正确！'){
						var a=$.getRandom(1000);
						$("#codeImge").get(0).src='code.do?rd='+a+'&captchaKey=${CAPTCHAKEY}';
					}
					enabledButton();
				}else{
					if(targetUrl){
						window.location.href=targetUrl;
					}else{
						window.location.href="${ctx}/indexPage.do";
					}
				}
			},
			error:function(){
				alert('登陆失败,请稍后重试!');
				var a=$.getRandom(1000);
				$("#codeImge").get(0).src='code.do?rd='+a+'&captchaKey=${CAPTCHAKEY}';
				enabledButton();
			}
		});
	});
});
</SCRIPT>
</HTML>