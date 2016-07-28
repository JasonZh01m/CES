(function ($) {
	
	var tabNum={};
	var tabMap={};
	var tabDownMap={};
	var tabLoadFlag={};
	var maxNum=6;
	
	$.fn.tab = function (options,item) {
		var o=$(this);
		var id=o.attr("id");
		if(!options || $.isJson(options)){
			o.addClass("u-tab");
			var tabo=o.get(0);
			createHtml(o,tabo,options);
			regHandler(id);
			tabMap[id]={};
			tabNum[id]=0;
			tabLoadFlag[id]=true;
			if(options && options.home){
				openTab(id,options.home);
			}
		}else if(options=="add"){
			openTab(id,item);
		}else if(options=="open"){
			openTab(id,item);
		}else if(options=="resize"){
			resizeTab(o,id);
		}else if(options=="setTabCount"){
			maxNum=item;
		}
	};
	
	var initWidget=function(pannel,tid){
		if(pannel.length<1 || !tid){
			return;
		}
		$("#"+tid).layout({
			items:pannel
		});
		
	};
	
	var resizeTab=function(o,id){
		var w=o.width();
		var h=o.height();
		$("#"+id+" >div").each(function(){
	         if(this.className=="tabtbr"){
	        	 this.style.width=w+"px";
	         }else if(this.className=="tabpannel"){
	        	 this.style.width=w-4+"px";
	        	 this.style.height=h-36+"px";
	        	 var tpo=$(this);
	        	 var tpco=tpo.children();
	            if(tpco){
	            	var pw=tpo.width();
		        	var ph=tpo.height();
		        	tpco.each(function(){
		        		this.style.width=pw+"px";
		        		this.style.height=ph+"px";
			        	var tpcco=$(this).children().eq(0);
			        	if(tpcco){
			        		tpcco.get(0).style.width=pw+"px";
			        		tpcco.get(0).style.height=ph+"px";
			        	}
		        	});
		        	
		        	
	            }
	        	
	         }
		});
	};
	
	var createHtml=function(o,tabo,_items){
		var w=o.width();
		var h=o.height();
		var tbro=createTabbar(tabo.id,w);
		var pannelo=createTabPannel(tabo.id,w,h);
		tabo.appendChild(tbro);
		tabo.appendChild(pannelo);
	};
	
	var regHandler=function(id){
		$("#tb_tbr_"+id).delegate("span","click",function(event){
			if(this.tagName=="SPAN" && this.className=="close"){
				var tid=$(this).parent().attr("id");
				closeTab(id,tid);
				event.stopPropagation();
			}
		});
		$("#tb_tbr_"+id).delegate("li","click",function(){
			if(!tabLoadFlag[id]){
				return;
			}
			if($(this).hasClass("liup")){
				setTabDown(id,this.id);
			}
		});
		
	};
	
	var closeTab=function(id,tid){
		var to=$("#"+tid);
		if(to.hasClass("lidown")){
			var prevTid=to.prev().attr("id");
			setTabDown(id,prevTid);
		}
		removeTab(id,tid);
	};

	var removeTab=function(id,tid){
		(tabMap[id])[$("#"+tid+">:first-child").text()]=null;
		var t=$("#"+tid);
		t.remove();
		var tifid=t.get(0).tinfId;
		clearIframe(tifid);
		$("#"+tifid).remove();
		//(tabMap[id])[item.name]
		var rmnum=--tabNum[id];
		tabNum[id]=rmnum;
	};
	
	function clearIframe(id){
	    var el = document.getElementById(id);
	    if(!el){
	    	return;
	    }
	    var iframe = el.contentWindow;
	    if(el){
	        //以上可以清除大部分的内存和文档节点记录数了
	        //最后删除掉这个 iframe 就哦咧。
	        if(navigator.userAgent.indexOf("MSIE")>0){
	        	el.src = '';
		        try{
		            iframe.document.write('');
		            iframe.document.clear();
		        }catch(e){};
	        	CollectGarbage();
	        }else{
	        	$("#"+id).remove();
	        }
	        
	    }
	}
	var createTabPannel=function(id,w,h){
		var tabpannelo=$.newEl("div");
		$.addCls(tabpannelo,"tabpannel","tb_p_"+id);
		tabpannelo.style.width=w-4+"px";
		tabpannelo.style.height=h-36+"px";
		return tabpannelo;
	};
	
	
	var createTabbar=function(id,w){
		var tbro=$.newEl("div");
		$.addCls(tbro,"tabtbr","tb_tbr_"+id);
		tbro.style.width=w+"px";
		return tbro;
	};
	
    var changeTabUp=function(id){
    	if(tabDownMap[id]){
    		$("#"+tabDownMap[id]).removeClass("lidown");
    		$("#"+tabDownMap[id]).addClass("liup");
    		var o=$.getEl(tabDownMap[id]);
    		if(o){
    			$("#"+o.tinfId).hide();
    		}
    		
    	}
	};
	
	var setTabDown=function(id,tid){
		var t=$("#"+tid);
		t.removeClass("liup");
		t.addClass("lidown");
		var o=t.get(0);
		if(o){
			$("#"+o.tinfId).show();
		}
		changeTabUp(id);
		tabDownMap[id]=tid;
	};
	
	
	var openTab=function(id,item){
		var ttid=(tabMap[id])[item.name];
		if(!tabLoadFlag[id] ){
			//alert("请待刚打开加载完成后,在尝试打开下个页面!");
			setTimeout(function(){openTab(id,item);},500);
			return;
		}
		if(!ttid){
			if(tabNum[id]>=maxNum){
				alert("系统最多可打开"+maxNum+"个标签,请关闭其他已打开的标签后,才可打开此标签!");
				return;
			}
			if(item.url){
			   tabLoadFlag[id]=false;
			}
			var titleo=createTabTitle(id,item);
			var w=$("#tb_p_"+id).width();
			var h=$("#tb_p_"+id).height();
			var tb_info=$.newEl("div");
			var tabInfId;
			if(item.id){
				tabInfId=item.id;
			}else{
				tabInfId=titleo.id+"_t";
			}
			titleo.tinfId=tabInfId;
			$.addCls(tb_info,"tabInf",tabInfId);
			tb_info.style.width=w+"px";
			tb_info.style.height=h+"px";
			$("#tb_tbr_"+id).get(0).appendChild(titleo);
			$("#tb_p_"+id).get(0).appendChild(tb_info);
			setTabDown(id,titleo.id);
			if(item.url){
				var ifo=addURI(id,item.url);
				ifo.id=titleo.id+"_ti";
				tb_info.appendChild(ifo);
				ifo.src=item.url;
				var sto=setTimeout(function(){recoverFlag(id);},15000);
				$("#"+ifo.id).load(function(){
					window.clearTimeout(sto);
					tabLoadFlag[id]=true;
				});
				if(!item.url && item.pannel){
					initWidget(item.pannel,item.id);
				}
			}else{
				if(!item.url && item.pannel){
					initWidget(item.pannel,item.id);
				}
			}
			tabNum[id]=++tabNum[id];
			
		}else{
			if($("#"+ttid).hasClass("liup")){
				setTabDown(id,ttid);
			}
		}
		
	};
	
	var recoverFlag=function(id){
		tabLoadFlag[id]=true;
	};
	
	var addURI=function(id,uri){
		var w=$("#tb_p_"+id).width();
		var h=$("#tb_p_"+id).height();
		var iframeo=$.newEl("iframe");
		iframeo.style.width=w+"px";
		iframeo.style.height=h+"px";
		iframeo.setAttribute("frameborder", "0", 0); 
		iframeo.scrolling="no";
		return iframeo;
	};
	
	
	var createTabTitle=function(id,item){
		var lio=$.newEl("li");
		var tid="tb_i_"+$.getRandom(100000);
		$.addCls(lio,"lidown",tid);
		
		var spano=$.newEl("span");
		if(item.name){
			var tx=$.newTn(item.name);
			spano.appendChild(tx);
			(tabMap[id])[item.name]=tid;
		}
		lio.appendChild(spano);
		if(item.closed){
			var closeo=$.newEl("span");
			$.addCls(closeo,"close");
			lio.appendChild(closeo);
		}
		return lio;
	};
	
	
	
})(jQuery);