$(function(){
var w=$(document.body).width();
var h=$(window).height();
$("#sb_q").searchBar({
	items:[
	       {field:"组织机构",name:"orgCode",id:"orgCode",type:"select", url: cp+"/organization/optionOrgListJson.do", blankText:"请选择"},
	       {field:"开始日期",id:"startTime",name:"startTime",type:"text"},
	   	   {field:"结束日期",id:"endTime",name:"endTime",type:"text"},
	   	   {field:"业务类型",id:'bCode',type:"text", readOnly:true}
	],
	button:[
	    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
	       if($("#startTime").val() && !$("#endTime").val()){
	    	   alert("请输入结束时间");
	    	   return;
	       }
	       if(!$("#startTime").val() && $("#endTime").val()){
	    	   alert("请输入开始时间");
	    	   return;
	       }
	       var param=$.getFormDate('sb_q');
	       var bcv=$("#hide_bc").val();
	       if(bcv){
	    	   param['serviceCode']=bcv;
	       }
	       $.clearGridPostParam("mainGrid");
	       $("#mainGrid").setGridParam({  
	  	        datatype:'json',  
	  	        postData:param,
	  	        page:1
	  	    }).trigger("reloadGrid");
	    }},
	    {name:"重置",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
	    	$("#sb_q").searchBar("clearForm");
	    	$("#orgCode").val("");
	    	serviceCodes={};
	    	$("#hide_bc").val("");
	    }},
	    {name:"导出Excel", type:"button", icon:cp+"/UI/css/icon/folder_up.png", on:function(){
	    	if(getCookie("userId")!=undefined || getCookie("userId")!='') {
		    	var param=$.getFormDate('sb_q');
		    	var startTime = param.startTime==undefined?'' : param.startTime;
		    	var endTime =  param.endTime==undefined?'' : param.endTime;
		    	var orgCode =  param.orgCode==undefined?'' : param.orgCode;
		    	var url = cp+"/report/satisOrgDescByExcelJson.do?startTime="+startTime+"&endTime="+endTime+"&orgCode="+orgCode
		    	window.open(url, "机构满意排行报表");
	    	} else {
	    		alert("用户超时，请点击退出系统重新登录再做操作！");
	    		return;
	    	}
	    }}
	]
});


$("#orgCode").replaceWith("<div id='orgCode_div' class='comboBox'></div>");
$("#orgCode_div").append("<input id='orgCode_txt' type='text' readOnly=true class='text'></input><input id='orgCode' name='orgCode' type='hidden'></input>");
$("#orgCode_div").append("<img id='orgCode_trigger' class='trigger' src="+cp+"/UI/css/images/s.gif></img>");
$(document.body).append("<div id='orgCode_tri_opt' class='cb_option'></div>");
$("#orgCode_tri_opt").append("<ul id='organTree' class='ztree'></ul>");
$("#orgCode_tri_opt").hide();

var odo=$("#orgCode_div");
var oto=$("#orgCode_tri_opt");
var octo=$("#orgCode_trigger");
$("#orgCode_div").click(function(event){
	if(octo.hasClass("expand")){
		octo.removeClass("expand");
		oto.hide();
	}else{
		octo.addClass("expand");
		var top=odo.offset().top+21;
		var left=odo.offset().left;
		oto.css({"top":top+"px","left":left+"px"});
		oto.show();
		loadOrganizationTree();
		oto.focus();
	}
	 return false;
}); 

$("#orgCode_tri_opt").click(function(event){
   return false;
});

$(document).click(function(event){
	if(oto.is(":hidden")){
		return;
	}
	octo.removeClass("expand");
	oto.hide();
});

$("#bCode").click(function(){
	$(this).focus();
	$("#serviceWin").window("open",{btns:[0,0]});
	$("#serviceGrid").trigger("reloadGrid");
	serviceCodes={};
	serviceNames="";
	$("#snc_txt").val("");
	$("#sn_q_txt").val("");
	//return false;
});
$(document.body).append("<div id='serviceWin'></div>");
$(document.body).append("<inpu id='hide_bc' name='businessCode' type='hidden'></input>");
$("#serviceWin").window({
	title:'选择业务',
	width:510,
	height:450,
	content:[{
		type:"form",
		rowNum:2,
		items:[
               {field:"已选业务",id:"snc_txt",type:"text",readOnly:true}
		]
	},
	{type:"pannel",id:"p1",width:494,height:323,}
	],
	button:[
           {name:"确认",on:function(){
        	   $("#bCode").val(serviceNames);
        	   var codeStr="";
        	   var i=0;
        	   for(var p in serviceCodes){
        		   if(i==0){
        			   codeStr+=p; 
        		   }else{
        			   codeStr+=","+p; 
        		   }
        		   ++i;
        	   }
        	   $("#hide_bc").val(codeStr);
        	   $("#serviceWin").window("close");
           }},
	       {name:"关闭",type:'close'}
	],
	openHandler:function(){
		 for(var p in serviceCodes){
  			var id=serviceCodes[p];
  			$("#serviceGrid").jqGrid('setSelection',id);
  	   }
	},
	closeHandler:function(){
		serviceNames="";
		snnum=0;
		$("#serviceGrid").jqGrid('setGridParam',{datatype:'json',postData:{},page:1}).trigger("reloadGrid");
    }
});

