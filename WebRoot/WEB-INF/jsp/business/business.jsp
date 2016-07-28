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

function del(flag,id){
	if(flag == 1){
		id=id.join(",");
	}
// 	console.log("id--->  "+id)
	if(confirm("确定要删除吗?")){
		$.ajax({type:"post",url:"deleBusinessByIdJson.do",data:{"ids":id}, async:true,dataType:"json",
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
	$("#businessGrid").jqGrid('setGridParam',{  
        datatype:'json',  
        postData:{},
        page:1
    }).trigger("reloadGrid");
}

$(function(){
	
	$("#sb_q").searchBar({
		items:[
			   {field:"业务编号",name:"code",type:"text"},
		   	   {field:"业务名称",name:"name",type:"text"}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		       var param=$.getFormDate("sb_q");
		       $.clearGridPostParam("businessGrid");
		       $("#businessGrid").setGridParam({  
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
	
	$("#t_business").toolbar({
		items:[{name:"添加",type:"button",icon:cp+"/UI/css/icon/add.png",on:function(){
				$("#business_win").window("open",{btns:[0,1,0]});
			}
		}, '-', {
			name: "修改", type:"button", icon:cp+"/UI/css/icon/application_form_edit.png", on:function(){
			 	var id=$("#businessGrid").jqGrid("getGridParam","selrow");
			 	var roleData=$("#businessGrid").jqGrid('getRowData',id);
			 	if(getSelectNumbusiness()>1){
					alert("只能选一行进行修改!");
					$("#businessGrid").resetSelection();
				} else if(getSelectNumbusiness()==0){
					alert("请选取要修改的一行进行修改!");
				} else {
					$("#btn_business_edit").prop("disabled", false);
					$("#business_win").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id":id}});
					
				}
			}
		}, '-', {
			name: "删除", type: "button", icon:cp+"/UI/css/icon/delete.png", on:function() {
				if(getSelectNumbusiness()==0){
					alert("请至少选取一行进行删除!");
				}else if(getSelectNumbusiness()>0){
					var id = $("#businessGrid").jqGrid("getGridParam", "selarrrow");
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
	
	$("#businessGrid").jqGrid({
		width: w-2,
		height: h-78-sbh,
		url: "PageJson.do",
		mtype: "POST",
		datatype: "json",
		altclass: "odd",
		colNames: [ "ID号", "业务编号", "业务名称", "创建时间"],
		colModel: [{name:"id", index:"id", width:80, search:false, sortable:false, sortable:false, hidden:true}, 
			{name:"code", index:"code", width:50, align:"right", hidden:false, sortable:false}, 
			{name:"name", index:"name", width:50, align:"right", hidden:false, sortable:false },
			{name:"createTime", index:"createTime", width:50, align:"right", hidden:false, sortable:false}
		],
		multiselect: true,
		viewrecords: true,
		rowNum: 15,
		rowList: [ 15, 25, 35],
		pager: "#pager2",
	});
	
	$("#business_win").window({
		title: "员工信息参数",
		width: 570,
		height: 330,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"ID",name:"id",type:"hidden"},
			        {field:"业务编号",name:"code",type:"text", allowBlank:false , 
						 validFn:function(name,value){
	            		   var f=false;
	            		   var param = {"code":value};
	            		   $.ajax({type:"post",url:"validatorBusiness.do",async:false,data:param,dataType:"json",
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
	            	   validFnText:"该业务已存在!"  },
					{field:"业务名称",name:"name",type:"text", allowBlank:false/* ,
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
		          {name:"添加",id:"btn_business_add", type: "button",
		        	 on:function(){
		        		  var bt = $("#btn_business_add");
						  var param=$.getFormDate("business_win");
						  if(!validator()){
							  return;
						  }
						  if(!chackCode(param.code)) {
							  alert("不能添加重复业务！");
							  return;
						  }
						  bt.html("<span>正在添加...</span>");
		        		  bt.prop("disabled", true);		        		
						  $.ajax({type:"post",url: cp + "/business/addBusinessJson.do",data:param,async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("操作成功!");
				    					$.closeWinAndFlush("business_win","businessGrid");
				    					bt.prop("disabled", false);
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
			      {name:"更新", id: "btn_business_edit", on:function(){
			    	  var bt = $("#btn_business_edit");
			    	  var param=$.getFormDate("business_win");
			    	  if(!validator()){
						  return;
					  }
			    	  bt.html("<span>正在更新...</span>");
	        		  bt.prop("disabled", true);
	        		  $.ajax({type:"post",url: "modifyJson.do",data:param,async:false,dataType:"json",
			    			success:function(d){
			    				if(d.success=="0"){
			    					alert("操作成功!");
			    					$.closeWinAndFlush("business_win","businessGrid");
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
	var param=$.getFormDate("business_win");
	if(param.code==undefined){
		$("#business_win input[name=code]").focus();
		isFlag = false;
	}
	if(param.name==undefined){
		$("#business_win input[name=name]").focus();
		isFlag = false;
	}
	return isFlag;
}

function chackCode(code) {
	var f = true;
	var param={"code" : code};
	$.ajax({type:"post",url:"validatorBusiness.do",async:false,data:param,dataType:"json",
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
}

function getSelectNumbusiness() {
	return $("#businessGrid").jqGrid("getGridParam", "selarrrow").length;
}
</script>
<div id="sb_q" class="searchBar"></div>
<div id="t_business"></div>
<table id="businessGrid"></table>
<div id="pager2"></div>
<div id="business_win"></div>
<div id="taskOrg_win"></div>
</html>