<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
input{border:1px solid #ff9900;}
div {width:300px;position:relative;}
.file {position:absolute;top:20px;right:300px;top:0;
right:73px;width:0px;height:30px;filter:alpha(opacity=0);
-moz-opacity:0;opacity:0;}
#txt {height:20px;margin:1px}
#ii {width:70px;height:24px;margin-top:1px;
margin-top:2px;_margin-top:2px;}
</style>
</head>

<body>
<input style="width:320px;" type="file" id="file" name="file"><br/>
<input type="hidden" id="flag" name="flag" value="ajax文件上传"/>
<input type="button" id="btnUpload" value="upload" />
</body>
<script type="text/javascript" src="UI/js/jq-1.9.1-min.js"></script>
<script type="text/javascript" src="UI/js/ajaxfileupload.js" ></script>
<script type="text/javascript">
$(function(){
	$("#btnUpload").click(function() {
        ajaxFileUpload();
   });
	
	
	function ajaxFileUpload() {
        // 开始上传文件时显示一个图片
        $("#wait_loading").ajaxStart(function() {
            $(this).show();
        // 文件上传完成将图片隐藏起来
        }).ajaxComplete(function() {
            $(this).hide();
        });
        $.ajaxFileUpload({
            url: 'media/uploadFile.do',
            data:{"name":"图片","type":"jpg"},
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: 'file', // 上传文件的id、name属性名
            dataType: 'text', //返回值类型，一般设置为json、application/json
            //elementIds: elementIds, //传递参数到服务器
            success: function(data, status){  
                alert("result:"+data.success);
            },
            error: function(data, status, e){ 
                alert("error:"+e);
            }
        });
        //return false;
    }
});
</script>
</html>
