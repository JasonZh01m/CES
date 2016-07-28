<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	   width:158px;
	   height:21px;
	   float:left;
	 }
	 .comboBox .text{
	   padding:0px;
	   margin:0px;
	   width:136px;
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
	    width:153px;
	    border:1px solid #95B8E7;
	    height:248px;
	    padding:0;
	    position:absolute;
/* 	    left:100px; */
/* 	    top:150px; */
	    background:#FFFFFF;
	    border:1px solid #95B8E7;
	    overflow:auto;
	    z-index:999999;
	 }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div id="devInfo_win"></div>
<div id="devInfo_audit_win"></div>
</body>


<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript">
var cp="${ctx}";
var parentid = null;
var s_ids = [];

// function filter(treeId, parentNode, childNodes) {
// 	if (!childNodes) return null;
// 	for (var i=0, l=childNodes.length; i<l; i++) {
// 		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
// 	}
// 	return childNodes;
// }

function del(flag,id){

	if(flag == 1){
		id=id.join(",");
	}
// 	console.log("id--->  "+id)
	if(confirm("确定要删除吗?")){
		$.ajax({type:"post",url:"deleDevByIdJson.do",data:{"ids":id}, async:true,dataType:"json",
			success: function(date){
				if(date.msg!='' && date.msg!=null){
					alert(date.msg);
				}else{
					if(date.success==0){
						alert("删除成功!");
						refushGird();
					}else{
						alert("删除失败!");
					}
				}
		}});
	}
}

function refushGird(){
	$("#devGrid").jqGrid('setGridParam',{  
        datatype:'json',  
        postData:{},
        page:1
    }).trigger("reloadGrid");
}
$(function(){
	
	$(document.body).layout({
		items:[{
			id: "p_devContext",
			region:"center",
			content: {
				id: "p_content_1"
			}
		}]
	});
	
	$("#p_content_1").html("<div id=\"sb_q\" class=\"searchBar\"><\/div> " + 
			"<div id=\"t_devGrid\"><\/div> " + 
			"<table id=\"devGrid\"><\/table>" +
			"<div id=\"pager2\"><\/div>");
	
	$("#sb_q").searchBar({
		items:[
	   	   {field:"设备编码",name:"code",type:"text"},
	   	   {field:"审核状态",name:"auditStatus", type:"select", 
	   		   option: [{text: "未审核", value: "1"},
	   	                {text: "审核通过", value: "0"},
	   	                {text: "禁用", value: "2"}
	   	                ], blankText:"请选择"}
		],
		button:[
			    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
			    	var param = null;
			       param=$.getFormDate('sb_q');
			       $.clearGridPostParam("devGrid");
			       param["userId"] = getCookie("userId");
			       param["rids"] = getCookie("rids");
			       $("#devGrid").setGridParam({  
			  	        datatype:'json',  
			  	        postData:param,
			  	        page:1,
			  	    }).trigger("reloadGrid");
			    }},
			    {name:"重置",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
			    	$("#sb_q").searchBar("clearForm");
			    }}
			]
	});
	
