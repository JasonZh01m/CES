var fullData={};
var _move=false;//移动标记 
var _x,_y;//鼠标离控件左上角的相对位置  
var maxTop=0;
var maxLeft=0;
var drapFlag=false;
var dragWin=null;
$(function(){
	fullData.w=$(window).width();
	fullData.h=$(window).height();
	$(window).resize(function() {
		var rw=$(window).width();
		$(".modal").width(rw);
		$(document.body).children().each(function(){
			if(this.className.indexOf("ui-jqgrid")>-1){
				var gid=this.id.split("_")[1];
				$("#"+gid).setGridWidth(rw);
			}
		});
		$(".window").each(function(){
			$("#"+this.id).window("center");
		});
		});
});

$.extend({
	rm_ajax:function(r){
		var _t=r.type?r.type:"get";
		var _u=r.url;
		var _a=r.async==false?r.async:true;
		var _d=r.data?r.data:null;
		var _dt=r.dataType?r.dataType:"json";
		$.ajax({type:_t,url:_u,async:_a,data:_d,dataType:_dt,
			success:function(d){
				 if(d.success>-1){
					 if(d.success===0){
						 if(r.success){
							 if(d.msg){
								 alert(d.msg);
							 }else{
								 alert('操作成功');
							 }
							 r.success();
						 }
				     }else if(d.success===1){
				    	 if(d.msg){
							 alert(d.msg);
						 }else{
							 alert("操作失败!");
						 }
				     }else if(d.success===2){
				    	 alert("后台异常!"+"\n"+d.msg);
				     }else if(d.success===3){
				    	 alert("您登陆已超时,或此账号已在其他电脑登陆,请重新登录!");
				     }else if(d.success===4){
				    	 alert("您无此操作权限!");
				     } 
				 }else{
					 r.success(d);
				 }
			},error:function(XMLHttpRequest, textStatus, errorThrown){
				if(XMLHttpRequest.status!="200"){
					alert("后台异常->状态码:"+XMLHttpRequest.status+"; 请求完毕状态码:"+XMLHttpRequest.readyState+"; 文本状态:"+textStatus+"\n"+
							"异常:"+errorThrown);
				}
				
			}
	     });
	},
	modal:function(){
		var o=null;
		if(!$.getEl("_mw")){
			$("body").append("<div id='_mw' class='modal'/>"); 
		}
		$(document.body).css("overflow","hidden");
		o=$("#_mw");
		var w=$(window).width();
	    var h=$(window).height();
	    o.css("width",w+"px");
	    o.css("height",h+"px");
		o.show();
	},
   modalClose:function(){
	   $(document.body).css("overflow","auto");
	   $("#_mw").hide();
   },
   clearForm:function(id){
	   $('#'+id)[0].reset();
   },
   isJson:function(obj){
	   return typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
   },
   newEl:function(e){
     return document.createElement(e);
   },
   getEl:function(id){
     return document.getElementById(id);
   },
   addIframe:function(pid,url){
	    var p=$("#"+pid);
	    var po=$.getEl(pid);
	    var w=p.width();
		var h=p.height();
	    var iframeo=$.newEl("iframe");
	    var fid="ifo_"+$.getRandom(10000);
	    iframeo.id=fid;
		iframeo.style.width=w+"px";
		iframeo.style.height=h+"px";
		iframeo.setAttribute("frameborder", "0", 0); 
		iframeo.scrolling="no";
		iframeo.src=uri;
		po.appendChild(iframeo);
		po.ifoid=fid;
   },
   flushIframe:function(pid){
	   var ifid=$.getEl(pid).ifoid;
	   if(ifid){
		   document.frames(ifid).document.location.reload(true);
	   }   
   },
   newTn:function(t){
    return document.createTextNode(t);
   },
   createSelect:function(obj){
	     var ino=this.newEl("select");
		 if(obj.readOnly==true){
			 ino.disabled=true; 
		 }
		 if(obj.url){
			 $.ajax({type:"get",url:obj.url,async:true,dataType:"json",
					success:function(d){
					     if(d && d.length>0){
					    	 if(obj.blankText){
				                 ino.options.add(new Option(obj.blankText,""));
				                 ino.btxt=obj.blankText;
					    	 }
					    	 for(var i=0;i<d.length;++i){
					    		 var dd=d[i];
					    		 if(dd.value){
					    			 var op=new Option(dd.text,dd.value);
					    			 ino.options.add(op);
					    		 }
							 }
					     }
					}
			});
			 if(obj.eachLoad){
				 ino.i=0;
				 $(ino).click(function(){
					 var so=this;
					 $.ajax({type:"get",url:obj.url,async:false,dataType:"json",
							success:function(d){
							     if(d && d.length>0){
							    	 $(so).html("");
							    	 if(obj.blankText){
						                 ino.options.add(new Option(obj.blankText,""));
						                 ino.btxt=obj.blankText;
							    	 }
							    	 for(var i=0;i<d.length;++i){
							    		 var dd=d[i];
							    		 if(dd.value){
							    			 var op=new Option(dd.text,dd.value);
							    			 op.optt=true;
							    			 so.options.add(op);
							    			 $(op).click(function(){
							    				 return false;
							    			 });
							    		 }
									 }
							     }
							}
					});
				 }); 
			 }
		 }else{
			 if(obj.blankText){
                 ino.options.add(new Option(obj.blankText,""));
                 ino.btxt=obj.blankText;
	    	 }
			 if(obj.option && obj.option.length>0){
			 for(var i=0;i<obj.option.length;++i){
				 var op=new Option(obj.option[i].text,obj.option[i].value);
    			 ino.options.add(op);
			 }
			 }
		 }
		 
		 if(obj.changeFn){
			 $(ino).change(function(){
				 var txt=$(this).find("option:selected").text();
				 var val=$(this).val();
				 obj.changeFn(txt,val);
			 });
		 }
		 return ino;
   },
   addOptions:function(sid,d){
	 var ino=$.getEl(sid);
	 if(ino && ino.tagName=="SELECT"){
		 $(ino).html("");
		 if(ino.btxt){
			 ino.options.add(new Option(ino.btxt,""));
	  	 }
	  	 for(var i=0;i<d.length;++i){
	  		 var dd=d[i];
	  		 if(dd.value){
	  			 var op=new Option(dd.text,dd.value);
	  			 ino.options.add(op);
	  		 }
		}
	 }
	
   },
   newInput:function(obj){
	   var ino;
	   if(obj.type=="select"){
			 ino=$.createSelect(obj);
		 }else if(obj.type=="date"){
			 ino=this.newEl("input");
			 ino.wtype="d";
			 if(!obj.id){
				 ino.id="d_"+this.getRandom(100);
			 }
			 ino.fm=obj.format;
			 ino.rs=obj.conf;
		 }else if(obj.type=="hidden"){
			 ino=this.newEl("input");
			 ino.type="hidden";
		 }else if(obj.type=="password"){
			 ino=this.newEl("input");
			 ino.type="password";
		 }else if(obj.type=="textarea"){
			 ino=this.newEl("textarea");
			 ino.style.fontSize="13px";
		 }else{
			 ino=this.newEl("input");
			 ino.type="text";
		 }
	   if(obj.readOnly==true && obj.type!="select"){
			 ino.readOnly=true;
	   }
	   if(obj.disabled==true){
		   ino.disabled=true;
	   }
	   if(obj.name){
		   ino.name=obj.name;  
	   }
	   if(obj.id){
		   ino.id=obj.id;
	   }else{
		   ino.id="f_ino_"+this.getRandom(10000);
	   }
	   if(obj.value){
		   ino.value=obj.value;  
	   }
	   if(obj.type!="hidden"){
		   $.addvalidate(ino,obj);
	   }
	   return ino;
   },
   getFormDate:function(id){
			var para = {};
			$('#'+id+' *[name]').each(function() {
				        var val=this.value;
				        	if (this.name && val ) {
				        			para[this.name] = this.value;
							}
			});
			return para;
   },
   clearGridPostParam:function(gid){
	   var f=$("#"+gid).jqGrid("getGridParam", "postData");
       for(var p in f){
    	   delete f[p];
       }
   },
   getUpdateDate:function(para,uf){
		     for(var p in para){
		    	 if(!uf[p] && uf[p]>-1){
		    		 delete para[p];
		    	 }
		     }
	   return para;
   },
   createBtnGroup:function(bs){
		 var wbg=this.newEl("div");
		 this.addCls(wbg,"btn_group","bg_"+this.getRandom(1000));
		 for(var i=0;i<bs.length;++i){
			 var o=bs[i];
			 var bo =this.createBtn(o);
			 bo.rn=i;
			 wbg.appendChild(bo);
		 }
		 return wbg;
	},
	createBtn:function(bs){
		var bo=$.newEl("button");
		$.addCls(bo,"u-button u-button-sd",bs.id);
		if(!bo.id){
			bo.id="btn_"+$.getRandom(1000);
		}
		if(bs.icon){
			var icono=$.newEl("span");
			$.addCls(icono,"u-icon");
			icono.style.backgroundImage="url("+bs.icon+")";
			bo.appendChild(icono);
		}else{
			if(bs.type || bs.on){
				var icono=$.newEl("span");
				$.addCls(icono,"u-icon");
				if(bs.type=="close"){
					$.addCls(icono,"closeIcon");
				}else if(bs.type=="clear"){
					$.addCls(icono,"clearIcon");
				}else{
					$.addCls(icono,"submitIcon");
				}
				bo.appendChild(icono);
			}
		}
		var nameo=$.newEl("span");
		var tx=$.newTn(bs.name);
		nameo.appendChild(tx);
		bo.appendChild(nameo);
		return bo;
	},
   initWidgetBtn:function(id,item,opt){
	    var bs=item;
	    $("#"+id+" .u-button" ).each(function(){
		var b=bs[this.rn];
		if(!b){
			return true;
		}
		if(b.type=="close"){
			$.closeBtnFn($(this),id);
		}else if(b.type=="submit"){
			if(b.url){
				if(opt){
					this.u=opt;
				}
			   this.sbt=true;
			   $.postBtnFn($(this),b,id,b.beforeSubmit);
			}
		}else if(b.type=="clear"){
			
		}else{
			if(b.on){
				$.btnFn($(this),b.on);
			}
		}
	});
   },
   initWidget:function(id,options){
		if(options.button)
			this.initWidgetBtn(id,options.button,options.opt);
		$('#'+id+' *[name]').each(function(){
			if(this.wtype=="d"){
				var dto=this,q=this.rs,sdf=null;
			     if(!q){
			    	 q={};
			     }else{
			    	 sdf=this.rs.onSetDate;
			     }
			     q["format"]=this.fm;
			     var sto=function(){
			    	 $.rmValiTn(dto);
			    	 $.rmTip(dto);
			     };
			     if(sdf){
			    	 q["onSetDate"]=function(){
			    		 sdf();
			    		 setTimeout(sto,240);
				     };
			     }else{
			    	 q["onSetDate"]=function(){
			    		 setTimeout(sto,240);
				     };
			     }
				 $('#'+this.id).calendar(q);
				 delete this.rs;
		   }else if(this.sign){
			   var o1=$.getEl(this.Ele);
				 if(o1){
					 o1.cpv=this.id;
					 o1.vd=true;
				 }
		   }
		});
	},
   closeBtnFn:function(o,id){
	   o.click(function(){
		   $("#"+id).window("close");
	   });
   },
   postBtnFn:function(o,b,id,bs){
	   o.click(function(){
		   var para=$.getFormDate(id);
		   var bsf=true;
		   var gid=this.u;
		   if(bs){
			   bsf=bs();
		   }	  
	       if($.validateForm("wcf_"+id) && bsf){
	    	   $.rm_ajax({type:"post",url:b.url,data:para,async:true,dataType:"json",
					success:function(d){
							$.closeWinAndFlush(id,gid);
					}
				});
	       }	
	   });
   },
   btnFn:function(o,fn){
	   o.click(fn);
   },
   addCls:function(o,cls,id){
	   if(id){
	      o.id=id;
	   }
	   var ocls="";
	   if(o.className){
		   ocls=o.className+" ";
	   }
	   o.className=ocls+cls;
   },
   getRandom:function(n){
       return Math.floor(Math.random()*n+1);
   },
   flushGrid:function(gid){
	   if(!gid){
		   return;
	   }
	   if(gid.indexOf(",")>0){
		   var _gs=gid.split(","); 
		   for(var i=0;i<_gs.length;++i){
			   var g=_gs[i];
			   $("#"+g).jqGrid('setGridParam',{datatype:'json',postData:{},page:1}).trigger("reloadGrid");
		   }
	   }else{
		   $("#"+gid).jqGrid('setGridParam',{datatype:'json',postData:{},page:1}).trigger("reloadGrid");
	   }
	   
   },
   closeWinAndFlush:function(id,gid){
	   $("#"+id).window("close");
		if(gid){
		$.flushGrid(gid);
		}
   },
   centerEle:function(id){
	   var l=($("#"+id).parent().width()-$("#"+id).width())/2;
	   $("#"+id).css("left",l+"px");
   },
   loadFormDate:function(id,url,para){
	   $.ajax({type:"post",url:url,data:para,async:false,dataType:"json",
			success:function(d){
				$('#'+id+' *[name]').each(function() {
					if (this.name) {
						var _val=d[this.name];
						if( _val || _val==0){
						   this.value=_val;
						   if(this.vFn){
		        				this.oleVal=this._val;
		        			}
						}
					}
				});
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus);
			}	
		});
   },
   createTip:function(t,tn){
	        var to=$(t);
			var h=to.height();
			var t=to.offset().top+h+8;
			var l=to.offset().left;
			var tid="tt_"+$.getRandom(1000);
			if(tn){
				$(document.body).append("<div class='u-tooltip' id='"+tid+"' style='top:"+t+"px;left:"+l+"px;'>" +
						"<span class='tip-word'>"+tn+"</span></div>");
				to.get(0).ttid=tid;
			}
   },
   rmTip:function(t){
	  $('#'+t.ttid).remove();
   },
   addvalidate:function(ino,obj){
	   var f=false;
	   if(obj.allowBlank==false){
		   ino.ab=1;
		   f=true;
	   }
	   if(obj.regex){
		   ino.vr=obj.regex;
		   ino.vrt=obj.regexText;
		   f=true;
	   }
	   if(obj.compare){
		   ino.sign=obj.compare.sign;
		   ino.Ele=obj.compare.Ele;
		   ino.ct=obj.compare.text;
		   f=true;
	   }
	   if(obj.max && !isNaN(obj.max)){
		   ino.max=obj.max;
		   f=true;
	   }
	   if(obj.min && !isNaN(obj.min)){
		   ino.min=obj.min;
		   f=true;
	   }
	   if(obj.maxLen && !isNaN(obj.maxLen)){
		   ino.maxLen=obj.maxLen;
		   f=true;
	   }
	   if(obj.minLen && !isNaN(obj.minLen)){
		   ino.minLen=obj.minLen;
		   f=true;
	   }
	   if(obj.validLenText){
		   ino.vLenTxt=obj.validLenText;
	   }
	   if(obj.validFn){
		   ino.vFn=obj.validFn;
		   ino.vFntxt=obj.validFnText;
		   f=true;
	   }
	   if(f){
		   ino.vd=true;   
	   }
	   
   },
   reg:{
	  "number":/^0$|^[1-9]+[0-9]*$/,
	  "email":/^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/,
	  "phone":/^[0][1-9]{2,3}-[0-9]{5,10}$/,
	  "money":/^[0-9]+[\.][0-9]{0,3}$/,
	  "mobile":/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/
   },
   validateForm:function(fid){
	   var f=true;
	   $('#'+fid+' *[name]').each(function() {
			if(this.vd){
				   if(!$.validatorData(this)){
					   f=false;
				   } 
			}
		});
	   return f;
   },  
   validatorData:function(o){
	   if(!o || o.disabled || o.readOnly){
		   return true;
	   }
	   var f=true;
	   var val=o.value;
		f=$.validatorBlank(o,val);
		if(val){
			if(o.vr && f){
				f=$.validatorRegex(o,val);
			}
			if(f){
		        f=$.validatorLength(o,val);
			}
			if(o.sign && f){
				f=$.validatorCompare(o,val);
			}
			if(o.vFn && f){
				if(o.oleVal && o.oleVal==val){
				   f=$.validatorFn(o,val);
				}
			}
		}
		if(!f && !o.className){
			  o.className="verify";
		}else if(f && o.className) {
			  o.className="";
		}
		if(o.cpv){
			   var o1= $.getEl(o.cpv);
			   if(o1){
				   obj=$.getEl(o.cpv);
				   $.validatorData(obj);
			   }
		 }
		return f;
   },
   validatorBlank:function(obj,val){
	   var f=true;
	   if (obj.ab && !val) {
			obj.ttn="必填项,不能为空!";
			f=false;
		}
	   return f;
   },
   getRealLen:function(str) {
	  // return str.length;
	   return str.replace(/[^\x00-\xff]/g, '___').length;
   },
   validatorLength:function(obj,val){
	   var f=true;
	   var min=-1,max=-1;
	   if(obj.vr=="number"){
		   var v=parseInt(val,10); 
		   if(obj.min)
			   min=parseInt(obj.min,10); 
		   if(obj.max)
			   max=parseInt(obj.max,10);  
		   if(max>0 && min>0 && !(v>=min && v<=max)){
			   obj.ttn="此项数字需在"+min+"-"+max+"的范围内";
			   f=false;
		   }else if(max>0 && max<v){
			   obj.ttn="此项不能大于"+max;
			   f=false;
		   }else if(min>0 && min>v){
			   obj.ttn="此项不能小于"+min;
			   f=false;
		   }
	   }else{
		   var len=$.getRealLen(val);
		   if(obj.maxLen)
			   max=parseInt(obj.maxLen,10); 
		   if(obj.minLen)
			   min=parseInt(obj.minLen,10); 
		   if(max>0 && min>0 && !(len>=min && len<=max)){
			   obj.ttn="此项的字节长度需在"+min+"-"+max+"的范围内"; 
			   f=false;
		   }else if(max>0 && max<len){
			   obj.ttn="此项字节长度不能大于"+max;
			   f=false;
		   }else if(min>0 && min>len){
			   obj.ttn="此项字节长度不能小于"+min;
			   f=false;
		   }
	   }
	   return f;
   },
   validatorFn:function(obj,val){
	   var f=false;
	   if(obj.vFn(obj.name,val)){
		   f=true;
	   }else{
		   obj.ttn=obj.vFntxt;
	   }
	   return f;
   },
   validatorRegex:function(obj,val){
	   var f=true;
	   var r=null;
	   var t="";
		if(obj.vr=="number"){
			t="此项必须为数字";
		}else if(obj.vr=="email"){
			r=this.reg.email;
			t="此项必须为email格式";
		}else if(obj.vr=="phone"){
			r=this.reg.phone;
			t="此项必须为电话号码格式";
		}else if(obj.vr=="money"){
			r=this.reg.money;
			t="此项必须为金钱格式";
		}else if(obj.vr=="mobile"){
			r=this.reg.mobile;
			t="此项必须为手机格式";
		}else if(obj.vr=="ip"){
			t="此项必须为ip格式";
		}else{
			r=obj.vr;
		}
		if(obj.vr=="number"){
			if(isNaN(val) || $.isUnRuleZero(val)){
				if(obj.vrt){
					   obj.ttn=obj.vrt;
					}else{
					   obj.ttn=t;
					}
				f=false;
			}
		}else if(obj.vr=="ip"){
			if(!$.isIP(val)){
				if(obj.vrt){
					   obj.ttn=obj.vrt;
				}else{
					   obj.ttn=t;
				}
				f=false;
			}
		}else{ 
			if(!r.test(val)){
			if(obj.vrt){
			   obj.ttn=obj.vrt;
			}else{
				obj.ttn=t;
			}
			f=false;
		}
		}
	   return f;
   },
   isUnRuleZero:function(v){
	   var f=false;
	   var arr=(v+"").split("");
	   if(arr.length>1){
		   var a1=arr[0];
		   var a2=arr[1];
		   if( a1=='0' && a2!='.'){
			   f=true;
		   }else if(a1=='-' && a2=='0' && arr[2]!='.'){
			   f=true;
		   }else if(a1=='.' || (a1=='-' && a2=='.')){
			   f=true; 
		   }
	   }
	   return f;
   },
   validatorCompare:function(obj,val){
	   var f=true;
	   var os=obj.sign;
		var ev=$.getEl(obj.Ele).value;
		if(os=="="){
			if(val!=ev){
				f=false;
			}
		}else if(os==">"){
			if(parseInt(val,10)<=parseInt(ev,10)){
				f=false;
			}
		}else if(os==">="){
			if(parseInt(val,10)<parseInt(ev,10)){
				f=false;
			}
		}else if(os=="<"){
			if(parseInt(val,10)>=parseInt(ev,10)){
				f=false;
			}
		}else if(os=="<="){
			if(parseInt(val,10)>parseInt(ev,10)){
				f=false;
			}
		}else if(os=="!="){
			if(val==ev){
				f=false;
			}
		}
		if(!f){
			obj.ttn=obj.ct;
		}
	   return f;
   },
   onFormValidator:function(id){
	   $("#"+id).delegate("input,textarea","blur", function(){
				 $.formHandler(this);
	   });
	   $("#"+id).delegate("input,textarea,select","focus", function(){
			 $.rmValiTn(this);
			 $.rmTip(this);
       });
	   $("#"+id).delegate("input,textarea,select","mouseenter",function(){
			if(this.vd && this.className=="verify"){
				$.createTip(this,this.ttn);
			}
		});
	   $("#"+id).delegate("input,textarea,select","mouseleave",function(){
			if(this.vd){
				$.rmTip(this);
			}
	   });
   },
   rmValiTn:function(o){
	   o.className="";
	   o.ttn="";
   },
   rmAllValiTn:function(id){
		$('#'+id+' *[name]').each(function() {
			if(this.vd){
				   $.rmValiTn(this);
			}
		});
   },
   formHandler:function(obj){
			 if(!obj.vd) return;
			 $.validatorData(obj);
   },
   isIP:function(ip){
   var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
   if (reSpaceCheck.test(ip))
   {
   ip.match(reSpaceCheck);
   if (RegExp.$1 <= 255 && RegExp.$1 >= 0
   && RegExp.$2 <= 255 && RegExp.$2 >= 0
   && RegExp.$3 <= 255 && RegExp.$3 >= 0
   && RegExp.$4 <= 255 && RegExp.$4 >= 0)
   {
   return true; 
   }
   else
   {
   return false;
   }
   }
   else
   {
   return false;
   }
   },
   drag:function(id){
	     $("#"+id).mousedown(function(){
	    	 $(this).window("ahead");
	     });
		 $("#"+id+" p").mousedown(function(e){
			    _move=true;
			    var winObj=$(this).parent();
			    $.rmAllValiTn(winObj.get(0).id);
		        _x=e.pageX-parseInt(winObj.css("left"));  
		        _y=e.pageY-parseInt(winObj.css("top"));
		        maxLeft=$(window).width()-winObj.width();
		        maxTop=$(window).height()-winObj.height();
		        winObj.css("cursor","move");
		        dragWin=winObj;
		    });
		 if(!drapFlag){
			 $(document).mousemove(function(e){  
			        if(_move && dragWin){
			            var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置  
			            var y=e.pageY-_y;
			            if((x<0 || x>maxLeft) || (y<0 || y>maxTop)){
			            	if(x<0){
			            		x=0;
			            	}
			            	if(y<0){
			            		y=0;
			            	}
			            	if(x>maxLeft){
			            		x=maxLeft;
			            	}
			            	if(y>maxTop){
			            		y=maxTop;
			            	}
			            }
			            dragWin.css({top:y,left:x});//控件新位置  
			        }  
			    }).mouseup(function(){
			    	   if(dragWin){
				           dragWin.css("cursor","");
				       }
			    	   dragWin=null;
				       _move=false;
				       maxLeft=0;
				       maxTop=0;
				});  
			 drapFlag=true;
		 }
	 }
});