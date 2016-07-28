//	 var staDate = new Date();
var orgids=null;
function del(flag,id){
	var userId = getCookie('userId');
	var ids=id;
	for(var i=0;i<ids.length;++i){
		var _id=ids[i];
		if(_id == userId){
			alert('不能删除自己!');
			return;
		}
		var rowData = $("#mainGrid").jqGrid("getRowData",_id);
		if(rowData.loginName == 'admin'){
			alert('最高权限admin不能被删除!');
			return;
		}
	}
	if(flag == 1){
		id=id.join(",");
	}

	if(confirm("确定要删除吗?")){
		$.ajax({type:"post",url:"deleUserById.do",data:{"id":id}, async:true,dataType:"json",
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
	$("#mainGrid").jqGrid('setGridParam',{  
        datatype:'json',  
        postData:{},
        page:1
    }).trigger("reloadGrid");
}


var rendomPwd=null;

$(function(){
var w=$(document.body).width();
var h=$(window).height();


$("#sb_q").searchBar({
	items:[
	   	   {field:"登录帐号",name:"loginName",type:"text"},
	   	   {field:"用户姓名",name:"userName",type:"text"},
	   	   {field:"性别",name:"sex",type:"select",id:'sex1',
		   	   option:[
		   	   {text:" ",value:""},
               {text:"男",value:"0"},
	   	   	   {text:"女",value:"1"}
		   	   ]
	   	   },
	   	   {field:"有效状态",name:"validStatus",type:"select",
	   		   option:[
	   		   {text:" ",value:""},
	   		   {text:"正常",value:"0"},
	   		   {text:"注销",value:"1"}
	   		   ]
	   	   }
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
	colNames:['登录帐号','用户姓名','性别','电话','邮箱','地址','单位','同时在线数','ip地址','注册日期','到期日期','用户卡ID','有效状态','备注','操作'], 
	colModel:[
	{name:'loginName',index:'loginName', width:80,search: false,sortable:false},
	{name:'userName',index:'userName', width:80,hidden:false,sortable:false}, 
	{name:'sex',index:'sex', width:50,formatter:"select",editoptions:{value:"-1:待定;0:男;1:女"}},
	{name:'phone',index:'phone', width:100,search: false,sortable:false},
	{name:'email',index:'email', width:130,hidden:false,sortable:false}, 
	{name:'address',index:'address', width:130,hidden:false, search: false,sortable:false},
	{name:'unit',index:'unit', width:80,hidden:false, search: false,sortable:false}, 
	{name:'timeLimit',index:'timeLimit', width:80,align:"right",search: false,sortable:false},
	{name:'ip',index:'ip', width:80,hidden:false,sortable:false}, 
	{name:'regDate',index:'regDate', width:130,hidden:false, search: false,sortable:false}, 
	{name:'expireDate',index:'expireDate', width:130,hidden:false, search: false,sortable:false}, 
	{name:'userCardId',index:'userCardId', width:80,hidden:false, search: false,sortable:false},
	{name:'validStatus',index:'validStatus', width:80,hidden:false,formatter:"select",editoptions:{value:"0:正常;1:注销"}},
	{name:'remark',index:'remark', width:130,hidden:false, search: false,sortable:false},
	{name:'id',index:'id', width:130,search: false,sortable:false,formatter:function(cv, options, rowObject){
		return "<a href='#' id='"+"q_"+cv+"'>查看</a>&nbsp;&nbsp;<a href='#' id='"+"d_"+cv+"'>删除</a>&nbsp;&nbsp;<a href='#' id='"+"e_"+cv+"'>更新</a>";
	}}
	],
	multiselect: true,
	rowNum:15, 
	rowList:[15,25,35], 
	pager: '#pager2', 
	sortname: 'id', 
	viewrecords: true, 
	sortorder: "asc"
//	toolbar: [true,"top"]
}); 
var gridRids=null;


$("#t_mainGrid").toolbar({
items:[
	{
	    name:"添加",type:"button",code:"u_m_add",icon:cp+"/UI/css/icon/add.png",on:function(){
	    	$("#_login_name").removeAttr("disabled");
	    	$("#_user_name").removeAttr("disabled");
	    	$("#draggable").window("open",{btns:[0,1,0]});
	    	//预留字段
	    	$("#_user_timeLimit").val(1);
	    	$("#_user_timeLimit").attr("disabled","disabled");
	    }
	},"-",
	{
		name:"删除",type:"button",code:"u_m_del",icon:cp+"/UI/css/icon/delete.png",on:function(){
			if(getSelectNum()==0){
				alert("请至少选取一行进行删除!");
			}else if(getSelectNum()>0){
				var id=$("#mainGrid").jqGrid("getGridParam","selarrrow");
				del(1,id);
			}else{
//				alert($("#mainGrid").jqGrid("getGridParam","selrow"));
				alert("请选取一行进行删除!");
			}
		}
	},"-",
	{
		name:"修改",type:"button",code:"u_m_modify",icon:cp+"/UI/css/icon/application_form_edit.png",on:function(){
			if(getSelectNum()>1){
				alert("只能选一行进行修改!");
				$("#mainGrid").resetSelection(); 
			}else if(getSelectNum()==0){
				alert("请选取要修改的一行进行修改!");
			}else{
				 $("#_login_name").attr("disabled","disabled");
				 var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				 $("#draggable").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id":id}});
				 getRandom();
			}
		}
	},"-",
	{
		name:"分配角色",type:"button",code:"u_m_userrole",icon:cp+"/UI/css/icon/user_edit.png",on:function(){
			if(getSelectNum()>1){
				alert("只能选一行进行分配!");
				$("#mainGrid").resetSelection(); 
			}else if(getSelectNum()==0){
				alert("请选择一行进行分配!");
			}else{
				
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				var rowData = $("#mainGrid").jqGrid("getRowData",id);
//				alert('id:'+id);	
				
				$("#_loginName").attr("disabled","disabled");
				$("#_userName").attr("disabled","disabled");
//					 var id=$("#mainGrid").jqGrid("getGridParam","selrow");
//					 var rowData = $("#mainGrid").jqGrid("getRowData",id);
				$("#_loginName").val(rowData.loginName);
				$("#_userName").val(rowData.userName);
				 
				//从DB里获取该用户的roleId
				gridRids = roleChecked(id);
				
				
//				$("#winGrid").resetSelection(); 
				$("#winGrid").jqGrid('setGridParam',{  
			        datatype:'json',  
			        postData:{},
			        page:1
			    }).trigger("reloadGrid");
				
//				$("#draggable2").window("open",{btns:[0,0],loadData:"../queryAllPermission.do",param:{"id":id}});
				$("#draggable2").window("open",{btns:[0,0]});
				
				
					
			}
		}
	},"-",
	{
		name:"分配机构",type:"button",code:"u_m_userrole",icon:cp+"/UI/css/icon/user_edit.png",on:function(){
			if(getSelectNum()>1){
				alert("只能选一行进行分配!");
				$("#mainGrid").resetSelection(); 
			}else if(getSelectNum()==0){
				alert("请选择一行进行分配!");
			}else{
				
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				var rowData = $("#mainGrid").jqGrid("getRowData",id);
				
				$("#_loginName2").attr("disabled","disabled");
				$("#_userName2").attr("disabled","disabled");
				
				$("#userId").val(id);
				$("#_loginName2").val(rowData.loginName);
				$("#_userName2").val(rowData.userName);
				
				loadOrganizationTree(id)
				$("#draggable3").window("open",{btns:[0,0]});
				$("#orgId").val("");
				//从DB里获取该用户的OrgId
//				orgids = OrgChecked(id);
//				$("#org_winGrid").jqGrid('setGridParam',{  
//			        datatype:'json',  
//			        postData:{},
//			        page:1
//			    }).trigger("reloadGrid");
//				$("#draggable3").window("open",{btns:[0,0]});
			}
		}
	},"-",
	{
		name:"禁用",type:"button",code:"u_m_disable",icon:cp+"/UI/css/icon/decline.png",on:function(){
			if(getSelectNum()==0){
				alert("请选择需要禁用的用户!");
			}else if(getSelectNum()==1){
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				updateValidStatus(id,1);				
			}else{
				var id=$("#mainGrid").jqGrid("getGridParam","selarrrow");
				id=id.join(",");
				modifyValidStatusByUserId(id,1);
			}
		}
	},"-",
	{
		name:"恢复",type:"button",code:"u_m_recovery",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
			if(getSelectNum()==0){
				alert("请选择需要恢复的用户!");
			}else if(getSelectNum()==1){
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				updateValidStatus(id,0);
			}else{
				var id=$("#mainGrid").jqGrid("getGridParam","selarrrow");
				id=id.join(",");
				modifyValidStatusByUserId(id,0);
			}
		}
	}
]
});

$("#draggable").window({
	title:'用户详细信息',
	width:560,
	height:380,
	enterTab:true,
	opt:"mainGrid",
	content:[{
		type:"form",
		rowNum:2,
		items:[
		       {field:"id",name:"id",type:"hidden"},

		       {field:"登陆账号",id:"_login_name",name:"loginName",type:"text",allowBlank:false,
		    	   validFn:function(name,value){
		    		               		   var f=false;
		    		               		   var param = {"loginName":value};
		    		               		   
		    		               		   $.ajax({type:"post",url:"validatorLoginName.do",async:false,data:param,dataType:"json",
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
		    		               	   validFnText:"该登陆帐号已存在!"
		    	},
               {field:"用户姓名",id:"_user_name",name:"userName",type:"text",allowBlank:false,
            		   minLen:1,maxLen:18
            		   },
               {field:"用户密码",name:"passWord",type:"password",id:"_password",allowBlank:false,regex:/^[0-9 | A-Z | a-z]{6,12}$/,regexText:"密码长度必须为6-12位"},
               {field:"性别",id:"sex",/*url:cp+"/popedom/getSexList.do",eachLoad:true,*/name:"sex",allowBlank:false,type:"select",
			   	   option:[
                   {text:"",value:""},
	               {text:"男",value:"0"},
		   	   	   {text:"女",value:"1"}
			   	   ]
		   	   },
               {field:"确认密码",name:"passWord1",type:"password",id:"_password1",compare:{sign:"=",Ele:"_password",text:"两次输入密码必须相同"},allowBlank:false,regex:/^[0-9 | A-Z | a-z]{6,12}$/,regexText:"密码长度必须为6-12位"},
		   	   {field:"电话",name:"phone",type:"text",regex:"phone",regex:/^(\d{3,4}\-?)?\d{7,8}$/,regexText:"请输入正确输入包含13位以内的正确格式电话号码!"},
		   	   {field:"邮箱",id:"em_txt",name:"email",type:"text",regex:"email",minLen:0,maxLen:24},
		   	   {field:"地址",name:"address",type:"text",minLen:0,maxLen:48},
		   	   {field:"单位",id:"unit11",name:"unit",type:"text",minLen:0,maxLen:48},
			   {field:"同时在线数",name:"timeLimit",id:"_user_timeLimit",type:"text",regex:"number",regexText:"必须为正整数",max:122},
			   {field:"ip地址",name:"ip",type:"text",regex:"ip",minLen:0,maxLen:15},
			   {field:"到期日期",name:"expireDate",type:"date",allowBlank:false,format:"yyyy-MM-dd"},
			   {field:"用户卡ID",name:"userCardId",type:"text",minLength:2,maxLength:12},
			   {field:"用户等级",name:"level",type:"text",regex:"number",value:2},
			   {field:"有效状态",id:"vss",name:"validStatus",type:"select",
				   option:[
	               {text:"正常",value:"0"},
		   	   	   {text:"注销",value:"1"}
			   	   ]},
			   {field:"备注",name:"remark",type:"textarea",height:60,minLen:0,maxLen:120}
		]
	}
	],
	button:[
	      {name:"添加",id:"addu",type:"submit",url:"addUser.do",success:"添加成功",fail:"添加失败"},
	       {name:"更新",on:modifyUserInfo},
	       {name:"取消",type:'close'}
	]
});



$("#draggable2").window({
	title:'用户分配角色',
	width:640,
	height:448,
	opt:"mainGrid",
	content:[{
		type:"form",
		rowNum:2,
		items:[
		       {field:"id",name:"id",type:"hidden"},
		       {field:"登陆账号",id:"_loginName",name:"loginName",type:"text",allowBlank:false},
               {field:"用户姓名",id:"_userName",name:"userName",type:"text",allowBlank:false}
		]
	},
	{type:"pannel",id:"p1",field:"分配角色",width:624,height:316}
	],
	button:[
	       {name:"分配角色",on:addUserRoleRelation},
	       {name:"取消",type:'close'}
	]
});

gc();

var pw=$("#p1").width()-2;
var ph=$("#p1").height()-28;

$("#p1").append("<table id='winGrid'></table>");

$("#winGrid").jqGrid({
	width:pw,
	height:ph,
//	url:'../queryAllPermission.do', 
	url:'queryAllRole.do', 
	datatype: "json", 
	altclass:'odd',
	jsonReader:{
	    root:'rows',
	    page: "page",  
	    total: "total",  
	    records: "record",
	    repeatitems : false  
	},
	colNames:['id','角色名称','角色编码','备注'], 
	colModel:[
	{name:'id',index:'id', width:10,hidden:true, search: false,sortable:false},
	{name:'name',index:'_role_name', width:10,hidden:false, search: false,sortable:false},
	{name:'code',index:'_role_code', width:10,hidden:false, search: false,sortable:false},
	{name:'remark',index:'_role_remark', width:10,hidden:false, search: false,sortable:false}
	], 
	multiselect: true,
	rowNum:200,
	sortname: 'id', 
	viewrecords: true,
	sortorder: "asc",
	scroll:true,
	gridComplete:function(){
		if(gridRids!=null){
//			alert(gridRids);
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
//alert(new Date - staDate);
});

$("#draggable3").window({
	title:'用户详细信息',
	width:640,
	height:448,
	opt:"mainGrid",
	content:[{
		type:"form",
		rowNum:2,
		items:[
		       {field:"id",id:"userId",name:"userId",type:"hidden"},
		       {field:"机构ID",name:"orgId",id:"orgId",type:"hidden"},
		       {field:"登陆账号",id:"_loginName2",name:"loginName",type:"text",allowBlank:false},
               {field:"用户姓名",id:"_userName2",name:"userName",type:"text",allowBlank:false}
		]
	},
	{type:"pannel",id:"p2",field:"分配机构",width:624,height:316}
	],
	button:[
	       {name:"分配机构",on:addUserOrgRelation},
	       {name:"取消",type:'close'}
	]
});

var pw=$("#p2").width()-2;
var ph=$("#p2").height()-28;

$("#p2").append("<div style='width:"+pw+"px;height:"+ph+"px;overflow:auto;'><ul id='organizationTree' class='ztree'></ul></div>");

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}
function loadOrganizationTree(id){
	var url = url = "../organization/getAllOrganiztionTree.do?userId="+id;
	var setting = {
		view : {
			showLine : true,
			selectedMulti : false,
			dblClickExpand : false
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
//			chkboxType : { "Y" : "ps", "N" : "s" }
			chkboxType: { "Y": "ps", "N": "ps" }
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
			onCheck:onCheck
		
		}
	};

	$.fn.zTree.init($("#organizationTree"), setting);
}
function onCheck(e,treeId,treeNode){
	 var treeObj=$.fn.zTree.getZTreeObj("organizationTree"),
     nodes=treeObj.getCheckedNodes(true),
     v="";
     for(var i=0;i<nodes.length;i++){
    	 v+=nodes[i].id + ",";
     }
     $("#orgId").val(v);
}

function addUserInfo(){
	var _p1_val=$("#_password").val();
	var _p2_val=$("#_password1").val();
	if(_p1_val!=_p2_val){
		alert("两次输入的密码不相同!");
		return;
	}
	var id="draggable";
	var para=$.getFormDate(id);
	
    if($.validateForm("wcf_"+id)){
 	   $.ajax({type:"post",url:"addUser.do",data:para,async:true,dataType:"json",
				success:function(d){
					if(d.msg!='' && d.msg!=null){
						alert(d.msg);
					}else{
						if(d.success=="0"){
							alert("操作成功!");
							
							$.closeWinAndFlush("draggable","mainGrid");
						}else{
							alert("操作失败!");
						}
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				}	
			});
    }
}

function modifyUserInfo(){
	var id="draggable";
	var para=$.getFormDate(id);
	var _p1_val=$("#_password").val();
	var _p2_val=$("#_password1").val();
	if(_p1_val!=_p2_val){
		alert("两次输入的密码不相同!");
		return;
	}
	
	if(_p1_val==rendomPwd){
		delete para["passWord"];
	}

	if($.validateForm("wcf_"+id)){
	 	   $.ajax({type:"post",url:"modifyUserInfo.do",data:para,async:true,dataType:"json",
				success:function(d){
					if(d.success=="0"){
						alert("操作成功");
						$.closeWinAndFlush("draggable","mainGrid");
					}else{
						alert("操作失败");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					if(XMLHttpRequest.status!="200")
					alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				}	
			});
    }
}

function addUserRoleRelation(){
	
	var ids=$("#mainGrid").jqGrid("getGridParam","selrow");
	var userId = getCookie('userId');
//	alert("userID:"+userId+"  ids:"+ids);
	if(ids == userId){
		alert('不能给自己分配角色!');
		return;
	}else{
		var roleIds = $("#winGrid").jqGrid("getGridParam","selarrrow");
		if(id == userId){
			alert('不能给自己分配角色!');
			return;
		}{
			var id="draggable2";
			var param=$.getFormDate(id);
			
			param['userId'] = ids;
			param['roleId'] = roleIds.toString();
			
			$.ajax({type:"post",url:"addUserRoleRelation.do",data:param,async:false,dataType:"json",
				success:function(d){
					if(d.success=="0"){
						alert("操作成功!");
						$.closeWinAndFlush("draggable2","mainGrid");
					}else{
						alert("操作失败!");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				}	
			});
		}
	}
}

function addUserOrgRelation() {
	var ids=$("#mainGrid").jqGrid("getGridParam","selrow");
	var userId = getCookie('userId');
	//alert("userID:"+userId+"  ids:"+ids);
	if(ids == userId){
		alert('不能给自己分配机构!');
		return;
	}else{
		var id="draggable3";
		var param=$.getFormDate(id);
		$.ajax({type:"post",url:"addUserOrgRelation.do",data:param,async:false,dataType:"json",
			success:function(d){
				if(d.success=="0"){
					alert("操作成功!");
					$.closeWinAndFlush("draggable3","mainGrid");
				}else{
					alert("操作失败!");
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
			}	
		});
	}
}

function roleChecked(userId){
	var id="draggable";
	var param=$.getFormDate(id);
	var rids=null;

	param['userId'] = userId;
	$.ajax({type:"post",url:"queryRoleByUserId.do",data:param,async:false,dataType:"json",
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

function OrgChecked(userId) {
	var id="draggable";
	var param=$.getFormDate(id);
	var oids=null;

	param['userId'] = userId;
	$.ajax({type:"post",url:"queryOrgByUserId.do",data:param,async:false,dataType:"json",
		success:function(d){
			if(d){
				oids=d;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
		}	
	});
	return oids;
}

function updateValidStatus(userId,validStatus){
	var post_data = {"id": userId, "validStatus":validStatus};
	$.ajax({type:"post",url:"modifyUserInfo.do",data:post_data,async:false,dataType:"json",
		success:function(d){
			if(d.success=="0"){
				alert("修改成功!");
				refushGird();
			}else{
				alert("修改失败!");
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
		}	
	});
}

function modifyValidStatusByUserId(userId,validStatus){
	var post_data = {"ids": userId, "validStatus":validStatus};
	$.ajax({type:"post",url:"modifyValidStatusByUserId.do",data:post_data,async:false,dataType:"json",
		success:function(d){
			if(d.success=="0"){
				alert("修改成功!");
				refushGird();
			}else{
				alert("修改失败!");
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
		}	
	});
}

function gc(){
	$("#mainGrid").delegate("a","click",function(){
		var str=this.id.split("_");
		var btn_type=str[0];
		var b_id=str[1];
		if(btn_type=='q'){
			$("#_login_name").attr("disabled","disabled");
			$("#draggable").window("open",{mode:"readOnly",btns:[1,1,0],loadData:"getMoJson.do",param:{"id":b_id}});
			getRandom();
		}else if(btn_type=='d'){
				del(0,b_id);
		}else if(btn_type=='e'){
			$("#_login_name").attr("disabled","disabled");
			$("#draggable").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param:{"id":b_id}});
			getRandom();
		}
	});
}

function getSelectNum(){
	return $("#mainGrid").jqGrid("getGridParam", "selarrrow").length;
}

function getRandom(){
	if(rendomPwd == null){
		 rendomPwd=$.getRandom(1000000000);
	}
	$("#_password").val(rendomPwd);
    $("#_password1").val(rendomPwd);
}

function SelectedCheckbox(wid,rids){
//	alert()
	$("#"+wid).resetSelection();
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

var userInfo=null;
function getCookie(objName){//获取指定名称的cookie的值 
	//alert(document.cookie);
	if(!userInfo){
		$.ajax({type:"post",url:cp+"/sso/getUser.do",data:{},async:false,dataType:"json",
			success:function(ud){
				userInfo=ud;
			}
	    });
	}
	return userInfo[objName];
};





