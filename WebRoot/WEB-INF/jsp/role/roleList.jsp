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
</style>
<title>角色信息</title>
</head>
<body>
	<div id="sb_q" class="searchBar"></div>
	<div id="t_mainGrid"></div>
	<table id="mainGrid">
	</table>
	
	<div id="pager2"></div>
	
    <div id="draggable"></div>
    
    <input type="hidden" id="hiddenRoleId">
    <script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/jqGrid-all.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/sq-ui-1.0.0.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/UI/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript">
	var cp="${ctx}"; 
	
	function verificationLayer(isadd){
 		var isPass = true;
		var code = $("#draggable input[name='code']").val();
		if(code==null||code==""){
			isPass =  false;
		}
		var name = $("#draggable input[name='name']").val();
		if(name==null||name==""){
			isPass =   false;
		}
		$("#draggable input[name='code']").focus();
		$("#draggable input[name='code']").blur();
		$("#draggable input[name='name']").focus();
		$("#draggable input[name='name']").blur();
		if(isadd&&code!=null&&code!=""){
			$.ajax({type:"post",url:"checkRoleCode.do",data:{"code":code}, async:false,dataType:"json",
				success: function(date){
					if(date.success==1){
						alert("角色编码已存在！");
						$("#draggable input[name='code']").focus();
						isPass =   false;
					}
			}}); 
		}
			return isPass ; 
	}
	
	function saveLayer(){
		if(!verificationLayer(true)){
			return;
		}
		var para={};
		$('#draggable').find('input[type="text"]').each(function(){
			id = $(this).attr("name");
			value = $(this).val();
			if(value!=''){
				para[id] = value;
			}
		});
		var zTree = $.fn.zTree.getZTreeObj("permissionTree");
		var  nodes=zTree.getCheckedNodes();
        var ids="";
        for(var i=0;i<nodes.length;i++){
        	ids+=nodes[i].id + ",";
        }
        ids = ids+"-1";
        
        para['permissionIds'] = ids;
	
		$.ajax({type:"post",url:"insertRole.do",data:para, async:false,dataType:"json",
			success: function(date){
			if(date.success==0){
				alert("新增成功!");
				$.closeWinAndFlush("draggable","mainGrid");
			}else{
				alert("新增失败!");
			}
		}}); 
	}
	
	function updateLayer(){
		if(!verificationLayer(false)){
			return;
		}
		var para={};
		$('#draggable').find('input[type="text"]').each(function(){
			id = $(this).attr("name");
			value = $(this).val();
			if(value!=''){
				para[id] = value;
			}
		});
		var zTree = $.fn.zTree.getZTreeObj("permissionTree");
		var  nodes=zTree.getCheckedNodes();
        var ids="";
        for(var i=0;i<nodes.length;i++){
        	ids+=nodes[i].id + ",";
        }
        ids = ids+"-1";
        
        para['permissionIds'] = ids;
        para['id'] = $("#hiddenRoleId").val();
	
		$.ajax({type:"post",url:"updateRole.do",data:para, async:false,dataType:"json",
			success: function(date){
			if(date.success==0){
				alert("更新成功!");
				$.closeWinAndFlush("draggable","mainGrid");
			}else{
				alert("更新失败!");
			}
		}}); 
		
	}
	
	function del(id){
		$.ajax({type:"post",url:"deleteRoleById.do",data:{"id":id}, async:true,dataType:"json",
			success: function(date){
			if(date.success==0){
				alert("删除成功!");
				refushGird();
			}else if(date.success==1){
				alert("该角色已分配给用户，或者有权限分配，不能删除!");
			}
		}});
	}

	function refushGird(){
		$("#mainGrid").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{},
	        page:1
	    }).trigger("reloadGrid");
	}

	$(function(){
	var w=$(document.body).width();
	var h=$(window).height();

	$("#sb_q").searchBar({
		items:[
		   	   /* {field:"角色ID",name:"id",type:"text"}, */
		   	   {field:"角色名称",name:"name",type:"text"}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		       var param=$.getFormDate('sb_q');
		       $.clearGridPostParam("mainGrid");
		       $("#mainGrid").setGridParam({  
		  	        datatype:'json',  
		  	        postData:param,
		  	        page:1
		  	    }).trigger("reloadGrid");
		    }},
		    {name:"重置",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
		    	$("#sb_q").searchBar("clearForm");
		    }}
		]
	});

	var sbh=$(".searchBar").height();


	$("#mainGrid").jqGrid({
		width:w-2,
		height:h-83-sbh,
		mtype: 'POST',
		url:'PageJson.do', 
		datatype: "json", 
		altclass:'odd',
		jsonReader:{  
		    root:'rows',     
		    page: "page",         
		    total: "total",  
		    records: "records",
		    repeatitems : false
		},
		colNames:['角色ID','角色编码','角色名称','角色说明'], 
		colModel:[
		{name:'id',index:'id', width:80,hidden:true,search: false,sortable:false,sortable:false},
		{name:'code',index:'code', width:80,align:"right",hidden:false,sortable:false},
		{name:'name',index:'name', width:80,align:"right",hidden:false,sortable:false},
		{name:'remark',index:'remark', width:80,align:"right",hidden:false,sortable:false}
		], 
		multiselect: true,
		rowNum:15, 
		rowList:[15,25,35], 
		pager: '#pager2', 
		sortname: 'id', 
		viewrecords: true, 
		sortorder: "asc"
	}); 


	//$("#t_mainGrid").append(btnstr);
	$("#t_mainGrid").toolbar({
	items:[
		{
		    name:"添加",type:"button",code:"r_m_add",icon:cp+"/UI/css/icon/add.png",on:function(){
		    	$("#draggable").window("open",{btns:[0,1,0]});
		    	$("#code").attr("disabled",false); 
		    	loadPermissionTree(0);
		    	
		    }
		},"-",
		{
			name:"删除",type:"button",code:"r_m_del",icon:cp+"/UI/css/icon/delete.png",on:function(){
				if(getSelectNum()>0){
					if(confirm("您是否删除选择的数据?")){
						var id=$("#mainGrid").jqGrid("getGridParam","selrow");
						var roleData=$("#mainGrid").jqGrid('getRowData',id);
						del(roleData.id);
						}					
				}else{
					alert("请至少选取一行进行删除!");
				}
			}
		},"-",
		{
			name:"修改",type:"button",code:"r_m_modify",icon:cp+"/UI/css/icon/application_form_edit.png",on:function(){
				if(getSelectNum()>1){
					alert("只能选一行进行修改!");
					$("#mainGrid").resetSelection(); 
				}else if(getSelectNum()==0){
					alert("请选取要修改的一行进行修改!");
				}else{
					 
					 var id=$("#mainGrid").jqGrid("getGridParam","selrow");
					 var roleData=$("#mainGrid").jqGrid('getRowData',id);
					 /* $("#draggable").window("open",{btns:[1,0,0],loadData:"getRoleById.do",param:{"id":roleData.id}}); */
					 $("#draggable").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id":roleData.id}});
					 loadPermissionTree(roleData.id);
					 $("#hiddenRoleId").val(roleData.id);
					 $("#code").attr("disabled",true); 
				}
			}
		}
	]
	});



	$("#draggable").window({
		title:'角色详细信息',
		width:510,
		height:450,
		opt:"mainGrid",
		content:[{
			type:"form",
			rowNum:2,
			items:[
	               {field:"角色编码",id:"code",name:"code",type:"text",allowBlank:false},
	               {field:"角色名称",name:"name",type:"text",allowBlank:false},
			       {field:"角色说明",name:"remark",type:"text"}
			]
		},
		{type:"pannel",id:"p1",width:494,height:277,field:"权限分配"}
		],
		button:[
		       {name:"添加",on:function(){saveLayer();}},
		       {name:"更新",on:function(){updateLayer();}},
		       {name:"取消",type:'close'}
		]
	});

	var pw=$("#p1").width();
	var ph=$("#p1").height();

	$("#p1").append("<div style='width:"+pw+"px;height:"+ph+"px;overflow:auto;'><ul id='permissionTree' class='ztree'></ul></div>");




	$("#t_mainGrid button" )
	.button()
	.click(function() {
		//alert(this.id);
		if(this.id=="bfa" || this.id=="bfe"){
			 submitForm();
		}else if(this.id=="bfc"){
			closeWin();
		}
	});

	});

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}

	function loadPermissionTree(id){
		var url = "../permission/getAllPermissionTree.do?id="+id;
		var setting = {
			   view: {   
				        showLine: true,   
				        selectedMulti: false,   
				        dblClickExpand: false  
				    }, 
				check: {
					enable: true,
					chkboxType : { "Y" : "ps", "N" : "s" }
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};

 		$.get(url,{},function(data){
			$.fn.zTree.init($("#permissionTree"), setting,eval(data)); 
		});
		$.fn.zTree.init($("#permissionTree"), setting);
		
		
	}
	function closeWin(){
		$("#draggable").window("close");
	}

	function openWin(){
		$("#draggable").window("open",[0,1,0]);
	}
	function getSelectNum(){
		return $("#mainGrid").jqGrid("getGridParam", "selarrrow").length;
	}

	function getSelectValue(id,type){
	    var selectedIds = $("#"+id).jqGrid("getGridParam", "selarrrow");
	    var _s_len=selectedIds.length;
	    if(_s_len==1){
	       var rowData = $("#"+id).getRowData(selectedIds[0]);
	      return rowData[type]; 
	    }else if(_s_len>1){
	       var vArr="";
	       vArr=$("#"+id).getRowData(selectedIds[0])[type];
	       for(var i=1;i<selectedIds.length;i++){
	          var rowData = $("#"+id).getRowData(selectedIds[i]);
	          vArr=vArr+","+rowData[type];
	       }
	       $("#"+id).resetSelection(); 
	       return vArr;
	    }
	   
	}
	</script>
</body>

</html>