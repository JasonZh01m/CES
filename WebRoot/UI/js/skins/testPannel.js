$(function(){
	
	var ww=$(window).width();
	
	var wh=$(window).height();
	
	$("#p0").pannel({
		width:ww,
	    height:80,
	    content:"p0_1"
	});
	
	alert($("#p0").height());
	
	$("#p1").pannel({
		title:"菜单",
		width:240,
	    height:wh-80,
	    content:"p1_1"
	});
	
	$("#p2").pannel({
		title:"操作页面",
		width:ww-240,
	    height:wh-80,
	});
	
});



