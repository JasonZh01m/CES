//	 var staDate = new Date();
var orgids=null;
function del(id){
		if(confirm("确定要删除吗?")){
		$.ajax({type:"post",url:cp+"/media/deleteFile.do",data:{"id":id}, async:true,dataType:"json",
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
	   	   {field:"媒体名称",name:"name",type:"text"},
	   	   {field:"媒体类型",name:"type",type:"text"}
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
	url:cp+'/media/PageJson.do', 
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
	colNames:['id','媒体名称','媒体类型','MD5码','下载路径','上传人','上传时间'], 
	colModel:[
    {name:'id',index:'id',hidden:true}, 
	{name:'name',index:'name', width:280,hidden:false,sortable:false}, 
	{name:'type',index:'type', width:100,hidden:false,sortable:false}, 
	{name:'md5Code',index:'md5Code', width:180,hidden:false,sortable:false}, 
	{name:'downloadPath',index:'downloadPath', width:220,hidden:false,sortable:false},
	{name:'createUserName',index:'createUserName', width:100,hidden:false,sortable:false},
	{name:'createTime',index:'createTime', width:100,hidden:false,sortable:false}
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
	/* {
	   name:"上传文件",type:"button",icon:cp+"/UI/css/icon/folder_up.png",on:function(){
	    	$("#draggable").window("open",{btns:[0,0]});
	    }
	},"-",*/
	{
		    name:"文件上传",type:"button",icon:cp+"/UI/css/icon/folder_up.png",on:function(){
		    	$("#mfileWin").window("open",{btns:[0,0]});
		    }
		},"-",
	{
		name:"删除",type:"button",icon:cp+"/UI/css/icon/delete.png",on:function(){
			if(getSelectNum()==0){
				alert("请至少选取一行进行删除!");
			}else if(getSelectNum()>0){
				var id="";
				var rowData = $("#mainGrid").jqGrid("getGridParam","selarrrow");
			    if(rowData.length) 
			    {
			    	id=$("#mainGrid").jqGrid("getCell",rowData[0],"id");
			        for(var i=1;i<rowData.length;i++)
			        {
			           id+=","+ $("#mainGrid").jqGrid("getCell",rowData[i],"id");//name是colModel中的一属性
			        }
			    }
				del(id);
			}else{
				alert("请选取一行进行删除!");
			}
		}
	},"-",
	{
		name:"预览",type:"button",icon:cp+"/UI/css/icon/eye.png",on:function(){
			if(getSelectNum()>1){
				alert("只能选一行进行预览!");
				$("#mainGrid").resetSelection(); 
			}else if(getSelectNum()==0){
				alert("请选取要修改的一行进行预览!");
			}else{
				 var id=$("#mainGrid").jqGrid("getGridParam","selrow");
				 var rowData=$("#mainGrid").jqGrid("getRowData",id);
				 var fname=rowData.name;
				 var fPath=rowData.md5Code;
				 if(fname && fPath){
					 var type=getFileType(fname);
					 var mtype=getMediaType(type);
					 var po=$("#p1");
	    			 var pw=$("#p1").width();
	    			 var ph=$("#p1").height();
	    			 $("#previewWin").window("open",{btns:[0]});
			    	 if(mtype=='mov'){
			    		 if (!!document.createElement('video').canPlayType) {
			    			 po.replaceWith("<video id='mov' width='"+pw+"' height='"+ph+"' controls='controls'></video>");
			    		 }else{
			    			 po.replaceWith("<object id='mov' width='"+pw+"' height='"+ph+"' units='pixels'></object>"); 
			    		 }
			    		 $.getEl('mov').src=cp+"/downLoad/getMedia.do?code="+fPath;
			    	 }else if(mtype=='pic'){
			    		 $("#p1").replaceWith("<img id='pic' width='"+pw+"' height='"+ph+"'></img>");
			    		 $.getEl('pic').src=cp+"/downLoad/getMedia.do?code="+fPath;
					}
			    	 
			    	}
			}
		}
	}
]
});


$("#mfileWin").window({
	title:'多文件上传',
	width:400,
	height:440,
	content:[
	         {type:"pannel",id:"p2",width:382,height:46, region:"top",field:"点击下方按钮,一次可上传最多文件10个"},
	         {type:"pannel",id:"p3",width:382,height:310,region:"bottom",field:"上传文件列表"}
	],
	button:[
           {name:"提交",on:function(){
        	   $('#upload_f').uploadify('upload','*');
           }},
	       {name:"关闭",type:'close'}
	],
	closeHandler:function(){
		$("#upload_f").uploadify("cancel", "*");
    }
});


$("#p2").append("<input id='upload_f' type='button' value='本地上传'></input>");
var limitNum=10;
var limitSize="500MB";
$("#upload_f").uploadify({
    'debug' : false,
    'auto':false,
    'successTimeout':99999,
    'swf': cp+"/UI/uploadify/uploadify.swf",
    'overrideEvents' : ['onDialogClose'],
    'queueID':'p3',
    'fileObjName':'file',
    'uploader':cp+"/media/uploadFile.do",
    'buttonText':'文件上传',
    removeCompleted:false,
    'width':'56',
    'height':'22',
    'fileTypeDesc':'支持的格式：',
    'fileTypeExts':"*.jpg;*.bmp;*.gif;*.png;*.jpeg;*.tiff;*.pcx;*.avi;*.mp4;*.wmv;*.3pg;*.mkv;*.flv;*.rm;*.rmvb;*.JPG;*.BMP;*.GIF;*.PNG;*.JPEG;*.TIFF;*.PCX;*.AVI;*.MP4;*.WMV;*.3PG;*.MTK;*.FLV;*.RM;*.RMVB",
    'fileSizeLimit':limitSize,
    'queueSizeLimit' : limitNum,
    //每次更新上载的文件的进展
    'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
         //有时候上传进度什么想自己个性化控制，可以利用这个方法
         //使用方法见官方说明
    },
    //选择上传文件后调用
    'onSelect' : function(file) {
    },
    //返回一个错误，选择文件的时候触发
    'onSelectError':function(file, errorCode, errorMsg){
        switch(errorCode) {
            case -100:
                alert("上传的文件数量已经超出系统限制的"+limitNum+"个文件！");
                break;
            case -110:
                alert("文件 ["+file.name+"] 大小超出系统限制的"+limitSize+"大小！");
                break;
            case -120:
                alert("文件 ["+file.name+"] 大小异常！");
                break;
            case -130:
                alert("文件 ["+file.name+"] 类型不正确！");
                break;
        }
    },
    //检测FLASH失败调用
    'onFallback':function(){
        alert("您未安装FLASH控件或版本过期,请安装FLASH控件后再试!");
    },
   /* //上传到服务器，服务器返回相应信息到data里
    'onUploadSuccess':function(file, data, response){
        //alert("文件:"+file.name+",上传成功!");
    },*/
    onQueueComplete:function(qd){
    	if(qd.uploadsSuccessful>0&&qd.uploadsErrored==0){
    		alert("所有文件上传成功!");
    	}else{
    		alert("文件上传成功:"+qd.uploadsSuccessful+"个, 失败:"+qd.uploadsErrored+"个!");
    	}
    	$.flushGrid("mainGrid");
    }
});

//$("#upload_photo").css("float","left");

$("#draggable").window({
	title:'媒体信息',
	width:320,
	height:180,
	enterTab:true,
	opt:"mainGrid",
	content:[{
		type:"form",
		rowNum:1,
		items:[
		       /*{field:"id",name:"id",type:"hidden"},
		       {field:"媒体名称",readOnly:true,id:"name",name:"name",type:"text"},
		       {field:"媒体类型",id:"type",readOnly:true,name:"type",type:"text"},
		       {field:"MD5码",id:"md5Code",readOnly:true,name:"md5Code",type:"text"},
		       {field:"下载路径",readOnly:true,id:"downloadPath",name:"downloadPath",type:"text"},*/
		       {field:"上传文件",id:"uploadFile",name:"file",type:"text"},
		       {field:"上传状态",id:"ulStauts",name:"ulStauts",type:"textarea"}
		     /*  {field:"tempMd5",readOnly:true,name:"tempMd5",type:"hidden"}*/
		]
	}
	],
	button:[
           {name:"提交",on:ajaxFileUpload},
	       {name:"关闭",type:'close'}
	],
	closeHandler:function(){
		uploaded();
		$.flushGrid("mainGrid");
    }
	
});

document.getElementById("ulStauts").readOnly = true;
uls=$("#ulStauts");
$("#previewWin").window({
	title:'媒体信息',
	width:460,
	height:440,
	content:[
	         {type:"pannel",id:"p1",width:442,height:356}
	],
	button:[
	       {name:"关闭",type:'close'}
	]
});
    $("#p1").parent().css("border","0px");
     $("#uploadFile").attr("type","file");
     $("#uploadFile").css("border","1px solid #95B8E7");
     $("#uploadFile").css("width",152+"px");
     
     
     function ajaxFileUpload() {
    	 uls.val("");
    	 var fname=$("#uploadFile").val();
    	 if(fname.indexOf('\\')>-1){
    		 var fns=fname.split('\\');
    		 if(fns.length>0){
    			 fname=fns[fns.length-1];
    		 }
    	 }
    	 var type=getFileType(fname);
    	 if(!getMediaType(type)){
    		 alert("只能上传图片或视频格式");
    		 $("#uploadFile").val("");
    		 return;
    	 }
    	 uploading();
         $.ajaxFileUpload({
             url: cp+'/media/uploadFile.do',
             type: 'post',
             secureuri: false, //一般设置为false
             fileElementId: 'uploadFile', // 上传文件的id、name属性名
             dataType: 'text', //返回值类型，一般设置为json、application/json
             success: function(d, status){
            	 uploaded();
            	 if(d.success==0 ){
                    alert("上传成功");
                    $.closeWinAndFlush("draggable","mainGrid");
            	 }else if(d.success==6 ){
            		 alert("此文件已存在服务器,无需再次上传!");
             	 }else{
             		uls.val(d.msg);
            	 }
             },
             error: function(data, status, e){
            	 uploaded();
        		 uls.val("error:"+e);
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
			type=sArray[sArray.length-1].toLowerCase();
	    }
    }
	return type;
}
function getMediaType(type){
	return typeMap[type];
};
var dotNum=1;

function showUploading(){
	var str="文件上传中,请稍后";
	for(var i=0;i<dotNum;++i){
		str=str+'.';
	}
	uls.val(str);
    if(dotNum==6){
    	dotNum=0;
	}else{
		++dotNum;
	}
	
}
var interval=null;
function uploading(){
	interval=setInterval("showUploading()",240);
}
function uploaded(){
	if(interval){
		clearInterval(interval);
		interval=null;
	}
}

function getSelectNum(){
	return $("#mainGrid").jqGrid("getGridParam", "selarrrow").length;
}


