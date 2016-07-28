<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
<head>
<link rel="icon" href="${ctx}/js.ico" type="image/x-icon" />
<link rel="stylesheet" href="${ctx}/UI/css/sq-ui-1.0.0.css" />
<link rel="stylesheet" href="${ctx}/UI/css/zTree/zTreeStyle.css" />
<style>
.cr{float: right;display: block;color: #5372AD;font-size: 11px;margin: 3px;line-height: 12px;}
.left_logo{float: left;margin: 3px 6px;width: 260px;height: 47px;background: url(${ctx}/UI/css/images/logo.jpg);background-repeat: no-repeat;}
.left_bg{background-image: url(${ctx}/UI/css/images/bg-login.png);}
.userMenu{width:360px;height:33px;float:right;}
.userMenu a{display:block;float:right;background: #F9F9F9;border:1px solid #F9F9F9;width:auto;}
.userMenu span{display:block;float:left;}
.navigationMenu{height:30px;background: #DCEBFD;border-top: 1px solid #95B8E7;}
.navigationMenu .nagbtncls{margin: 2px 2px;height: 20px;border: 1px solid #DCEBFD;background: none;font-size: 14px;line-height: 14px;}
.navigationMenu .nagbtncls:hover{border: 1px solid #95B8E7;background: #F9F9F9;color: #020004;}
.navigationMenu a span{margin: 2px 6px;}
.navigationMenu .navBtnfocus{border: 1px solid #95B8E7;background: #F9F9F9;}
.navigationMenu .u-separator{border-right: 1px solid #95B8E7;margin: 4px 3px;height: 22px;}
#p_top{background-color:white;}
#p_bottom{background: #E5ECFC;}
</style>
<title>信息发布系统</title>
<script type="text/javascript">
	var cp = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/ui_src/coreFn.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/ui_src/jquery.searchBar.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/ui_src/jquery.toolbar.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/ui_src/jquery.window.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/ui_src/jquery.pannel.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/ui_src/jquery.tab.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/main/main.js"></script>
</head>
<body>
<div id="pwdWin"></div>
</body>
</html>