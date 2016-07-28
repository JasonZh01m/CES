var permissionId = 0;
function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

$(function() {
	$(document.body).layout({
						// margin:2,
						items : [{
									id : "p1",
									title : "权限列表",
									width : 420,
									region : "left",
									content : {
										id : "p1_1",
										tbr : {
											id : 'p1_tb',
											items : [
													{
														name : "添加",
														type : "button",
														icon : cp
																+ "/UI/css/icon/add.png",
														on : function() {
															loadParentPermissionTree();
															$("#draggable").window("open",{btns : [0,1,0 ]});
															$("#parentId").val('0');
															$("#type").attr("disabled",false);
															$("#sysId").attr("disabled",false);
														}
													},
													"-",
													{
														name : "修改",
														type : "button",
														icon : cp
																+ "/UI/css/icon/application_form_edit.png",
														on : function() {
															permissionId = $("#hiddenSelectPermissionId").val();
															if(permissionId==1){
																alert("系统主页数据不能修改!");
																return;
															}
															if (permissionId) {
																var treeObj=loadParentPermissionTree(permissionId);
																/* $("#draggable").window("open",{btns:[1,0,0],loadData:"getPermissionById.do",param:{"id":permissionId}}); */
																$("#draggable").window("open",
																				{
																					btns : [
																							1,
																							0,
																							0 ],
																					loadData : "getMoJson.do",
																					param : {
																						"id" : permissionId
																					}
																				});
																$("#type").attr("disabled",true);
															} else {
																alert("请选择要修改的一行!");
															}
														}

													},
													"-",
													{
														name : "删除",
														type : "button",
														icon : cp
																+ "/UI/css/icon/delete.png",
														on : function() {
															permissionId = $(
																	"#hiddenSelectPermissionId")
																	.val();
															if (permissionId != null
																	&& permissionId != "") {
																if (confirm("您是确定删除此数据?")) {
																	$
																			.ajax({
																				type : "post",
																				url : "delPermissionJson.do",
																				data : {
																					"permissionId" : permissionId
																				},
																				async : true,
																				dataType : "json",
																				success : function(
																						date) {
																					if (date.success == 0) {
																						alert("删除成功!");
																						reloadPermissionTree();
																					} else if (date.success == 1) {
																						alert("权限被角色使用 ，不能删除!");
																					}
																				}
																			});
																}

															} else {
																alert("请选择要删除的一行!");
															}
														}
													},
													"-",
													{
														name : "刷新",
														type : "button",
														icon : cp
																+ "/UI/css/icon/arrow_refresh.png",
														on : function() {
															reloadPermissionTree();
														}
													}
											]
										}
									}

								}, {
									id : "p2",
									region : "center",
									title : "权限详细信息",
									content : {
										id : "p2_1"
									}
								} ]
					});
	$("#p1_1")
			.html(
					"<div style='overflow:auto;'><ul id='permissionTree' class='ztree'></ul></div>");

	$("#p2_1")
			.html(
					"<div id='sb_q' class='searchBar'><form id='sbf_sb_q'><ul><li style='width: 250px;'><label style='width: 80px;'>权限名称:</label>"
							+ "<input type='text' name='name' disabled></li><li style='width: 250px;'><label style='width: 80px;'>权限编码:</label><input type='text' name='code' disabled></li></ul><ul>"
							+ "<li style='width: 250px;'><label style='width: 80px;'>权限URL:</label><input type='text' name='resourceString' disabled></li><li style='width: 250px;'><label style='width: 80px;'>权限类型:</label>"
							+ "<input type='text' name='type' disabled></li></ul><ul><li style='width: 250px;'><label style='width: 80px;'>系统编号:</label><input type='text' name='sysId' disabled></li><li style='width: 250px;'><label style='width: 80px;'>权限序号:</label><input type='text' name='orderIndex' disabled></li></ul></form></div>");
	$("#sb_q").css("border-bottom", "0px");
	$("#sb_q").height($("#p2_1").height());
	$("#draggable").window({
						title : '权限详细信息编辑',
						width : 540,
						height : 450,
						opt : "mainGrid",
						content : [
								{
									type : "form",
									rowNum : 2,
									items : [

											{
												field : "权限编码",
												name : "code",
												type : "text",
												allowBlank : false
											},
											{
												field : "权限名称",
												name : "name",
												type : "text",
												allowBlank : false
											},
											{
												field : "权限类型",
												id : "type",
												name : "type",
												type : "select",
												option : [ {
													text : "链接",
													value : "0"
												}, {
													text : "按钮",
													value : "1"
												} ]
											},
											{
												field : "权限URL",
												name : "resourceString",
												id : "_resourceString",
												type : "text",
												allowBlank : false,
												validFn : function(name, value) {
													var f = false;
													$.ajax({
																type : "post",
																url : "validateResourceString.do?resourceString="
																		+ $(
																				"#_resourceString")
																				.val()
																		+ "&permissionId="
																		+ permissionId,
																async : false,
																data : {
																	"loginName" : value
																},
																dataType : "json",
																success : function(
																		d) {
																	if (d) {
																		if (d.success === 0) {
																			f = true;
																		} else {
																			f = false;
																		}
																	}
																}
															});
													return f;
												},
												validFnText : "此权限URL已存在!"
											}, {
												field : "权限序号",
												name : "orderIndex",
												type : "text",
												regex : /^\d+$/,
												regexText : "必须为整数"
											},
											{
												field : "上级权限",
												id : "parentId",
												name : "parentId",
												type : "hidden"
											} ]
								}, {
									type : "pannel",
									id : "pp",
									field : "上级权限",
									width : 524,
									height : 270
								} ],
						button : [ {
							name : "添加",
							on : function() {
								 if($("#parentId").val()>0){
								      saveLayer(); /* validateResourceString(); */
								 }else{
									  alert("请在菜单下面选择上级权限!");
									  return false;
								  }
							}
						}, {
							name : "更新",
							on : function() {
								updateLayer();
							}
						}, {
							name : "取消",
							type : 'close'
						} ]
					});

	var pw = $("#pp").width();
	var ph = $("#pp").height();

	$("#pp").append("<div style='width:"+ pw+ "px;height:"+ ph+ "px;overflow:auto;'><ul id='parentPermissionTree' class='ztree'></ul></div>");

	$("#fileUpload").window({
		title : '权限导入',
		width : 640,
		height : 208,
		opt : "mainGrid",
		content : [ {
			type : "pannel",
			id : "fileUploadField",
			field : ""
		} ],
		button : [ {
			name : "导入",
			on : function() {
				importFile();
			}
		}, {
			name : "更新",
			on : function() {
			}
		}, {
			name : "取消",
			type : 'close'
		} ]
	});

	loadPermissionTree();

	$("#fileUploadField").append("<div><input type=\"file\" id=\"myfiles\" name=\"myfiles\"/></div>");

	$(window).resize(function() {
		$("#p2").css("width", $(window).width() - $("#p1").width() + "px");

	});

});