// 	var top=$("#orgCode_div").offset().top;
// 	var left=$("#orgCode_div").offset().left;
// 	$("#sbf_sb_q #orgCode_tri_opt").css({"top":top+"px","left":left+"px"});
	
	$("#t_devGrid").toolbar({
		items:[{name:"添加",type:"button",icon:cp+"/UI/css/icon/add.png",on:function(){
				$("#devInfo_win").window("open",{btns:[0,1,0]});
				var top=$("#orgCode_div").offset().top;
				var left=$("#orgCode_div").offset().left;
				$("#orgCode_tri_opt").css({"top":top+"px","left":left+"px"});
// 				$("#orgId").val("");
// 				$("#orgCode").val("");
// 				loadParentOrganizationTree();
			}
		}, '-', {
			name: "修改", type:"button", icon:cp+"/UI/css/icon/application_form_edit.png", on:function(){
			 	var id=$("#devGrid").jqGrid("getGridParam","selrow");
			 	var roleData=$("#devGrid").jqGrid('getRowData',id);
			 	if(getSelectNumDev()>1){
					alert("只能选一行进行修改!");
					$("#devGrid").resetSelection();
				} else if(getSelectNumDev()==0){
					alert("请选取要修改的一行进行修改!");
				} else {
					if(roleData.auditStatus==="0") {
						alert("已审核的终端不能进行修改！");
					} else {
// 						loadParentOrganizationTree(roleData.orgId);
						$("#btn_devInfo_edit").prop("disabled", false);
					 	$("#devInfo_win").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id":id}});
						var pid_t = $("#devInfo_win input[name=parentId]");
						pid_t.removeAttr("readonly");
						$("#orgCode_txt").val(roleData.orgName);
						var top=$("#orgCode_div").offset().top;
						var left=$("#orgCode_div").offset().left;
						$("#orgCode_tri_opt").css({"top":top+"px","left":left+"px"});
					}
				}
			}
		}, '-', {
			name: "删除", type: "button", icon:cp+"/UI/css/icon/delete.png", on:function() {
				if(getSelectNumDev()==0){
					alert("请至少选取一行进行删除!");
				}else if(getSelectNumDev()>0){
					var id = $("#devGrid").jqGrid("getGridParam", "selarrrow");
					del(1,id);
				} else {
					alert("请选取一行进行删除！");
				}
			}
		}, '-', {
			name: "审核", type: "button", icon:cp+"/UI/css/icon/application_form_edit.png", on:function() {
				if(getSelectNumDev()==0){
					alert("请至少选取一行进行删除!");
				}else if(getSelectNumDev()>0){
					var id=$("#devGrid").jqGrid("getGridParam","selrow");
					var roleData=$("#devGrid").jqGrid('getRowData',id);
					if(roleData.auditStatus==="0") {
						alert("已审核的终端不能进行再次审核！");
					} else {
						$("#_login_name").attr("disabled","disabled");
			 			$("#devInfo_audit_win").window("open",{mode:"readOnly",btns:[0,1,0,0],loadData:"getMoJson.do",param:{"id":id}});
			 			var bt_is = $("#btn_devInfo_audit_is");
			 			bt_is.html("<span class=\"ui-icon auditIco\"></span><span>同意</span>");
			 			bt_is.prop("disabled", false);
// 						auditDevfun(0,id);
					}
					
				} else {
					alert("请选取一行进行删除！");
				}
			}
		}, '-', {
			name: "禁用", type: "button", icon:cp+"/UI/css/icon/delete.png", on:function() {
				if(getSelectNumDev()==0){
					alert("请至少选取一行进行删除!");
				}else if(getSelectNumDev()>0){
					var id=$("#devGrid").jqGrid("getGridParam","selrow");
					auditDevfun(2,id);
				} else {
					alert("请选取一行进行删除！");
				}
			}
		}]
	});
	
	$("#devInfo_win").window({
		title: "设备基础信息参数",
		width: 650,
		height: 230,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"ID",name:"id",type:"hidden"},
// 			        {field:"终端编号",name:"code",type:"text", allowBlank:false,
// 						validFn:function(name,value){
// 			         		   var f=false;
// 			         		   var param = {"code":value};
// 			         		   $.ajax({type:"post",url:"validatorDevCodeJson.do",async:false,data:param,dataType:"json",
// 			         				success:function(d){
// 			         					console.info(d);
// 			         		            if(d){
// 			         		            	if(d.success===0){
// 			         		            	    f=true;
// 			         		            	   $("#btn_devInfo_add").prop("disabled", false);
// 			         		            	}else{
// 			         		            		$("#btn_devInfo_add").prop("disabled", true);
// 			         		            		f=false;
// 			         		            	}
// 			         		            }
// 			         				}
// 			         		   });
// 			         		   return f;
// 			         	   },
// 			         	   validFnText:"该终端已存在!"
// 			        },
					{field:"终端序号",name:"serialNo",type:"text", allowBlank:false},
					{field:"ip",name:"ip",type:"text", allowBlank:false},
					{field:"终端网卡",name:"mac",type:"text"},
					{field:"窗口号",name:"windowName",type:"text"},
					{field:"审核状态", name: "auditStatus", type:"select", 
						option: [
						         {text: "未审核", value: "1"},
						         {text: "审核通过", value: "0"},
						         {text: "禁用", value: "2"}
						]},
// 					{field:"终端所属机构", id: "orgCode", name: "orgCode", type:"hidden"}
					{field:"终端所属机构", name: "orgCode", id:"orgCode", type: "select", url: cp + "/organization/optionOrgListJson.do", blankText:"请选择"}
					]
		}],
		button : [
		          {name:"添加",id:"btn_devInfo_add", type: "button",
		        	  on:function(){
		        		  var bt = $("#btn_devInfo_add");
						  var param=$.getFormDate("devInfo_win");
						  if(!validator()){
							  return;
						  }
						  param["code"] = "";
						  bt.html("<span class=\"u-icon submitIcon\"></span><span>正在添加...</span>");
						  bt.prop("disabled", true);
						  $.ajax({type:"post",url:"addDevInfoJson.do",data:param,async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("操作成功!");
				    					$.closeWinAndFlush("devInfo_win","devGrid");
				    					bt.html("<span class=\"u-icon submitIcon\"></span><span>添加</span>");
										bt.prop("disabled", false);
				    				}else{
				    					alert("操作失败!");
				    					bt.html("<span class=\"u-icon submitIcon\"></span><span>请重试</span>");
				    					bt.prop("disabled",false);
				    				}
				    			},
				    			error:function(XMLHttpRequest, textStatus, errorThrown){
				    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				    			}	
				    		});
		        	  }},
			      {name:"更新", id: "btn_devInfo_edit", on:function(){
			    	  var bt = $("#btn_devInfo_edit");
			    	  var param=$.getFormDate("devInfo_win");
			    	  param["auditUserId"] = getCookie("userId");
			    	  param["auditUserName"] = getCookie("userName");
			    	  param["orgName"] = $("#orgCode_txt").val();
			    	  if(!validator()){
						  return;
					  }
			    	  bt.html("<span class=\"u-icon submitIcon\"></span><span>正在更新...</span>");
	        		  bt.prop("disabled", true);
	        		  $.ajax({type:"post",url: "auditDevJson.do", data:param, async:false, dataType:"json",
			    			success:function(d){
			    				if(d.success=="0"){
			    					alert("操作成功!");
			    					$.closeWinAndFlush("devInfo_win","devGrid");
			    					bt.html("<span class=\"u-icon submitIcon\"></span><span>更新</span>");
			    				}else{
			    					alert("操作失败!");
			    					bt.html("<span class=\"u-icon submitIcon\"></span><span>请重试</span>");
			    					bt.prop("disabled",false);
			    				}
			    			},
			    			error:function(XMLHttpRequest, textStatus, errorThrown){
			    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
			    			}	
			    		});
			      }},
			      {name:"取消",type:'close'}]
	})
	$("#orgCode2").replaceWith("<div id='orgCode_div' class='comboBox'></div>");
	
	$("#orgCode").replaceWith("<div id='orgCode_div' class='comboBox'></div>");

	$("#orgCode_div").append("<input id='orgCode_txt' type='text' readOnly=true class='text'></input><input id='orgCode' name='orgCode' type='hidden'></input>");
	$("#orgCode_div").append("<img id='orgCode_trigger' class='trigger' src="+cp+"/UI/css/images/s.gif></img>");
	$(document.body).append("<div id='orgCode_tri_opt' class='cb_option'></div>");
	$("#orgCode_tri_opt").append("<ul id='organTree' class='ztree'></ul>");
	$("#orgCode_tri_opt").hide();

	$("#orgCode_div").click(function(event){
		if($("#orgCode_trigger").hasClass("expand")){
			$("#orgCode_trigger").removeClass("expand");
			$("#orgCode_tri_opt").hide();
		}else{
			$("#orgCode_trigger").addClass("expand");
			$("#orgCode_tri_opt").show();
			loadOrganizationTree();
			$("#orgCode_tri_opt").focus();
		}
		 event.stopPropagation();  
	}); 

	$("#orgCode_tri_opt").click(function(event){
	    event.stopPropagation();  
	});

	$(document).click(function(event){
		if($("#orgCode_tri_opt").is(":hidden")){
			return;
		}
		$("#orgCode_trigger").removeClass("expand");
		$("#orgCode_tri_opt").hide();
	});
	$("#orgCode_div").parent().css("width","238px");

	function loadOrganizationTree(){
		var url = "/CES/organization/getOrganizationTreeJson.do";
		var setting = {
			view : {
				showLine : true,
				selectedMulti : false,
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			async : {
				enable : true,
				url : url,
				autoParam : [ "id=pId" ]/*,
				dataFilter : filter*/
			},
			callback:{
				onClick: onClick
			
			}
		};					
		$.fn.zTree.init($("#organTree"), setting);
	}

	function onClick(event, treeId, treeNode, clickFlag){
		$("#orgCode_txt").val(treeNode.name);
		$("#orgCode").val(treeNode.code);
		$("#orgCode_trigger").removeClass("expand");
		$("#orgCode_tri_opt").hide();
	}
	
	var ppw=$("#pp").width();
	var pph=$("#pp").height();
	
	
	$("#pp").append("<div style='width:"+ppw+"px;height:"+pph+"px;overflow:auto;'><ul id='parentOrganizationTree' class='ztree'></ul></div>");
	
	
	var w = $("#p_content_1").width();
	var h = $("#p_content_1").height();
	var sbh = $("#sb_q").height();
	$("#devGrid").setGridParam({  
	        datatype:'json',  
	        postData:{"rids" : getCookie("rids")},
	        page:1,
	    })
	$("#devGrid").jqGrid({
		width: w-2,
		height: h-83-sbh,
		mtype: "POST",
		datatype: "json",
		altclass:'odd',
		url:"PageJson.do",
		postData: {    
			userId: getCookie("userId"),
			rids: getCookie("rids")
		}, 
		colNames:['终端编码','终端序列号','终端IP','终端网卡MAC', '终端所属机构', '终端所属机构ID', '窗口名称','审核状态','审核人ID','审核人','创建时间','审核完成时间','id号'],
		colModel:[
				{name:'code',index:'code', width:80,align:"right",hidden:false,sortable:false},
				{name:'serialNo',index:'serialno', width:80,align:"right",hidden:false,sortable:false},
				{name:'ip',index:'ip', width:80,align:"right",hidden:false,sortable:false},
				{name:'mac',index:'mac', width:80,align:"right",hidden:false,sortable:false},
				{name:'orgName',index:'orgName', width:80,align:"right",hidden:false,sortable:false},
				{name:'orgCode',index:'orgCode', width:80,align:"right",hidden:true,sortable:false},
				{name:'windowName',index:'windowName', width:80,align:"right",hidden:false,sortable:false},
				{name:'auditStatus',index:'auditStatus', width:80,align:"right",hidden:false,sortable:false,
					formatter: function (cellvalue, options, rowObject){
						if(cellvalue===1) {
							return "未审核";
						} else if(cellvalue===0) {
							return "已审核";
						} else if(cellvalue===2) {
							return "禁用";
						}
					}, 
					unformat: function(cellvalue) {
						if(cellvalue == "未审核") {
							return "1";
						} else if(cellvalue == "已审核") {
							return "0";
						} else {
							return "2";
						}
					} 
				},
				{name:'auditUserId',index:'auditUserId', width:80,align:"right",hidden:false,sortable:false},
				{name:'auditUserName',index:'auditUserName', width:80,align:"right",hidden:false,sortable:false},
				{name:'createTime',index:'createTime', width:80,align:"right",hidden:false,sortable:false},
				{name:'installTime',index:'installTime', width:80,search: false,sortable:false,sortable:false,hidden:false},
				{name:'id',index:'id', width:130,search: false,sortable:false, hidden:true/* ,formatter:function(cv, options, rowObject){
					if(rowObject["auditStatus"] === 0) {
						return "<div id=\"audit_"+cv+"\"><a href='#' id=''>已审核</a>&nbsp;&nbsp;<a href='#' id='"+"d_"+cv+"'>禁用</a>&nbsp;&nbsp;</div>";
					} else if(rowObject["auditStatus"] === 2){
						return "<div id=\"audit_"+cv+"\"><a href='#' id=''>审核</a>&nbsp;&nbsp;<a href='#' id='"+"s_"+cv+"'>启用</a>&nbsp;&nbsp;</div>";
					} else {
						return "<div id=\"audit_"+cv+"\"><a href='#' id='"+"q_"+cv+"'>审核</a>&nbsp;&nbsp;<a href='#' id='"+"d_"+cv+"'>禁用</a>&nbsp;&nbsp;</div>";
					}
					
				} */}
				],
		multiselect: true,
		viewrecords: true,
		rowNum:10, 
		rowList:[10,20,30], 
		pager: '#pager2'
	});
// 	gc();
	
	$("#devInfo_audit_win").window({
		title: "设备基础信息审核",
		width: 620,
		height: 200,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"ID",name:"id",type:"hidden"},
			        {field:"终端编号",name:"code",type:"text", allowBlank:false},
					{field:"终端序号",name:"serialNo",type:"text", allowBlank:false},
					{field:"终端网卡",name:"mac",type:"text"},
					{field:"ip",name:"ip",type:"text", allowBlank:false},
					{field:"窗口号",name:"windowName",type:"text"},
					{field:"终端所属机构", name: "orgName", type:"text"}
					]
		}],
		button : [
		          {name:"同意",id:"btn_devInfo_audit_is", type: "button",
		        	  on:function(){
		        		  var bt = $("#btn_devInfo_audit_is");
						  bt.html("<span class=\"ui-icon auditIco\"></span><span>正在执行...</span>");
						  bt.prop("disabled", true);
						  var param=$.getFormDate("devInfo_audit_win");
						  $.ajax({
							  type:"post",
							  url:"auditDevJson.do",
							  data:{
								  "auditUserId": getCookie("userId"), 
								  "auditUserName": getCookie("userName"),
								  "auditStatus" : 0,
								  "id": param.id
								  },
							  async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("审核成功!");
				    					$.closeWinAndFlush("devInfo_audit_win","devGrid");
				    					bt.html("<span class=\"ui-icon auditIco\"></span><span>同意</span>");
										bt.prop("disabled", false);
				    				}else{
				    					alert("审核失败!");
				    					bt.html("<span class=\"u-icon auditIco\"></span><span>请重试</span>");
				    					bt.prop("disabled",false);
				    				}
				    			},
				    			error:function(XMLHttpRequest, textStatus, errorThrown){
				    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				    			}	
				    		});
		        	  }
		          }, 
		          {name:"启用",id:"btn_devInfo_audit_st", type: "button",
		        	  on:function(){
		        		  var bt = $("#btn_devInfo_audit_st");
						  bt.html("<span class=\"ui-icon auditIco\"></span><span>正在执行...</span>");
						  bt.prop("disabled", true);
						  var param=$.getFormDate("devInfo_audit_win");
						  $.ajax({
							  type:"post",
							  url:"auditDevJson.do",
							  data:{
// 								  "auditUserId": getCookie("userId"), 
// 								  "auditUserName": getCookie("userName"),
								  "auditStatus" : 1,
								  "id": param.id
								  },
							  async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("启用成功!");
				    					$.closeWinAndFlush("devInfo_audit_win","devGrid");
				    					bt.html("<span class=\"ui-icon auditIco\"></span><span>启用</span>");
										bt.prop("disabled", false);
				    				}else{
				    					alert("启用失败!");
				    					bt.html("<span class=\"u-icon auditIco\"></span><span>请重试</span>");
				    					bt.prop("disabled",false);
				    				}
				    			},
				    			error:function(XMLHttpRequest, textStatus, errorThrown){
				    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				    			}	
				    		});
		        	  }
		          },
		          {name:"驳回", id: "btn_devInfo_audit_no", on:function(){
			    	  var bt = $("#btn_devInfo_audit_no");
			    	  bt.html("<span class=\"ui-icon auditDelIco\"></span><span>正在执行...</span>");
	        		  bt.prop("disabled", true);
	        		  var param=$.getFormDate("devInfo_audit_win");
	        		  $.ajax({
	        			  type:"post",
	        			  url: "auditDevJson.do",
	        			  data:{
// 							  "auditUserId": getCookie("userId"), 
// 							  "auditUserName": getCookie("userName"),
							  "auditStatus" : 1,
							  "id": param.id
							  },
	        			  async:false,
	        			  dataType:"json",
			    		  success:function(d){
			    				if(d.success=="0"){
			    					alert("驳回成功!");
			    					$.closeWinAndFlush("devInfo_audit_win","devGrid");
			    					bt.html("<span class=\"ui-icon auditDelIco\"></span><span>驳回</span>");
			    				}else{
			    					alert("驳回失败!");
			    					bt.html("<span class=\"u-icon auditDelIco\"></span><span>请重试</span>");
			    					bt.prop("disabled",false);
			    				}
			    			},
			    			error:function(XMLHttpRequest, textStatus, errorThrown){
			    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
			    			}	
			    		});
			      }},
			      {name:"取消",type:'close'}]
	})
})

