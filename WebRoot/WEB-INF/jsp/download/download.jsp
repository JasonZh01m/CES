<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx}/UI/css/jqGrid-all.css" />
<link rel="stylesheet" href="${ctx}/UI/css/sq-ui-1.0.0.css" />
<link rel="stylesheet" href="${ctx}/UI/css/zTree/zTreeStyle.css" />
<title>软件下载页面</title>
<style>
	 a{text-decoration:none;}
	 a:hover{font-weight:bold;color:red;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
</head>
<script type="text/javascript">
var cp="${ctx}";
var parentid = null;
var s_ids = [];
$(function(){

	var w=$(document.body).width();
	var h=$(window).height();
	
// 	var sbh = $("#sb_q").height();
	
	$("#mainGrid").jqGrid({
		width:w-2,
		height:h-83,
		mtype: 'POST',
		url:cp+'/updateFile/updatePageJson.do', 
		datatype: "json", 
		altclass:'odd',
		multiselect:false,
		jsonReader:{
		    root:'rows',
		    page: "page",
		    total: "total",
		    records: "records",
		    repeatitems : false
		},
		colNames:['文件名称','文件版本','文件类型','MD5码','下载链接','保存路径',"下载后动作","文件说明"], 
		colModel:[
		{name:'fileName',index:'fileName', width:120,hidden:false,sortable:false}, 
		{name:'fileVersion',index:'fileVersion', width:60,hidden:false,sortable:false}, 
		{name:'fileType',index:'fileType',align:'center',width:80,hidden:false,sortable:false}, 
		{name:'fileMd5',index:'fileMd5', width:180,hidden:false,sortable:false},
		{name:'filePath',index:'filePath', width:250,hidden:false,sortable:false},
		{name:'downloadPath',index:'downloadPath', width:260,hidden:false,sortable:false},
		{name:'action',index:'action',align:'center',width:80,hidden:false,sortable:false,formatter:function(cv, options, rowObject){
			if(cv=='copy'){
				return '复制';
			}else{
				return '运行';
			}
		}},
		{name:'fileNote',index:'fileNote', width:120,hidden:false,sortable:false}
		],
		multiselect:true,
		multiboxonly:true,
		rowNum:15, 
		rowList:[15,25,35], 
		pager: '#pager2', 
		sortname: 'id', 
		viewrecords: true, 
		sortorder: "asc"
	}); 
	
	$("#t_mainGrid").toolbar({
		items:[
		       {name:"下载文件",type:"button",icon:cp+"/UI/css/icon/folder_dow.png",on:function(){
					if(getSelectNum()==0){
						alert("请至少选取一行进行下载!");
					}else if(getSelectNum()>0){
						var id=$("#mainGrid").jqGrid("getGridParam","selrow");
						var roleData=$("#mainGrid").jqGrid('getRowData',id);
						var url = cp + roleData.filePath;
				    	window.open(url, "文件下载");
					}else{
						alert("请选取一行进行下载!");
					}
					
		    }}]
	});

});

function getSelectNum() {
	return $("#mainGrid").jqGrid("getGridParam", "selarrrow").length;
}
</script>
<body>
	<div id="t_mainGrid">
	</div>
	<table id="mainGrid">
	</table>
	<div id="pager2"></div>
</body>

</html>