var urlP = "../permission/getParentPermissionTree.do";
var settingP = {
	view : {
		showLine : true,
		selectedMulti : false,
		dblClickExpand : false
	},
	check : {
		enable : true,
		chkStyle : "radio",
		radioType : "all"
	},
	data : {
		simpleData : {
			enable : true,
			idKey: "id"
		}
	},
	async : {
		enable : true,
		url : urlP,
		autoParam : [ "id=pId" ]
	},
	callback : {
		onCheck : onParentCheck,
		onNodeCreated : function(event, treeId, treeNode, msg) {
				setTimeout("selectedNode()",80);
		}
	}

};
var ii=0;
function selectedNode(){
	ii++;
	try{
	var pid=$("#parentId").val();
	var id=$("#hiddenSelectPermissionId").val();
	console.log(pid);
	var node = treeObjP.getNodeByParam("id", pid);
	if(node){
		treeObjP.selectNode(node,true);
		console.log(node['tId']);
		var nco=$("#"+node['tId']+"_check");
		if(nco.get(0).className!="button chk radio_true_full"){
			nco.addClass("button chk radio_true_full");
		}
		var nid = treeObjP.getNodeByParam("id", id);
		if(nid){
			treeObjP.removeNode(nid,false);	
		}
	}
	
	}catch(e){
		alert(e);
		alert(ii);
	}
}

var treeObjP =$.fn.zTree.init($("#parentPermissionTree"), settingP);

function loadParentPermissionTree(permissionId) {
	treeObjP=$.fn.zTree.init($("#parentPermissionTree"), settingP);
}


