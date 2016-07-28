<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/taglibs.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>密码修改</title>
<style type="text/css">
.control-group INPUT{
		BORDER-BOTTOM: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; PADDING-BOTTOM: 4px; LINE-HEIGHT: 20px; 
		BACKGROUND-COLOR: #ffffff; PADDING-LEFT: 6px; WIDTH: 206px; PADDING-RIGHT: 6px; DISPLAY: inline-block; 
		FONT-FAMILY: "Helvetica Neue", Helvetica, Arial, sans-serif; MARGIN-BOTTOM: 10px; HEIGHT: 18px; COLOR: #555555; 
		MARGIN-LEFT: 0px; FONT-SIZE: 14px; VERTICAL-ALIGN: middle; BORDER-TOP: #cccccc 1px solid; BORDER-RIGHT: #cccccc 1px solid; 
		PADDING-TOP: 4px; border-radius: 0 4px 4px 0; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset; 
		transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s
	}
.control-group INPUT:focus{
		Z-INDEX: 2; OUTLINE-STYLE: dotted; OUTLINE-COLOR: invert; OUTLINE-WIDTH: thin; box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6); 
		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6); 
		-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6)
	}
.login-btn INPUT{BORDER-BOTTOM: 1px solid; BORDER-LEFT: 1px solid; PADDING-BOTTOM: 4px; LINE-HEIGHT: 20px;  
		MARGIN: 0px auto; PADDING-LEFT: 12px; WIDTH: 80px; PADDING-RIGHT: 12px; BACKGROUND-REPEAT: repeat-x; 
		FONT-SIZE: 13px; BORDER-TOP: 1px solid; CURSOR: pointer; BORDER-RIGHT: 1px solid; PADDING-TOP: 4px; border-radius: 4px 4px 4px 4px; 
		box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px rgba(0, 0, 0, 0.05); text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25)
	}
.login-btn INPUT:hover{BACKGROUND-COLOR: #D9EEFF}

</style>

</head>
<body>
	<form method="post">
		<div style="background-color: #819FF7;width: 400px;">
			<div align="center">密码修改</div></br>
			<div class="control-group">当前密码:&nbsp; &nbsp;<input type="password" id="oldPwd" name="oldPwd" /></div>
			<div class="control-group">新密码: &nbsp; &nbsp; <input type="password" id="newPwd" name="newPwd" /></div>
			<div class="control-group">确认新密码: <input type="password" id="newPwd1" name="newPwd1" /></div>
			<div class="login-btn" align="center"><input type="button" value="修改" id="modify" /> &nbsp; 
			<input type="reset" id="reset" /></div>
		</div>
	</form>
<input type="hidden" value="${userId}" id="userId" />


<SCRIPT type="text/javascript" src="${ctx}/UI/js/jquery-1.9.1.js"></SCRIPT>
<script type="text/javascript" src="${ctx}/UI/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/sFn.js"></script>

<script>

$(function(){
	
	$("#modify").click(function(){
		
		var oldPwd = $("#oldPwd").val();
		var newPwd = $("#newPwd").val();
		var newPwd1 = $("#newPwd1").val();
		
		if(oldPwd == ""){
			alert('请输入当前密码!');
			$("#oldPwd").focus();
			return;
		}
		if(newPwd == ""){
			alert('请输入新密码!');
			$("#newPwd").focus();
			return;
		}

		if(newPwd1 == ""){
			alert('请输入确认新密码!');
			$("#newPwd1").focus();
			return;
		}
		
		if(newPwd != newPwd1){
			alert('新密码和确认新密码不同!');
			$("#newPwd1").focus();
			return ;
		}
		if(oldPwd == newPwd){
			alert('当前密码与新密码相同!');
			$("#newPwd").focus();
			return ;
		}
		
		var patrn=/^[0-9 | A-Z | a-z]{6,12}$/;
		if (!patrn.test(newPwd)){
			alert('请正确输入6-18位数字或字母的字符!');
			$("#newPwd").focus();
			return ;
		}
		
		if (!patrn.test(newPwd1)){
			alert('请正确输入6-18位数字或字母的字符!');
			$("#newPwd1").focus();
			return ;
		}
		
		var pwd = oldPwd +","+newPwd;
		var userId = $("#userId").val(); 
		var post_data = {"passWord":pwd,"id":userId};
		$.ajax({
			type:"post",
			url:"updatePwd.do?",
			dataType:"json",
			data:post_data,
		    async:false,
			success:function(data){
				if(data.msg){
					alert(data.msg);
					return;
				}else{
					if(data.success == 0){
						alert('修改密码成功!');
					}else{
						alert('修改密码失败!');
					}
				}
			},
			error:function(){
				alert('error');
			}
		});
		
	});
	
});


</script>

</body>
</html>