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
	 .comboBox{
	   padding:0px;
	   margin:3px 0px;
	   width:138px;
	   height:21px;
	   float:right;
	 }
	 .comboBox .text{
	   padding:0px;
	   margin:0px;
	   width:116px;
	   float:left;
	   height:20px;
	 }
	 .comboBox .trigger{
	   width:17px;
	   height:21px;
	   float:left;
	   border-bottom:1px solid #95B8E7;
	   background-image:url('${ctx}/UI/css/images/trigger.gif');
	   background-repeat:no-repeat;
       background-position:-68px 0px;
	 }
	 .comboBox .expand{
	      background-position:-51px 0px;
	 }
	 .cb_option{
	    width:133px;
	    border:1px solid #95B8E7;
	    height:248px;
	    padding:0;
	    position:absolute;
	    background:#FFFFFF;
	    border:1px solid #95B8E7;
	    overflow:auto;
	 }
</style>
<title>评价明细</title>
</head>
<body>
<div id="sb_q" class="searchBar">
    </div>
	<table id="mainGrid">
	</table>
	<div id="pager2"></div>
	<script type="text/javascript">
        var cp="${ctx}"; 
    </script>
	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
    <script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/report/evaluateDetail.js"></script>
</body>
	
</html>