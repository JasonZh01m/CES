<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx}/UI/css/sq-ui-1.0.0.css" />
<link rel="stylesheet" href="${ctx}/UI/css/zTree/zTreeStyle.css" />
<style>
a {
	text-decoration: none;
}

a:hover {
	font-weight: bold;
	color: red;
}
</style>
<title>权限管理</title>
</head>
<body>
	<div id="p1"></div>
	<div id="p2"></div>
	<div id="draggable"></div>
	<div id="fileUpload" style="display: none"></div>
	<input type="hidden" id="hiddenSelectPermissionId" />
	<iframe name="fileExport" style="display: none;"></iframe>
	<script type="text/javascript">
        var cp="${ctx}"; 
    </script>
	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.exedit-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/permission/permission.js"></script>
</body>


</html>