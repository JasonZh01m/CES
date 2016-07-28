<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx}/UI/css/jqGrid-all.css" />
<link rel="stylesheet" href="${ctx}/UI/css/sq-ui-1.0.0.css" />
<link rel="stylesheet" href="${ctx}/UI/css/zTree/zTreeStyle.css" />
<style>
	 a{text-decoration:none;}
	 a:hover{font-weight:bold;color:red;}
</style>
<title>用户信息</title>
</head>
<body>
<div id="sb_q" class="searchBar">
    </div>
    
    <div id="t_mainGrid"></div>
	<table id="mainGrid">
	</table>
	<div id="pager2"></div>
	
    <div id="draggable">
	</div>
	<div id="draggable2">
	</div>
	<div id="draggable3"></div>

	<input type="hidden" id="roleIds" value="" ></input>
	<script type="text/javascript">
        var cp="${ctx}"; 
    </script>
	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.exedit-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/popedom/userInfo.js"></script>
</body>
	
</html>