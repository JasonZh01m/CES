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
	
	function del(flag, id){

		if(flag == 1){
			id=id.join(",");
		}
// 	 	console.log("id--->  "+id)
		if(confirm("确定要删除吗?")){
			$.ajax({type:"post",url:"deleEvaluateLevelByLevelJson.do",data:{"ids":id}, async:true,dataType:"json",
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
	
	$("#sb_q").searchBar({
		items:[
			   {field:"分数等级",name:"level",type:"text"},
		   	   {field:"等级名称",name:"name",type:"text"}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		       var param=$.getFormDate("sb_q");
		       $.clearGridPostParam("evaluateLevelGrid");
		       $("#evaluateLevelGrid").setGridParam({  
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
	
	$("#t_evaluateLevelGrid").toolbar({
		items:[{name:"添加",type:"button",icon:cp+"/UI/css/icon/add.png",on:function(){
				$("#evaluateLevel_win").window("open",{btns:[0,1,0]});
			}
		}, '-', {
			name: "修改", type:"button", icon:cp+"/UI/css/icon/application_form_edit.png", on:function(){
			 	var id=$("#evaluateLevelGrid").jqGrid("getGridParam","selrow");
			 	var roleData=$("#evaluateLevelGrid").jqGrid('getRowData',id);
			 	if(getSelectNumLevel()>1){
					alert("只能选一行进行修改!");
					$("#evaluateLevelGrid").resetSelection();
				} else if(getSelectNumLevel()==0){
					alert("请选取要修改的一行进行修改!");
				} else {
					$("#btn_evaluateLevel_edit").prop("disabled", false);
					$("#evaluateLevel_win").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id":roleData.level}});
				}
			}
		}, '-', {
			name: "删除", type: "button", icon:cp+"/UI/css/icon/delete.png", on:function() {
				if(getSelectNumLevel()==0){
					alert("请至少选取一行进行删除!");
				}else if(getSelectNumLevel()>0){
					var id = $("#evaluateLevelGrid").jqGrid("getGridParam", "selarrrow");
					del(1,id);
				} else {
					alert("请选取一行进行删除！");
				}
			}
		}]
	});
	
	var w=$(document.body).width();
	var h=$(window).height();
	
	var sbh = $("#sb_q").height();
	
	$("#evaluateLevelGrid").jqGrid({
		width: w-2,
		height: h-83-sbh,
		url: "PageJson.do",
		mtype: "POST",
		datatype: "json",
		altclass: "odd",
		colNames: ["分数等级", "等级名称", "分数值", "备注"],
		colModel: [	   
		    {name:"level", index:"level", width:80, search:false, sortable:false, key:true}, 
			{name:"name", index:"name", width:50, align:"right", hidden:false, sortable:false}, 
			{name:"value", index:"value", width:50, align:"right", hidden:false, sortable:false },
			{name:"remark", index:"remark", width:50, align:"right", hidden:false, sortable:false}
		],
		multiselect: true,
		viewrecords: true,
		rowNum: 10,
		rowList: [ 10, 20, 30 ],
		pager: "#pager2",
	});
	
	$("#evaluateLevel_win").window({
		title: "评价等级参数",
		width: 570,
		height: 260,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"分数等级",name:"level",type:"text", allowBlank:false, 
						 validFn:function(name,value){
							 alert("1111")
	            		   var f=false;
	            		   var param = {"level":value};
	            		   $.ajax({type:"post",url:"validatorLevelJson.do",async:false,data:param,dataType:"json",
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
	            	   validFnText:"该等级已存在!"},
					{field:"分数名称",name:"name",type:"text", minLen:0,maxLen:24, allowBlank:false},
	            	{field:"分数值",name:"value",type:"text", minLen:0,maxLen:24, allowBlank:false},
	            	{field:"备注",name:"remark",type:"textarea",height:60,minLen:0,maxLen:120}
					]
		}],
		button: [ 
		          {name:"添加",id:"btn_evaluateLevel_add", type: "button",
		        	 on:function(){
		        		  var bt = $("#btn_evaluateLevel_add");
						  var param=$.getFormDate("evaluateLevel_win");
						  if(!validator()){
							  return;
						  }
						  bt.html("<span>正在添加...</span>");
		        		  bt.prop("disabled", true);		        		
						  $.ajax({type:"post",url: "addJson.do",data:param,async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("操作成功!");
				    					$.closeWinAndFlush("evaluateLevel_win","evaluateLevelGrid");
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
			      {name:"更新", id: "btn_evaluateLevel_edit", on:function(){
			    	  var bt = $("#btn_evaluateLevel_edit");
			    	  var param=$.getFormDate("evaluateLevel_win");
			    	  if(!validator()){
						  return;
					  }
			    	  bt.html("<span>正在更新...</span>");
	        		  bt.prop("disabled", true);
	        		  $.ajax({type:"post",url: "modifyJson.do",data:param,async:false,dataType:"json",
			    			success:function(d){
			    				if(d.success=="0"){
			    					alert("操作成功!");
			    					$.closeWinAndFlush("evaluateLevel_win","evaluateLevelGrid");
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

function refushGird(){
	$("#evaluateLevelGrid").jqGrid('setGridParam',{  
        datatype:'json',  
        postData:{},
        page:1
    }).trigger("reloadGrid");
}

function validator() {
	var isFlag = true;
	var param=$.getFormDate("evaluateLevel_win");
	if(param.level==undefined){
		$("#evaluateLevel_win input[name=level]").focus();
		isFlag = false;
	}
	if(param.name==undefined){
		$("#evaluateLevel_win input[name=name]").focus();
		isFlag = false;
	}
	if(param.value==undefined){
		$("#evaluateLevel_win input[name=value]").focus();
		isFlag = false;
	}
	return isFlag;
}

function getSelectNumLevel() {
	return $("#evaluateLevelGrid").jqGrid("getGridParam", "selarrrow").length;
}
</script>
<div id="sb_q" class="searchBar"></div>
<div id="t_evaluateLevelGrid"></div> 
<table id="evaluateLevelGrid"></table>
<div id="pager2"></div>
<div id="evaluateLevel_win"></div>
<div id="taskOrg_win"></div>
</html>