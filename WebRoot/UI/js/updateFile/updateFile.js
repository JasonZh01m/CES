var orgids=null;
function del(id){
		if(confirm("确定要删除吗?")){
		$.ajax({type:"post",url:cp+"/updateFile/deleteFile.do",data:{"id":id}, async:true,dataType:"json",
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
	   	   {field:"文件名称",name:"fileName",type:"text"},
	   	   {field:"文件类型",name:"fileType",type:"text"},
	   	   {field:"下载后动作",name:"action",type:"select",option:[{text:"请选择",value:""},{text:"复制",value:"copy"},{text:"执行",value:"run"}]}
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
	url:cp+'/updateFile/PageJson.do', 
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
	colNames:['文件名称','文件版本','文件类型','MD5码','下载链接','保存路径',"下载后动作","文件说明"], 
	colModel:[
	{name:'fileName',index:'fileName', width:120,hidden:false,sortable:false}, 
	{name:'fileVersion',index:'fileVersion', width:60,hidden:false,sortable:false}, 
	{name:'fileType',index:'fileType',align:'center',width:80,hidden:false,sortable:false}, 
	{name:'fileMd5',index:'fileMd5', width:180,hidden:false,sortable:false},
	{name:'filePath',index:'filePath', width:250,hidden:false,sortable:false},
	{name:'downloadPath',index:'downloadPath', width:260,hidden:false,sortable:false},
	{name:'action',index:'action',align:'center',width:80,hidden:false,sortable:false,formatter:function(cv, options, rowObject){
		if(cv=='copy'){
			return '复制';
		}else{
			return '运行';
		}
	}},
	{name:'fileNote',index:'fileNote', width:120,hidden:false,sortable:false}
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
	    name:"上传文件",type:"button",icon:cp+"/UI/css/icon/folder_up.png",on:function(){
	    	$("#draggable").window("open",{btns:[0,0]});
	    }
	},"-", {
		name:"下载文件",type:"button",icon:cp+"/UI/css/icon/folder_dow.png",on:function(){
			if(getSelectNum()==0){
				alert("请至少选取一行进行下载!");
			}else if(getSelectNum()>0){
				var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				var roleData=$("#mainGrid").jqGrid('getRowData',id);
				var url = cp + roleData.filePath;
		    	window.open(url, "文件下载");
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

$("#draggable").window({
	title:'媒体信息',
	width:560,
	height:240,
	enterTab:true,
	opt:"mainGrid",
	content:[{
		type:"form",
		rowNum:2,
		items:[
		       {field:"id",name:"id",type:"hidden"},
		       {field:"文件名称",disabled:true,id:"fileName",name:"fileName",type:"text"},
		       {field:"文件版本",id:"fileVersion",name:"fileVersion",type:"text"},
		       {field:"保存目录",id:"downloadPath",name:"downloadPath",type:"text"},
		       {field:"上传文件",id:"uploadFile",name:"file",type:"text"},
		       {field:"下载后动作",id:"action",name:"action",type:"select",option:[{text:"复制",value:"copy"},{text:"执行",value:"run"}]},
		       {field:"文件用途",id:"type",name:"type",type:"select",option:[{text:"请选择",value:""},{text:"升级文件",value:"1"},{text:"安装文件",value:"2"}]},
		       {field:"文件说明",id:"fileNote",name:"fileNote",type:"textarea"},
		]
	}
	],
	button:[
           {name:"提交",id:'submitBtn',on:ajaxFileUpload},
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

/*document.getElementById("ulStauts").readOnly = true;*/
/*uls=$("#ulStauts");*/
    sbbtn=$("#submitBtn");
     function ajaxFileUpload() {
    	// uls.val("");
    	 if(!$("#uploadFile").val()){
    		 alert("请选择完文件后再上传文件!");
    		 return;
    	 }
    	 if(!$("#draggable select[name=type]").val()) {
    		 alert("请选择文件用途!");
    		 return;
    	 }
    	 uploading();
    	 var formData=$.getFormDate("draggable");
         $.ajaxFileUpload({
             url: cp+'/updateFile/uploadFile.do',
             type: 'post',
             data:formData,
             secureuri: false, //一般设置为false
             fileElementId: 'uploadFile', // 上传文件的id、name属性名
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