$("#wct_serviceWin input").css("width","388px");
$("#p1").append("<div id='sb_win'></div><table id='serviceGrid'></table>");
$("#sb_win").searchBar({
	items:[
	   	   {field:"业务名称",id:'sn_q_txt',name:"serviceName",type:"text"}
	],
	button:[
	    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
	       var param={"name":$("#sn_q_txt").val()};
	       $.clearGridPostParam("serviceGrid");
	       $("#serviceGrid").setGridParam({  
	  	        datatype:'json',  
	  	        postData:param,
	  	        page:1
	  	    }).trigger("reloadGrid");
	    },
	    },
	    {name:"清空选中",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
			for(var p in serviceCodes){
	      			var id=serviceCodes[p];
	      			$("#serviceGrid").setSelection(id,true);
	      	}
			serviceNames="";
			snnum=0;
			serviceCodes={};
			$("#snc_txt").val("");
	    }
	    }
	]
});
//$("#orgCode_div").parent().css("width","218px");

$("#sbf_sb_win ul").css("padding","2px");
$("#sbf_sb_win").css("height","32px");
$("#sbf_sb_win").css("margin","2px");
$("#sb_win").css("padding","2px 0px");
$("#sb_win").css("height","42px");
$("#sb_win").css("background","#FFFFFF");
$("#sb_win").css("border","1px");
var sgw=$("#p1").width();
var sgh=$("#p1").height();
$("#serviceGrid").jqGrid({
	width:sgw-2,
	height:sgh-72,
	url:'/CES/business/queryBusiness.do',
	mtype: 'POST',
	datatype: "json", 
	altclass:'odd',
	jsonReader:{
	    root:'rows',
	    page: "page",  
	    total: "total",  
	    records: "record",
	    repeatitems : false
	},
	colNames:['id','业务名称','业务编码'], 
	colModel:[
	{name:'id',index:'id', width:10,hidden:true, search: false,sortable:false},
	{name:'name',index:'_role_name', width:10,hidden:false, search: false,sortable:false},
	{name:'code',index:'_role_code', width:10,hidden:false, search: false,sortable:false}
	], 
	multiselect: true,
	rowNum:2000,
	sortname: 'id', 
	viewrecords: true,
	sortorder: "asc",
	scroll:true,
	onSelectRow: function(rowid,status){ 
		 setVal(rowid,status);
	}
});

$("#serviceGrid").delegate("input:checkbox","click",function(){
	var rwnum=$(this).parent().parent().get(0).id;
	if(this.checked){
		$("#serviceGrid").setSelection(rwnum,true);
	}else{
		$("#serviceGrid").setSelection(rwnum,false);
	}
});

var serviceNames="";
var serviceCodes={};
var snnum=0;
function setVal(rowid,status){
	var rowData = $("#serviceGrid").jqGrid("getRowData",rowid);
	if(snnum>10){
		alert("选取的业务不能超过10项,请取消部分选中业务后再试!");
		return;
	}
	if(status){
		if(!serviceCodes[rowData.code]){
			if(snnum>0){
				serviceNames+=","+rowData.name;
				serviceCodes[rowData.code]=rowid;
			}else{
				serviceNames+=rowData.name;
				serviceCodes[rowData.code]=rowid;
			}
			++snnum;
		}
	}else{
		if(serviceCodes[rowData.code]){
			var snTemp="";
			var snArr=serviceNames.split(",");
			var f=false;
			for(var i=0;i<snArr.length;++i){
				var sn=snArr[i];
				if(i==0){
					if(sn!=rowData.name){
						snTemp+=sn;
					}else{
						f=true;
					}
				}else{
					if(f && i==1){
						snTemp+=sn;
					}else{
						if(sn!=rowData.name){
						   snTemp+=","+sn;
						}
					}
					
				}
			}
			serviceNames=snTemp;
			delete serviceCodes[rowData.code];
			--snnum;
		}
	}
    $("#snc_txt").val(serviceNames);
}

function loadOrganizationTree(){
	var url = "/CES/organization/getAllOrganiztionTree.do";
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

var sbh=$(".searchBar").height();
$('#startTime').calendar({onSetDate:function(){
	$('#endTime').calendar({minDate:'#startTime'});
	
	var ev=$('#endTime').val();
	if(ev){
		var d1= new Date(Date.parse(this.getDate('date').replace(/-/g,  "/")));
		var dates=d1.getTime();
		var d2= new Date(Date.parse(ev.replace(/-/g,  "/")));
		var datee=d2.getTime();
		if(datee<dates){
			$('#endTime').val("");
		}
	}
	
}});
$('#endTime').calendar();



$("#mainGrid").jqGrid({
	width:w-2,
	height:h-53-sbh,
	mtype: 'POST',
	url:cp+'/report/satisfactionByOrgJson.do', 
	datatype: "json", 
	altclass:'odd',
	jsonReader:{
	    root:'rows',
	    page: "page",
	    total: "total",
	    records: "records",
	    repeatitems : false
	},
	colNames:['机构名','非常满意次数','一般满意次数','不满意次数','平均满意度','业务量'], 
	colModel:[
		{name:'orgName',index:'orgName', width:80,search: false,hidden:false,sortable:false},
		{name:'point1',index:'point1', width:80,hidden:false,sortable:false}, 
		{name:'point3',index:'point3', width:80,hidden:false,sortable:false}, 
		{name:'point5',index:'point5', width:80,hidden:false,sortable:false}, 
		{name:'agvsSatis',index:'agvsSatis', width:80,hidden:false,sortable:false}, 
		{name:'sumCount',index:'sumCount', width:80,hidden:false,sortable:false}
	],
	multiselect: false,
	rowNum:15, 
	rowList:[15,25,35], 
	pager: '#pager2', 
	sortname: 'orgName', 
	viewrecords: true, 
	sortorder: "asc"
}); 

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
});