function onParentCheck(e, treeId, treeNode) {
	if (treeNode.checked) {
		$("#parentId").val(treeNode.id);
	}
}

function importFile() {
	$.ajaxFileUpload({
		url : 'importFile.do', // 用于文件上传的服务器端请求地址
		secureuri : false, // 是否需要安全协议，一般设置为false
		fileElementId : 'myfiles', // 文件上传域的ID
		dataType : 'json', // 返回值类型 一般设置为json
		success : function(data, status) // 服务器成功响应处理函数
		{
			if (data.success == '0') {
				alert("权限导入成功！");
				reloadPermissionTree();
				$("#fileUpload").window("close");
			} else {
				alert("权限导入失败！");
			}

		}
	});
	return false;
}

function onClick(event, treeId, treeNode, clickFlag) {
	$.ajax({
		type : "post",
		url : "getMoJson.do?id=" + treeNode.id,
		async : false,
		dataType : "json",
		success : function(date) {

			$("#p2_1").find("input[name=name]").val(date.name);
			$("#p2_1").find("input[name=code]").val(date.code);
			$("#p2_1").find("input[name=resourceString]").val(
					date.resourceString);
			$("#p2_1").find("input[name=type]").val(date.type);
			if (date.type == "0") {
				$("#p2_1").find("input[name=type]").val("链接");
			} else {
				$("#p2_1").find("input[name=type]").val("按钮");
			}
			$("#p2_1").find("input[name=orderIndex]").val(date.orderIndex);
		}
	});
	$("#hiddenSelectPermissionId").val(treeNode.id);
}
function reloadPermissionTree() {
	loadPermissionTree();

	$("input[name=name]").val('');
	$("input[name=code]").val('');
	$("input[name=resourceString]").val('');
	$("input[name=type]").val('');
	$("input[name=sysId]").val('');
	$("input[name=orderIndex]").val('');
	$("#hiddenSelectPermissionId").val('');
}

function loadPermissionTree(roleId) {
	var url = "../permission/getPermissionTree.do";
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
		callback : {
			onClick : onClick
		}
	};
	var PtreeObj = $.fn.zTree.init($("#permissionTree"), setting);
	PtreeObj.expandAll(true);
}

function verificationLayer() {
	var result = true;
	var code = $("#draggable input[name='code']").val();
	if (code == null || code == "") {
		$("#draggable input[name='code']").focus();
		$("#draggable input[name='code']").blur();
		result = false;
	}
	var name = $("#draggable input[name='name']").val();
	if (name == null || name == "") {
		$("#draggable input[name='name']").focus();
		$("#draggable input[name='name']").blur();
		result = false;
	}
	var resourceString = $("#draggable input[name='resourceString']").val();
	if (resourceString == null || resourceString == "") {
		$("#draggable input[name='resourceString']").focus();
		$("#draggable input[name='resourceString']").blur();
		result = false;
	}
	return result;
}

function saveLayer() {
	verificationLayer();
	var para = {};
	$('#draggable *[name]').each(function() {
		id = this.name;
		value =this.value;
		if (value != '') {
			para[id] = value;
		}
    });
	if ($.validateForm("draggable")) {
		$.ajax({
			type : "post",
			url : "addPermission.do",
			data : para,
			async : false,
			dataType : "json",
			success : function(date) {
				if (date.success == 0) {
					alert("新增成功!");
					reloadPermissionTree();
					$("#draggable").window("close");

				} else {
					alert("新增失败!");
				}
			}
		});
	}
}

function updateLayer() {
	verificationLayer();
	var para = {};
	$('#draggable *[name]').each(function() {
		id = this.name;
		value =this.value;
		if (value != '') {
			para[id] = value;
		}
    });
	para['id'] = $("#hiddenSelectPermissionId").val();

	if ($.validateForm("draggable")) {
		$.ajax({
			type : "post",
			url : "updatePermission.do",
			data : para,
			async : false,
			dataType : "json",
			success : function(date) {
				if (date.success == 0) {
					alert("更新成功！");
					reloadPermissionTree();
					$("#draggable").window("close");

				} else {
					alert("更新失败，请检查数据格式！");
				}
			}
		});
	}
}
function saveFace(url) {
	if (window.fileExport && url) {
		window.fileExport.location = url;
	}
}