function validator() {
	var isFlag = true;
	var param=$.getFormDate("devInfo_win");
// 	if(param.code==undefined){
// 		$("#devInfo_win input[name=code]").focus();
// 		isFlag = false;
// 	}
	if(param.serialNo==undefined){
		$("#devInfo_win input[name=serialNo]").focus();
		isFlag = false;
	}
	if(param.orgCode==undefined || param.orgCode==0){
		alert("请选择终端所属机构！");
		isFlag = false;
	}
	return isFlag;
}

// function gc(){
// 	$("#devGrid").delegate("a","click",function(){
// 		var str=this.id.split("_");
// 		var btn_type=str[0];
// 		var b_id=str[1];
// 		if(btn_type=='q'){
// 			$("#_login_name").attr("disabled","disabled");
// 			$("#devInfo_audit_win").window("open",{mode:"readOnly",btns:[0,1,1,0],loadData:"getMoJson.do",param:{"id":b_id}});
// 			var bt_is = $("#btn_devInfo_audit_is");
// 			bt_is.html("<span class=\"ui-icon auditIco\"></span><span>审核</span>");
// 			bt_is.prop("disabled", false);
// 		}else if(btn_type=='d'){
// 			$("#_login_name").attr("disabled","disabled");
// 			$("#devInfo_audit_win").window("open",{mode:"readOnly",btns:[1,1,0,0],loadData:"getMoJson.do",param:{"id":b_id}});
// 			var bt_on = $("#btn_devInfo_audit_no");
// 			bt_on.html("<span class=\"ui-icon auditDelIco\"></span><span>禁用</span>");
// 			bt_on.prop("disabled", false);
// 		} else if(btn_type=='s'){
// 			$("#_login_name").attr("disabled","disabled");
// 			$("#devInfo_audit_win").window("open",{mode:"readOnly",btns:[1,0,1,0],loadData:"getMoJson.do",param:{"id":b_id}});
// 			var bt_on = $("#btn_devInfo_audit_no");
// 			bt_on.html("<span class=\"ui-icon auditDelIco\"></span><span>启用</span>");
// 			bt_on.prop("disabled", false);
// 		}
// 	});
// }

function auditDevfun(flag, id) {
	var param = {};

	param["id"] = id;
	if(flag == 0) {
		param["auditUserId"] = getCookie("userId");
		param["auditUserName"] = getCookie("userName");
		param["auditStatus"] = 0;
	} else {
		param["auditStatus"] = 2;
	}
	$.ajax({type:"post", url: "auditDevJson.do", data: param, async:false, dataType: "json",
		success: function(d) {
			if(d.success=="0"){
				alert("操作成功!");
				refushGird();
			}else{
				alert("操作失败!");
			}
		}
	});
}

function getSelectNumDev() {
	return $("#devGrid").jqGrid("getGridParam", "selarrrow").length;
}

var userInfo=null;
function getCookie(objName){//获取指定名称的cookie的值 
	//alert(document.cookie);
	if(!userInfo){
		$.ajax({type:"post",url:cp+"/sso/getUser.do",data:{},async:false,dataType:"json",
			success:function(ud){
// 				console.log(ud)
				userInfo=ud;
			}
	    });
	}
	return userInfo[objName];
};
</script>


</html>