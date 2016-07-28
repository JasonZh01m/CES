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
	
	$("#sb_q").searchBar({
		items:[
			   {field:"员工编号",name:"code",type:"text"},
		   	   {field:"员工姓名",name:"name",type:"text"}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		       var param=$.getFormDate("sb_q");
		       $.clearGridPostParam("employeeGrid");
		       $("#employeeGrid").setGridParam({  
		  	        datatype:"json",  
		  	        postData:param,
		  	        page:1
		  	    }).trigger("reloadGrid");
		    }},
		    {name:"重置",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
		    	$("#sb_q").searchBar("clearForm");
		    }}
		]
	});
	
	var w=$(document.body).width();
	var h=$(window).height();
	
	var sbh = $("#sb_q").height();
	
	$("#employeeGrid").jqGrid({
		width: w-2,
		height: h-45-sbh,
		url: "PageJson.do",
		mtype: "POST",
		datatype: "json",
		altclass: "odd",
		colNames: [ "ID号", "员工编号", "员工名称", "所属机构编码", "所属机构", "最后登录时间"],
		colModel: [{name:"id", index:"id", width:80, search:false, sortable:false, sortable:false, hidden:true}, 
			{name:"code", index:"code", width:50, align:"right", hidden:false, sortable:false}, 
			{name:"name", index:"name", width:50, align:"right", hidden:false, sortable:false },
			{name:"orgCode", index:"orgCode", width:50, align:"right", hidden:true, sortable:false},
			{name:"orgCodeName", index:"orgCodeName", width:50, align:"right", hidden:false, sortable:false,
				formatter: function (cellvalue, options, rowObject){
					if(cellvalue==undefined) {
						return "未分配";
					} else{
						return cellvalue;
					}
				}
			},
			{name:"lastTime", index:"lastTime", width:50, align:"right", hidden:false, sortable:false,
				formatter: function (cellvalue, options, rowObject){
					if(cellvalue==undefined) {
						return "未登录";
					} else{
						return cellvalue;
					}
				}
			}
		],
		multiselect: false,
		viewrecords: true,
		rowNum: 10,
		rowList: [ 10, 20, 30 ],
		pager: "#pager2",
	});
	
	$("#employee_win").window({
		title: "员工信息参数",
		width: 570,
		height: 330,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"ID",name:"id",type:"hidden"},
			        {field:"员工编号",name:"code",type:"text", allowBlank:false/* , 
						 validFn:function(name,value){
	            		   var f=false;
	            		   var param = {"code":value};
	            		   $.ajax({type:"post",url:"validatorOrgInfo.do",async:false,data:param,dataType:"json",
	            				success:function(d){
	            		            if(d){
	            		            	if(d.success===0){
	            		            	    f=true;
	            		            	}else{
	            		            		f=false;
	            		            	}
	            		            }
	            				}
	            		   });
	            		   return f;
	            	   },
	            	   validFnText:"该员工已存在!"  */},
					{field:"员工名称",name:"name",type:"text", minLen:0,maxLen:24, allowBlank:false/* ,
	            		     validFn:function(name,value){
	            		   var f=false;
	            		   var param = {"name":value};
	            		   
	            		   $.ajax({type:"post",url:"validatorOrgInfo.do",async:false,data:param,dataType:"json",
	            				success:function(d){
	            		            if(d){
	            		            	if(d.success===0){
	            		            	    f=true;
	            		            	}else{
	            		            		f=false;
	            		            	}
	            		            }
	            				}
	            		   });
	            		   return f;
	            	   },
	            	   validFnText:"该员工已存在!" */}
// 					{field:"所属机构",name:"orgCode",type:"select", url: "optionOrganizationJson.do", blankText:"请选择"}
					]
		}],
		button: [ 
		          {name:"添加",id:"btn_employee_add", type: "button",
		        	 on:function(){
		        		  var bt = $("#btn_employee_add");
						  var param=$.getFormDate("employee_win");
						  if(!validator()){
							  return;
						  }
						  bt.html("<span>正在添加...</span>");
		        		  bt.prop("disabled", true);		        		
						  $.ajax({type:"post",url: "addJson.do",data:param,async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("操作成功!");
				    					$.closeWinAndFlush("employee_win","employeeGrid");
				    					bt.html("<span>添加</span>");
				    				}else{
				    					alert("操作失败!");
				    					bt.html("<span>请重试</span>");
				    					bt.prop("disabled",false);
				    				}
				    			},
				    			error:function(XMLHttpRequest, textStatus, errorThrown){
				    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				    			}	
				    		});
					
		        	  }},
			      {name:"更新", id: "btn_employee_edit", on:function(){
			    	  var bt = $("#btn_employee_edit");
			    	  var param=$.getFormDate("employee_win");
			    	  if(!validator()){
						  return;
					  }
			    	  bt.html("<span>正在更新...</span>");
	        		  bt.prop("disabled", true);
	        		  $.ajax({type:"post",url: "modifyJson.do",data:param,async:false,dataType:"json",
			    			success:function(d){
			    				if(d.success=="0"){
			    					alert("操作成功!");
			    					$.closeWinAndFlush("employee_win","employeeGrid");
			    					bt.html("<span>更新</span>");
			    					bt.prop("disabled",false);
			    				}else{
			    					alert("操作失败!");
			    					bt.html("<span>请重试</span>");
			    					bt.prop("disabled",false);
			    				}
			    			},
			    			error:function(XMLHttpRequest, textStatus, errorThrown){
			    				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
			    			}	
			    		});
			      }},
			      {name:"取消",type:'close'}
		]
	});
})

function validator() {
	var isFlag = true;
	var param=$.getFormDate("employee_win");
	if(param.code==undefined){
		$("#employee_win input[name=code]").focus();
		isFlag = false;
	}
	if(param.name==undefined){
		$("#employee_win input[name=name]").focus();
		isFlag = false;
	}
// 	if(param.level==undefined){
// 		$("#employee_win input[name=level]").focus();
// 		isFlag = false;
// 	}
	return isFlag;
}

function getSelectNumCommand() {
	return $("#employeeGrid").jqGrid("getGridParam", "selarrrow").length;
}
</script>
<div id="sb_q" class="searchBar"></div>
<!-- <div id="t_employee"></div> -->
<table id="employeeGrid"></table>
<div id="pager2"></div>
<div id="employee_win"></div>
<div id="taskOrg_win"></div>
</html>