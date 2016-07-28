<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx}/UI/css/sq-ui-1.0.0.css" />
<style>
	 a{text-decoration:none;}
	 a:hover{font-weight:bold;color:red;}
</style>
<title>用户信息</title>
</head>
<body>
<input type="text" id="t1"></input>
<button id="b1">按钮</button><br />
		<div id="draggable3"></div>
		<div id="draggable4"></div>
		<div id="draggable5"></div>
		<div id="draggable6"></div>
		<div id="draggable7"></div>
</body>
	<script type="text/javascript">
        var cp="${ctx}"; 
    </script>
	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/ui_src/coreFn.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/ui_src/jquery.window.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#draggable3").window({
			title:'信息3',
			width:640,
			height:450,
			opt:"mainGrid",
			content:[{
				type:"form",
				rowNum:2,
				items:[
				       {field:"id",name:"id",type:"hidden"},
				       {field:"登陆账号",id:"_loginName",name:"loginName",type:"text",allowBlank:false},
		               {field:"用户姓名",id:"_userName",name:"userName",type:"text",allowBlank:false}
				]
			},
			{type:"pannel",id:"p1",field:"角色1",width:280,height:318},
			{type:"pannel",id:"p",width:60,height:318},
			{type:"pannel",id:"p2",field:"角色2",width:284,height:318}
			],
			button:[
			       {name:"分配角色",on:function(){}},
			       {name:"取消",type:'close'}
			]});

		$("#draggable3").window("open",{btns:[0,0]});
		$("#draggable4").window({
			title:'信息4',
			width:640,
			height:450,
			opt:"mainGrid",
			content:[{
				type:"form",
				rowNum:2,
				items:[
				       {field:"id",name:"id",type:"hidden"},
				       {field:"登陆账号",id:"_loginName",name:"loginName",type:"text",allowBlank:false},
		               {field:"用户姓名",id:"_userName",name:"userName",type:"text",allowBlank:false}
				]
			},
			{type:"pannel",id:"p1",field:"角色1",width:280,height:318},
			{type:"pannel",id:"p",width:60,height:318},
			{type:"pannel",id:"p2",field:"角色2",width:284,height:318}
			],
			button:[
			       {name:"分配角色",on:function(){}},
			       {name:"取消",type:'close'}
			]});

		$("#draggable4").window("open",{btns:[0,0]});
		$("#draggable5").window({
			title:'信息5',
			width:640,
			height:450,
			opt:"mainGrid",
			content:[{
				type:"form",
				rowNum:2,
				items:[
				       {field:"id",name:"id",type:"hidden"},
				       {field:"登陆账号",id:"_loginName",name:"loginName",type:"text",allowBlank:false},
		               {field:"用户姓名",id:"_userName",name:"userName",type:"text",allowBlank:false}
				]
			},
			{type:"pannel",id:"p1",field:"角色1",width:280,height:318},
			{type:"pannel",id:"p",width:60,height:318},
			{type:"pannel",id:"p2",field:"角色2",width:284,height:318}
			],
			button:[
			       {name:"分配角色",on:function(){}},
			       {name:"取消",type:'close'}
			]});

		$("#draggable5").window("open",{btns:[0,0]});
		$("#draggable6").window({
			title:'信息6',
			width:640,
			height:450,
			opt:"mainGrid",
			content:[{
				type:"form",
				rowNum:2,
				items:[
				       {field:"id",name:"id",type:"hidden"},
				       {field:"登陆账号",id:"_loginName",name:"loginName",type:"text",allowBlank:false},
		               {field:"用户姓名",id:"_userName",name:"userName",type:"text",allowBlank:false}
				]
			},
			{type:"pannel",id:"p1",field:"角色1",width:280,height:318},
			{type:"pannel",id:"p",width:60,height:318},
			{type:"pannel",id:"p2",field:"角色2",width:284,height:318}
			],
			button:[
			       {name:"分配角色",on:function(){}},
			       {name:"取消",type:'close'}
			]});

		$("#draggable6").window("open",{btns:[0,0]});
		$("#draggable7").window({
			title:'信息7',
			width:640,
			height:450,
			opt:"mainGrid",
			content:[{
				type:"form",
				rowNum:2,
				items:[
				       {field:"id",name:"id",type:"hidden"},
				       {field:"登陆账号",id:"_loginName",name:"loginName",type:"text",allowBlank:false},
		               {field:"用户姓名",id:"_userName",name:"userName",type:"text",allowBlank:false}
				]
			},
			{type:"pannel",id:"p1",field:"角色1",width:280,height:318},
			{type:"pannel",id:"p",width:60,height:318},
			{type:"pannel",id:"p2",field:"角色2",width:284,height:318}
			],
			button:[
			       {name:"分配角色",on:function(){}},
			       {name:"取消",type:'close'}
			]});

		$("#draggable7").window("open",{btns:[0,0]});
		
		$("#b1").click(function(){
			if($("#t1").val()){
				$("#draggable"+$("#t1").val()).window("open",{btns:[0,0]});
			}
		});
		
		
		
	});
	</script>
</html>