(function ($) {
	$.fn.toolbar = function (options) {
		if($.isJson(options)){
			var o=$(this);
			var id=o.attr("id");
			var tbro=o.get(0);
			$.addCls(tbro,"u-tbr");
			var _items=options.items;
			var ajaxFlag=false;
			for(var i=0;i<_items.length;++i){
				var item=_items[i];
				if(item.code){
					ajaxFlag=true;
					break;
				}
			}
			var uri=window.location.pathname;
			 if(ajaxFlag){
				 $.ajax({type:"post",url:"getOperate.do",data:{"uri":uri},async:true,dataType:"json",
						success:function(d){
							if(_items && _items.length>0){
								createHtml(o,_items,d);
							}
							$.initWidgetBtn(id,_items);
						}
				});
			 }else{
				 if(_items && _items.length>0){
						createHtml(o,_items,"null");
				 }
				 $.initWidgetBtn(id,_items);
			 }
		}
	};
	
	function getCookie(objName){//获取指定名称的cookie的值 
		var arrStr = document.cookie.split("; "); 
		for(var i = 0;i < arrStr.length;i ++){ 
		var temp = arrStr[i].split("="); 
		if(temp[0] == objName) return unescape(temp[1]); 
		} 
	} 

	
	var createHtml=function(o,_items,pids){
		var of = document.createDocumentFragment();
		var f=true;
		var parr=[];
		if(pids){
			parr=pids.split(",");
		}
		
		for(var i=0;i<_items.length;++i){
			var item=_items[i];
			var bo=null;
			if(item=="-"){
				if(f){
					bo=createSeparator();
					if(i>0)
					f=false;
				}
			}else if(!item.type || item.type=="button"){
				//alert(item.code+"->"+pids.indexOf(item.code));
				if(!item.code){
					f=true;
					bo=createBtn(item);
					item.id=bo.id;
					if(item.on){
						regClickFunc(bo,item.on);
					}
				}else if(item.code && isHasBtn(parr,item.code)){
					bo=createBtn(item);
					item.id=bo.id;
					f=true;
					if(item.on){
						regClickFunc(bo,item.on);
					}
				}
					
			}
			
			if(bo){
				of.appendChild(bo);	
			}
			
		}
		o.get(0).appendChild(of);
		initAllBtn(_items);
		var nextNode=o.next().get(0);
		if(nextNode){
			if(nextNode.className.indexOf("ui-jqgrid")>-1){
				o.css("border-bottom","0px");
			}
		}
	};
	
	var isHasBtn=function(pArr,code){
		var f=false;
		if(pArr && pArr.length>0){
			for(var i=0;i<pArr.length;++i){
				if(code==pArr[i]){
					f=true;
					break;
				}
			}
			
		}
		return f;
	};
	
	
	var initAllBtn=function(_items){
		for(var i=0;i<_items.length;++i){
			var item=_items[i];
			if(item!="-" && (!item.type || item.type=="button")){
					if(item.id && item.on){
						regClickFunc(item.id,item.on);
					}
			}
		}
	};
	
	var regClickFunc=function(bid,on){
		$("#"+bid).click(on);
	};
	
	var createBtn=function(item){
		var ao=$.newEl("a");
		$.addCls(ao,"u-button",item.id);
		if(!ao.id){
			ao.id="tbr_"+$.getRandom(1000);
		}
		if(item.icon){
			var icono=$.newEl("span");
			$.addCls(icono,"u-icon");
			icono.style.backgroundImage="url("+item.icon+")";
			ao.appendChild(icono);
		}
		var nameo=$.newEl("span");
		var tx=$.newTn(item.name);
		nameo.appendChild(tx);
		ao.appendChild(nameo);
		return ao;
	};
	
	var createSeparator=function(){
		var so=$.newEl("span");
		$.addCls(so,"u-separator");
		return so;
	};
	
})(jQuery);

(function ($) {
	$.fn.ui_button = function (options) {
		if($.isJson(options)){
			var o=$(this);
			var tagFlag=0;
			var ta=o.get(0).tagName;
			if(ta=="A"){
				tagFlag=1;
			}else if(ta=="BUTTON"){
				tagFlag=2;
			}
			if(tagFlag>0){
				createHtml(o,options,tagFlag);
			}
		}
	};
	
	var createHtml=function(o,item,taf){
		var bo=o.get(0);
		$.addCls(bo,"u-button");
		if(taf>1){
			$.addCls(bo,"u-button-sd");
		}
//		background-image:url("images/add.png");
		var nameo=$.newEl("span");
		var tx=$.newTn(item.name);
		nameo.appendChild(tx);
		bo.appendChild(nameo);
		if(item.icon){
			var icono=$.newEl("span");
			$.addCls(icono,"u-icon");
			icono.style.backgroundImage="url("+item.icon+")";
			bo.appendChild(icono);
		}
		if(item.handler){
			o.click(item.handler);
		}
	}; 

})(jQuery);