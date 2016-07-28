var orgids=null;
function del(id){
		if(confirm("确定要删除吗?")){
		$.ajax({type:"post",url:cp+"/employeePhoto/deleteFile.do",data:{"id":id}, async:true,dataType:"json",
			success: function(date){
					if(date.success==0){
						alert("删除成功!");
						refushGird();
					}else{
						alert("删除失败!");
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


var uls=null;
$(function(){
var w=$(document.body).width();
var h=$(window).height();


$("#sb_q").searchBar({
	items:[
//	   	   {field:"组织机构",name:"orgCode", id:"orgCode", type:"select", url: cp+"/organization/optionOrgListJson.do", blankText:"请选择"},
           {field:"组织机构",name:"organName", id:"orgCode", type:"text"},
	   	   {field:"员工编号",name:"employeeNumber",type:"text"},
	   	   {field:"员工姓名",name:"employeeName",type:"text"}
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
	    	$("#orgCode").val("");
	    }}
	]
});

var sbh=$(".searchBar").height();

/*$("#orgCode").replaceWith("<div id='orgCode_div' class='comboBox'></div>");
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
			autoParam : [ "id=pId" ],
			dataFilter : filter
		},
		callback:{
			onClick: onClick
		
		}
	};					
	$.fn.zTree.init($("#organTree"), setting);
}

function onClick(event, treeId, treeNode, clickFlag){
	$("#orgCode_txt").val(treeNode.name);
	$("#orgCode").val(treeNode.name);//向后传
	$("#orgCode_trigger").removeClass("expand");
	$("#orgCode_tri_opt").hide();
}*/

$("#mainGrid").jqGrid({
	width:w-2,
	height:h-83-sbh,
	mtype: 'POST',
	url:cp+'/employeePhoto/PageJson.do', 
	datatype: "json", 
	altclass:'odd',
	multiselect:false,
	jsonReader:{
	    root:'rows',
	    page: "page",
	    total: "total",
	    records: "records",
	    repeatitems : false
	},
	colNames:['id','机构名称','员工姓名','员工编码','头像','防伪币证书','编辑时间'], 
	colModel:[
    {name:'id',index:'id', width:120,hidden:true,},
    {name:'organName',index:'organName', width:120,hidden:false,sortable:false}, 
	{name:'employeeName',index:'employeeName', width:120,hidden:false,sortable:false}, 
	{name:'employeeNumber',index:'employeeNumber', width:60,hidden:false,sortable:false}, 
	{name:'hpImgUrl',index:'hpImgUrl',align:'center',width:80,hidden:false,sortable:false,formatter:function(cv, options, rowObject){
		if(cv){
			return "<button id='"+cv+"'>查看图片</button>";
		}else{
			return "<button>上传图片</button>";
		}
	}},
	{name:'certificateUrl',index:'certificateUrl',align:'center',width:80,hidden:false,sortable:false,formatter:function(cv, options, rowObject){
		if(cv){
			return "<button id='"+cv+"'>查看图片</button>";
		}else{
			return "<button rid='"+rowObject.id+"' >上传图片</button>";
		}
	}},
	{name:'updateTime',index:'updateTime', width:120,hidden:false,sortable:false}
	],
	multiselect:true,
	multiboxonly:true,
	rowNum:15, 
	rowList:[15,25,35], 
	pager: '#pager2', 
	sortname: 'id', 
	viewrecords: true, 
	sortorder: "asc"
}); 


$("#t_mainGrid").toolbar({
items:[
	{
	    name:"添加",type:"button",icon:cp+"/UI/css/icon/add.png",on:function(){
	    	$("#draggable").window("open",{btns:[0,1,0]});
	    }
	},"-", {
		name:"修改",type:"button",icon:cp+"/UI/css/icon/folder_dow.png",on:function(){
			if(getSelectNum()==0){
				alert("请至少选取一行进行下载!");
			}else if(getSelectNum()>0){
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				$("#draggable").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param :{"id" : id}});
			}else{
				alert("请选取一行进行下载!");
			}
			
    }}, "-",
	{
		name:"删除",type:"button",icon:cp+"/UI/css/icon/delete.png",on:function(){
			if(getSelectNum()==0){
				alert("请至少选取一行进行删除!");
			}else if(getSelectNum()>0){
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				del(id);
			}else{
				alert("请选取一行进行删除!");
			}
		}
	}
]
});

$("#mainGrid").on("click","button",function(){
	    if(this.id){
	    	 $("#previewWin").window("open",{btns:[0]});
	   		 $.getEl('pic').src=cp+this.id;
	    }else{
	    	var id=$(this).attr("rid");
			$("#draggable").window("open",{btns:[1,0,0],loadData:"getMoJson.do",param :{"id" : id}});
	    }
		
});
$("#previewWin").window({
	title:'显示图片',
	width:460,
	height:440,
	content:[
	         {type:"pannel",id:"p1",width:446,height:356}
	],
	button:[
	       {name:"关闭",type:'close'}
	],
	openHandler:function(){
		var po=$("#p1");
		 var pw=po.width();
		 var ph=po.height();
		po.replaceWith("<img id='pic' width='"+pw+"' height='"+ph+"'></img>");
	}
});
    $("#p1").parent().css("border","0px");

$("#draggable").window({
	title:'员工相片',
	width:360,
	height:240,
	enterTab:true,
	opt:"mainGrid",
	content:[{
		type:"form",
		rowNum:1,
		items:[
		       {field:"id",name:"id",type:"hidden"},
		       {field:"机构名",id:"organName",name:"organName",type:"text"},
		       {field:"员工姓名",id:"employeeName",name:"employeeName",type:"text"},
		       {field:"员工编号",id:"employeeNumber",name:"employeeNumber",type:"text"},
		       {field:"文件头像",id:"uploadFile",name:"file",type:"file"},
		       {field:"防假币证书文件",id:"uploadFile2",name:"file1",type:"file"}
		]
	}
	],
	button:[
           {name:"提交",id:'submitBtn',on:function(){ajaxFileUpload( cp+'/employeePhoto/uploadFile.do');}},
           {name:"编辑",id:'editBtn',on:function(){ajaxFileUpload(cp+'/employeePhoto/editFile.do');}},
	       {name:"关闭",type:'close'}
	],
	closeHandler:function(){
		uploaded();
		$.flushGrid("mainGrid");
    }
});

var uploadFileObj=$("#uploadFile");
uploadFileObj.attr("type","file");
uploadFileObj.css("border","1px solid #95B8E7");
uploadFileObj.css("width",152+"px");

var uploadFileObj2=$("#uploadFile2");
uploadFileObj2.attr("type","file");
uploadFileObj2.css("border","1px solid #95B8E7");
uploadFileObj2.css("width",152+"px");


$(document).on('change', '#uploadFile', function() {
	 var fname=$("#uploadFile").val();
	 if(fname.indexOf('\\')>-1){
		 var fns=fname.split('\\');
		 if(fns.length>0){
			 fname=fns[fns.length-1];
		 }
	 }
	 $("#fileName").val(fname);
});

$(document).on('change', '#uploadFile2', function() {
	 var fname=$("#uploadFile2").val();
	 if(fname.indexOf('\\')>-1){
		 var fns=fname.split('\\');
		 if(fns.length>0){
			 fname=fns[fns.length-1];
		 }
	 }
	 $("#fileName2").val(fname);
});

/*document.getElementById("ulStauts").readOnly = true;*/
/*uls=$("#ulStauts");*/
    sbbtn=$("#submitBtn");
     function ajaxFileUpload(url) {
    	// uls.val("");
    	/* if(!$("#uploadFile").val() || !$("#uploadFile2").val()){
    		 alert("请选择完文件后再上传文件!");
    		 return;
    	 }*/
    	 uploading();
    	 var formData=$.getFormDate("draggable");
         $.ajaxFileUpload({
             url:url,
             type: 'post',
             data:formData,
             secureuri: false, //一般设置为false
             fileElementId:["uploadFile","uploadFile2"], // 上传文件的id、name属性名
             dataType: 'text', //返回值类型，一般设置为json、application/json
             success: function(d, status){
            	 uploaded();
            	 if(d.success==0 ){
            		 alert("上传成功!");
            		 $.closeWinAndFlush("draggable","mainGrid");
            	 }else if(d.success==6 ){
            		 alert("此文件已存在服务器,无需再次上传!");
             	 }else{
             		uls.val(d.msg);
            	 }
             },
             error: function(data, status, e){
            	 uploaded();
        		// uls.val("error:"+e);
             }
         });
        
     }
     var picFileType = new Array("jpg","bmp","gif","png","jpeg","tiff","pcx");
     var movFileType = new Array("avi","mp4","wmv","3pg","mkv","flv","rm","rmvb");
     for(var i=0;i<movFileType.length;++i){
    		var mov=movFileType[i];
    		if(mov){
    			typeMap[mov]='mov';
    		}
    		var pic=picFileType[i];
    		if(pic){
    			typeMap[pic]='pic';
    		}
    	}
});

var typeMap={};
function getFileType(fname){
	var type=null;
	if(fname){
		var sArray=fname.split('.');
		if(sArray.length>0){
			type=sArray[sArray.length-1];
	    }
    }
	return type;
}
function getMediaType(type){
	return typeMap[type];
};
var dotNum=1;

function showUploading(){
	var str="文件中";
	for(var i=0;i<dotNum;++i){
		str=str+'.';
	}
	//uls.val(str);
    if(dotNum==6){
    	dotNum=0;
	}else{
		++dotNum;
	}
	
}
var sbbtn=null;
function uploading(){
	sbbtn.children().eq(1).html("提交中");
	sbbtn.children().eq(0).removeClass("submitIcon");
	sbbtn.children().eq(0).addClass("loadingIcon");
	sbbtn.attr('disabled',"true");
}
function uploaded(){
	sbbtn.children().eq(1).html("提交");
	sbbtn.children().eq(0).removeClass("loadingIcon");
	sbbtn.children().eq(0).addClass("submitIcon");
	sbbtn.removeAttr("disabled");
}

function getSelectNum(){
	return $("#mainGrid").jqGrid("getGridParam", "selarrrow").length;
}


