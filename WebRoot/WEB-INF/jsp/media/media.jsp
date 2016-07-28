<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx}/UI/css/jqGrid-all.css" />
<link rel="stylesheet" href="${ctx}/UI/css/sq-ui-1.0.0.css" />
<link href="${ctx}/UI/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<style>
	 a{text-decoration:none;}
	 a:hover{font-weight:bold;color:red;}
	 .upload_btn{
	cursor:pointer;line-height:16px;float:left;text-decoration:none;border-radius:3px;font-size:13px;font-weight:bold;margin:2px;color:#0E2D5F;border:1px solid #95B8E7;background:#F9F9F9;padding:2px 4px;text-shadow:none;}
	 .upload_btn span{display:block;float:left;margin:2px 2px 2px 1px;}
	 .upload_btn:hover{border:1px solid #95B8E7;background:#F9F9F9;color:#020004;}
</style>
<title>媒体文件管理</title>
</head>
<body>
<div id="sb_q" class="searchBar">
    </div>
    <div id="t_mainGrid">
	</div>
	<table id="mainGrid">
	</table>
	<div id="pager2"></div>
	<div id="draggable"></div>
	<div id="mfileWin"></div>
	<div id="previewWin"></div>
	<script type="text/javascript">
        var cp="${ctx}"; 
    </script>
	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/ajaxfileupload.js" ></script>
  <script type="text/javascript" src="${ctx}/UI/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/media/media.js"></script>
	
</body>
	
</html>