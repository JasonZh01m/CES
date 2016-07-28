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
</head>
<body>
	<div id="p_org_content"></div>
    <div id="p_org_config"></div>
    <div id="organization_win"></div>
    <div id="draggable2"></div>
    <input id="hiddenSelectOrganizationId" type="hidden" />
</body>
    <script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.excheck-3.5.js"></script> --%>
	<script type="text/javascript" src="${ctx}/UI/js/ajaxfileupload.js"></script>
	<script type="text/javascript">
var cp="${ctx}";
var parentid, isMana = null;
var gridRids=null;
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

$(function(){
	
	$(document.body).layout({
		items:[{
			id: "p_org_content",
			title: "组织机构列表",
			width:"0.4",
			region:"left",
			content: {
				id: "p_org_1",
				tbr: {id: "p_org_1_tb",
					items:[{name:"添加",type:"button",icon:cp+"/UI/css/icon/add.png",on:function(){
							loadParentOrganizationTree();
							isMana = 0;
							$("#organization_win").window("open",{btns:[0,1,0]});
		    	   	    }
		    	   	},"-",
		    	   	{
		    	   	    name:"修改",type:"button",icon:cp+"/UI/css/icon/application_form_edit.png",on:function(){
		    	   	    	organizationId = $("#hiddenSelectOrganizationId").val();
		    	   	    	isMana = 1;
		    	   	    	if(organizationId != null && organizationId != ""){
		    	   	    		loadParentOrganizationTree(organizationId);
		    	   	    		$("#organization_win").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id": organizationId}});
			    	   	    	$("#parentId").attr("disabled",true); 
		    	   	    	}else{
		    	   	    		alert("请选择要修改的一行!");
		    	   	    	}
		    	   	    }
		    	   	
		    	   	},"-",
		    	   	{name:"分配用户",type:"button",icon:cp+"/UI/css/icon/application_form_edit.png",on:function(){
	    	   	    	organizationId = $("#hiddenSelectOrganizationId").val();
	    	   	    	isMana = 1;
	    	   	    	if(organizationId != null && organizationId != ""){
// 	    	   	    		var id=$("#mainGrid").jqGrid("getGridParam","selrow");
// 	    					var rowData = $("#mainGrid").jqGrid("getRowData",id);
	    					
// 	    					$("#_loginName").attr("disabled","disabled");
// 	    					$("#_userName").attr("disabled","disabled");
// 	    					$("#_loginName").val(rowData.loginName);
// 	    					$("#_userName").val(rowData.userName);

	    					//从DB里获取该用户的roleId
	    					gridRids = userChecked(organizationId);
	    					
	    					
	    					$("#winGrid").resetSelection(); 
	    					$("#winGrid").jqGrid('setGridParam',{  
	    				        datatype:'json',  
	    				        postData:{},
	    				        page:1
	    				    }).trigger("reloadGrid");
	    					
	    					$("#draggable2").window("open",{btns:[0,0],loadData:"getMoJson.do",param:{"id": organizationId}});
	    	   	    	}else{
	    	   	    		alert("请选择要分配用户的机构!");
	    	   	    	}
	    	   	    }},"-",
		    	   	{
		    	   		name:"删除",type:"button",icon:cp+"/UI/css/icon/delete.png",on:function(){
		    	   			organizationId = $("#hiddenSelectOrganizationId").val();
		    	   			if(organizationId != null && organizationId != ""){
		    	   				if(confirm("您是确定删除此数据?")){
		    	   					$.ajax({type:"post",url:"deleteOrganizationJson.do",data:{"organizationId":organizationId}, async:true,dataType:"json",
				    	   				success: function(date){
				    	   				if(date.success==0){
				    	   					alert("删除成功!");
				    	   					reloadOrganizationTree();
				    	   				}else if(date.success==1){
				    	   					alert("权限被角色使用 ，不能删除!");
				    	   				}
				    	   			}});
		    	   				}
			    	   			
		    	   			}else{
		    	   	    		alert("请选择要删除的一行!");
		    	   	    	}
		    	   		}
		    	   	},"-",
		    	   	{
		    	   		name:"刷新",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
		    	   			reloadOrganizationTree();
		    	   		}}]
			}}
		}, {
			id:"p_org_config",
			title: "组织机构详细信息",
			width:"0.6",
			region:"center",
			content:{
				    id:"p_org_2"
			}
		}]
	});
	
	$("#p_org_1").html("<div style='overflow:auto;'><ul id='organizationTree' class='ztree'></ul></div>");
	
	$("#p_org_2").html(
			"<div id='sb_q' class='searchBar'>"+
				"<form id='sbf_sb_q'>"+
					"<ul>"+
						"<li style='width: 250px;'>"+
							"<label style='width: 80px;'>机构名称:</label>"+
							"<input type='text' name='name' disabled>"+
						"</li>"+
						"<li style='width: 250px;'>"+
							"<label style='width: 80px;'>机构编码:</label>"+
							"<input type='text' name='code' disabled>"+
						"</li>"+
					"</ul>"+
					"<ul>"+
						"<li style='width: 250px;'>"+
							"<label style='width: 80px;'>机构级别:</label>"+
							"<input type='text' name='level' disabled>"+
						"</li>"+
						"<li style='width: 250px;'>"+
							"<label style='width: 80px;'>机构地址:</label>"+
							"<input type='text' name='address' disabled>"+
						"</li>"+
					"</ul>"+
				"</form>"+
			"</div>"
	);
	$("#sb_q").css("border-bottom","0px");
	$("#sb_q").height($("#p_org_2").height());
	$("#organization_win").window({
		title: "组织机构参数",
		width: 570,
		height: 460,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"ID",name:"id",type:"hidden"},
			        {field:"机构编号",name:"code",type:"text", allowBlank:false, 
						 validFn:function(name,value){
	            		   var f=false;
	            		   var param = {"code":value};
	            		   if(isMana!=1) {
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
	            		   } else {
	            			   f = true;
	            		   }
	            		   return f;
	            	   },
	            	   validFnText:"该机构已存在!" },
					{field:"机构名称",name:"name",type:"text", minLen:0,maxLen:24, allowBlank:false,
	            		     validFn:function(name,value){
	            		   var f=false;
	            		   var param = {"name":value};
	            		   if(isMana!=1) {
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
	            		   } else {
	            			   f = true;
	            		   }
	            		   return f;
	            	   },
	            	   validFnText:"该机构已存在!"},
					{field:"机构级别",name:"level",type:"text",regex:"number", regexText:"必须为正整数", allowBlank:false},
					{field:"机构地址",name:"address",type:"text"},
					{field:"上级机构",id:"parentId",name:"parentId",type:"text",  readOnly:true}
					]
		}, {type:"pannel",id:"pp",width:554,height:281,field:"选择上级机构"}],
		button: [ 
		          {name:"添加",id:"btn_organization_add", type: "button",
		        	 on:function(){
		        		  var bt = $("#btn_organization_add");
						  var param=$.getFormDate("organization_win");
						  if(!validator()){
							  return;
						  }
						  bt.html("<span>正在添加...</span>");
		        		  bt.prop("disabled", true);		        		
						  $.ajax({type:"post",url: "addJson.do",data:param,async:false,dataType:"json",
				    			success:function(d){
				    				if(d.success=="0"){
				    					alert("操作成功!");
				    					reloadOrganizationTree();
				                        $("#organization_win").window("close");
				    					bt.html("<span>添加</span>");
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
			      {name:"更新", id: "btn_organization_edit", on:function(){
			    	  var bt = $("#btn_organization_edit");
			    	  var param=$.getFormDate("organization_win");
			    	  if(!validator()){
						  return;
					  }
			    	  bt.html("<span>正在更新...</span>");
	        		  bt.prop("disabled", true);
	        		  $.ajax({type:"post",url: "modifyJson.do",data:param,async:false,dataType:"json",
			    			success:function(d){
			    				if(d.success=="0"){
			    					alert("操作成功!");
			    					reloadOrganizationTree();
			                        $("#organization_win").window("close");
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
	
	var ppw=$("#pp").width();
	var pph=$("#pp").height();
	loadOrganizationTree();
	$("#pp").append("<div style='width:"+ppw+"px;height:"+pph+"px;overflow:auto;'><ul id='parentOrganizationTree' class='ztree'></ul></div>");

	
	$("#draggable2").window({
		title: "分配组织机构",
		width: 570,
		height: 435,
		content: [{
			type: "form",
			rowNum: 2,
			items: [{field:"ID",name:"id",type:"hidden"},
			        {field:"机构编号",name:"code",type:"text", allowBlank:false},
					{field:"机构名称",name:"name",type:"text", minLen:0,maxLen:24, allowBlank:false}
					]
		}, 
		{type:"pannel",id:"pp1",field:"用户信息",width:554,height:306}
		],
		button: [ 
		          {name:"添加",id:"btn_organization_add", type: "button", on:addUserOrgRelation},
			      {name:"取消",type:'close'}
		]
	});
	
	var ppw1=$("#pp1").width();
	var pph1=$("#pp1").height();
// 	alert(pph1)
	$("#pp1").append("<table id='winGrid'></table><div id='pager2'></div>");
	$("#winGrid").jqGrid({
		width:ppw1-10,
		height:pph1-55,
		url:cp+'/popedom/PageJson.do', 
		datatype: "json", 
		altclass:'odd',
		jsonReader:{
		    root:'rows',
		    page: "page",  
		    total: "total",  
		    records: "record",
		    repeatitems : false  
		},
		colNames:['id','登录用户','用户名称','备注'], 
		colModel:[
		{name:'id',index:'id', width:10,hidden:true, search: false,sortable:false},
		{name:'loginName',index:'_login_name', width:10,hidden:false, search: false,sortable:false},
		{name:'userName',index:'_user_name', width:10,hidden:false, search: false,sortable:false},
		{name:'remark',index:'_user_remark', width:10,hidden:false, search: false,sortable:false}
		], 
		multiselect: true,
		rowNum:10, 
		rowList:[10,20,30], 
		pager: '#pager2',
		gridComplete:function(){
			if(gridRids!=null){
			    SelectedCheckbox("winGrid", gridRids);
			}
		}
	});

	$("#winGrid").delegate("input:checkbox","click",function(){
		var rwnum=$(this).parent().parent().get(0).id;
		if(this.checked){
			$("#winGrid").setSelection(rwnum,true);
		}else{
			$("#winGrid").setSelection(rwnum,false);
		}
	});
});


function SelectedCheckbox(wid,rids){
	
	$("#"+wid).resetSelection(); 
// 	alert(rids)
	var str=rids.split(",");
	var obj = $("#"+wid).jqGrid("getRowData");
	
	for(var i=0;i<str.length;i++){
		jQuery(obj).each(function(){
			if(str[i]==this.id){
				$("#"+wid).setSelection(this.id);
			}
	    });
		
	}
}


function loadOrganizationTree(){
	var url = "getOrganizationTreeJson.do";
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
			autoParam : [ "id=pId" ],
			dataFilter : filter
		},
		callback:{
			onClick: onClick
		
		}
	};					
	$.fn.zTree.init($("#organizationTree"), setting);
}


function loadParentOrganizationTree(parentId) {
	var url = "getOrganizationTreeJson.do";
	var setting = {
			view: {   
		        showLine: true,   
		        selectedMulti: false,   
		        dblClickExpand: false  
			}, 
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable:true,
				url:url,
				autoParam:["id=pId"],
				dataFilter: filter
			},
			callback:{
				onClick: onParentCheck,
				onAsyncSuccess:function(){
// 					if(permissionId){
// 						var node = treeObj.getNodeByParam("id", permissionId);
// 						treeObj.selectNode(node,false);
// 					}
				}
			}
			
		};
	
	$.fn.zTree.init($("#parentOrganizationTree"), setting);
}
function onParentCheck(e, treeId, treeNode) {
// 	console.log("treeNode.id--->  "+treeNode.id)
	if(!treeNode.checked){
		$("#parentId").val(treeNode.id);
	}else{
		$("#parentId").val('0');
	}
	
}

function onClick(event, treeId, treeNode, clickFlag){
	var p = $("#p_org_2");
	$.ajax({type : "post", url : "getMoJson.do?id=" + treeNode.id, async : false, dataType : "json",
			success : function(date) {
			p.find("input[name=name]").val(date.name);
			p.find("input[name=code]").val(date.code);
			p.find("input[name=level]").val(date.level);
			p.find("input[name=address]").val(date.address);
		}
	});
	$("#hiddenSelectOrganizationId").val(treeNode.id);
}

function validator() {
	var isFlag = true;
	var param=$.getFormDate("organization_win");
	if(param.code==undefined){
		$("#organization_win input[name=code]").focus();
		isFlag = false;
	}
	if(param.name==undefined){
		$("#organization_win input[name=name]").focus();
		isFlag = false;
	}
	if(param.level==undefined){
		$("#organization_win input[name=level]").focus();
		isFlag = false;
	}
	return isFlag;
}

function reloadOrganizationTree() {
	loadOrganizationTree();
	var p = $("#p_org_2");
	p.find("input[name=name]").val("");
	p.find("input[name=code]").val("");
	p.find("input[name=level]").val("");
	p.find("input[name=address]").val("");
	$("#hiddenSelectOrganizationId").val("");
}

function addUserOrgRelation() {
	var ids=$("#winGrid").jqGrid("getGridParam","selarrrow");
	var id="draggable2";
	var param=$.getFormDate(id);
	param["userIds"] = ids.toString();
	param["orgId"] = param["id"];
	$.ajax({type:"post",url:"addOrgRelationUser.do",data:param,async:false,dataType:"json",
		success:function(d){
			if(d.success=="0"){
				alert("操作成功!");
				$.closeWinAndFlush("draggable2","");
			}else{
				alert("操作失败!");
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
		}	
	});
}

function userChecked(orgId){
	var id="draggable2";
	var param=$.getFormDate(id);
	var rids=null;

	param['orgId'] = orgId;
	$.ajax({type:"post",url:"queryAllUserByOrgJson.do",data:param,async:false,dataType:"json",
		success:function(d){
			if(d){
				rids=d;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
		}	
	});
	return rids;
}

</script>
</html>