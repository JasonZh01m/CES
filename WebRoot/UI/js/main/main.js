var user_info_obj=null;
$(function(){
//	alert($(window).height);
	$(document.body).layout({
		items:[
        {id:"p_top",
         height:86,
         region:"top"
        },{ 
        	id:"p_menu",
    		title:"系统菜单",
    		width:220,
    	    region:"left",
    	    content:{
    	    	id:"p_menu_1"
    	    }
    	},{
    		id:"p_main",
    		region:"center"
    	}/*,{
    		id:"p_bottom",
    	    height:22,
    	    region:"bottom"
    	}*/
		]
	});
	
	$("#p_menu >p").append("<a id='r_btn' class='u-button u-button-sd'><span>&lt;&lt;</span></a>");
	$("#r_btn").css("font-size","10px");
	$("#r_btn").css("float","right");
	var top_p=$("#p_top");
	top_p.append("<div class='left_logo'></div>");
//	top_p.append("<div>客户评价管理系统</div>");
	top_p.append("<div class='userMenu' id='userMenu'>");
	$("#p_menu_1").append("<ul class='ztree' id='menuTree'/>");
//	$("#p_menu_1").css({"background-image":"url("+cp+"/UI/css/images/bg.jpg"});
//	$("#p_bottom").append("<span id='copyright' class='cr'>版权所有&nbsp;惠州市捷思科技有限公司&nbsp;2015</span>");
	$("#navBottom").css({"top":top_p.height()-5+"px"});
	$("#userMenu").css({right:"0px","margin-top":top_p.height()-31+"px"});
	$("#userMenu").append("<a id='bt_logout'/><span class='u-separator'/><a id='bt_edut_pwd'/><span class='u-separator'/><a id='bt_user'/>");
	$("#p_main").tab({home:{name:"系统主页",url:cp+"/common/welcome.jsp"}});
	top_p.append("<div class='navigationMenu' id='navMenu'>");
	$("#navMenu").css({"width":top_p.width()+"px","margin-top":top_p.height()-31+"px",right:"0px"});
	$("#bt_logout").ui_button({
		name:'退出系统',
		icon:cp+"/UI/css/icon/cross.png",
		handler:function(){
			logout();
		}
	});
	$("#bt_edut_pwd").ui_button({
		name:'修改密码',
		icon:cp+"/UI/css/icon/lock_edit.png",
		handler:function(){
			$("#pwdWin").window("open",{btns:[0,0]});
		}
	});
	
	$("#bt_user").ui_button({
		icon:cp+"/UI/css/icon/user.png",
		name:'用户:****',
		handler:function(){
		if(user_info_obj){
    		alert("姓名:"+user_info_obj.userName+"\n"
	    			+"账号:"+user_info_obj.loginName+"\n"
	    			+"角色:"+user_info_obj.roleName+"\n"
	    			+"IP:"+user_info_obj.ip+"\n"
	    	);
		};
		}
	});
	
	$("#pwdWin").window({
		title:'修改密码',
		width:280,
		height:240,
		enterTab:true,
		content:[{
			type:"form",
			rowNum:1,
			items:[
			       {field:"旧密码",name:"oldPwd",type:"password",id:"_oldPwd",allowBlank:false,regex:/^[0-9 | A-Z | a-z]{6,12}$/,regexText:"密码长度必须为6-12位"},
	               {field:"新密码",name:"passWord",type:"password",id:"_password",allowBlank:false,regex:/^[0-9 | A-Z | a-z]{6,12}$/,regexText:"密码长度必须为6-12位"},
	               {field:"确认密码",name:"passWord1",type:"password",id:"_password1",compare:{sign:"=",Ele:"_password",text:"两次输入密码必须相同"},allowBlank:false,regex:/^[0-9 | A-Z | a-z]{6,12}$/,regexText:"密码长度必须为6-12位"},
			]
		}
		],
		button:[
		      {name:"提交",id:"cpwdb",type:"submit",url:cp+"/login/changePwd.do",success:"修改成功",fail:"修改失败"},
		      {name:"取消",type:'close'}
		]
	});
	
	$.ajax({type:"post",url:cp+"/sso/getUser.do",data:{},async:true,dataType:"json",
		success:function(ud){
			user_info_obj=ud;
		}
    });
	
	$("#navMenu").delegate("a","click",function(){
		var id=this.id;
		if(this.className.indexOf("navBtnfocus")==-1){
			$.addCls(this,"navBtnfocus");
		}
		$("#navMenu a").each(function(){
			if(this.id!=id){
				$(this).removeClass("navBtnfocus");
			}
		});
		listLevel2Menu(this.order);
	});
	
	$("#r_btn").click(function(){
		var btn_inf=$("#r_btn >span").html();
		if(btn_inf=="&lt;&lt;"){
			        $("#r_btn >span").html("&gt;&gt");
					$("#p_menu >p").children().eq(0).hide();
					$("#p_menu").children().eq(0).css("border-bottom","1px solid #e7f0ff");
					$("#p_menu >div").hide();
					ww=$(window).width();
					$("#p_menu").animate({
					      width:25+'px'
				    },"normal");
					$("#p_main").animate({
						 width:ww-32+'px',
					      left:"29px"
				    },"fast");
					
					$("#p_main").queue(function () {
						$("#p_main").tab("resize");
					    $(this).dequeue();
					 });
					$("#p_menu").css("background","#e7f0ff");
		}else{
			        $("#p_menu").css("background","white");
			        $("#r_btn >span").html("&lt;&lt;");
					$("#p_menu").children().show();
					ww=$(window).width();
					
					$("#p_menu").animate({
					      width:217+'px'
				    },"normal");
					
					$("#p_main").animate({
						  left:"221px",
					      width:ww-224+'px'
				    },"fast");
					
					$("#p_main").queue(function () {
						$("#p_main").tab("resize");
					    $(this).dequeue();
					 });
					$("#p_menu >p").children().eq(0).show();
					$("#p_menu").children().eq(0).css("border-bottom","1px solid #95B8E7");
					$("#p_menu >div").show();
		}
	});
	
	var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
	};
	
	function logout(){
	    	if(confirm("您是否将退出此登录?")){
	    		$.ajax({type:"post",url:cp+"/sso/logout.do",data:{},async:true,dataType:"json",
	    			success:function(lo){
	    				if(lo){
	    				window.location.reload();
	    				}
	    			}
	    	    });
	    		return true;
	    	}else {
	    		return false;
	    	}
	 }
	
	function onClick(event, treeId, treeNode, clickFlag){
		if(!treeNode.isParent){
			$("#p_main").tab("open",{name:treeNode.name,url:treeNode.resourceString,closed:true});
		}
	}
	 var permissionData=null;
	 var permissionMenu={}; 
	 var jqNavMenu=$("#navMenu");
	 $.ajax({type:"post",url:cp+"/sso/getAllMenuByRole.do",data:{},async:false,dataType:"json",
			success:function(d){
				permissionData=d;
				var rootId=findRoot(d).id;
				findNavigation(rootId,d);
				var navigation=permissionMenu.root.children;
				var firstBtn=null;
				for(var i=0;i<navigation.length;++i){
					var p=navigation[i];
					var pid=p.id;
					if(i==0){
						jqNavMenu.append("<a id='bt_"+pid+"' class='nagbtncls'/>");
					}else{
						jqNavMenu.append("<span class='u-separator'/><a id='bt_"+pid+"' class='nagbtncls'/>");
					}
					var pbt=$.getEl("bt_"+pid);
					pbt.order=i;
					$("#bt_"+p.id).ui_button({
						name:p.name
					});
					if(i==0){
						firstBtn=pbt;
					}
				}
				if(firstBtn){
					$.addCls(firstBtn,"navBtnfocus");
					listLevel2Menu(firstBtn.order);
					firstBtn.style.marginLeft="5px";
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				if(XMLHttpRequest.readyState>0){
					alert("网络异常,请检查网络或服务器情况!\n错误码:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
				}
				
			}	
	});
	 
	function listLevel2Menu(order){
		var nmp=permissionMenu.root.children[order];
		if(nmp.children.length==0){
			findAllNode(nmp,nmp,permissionData);
		}
		treeObj=$.fn.zTree.init($("#menuTree"), setting, nmp.children);
		treeObj.expandAll(true); 
	 }
	 
	
	 function findRoot(d){
		 for(var i=0;i<d.length;++i){
			 var p=d[i];
			 if(p.parentId===0){
				 p['children']=[];
				 permissionMenu['root']=p;
				 return p;
			 }
		 }
	  }
	 
	 function findNavigation(rootId,d){
		 for(var i=0;i<d.length;++i){
			 var p=d[i];
			 if(p.parentId===rootId){
				 var pmr=permissionMenu['root'];
				 pmr['children'].push(p);
				 p['children']=[];
			 }
		 }
	 }
	 
	 function findAllNode(n,m,permissionData){
		 var nid=m.id;
		 var d=permissionData;
		 for(var i=0;i<d.length;++i){
			 var p=d[i];
			 if(p.parentId===nid){
				 var pmr=n;
				 pmr['children'].push(p);
				 p['children']=[];
				 findAllNode(n,p,permissionData);
			 }
		 }
	 }
	 
    setTimeout("loadUserData()",500);
    $("#p_top a").attr('unselectable','on') 
    .css({'-moz-user-select':'-moz-none', 
    '-moz-user-select':'none', 
    '-o-user-select':'none', 
    '-khtml-user-select':'none', /* you could also put this in a class */ 
    '-webkit-user-select':'none',/* and add the CSS class here instead */ 
    '-ms-user-select':'none', 
    'user-select':'none' 
    }).bind('selectstart', function(){ return false; }); 
});
function onLineHeartBeat(){
	$.ajax({type:"post",url:cp+"/sso/userOnline.do",data:{},async:true,dataType:"json",
		success:function(lo){
			if(lo && lo.success==0){
				setTimeout("onLineHeartBeat()",120000);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(XMLHttpRequest.readyState>0){
				alert("网络异常,请检查网络或服务器情况!\n错误码:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
			}
			
		}
    });
}


function loadUserData(){
	 $("#bt_user >span").each(function(index){
			if(index==0){
				$(this).html("用户:"+user_info_obj.userName);
			}
	 });
}
