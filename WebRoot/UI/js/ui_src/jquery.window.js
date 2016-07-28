(function ($) {
var nowZIndex=2;
var oh={};
var ow={};
var updateFields={};
var openfn={};
var closefn={};
$.fn.window = function (options,ot) {
    var o=$(this);
    var id=o.attr('id');
    if(options=="open"){
    	o.css("z-Index",nowZIndex++);
    	if(ot){
    	var otb=ot.btns;
    	if(otb && otb.length>0){
    	$("#"+id+" .u-button").each(function(index){
    		if(otb[index] && otb[index]==1){
    			$(this).hide();
    		}else{
    			$(this).show();
    		}
    	});
    	}else{
    		alert("btn为空");
    	}
    	if(ot.loadData){
    		$.loadFormDate(id,ot.loadData,ot.param);
    	}
    	if(ot.mode && ot.mode=="readOnly"){
    		o.get(0).mro=true;
    		$('#'+id+' *[name]').each(function() {
					this.disabled=true;
			});
    	}
    	}
    	ahead(o);
    	open(o);
    }else if(options=="close"){
    	close(o);
    }else if(options=="clear"){
    	if(!o.get(0).mro){
    		clearWinForm(o);
    	}
    }else if(options=="center"){
    	center(o);
    }else if(options=="getUpdateData"){
    	return updateFields;
    }else if(options=="ahead"){
    	ahead(o);
    }else if($.isJson(options)){
    	init(o,options);
   }
    
};

 var ahead=function(o){
	 var nowz=o.get(0).style.zIndex;
	 $(".window").each(function(){
		 var wi=this.style.zIndex;
		 if(wi>nowz){
			 this.style.zIndex=wi-1;
		 }
	 });
	 o.get(0).style.zIndex=nowZIndex;
 };
 
 var closeZIndex=function(o){
	 var nowz=o.get(0).style.zIndex;
	 $(".window").each(function(){
		 var wi=this.style.zIndex;
		 if(wi>nowz){
			 this.style.zIndex=wi-1;
		 }
	 });
	 --nowZIndex;
 };

 var createHtml=function(o,options){
	 var _ww=options.width;
	 var _wh=options.height;
	 var id=o.attr('id');
	 o.addClass("window ui-widget-content");
	 o.css("width",_ww+"px");
	 o.css("height",_wh+"px");
	 var oFragment = document.createDocumentFragment();
	 var titleObj=createTitle(id,options.title);
	 var winPannelObj=createWinPannel(id,_ww,_wh,options);
	 oFragment.appendChild(titleObj);
	 oFragment.appendChild(winPannelObj);
	 if(options.button){
		 var wbg=$.createBtnGroup(options.button);
		 oFragment.appendChild(wbg);
	 }
	 o.get(0).appendChild(oFragment);
	 setHeightDiv(id);
	 oh[id]=_wh;
	 ow[id]=_ww;
 };
 
 
 function setHeightDiv(id){
 };
 
 var createTitle=function(id,title){
	 var po=$.newEl("p");
	 $.addCls(po,"wtitle");
	 var spano=$.newEl("span");
	 var texto=$.newTn(title);
	 spano.appendChild(texto);
	 var ao=$.newEl("a");
	 ao.id="_wc_"+id;
	 po.appendChild(spano);
	 po.appendChild(ao);
	 return po;
 };
 
 var createWinPannel=function(id,ww,wh,opt){
	 var wp=$.newEl("div");
	 $.addCls(wp,"winPanel","wp_"+id);
	 wp.style.width=(ww-12)+"px";
	 wp.style.height=(wh-75)+"px";
	 
	 var wc=$.newEl("div");
	 $.addCls(wc,"winContext","wc_"+id);
	 
	 var cww=ww-16;
	 var yuHeight=wh-86;
	 wc.style.height=yuHeight+"px";
	 wc.style.width=cww+"px";
	 
	 var optArr=opt.content;
	 if(optArr){
		 var f=true;
		 var fnum=0;
		 for(var i=0;i<optArr.length;++i){
				var opt=optArr[i];
				if(fnum>1){
					f=false;
					break;
				}
				if(opt.type=="pannel"){
					++fnum;
				}
		 }
		 
		 for(var i=0;i<optArr.length;++i){
				var fo=createContent(id,optArr[i],yuHeight,cww,f);
				if(fo){
					wc.appendChild(fo); 
				}
		 }
		
	 }
	 wp.appendChild(wc);
	 return wp;
	 
 };
 
 var createContent=function(id,opt,h,w,f){
	 var fo=null;
	 if(opt.type=="form"){
		 var to=$.newEl("table");
		 to.id="wct_"+id;
		 fo=$.newEl("form");
		 fo.id="wcf_"+id;
		 var of=createAllInput(opt.items,opt.rowNum,id);
		 to.appendChild(of);
		 fo.appendChild(to); 
	 }else if(opt.type=="pannel"){
		 var divpo=$.newEl("div");
		 $.addCls(divpo,"wpannel","p_"+opt.id);	
		 if(opt.height){
			 divpo.style.height=opt.height+"px";
		 }else{
			 
		 }
		 if(opt.width){
			 divpo.style.width=opt.width+"px";
		 }else{
			 if(f){
				 divpo.style.width=($("#"+id).width()-20)+"px";
			 }
		 }
		 var fso=$.newEl("fieldset");
		 divpo.appendChild(fso);
		 var flsH=opt.height;
		 if(opt.field){
			 var lo=$.newEl("legend");
			 var t = $.newTn(opt.field);
			 lo.appendChild(t);
			 fso.appendChild(lo);
			 flsH-=16;
		 }else{
			 fso.style.marginTop="7px";
			 flsH-=9;
		 }
		 var divo=$.newEl("div");
		 $.addCls(divo,"dp",opt.id);
		 divo.style.width=(opt.width-12)+"px";
		 divo.style.height=flsH+"px";
		 fso.appendChild(divo);
		 fso.ptid=opt.id;
		 fo=divpo;
	 }
	return fo;
 };
 
 var sortTheHidden=function(items){
	 var len =items.length;
	 var nArray=new Array(len);
	 var j=0;
	 var k=len-1;
	 for(var i=0;i<len;++i){
		 var item=items[i];
		 if(item.type=="hidden"){	 
				 nArray[k]=items[i];
				 --k;
		 }else{
			 nArray[j]=item;
			 ++j;
		 }
	 }
	 items=null;
	 return nArray;
 };
 
 var createAllInput=function(arr,num,id){
	 arr=sortTheHidden(arr);
	 var of = document.createDocumentFragment(); 
	 var len=arr.length;
	 var tro=null;
	 var n=0;
	 var sort= 1;
	 for(var i=0;i<len;++i){
		if(arr[i].type=="textarea" && (n!=num || n!=0)){
			if(tro)
			of.appendChild(tro);
			n=0;
		}
		if(n==0){
			tro=$.newEl("tr");
		}
		var f=(n==num-1);
		if(arr[i].type!="hidden"){
			createInput(tro,arr[i],num,f,sort);
			++sort;
		}else{
			createInput(tro,arr[i],num,f);
		}
		++n;
		if(n==num ||i==(len-1) || arr[i].type=="textarea"){
			of.appendChild(tro);
			n=0;
		}
	 }
	 return of;
 };
 
 var createInput=function(tro,obj,num,f,s){
		 var tdo=$.newEl("td");
		 if(obj.type!="hidden"){
			 var tdtno=$.newTn(obj.field+":");
			 tdo.appendChild(tdtno); 
		 }
		 tro.appendChild(tdo);
		 var tdino=$.newEl("td");
		 var ino=$.newInput(obj);
		 if(s){
			 ino.wsort=s;
		 }
		 if(obj.type=="textarea"){
			 tdo.vAlign="top";
			 tdino.colSpan=num*2-1;
			 ino.style.width="98.5%";
			 if(obj.height){
				 ino.style.height=obj.height+"px";
			 }
		 }else{
			 if(!f)
			 tdino.style.width="180px"; 
		 }
		 tdino.appendChild(ino);
		 tro.appendChild(tdino);
 };
 
 var init=function(o,options){
	 var id=o.attr('id');
	 createHtml(o,options);
	 initWindow(o);
	 $.initWidget(id,options);
	 $("#_wc_"+id).click(function(){
		 var p=$(this).parent().parent();
		 close(p);
	 });
	 o.find("p").mousedown(function(){
		 $("table.lcui_border").parent().hide();
	 });
	 if(options.openHandler){
		 openfn[id]=options.openHandler; 
	 }
	 if(options.closeHandler){
         closefn[id]=options.closeHandler;
	 }
	 
	 $.onFormValidator(id);
	 $.drag(id);
	 if(!o.get(0).mro && options.enterTab){
		 var ipt=$('#'+id+' *[name]');
		 var len=ipt.length;
		 ipt.keydown(function(e){
			  var key = e.keyCode; 
	    	  if(key==13){
	    		  var s=this.wsort+1;
	    		  if(s>len){
	    			  $('#'+id+' .u-button').each(function(){
	    				  if(this.sbt && this.style.display!="none"){
	    					  $(this).select();
	    					  return false;
	    				  }
	    			  });
					 return;
				  }    		  
	    		  ipt.each(function(index){
	    			  if(this.wsort==s){
	    				  if(!(this.readOnly || this.disabled)){
	    					  $(this).focus();
	    					 if(this.tagName=="SELECT"){
	    						 
	    					 }
		    			      $(this).select();
	    					  this.click();
		    				  return false; 
	    				  }else{
	    					  ++s;
	    				  }
	    			  }
	    		  });
	    	  }
	     });
		 }
 };

var initWindow=function(o){
	o.hide();
};

var open = function(o){
	 var id=o.attr('id');
	 $.modal();
	 o.show(); 
	 $.centerEle(o.find(".btn_group").attr("id"));
	 center(o);
	 if(!o.get(0).mro){
	    /*$('#'+id+' *[name]').each(function(){
	    		if(this.tagName!="SELECT" && this.type!="hidden"){
	    			$(this).focus();
	    			return false; 
	    		}
	  });*/
	    if(openfn[id]){
	    	openfn[id]();
	    }
	 }
};

var center= function(o){
	 var ww=$(window).width();
	 var wh=$(window).height();
     var id=o.attr('id');
	 var _top=(wh-oh[id])/2+$(document).scrollTop();
	 var _left=(ww-ow[id])/2+$(document).scrollLeft();
	 o.css({"top":_top+"px","left":_left+"px"});
};

var close=function(o){
	var id=o.attr('id');
	if(closefn[id]){
		closefn[id]();
	}
	clearWinForm(o);
	$.modalClose();
	closeZIndex(o);
	o.hide();
	if(o.get(0).mro){
		$('#'+id+' *[name]').each(function() {
				this.disabled="";
		});
		o.get(0).mro=false;
	}
	$.rmAllValiTn(id);
};

var clearWinForm=function(o){
	var id=o.find("form").attr('id');
	if(id){
		$.clearForm(id,updateFields);
	}
};

})(jQuery);