(function($){var tabNum={};var tabMap={};var tabDownMap={};var tabLoadFlag={};var maxNum=6;$.fn.tab=function(options,item){var o=$(this);var id=o.attr("id");if(!options||$.isJson(options)){o.addClass("u-tab");var tabo=o.get(0);createHtml(o,tabo,options);regHandler(id);tabMap[id]={};tabNum[id]=0;tabLoadFlag[id]=true;if(options&&options.home){openTab(id,options.home)}}else{if(options=="add"){openTab(id,item)}else{if(options=="open"){openTab(id,item)}else{if(options=="resize"){resizeTab(o,id)}else{if(options=="setTabCount"){maxNum=item}}}}}};var initWidget=function(pannel,tid){if(pannel.length<1||!tid){return}$("#"+tid).layout({items:pannel})};var resizeTab=function(o,id){var w=o.width();var h=o.height();$("#"+id+" >div").each(function(){if(this.className=="tabtbr"){this.style.width=w+"px"}else{if(this.className=="tabpannel"){this.style.width=w-4+"px";this.style.height=h-36+"px";var tpo=$(this);var tpco=tpo.children();if(tpco){var pw=tpo.width();var ph=tpo.height();tpco.each(function(){this.style.width=pw+"px";this.style.height=ph+"px";var tpcco=$(this).children().eq(0);if(tpcco){tpcco.get(0).style.width=pw+"px";tpcco.get(0).style.height=ph+"px"}})}}}})};var createHtml=function(o,tabo,_items){var w=o.width();var h=o.height();var tbro=createTabbar(tabo.id,w);var pannelo=createTabPannel(tabo.id,w,h);tabo.appendChild(tbro);tabo.appendChild(pannelo)};var regHandler=function(id){$("#tb_tbr_"+id).delegate("span","click",function(event){if(this.tagName=="SPAN"&&this.className=="close"){var tid=$(this).parent().attr("id");closeTab(id,tid);event.stopPropagation()}});$("#tb_tbr_"+id).delegate("li","click",function(){if(!tabLoadFlag[id]){return}if($(this).hasClass("liup")){setTabDown(id,this.id)}})};var closeTab=function(id,tid){var to=$("#"+tid);if(to.hasClass("lidown")){var prevTid=to.prev().attr("id");setTabDown(id,prevTid)}removeTab(id,tid)};var removeTab=function(id,tid){(tabMap[id])[$("#"+tid+">:first-child").text()]=null;var t=$("#"+tid);t.remove();var tifid=t.get(0).tinfId;
clearIframe(tifid);$("#"+tifid).remove();var rmnum=--tabNum[id];tabNum[id]=rmnum};function clearIframe(id){var el=document.getElementById(id);if(!el){return}var iframe=el.contentWindow;if(el){if(navigator.userAgent.indexOf("MSIE")>0){el.src="";try{iframe.document.write("");iframe.document.clear()}catch(e){}CollectGarbage()}else{$("#"+id).remove()}}}var createTabPannel=function(id,w,h){var tabpannelo=$.newEl("div");$.addCls(tabpannelo,"tabpannel","tb_p_"+id);tabpannelo.style.width=w-4+"px";tabpannelo.style.height=h-36+"px";return tabpannelo};var createTabbar=function(id,w){var tbro=$.newEl("div");$.addCls(tbro,"tabtbr","tb_tbr_"+id);tbro.style.width=w+"px";return tbro};var changeTabUp=function(id){if(tabDownMap[id]){$("#"+tabDownMap[id]).removeClass("lidown");$("#"+tabDownMap[id]).addClass("liup");var o=$.getEl(tabDownMap[id]);if(o){$("#"+o.tinfId).hide()}}};var setTabDown=function(id,tid){var t=$("#"+tid);t.removeClass("liup");t.addClass("lidown");var o=t.get(0);if(o){$("#"+o.tinfId).show()}changeTabUp(id);tabDownMap[id]=tid};var openTab=function(id,item){var ttid=(tabMap[id])[item.name];if(!tabLoadFlag[id]){setTimeout(function(){openTab(id,item)},500);return}if(!ttid){if(tabNum[id]>=maxNum){alert("系统最多可打开"+maxNum+"个标签,请关闭其他已打开的标签后,才可打开此标签!");return}if(item.url){tabLoadFlag[id]=false}var titleo=createTabTitle(id,item);var w=$("#tb_p_"+id).width();var h=$("#tb_p_"+id).height();var tb_info=$.newEl("div");var tabInfId;if(item.id){tabInfId=item.id}else{tabInfId=titleo.id+"_t"}titleo.tinfId=tabInfId;$.addCls(tb_info,"tabInf",tabInfId);tb_info.style.width=w+"px";tb_info.style.height=h+"px";$("#tb_tbr_"+id).get(0).appendChild(titleo);$("#tb_p_"+id).get(0).appendChild(tb_info);setTabDown(id,titleo.id);if(item.url){var ifo=addURI(id,item.url);ifo.id=titleo.id+"_ti";tb_info.appendChild(ifo);ifo.src=item.url;var sto=setTimeout(function(){recoverFlag(id)},15000);$("#"+ifo.id).load(function(){window.clearTimeout(sto);tabLoadFlag[id]=true});if(!item.url&&item.pannel){initWidget(item.pannel,item.id)
}}else{if(!item.url&&item.pannel){initWidget(item.pannel,item.id)}}tabNum[id]=++tabNum[id]}else{if($("#"+ttid).hasClass("liup")){setTabDown(id,ttid)}}};var recoverFlag=function(id){tabLoadFlag[id]=true};var addURI=function(id,uri){var w=$("#tb_p_"+id).width();var h=$("#tb_p_"+id).height();var iframeo=$.newEl("iframe");iframeo.style.width=w+"px";iframeo.style.height=h+"px";iframeo.setAttribute("frameborder","0",0);iframeo.scrolling="no";return iframeo};var createTabTitle=function(id,item){var lio=$.newEl("li");var tid="tb_i_"+$.getRandom(100000);$.addCls(lio,"lidown",tid);var spano=$.newEl("span");if(item.name){var tx=$.newTn(item.name);spano.appendChild(tx);(tabMap[id])[item.name]=tid}lio.appendChild(spano);if(item.closed){var closeo=$.newEl("span");$.addCls(closeo,"close");lio.appendChild(closeo)}return lio}})(jQuery);
(function($){var nowZIndex=2;var oh={};var ow={};var updateFields={};var openfn={};var closefn={};$.fn.window=function(options,ot){var o=$(this);var id=o.attr("id");if(options=="open"){o.css("z-Index",nowZIndex++);if(ot){var otb=ot.btns;if(otb&&otb.length>0){$("#"+id+" .u-button").each(function(index){if(otb[index]&&otb[index]==1){$(this).hide()}else{$(this).show()}})}else{alert("btn为空")}if(ot.loadData){$.loadFormDate(id,ot.loadData,ot.param)}if(ot.mode&&ot.mode=="readOnly"){o.get(0).mro=true;$("#"+id+" *[name]").each(function(){this.disabled=true})}}ahead(o);open(o)}else{if(options=="close"){close(o)}else{if(options=="clear"){if(!o.get(0).mro){clearWinForm(o)}}else{if(options=="center"){center(o)}else{if(options=="getUpdateData"){return updateFields}else{if(options=="ahead"){ahead(o)}else{if($.isJson(options)){init(o,options)}}}}}}}};var ahead=function(o){var nowz=o.get(0).style.zIndex;$(".window").each(function(){var wi=this.style.zIndex;if(wi>nowz){this.style.zIndex=wi-1}});o.get(0).style.zIndex=nowZIndex};var closeZIndex=function(o){var nowz=o.get(0).style.zIndex;$(".window").each(function(){var wi=this.style.zIndex;if(wi>nowz){this.style.zIndex=wi-1}});--nowZIndex};var createHtml=function(o,options){var _ww=options.width;var _wh=options.height;var id=o.attr("id");o.addClass("window ui-widget-content");o.css("width",_ww+"px");o.css("height",_wh+"px");var oFragment=document.createDocumentFragment();var titleObj=createTitle(id,options.title);var winPannelObj=createWinPannel(id,_ww,_wh,options);oFragment.appendChild(titleObj);oFragment.appendChild(winPannelObj);if(options.button){var wbg=$.createBtnGroup(options.button);oFragment.appendChild(wbg)}o.get(0).appendChild(oFragment);setHeightDiv(id);oh[id]=_wh;ow[id]=_ww};function setHeightDiv(id){}var createTitle=function(id,title){var po=$.newEl("p");$.addCls(po,"wtitle");var spano=$.newEl("span");var texto=$.newTn(title);spano.appendChild(texto);var ao=$.newEl("a");ao.id="_wc_"+id;po.appendChild(spano);po.appendChild(ao);
return po};var createWinPannel=function(id,ww,wh,opt){var wp=$.newEl("div");$.addCls(wp,"winPanel","wp_"+id);wp.style.width=(ww-12)+"px";wp.style.height=(wh-75)+"px";var wc=$.newEl("div");$.addCls(wc,"winContext","wc_"+id);var cww=ww-16;var yuHeight=wh-86;wc.style.height=yuHeight+"px";wc.style.width=cww+"px";var optArr=opt.content;if(optArr){var f=true;var fnum=0;for(var i=0;i<optArr.length;++i){var opt=optArr[i];if(fnum>1){f=false;break}if(opt.type=="pannel"){++fnum}}for(var i=0;i<optArr.length;++i){var fo=createContent(id,optArr[i],yuHeight,cww,f);if(fo){wc.appendChild(fo)}}}wp.appendChild(wc);return wp};var createContent=function(id,opt,h,w,f){var fo=null;if(opt.type=="form"){var to=$.newEl("table");to.id="wct_"+id;fo=$.newEl("form");fo.id="wcf_"+id;var of=createAllInput(opt.items,opt.rowNum,id);to.appendChild(of);fo.appendChild(to)}else{if(opt.type=="pannel"){var divpo=$.newEl("div");$.addCls(divpo,"wpannel","p_"+opt.id);if(opt.height){divpo.style.height=opt.height+"px"}else{}if(opt.width){divpo.style.width=opt.width+"px"}else{if(f){divpo.style.width=($("#"+id).width()-20)+"px"}}var fso=$.newEl("fieldset");divpo.appendChild(fso);var flsH=opt.height;if(opt.field){var lo=$.newEl("legend");var t=$.newTn(opt.field);lo.appendChild(t);fso.appendChild(lo);flsH-=16}else{fso.style.marginTop="7px";flsH-=9}var divo=$.newEl("div");$.addCls(divo,"dp",opt.id);divo.style.width=(opt.width-12)+"px";divo.style.height=flsH+"px";fso.appendChild(divo);fso.ptid=opt.id;fo=divpo}}return fo};var sortTheHidden=function(items){var len=items.length;var nArray=new Array(len);var j=0;var k=len-1;for(var i=0;i<len;++i){var item=items[i];if(item.type=="hidden"){nArray[k]=items[i];--k}else{nArray[j]=item;++j}}items=null;return nArray};var createAllInput=function(arr,num,id){arr=sortTheHidden(arr);var of=document.createDocumentFragment();var len=arr.length;var tro=null;var n=0;var sort=1;for(var i=0;i<len;++i){if(arr[i].type=="textarea"&&(n!=num||n!=0)){if(tro){of.appendChild(tro)}n=0}if(n==0){tro=$.newEl("tr")
}var f=(n==num-1);if(arr[i].type!="hidden"){createInput(tro,arr[i],num,f,sort);++sort}else{createInput(tro,arr[i],num,f)}++n;if(n==num||i==(len-1)||arr[i].type=="textarea"){of.appendChild(tro);n=0}}return of};var createInput=function(tro,obj,num,f,s){var tdo=$.newEl("td");if(obj.type!="hidden"){var tdtno=$.newTn(obj.field+":");tdo.appendChild(tdtno)}tro.appendChild(tdo);var tdino=$.newEl("td");var ino=$.newInput(obj);if(s){ino.wsort=s}if(obj.type=="textarea"){tdo.vAlign="top";tdino.colSpan=num*2-1;ino.style.width="98.5%";if(obj.height){ino.style.height=obj.height+"px"}}else{if(!f){tdino.style.width="180px"}}tdino.appendChild(ino);tro.appendChild(tdino)};var init=function(o,options){var id=o.attr("id");createHtml(o,options);initWindow(o);$.initWidget(id,options);$("#_wc_"+id).click(function(){var p=$(this).parent().parent();close(p)});o.find("p").mousedown(function(){$("table.lcui_border").parent().hide()});if(options.openHandler){openfn[id]=options.openHandler}if(options.closeHandler){closefn[id]=options.closeHandler}$.onFormValidator(id);$.drag(id);if(!o.get(0).mro&&options.enterTab){var ipt=$("#"+id+" *[name]");var len=ipt.length;ipt.keydown(function(e){var key=e.keyCode;if(key==13){var s=this.wsort+1;if(s>len){$("#"+id+" .u-button").each(function(){if(this.sbt&&this.style.display!="none"){$(this).select();return false}});return}ipt.each(function(index){if(this.wsort==s){if(!(this.readOnly||this.disabled)){$(this).focus();if(this.tagName=="SELECT"){}$(this).select();this.click();return false}else{++s}}})}})}};var initWindow=function(o){o.hide()};var open=function(o){var id=o.attr("id");$.modal();o.show();$.centerEle(o.find(".btn_group").attr("id"));center(o);if(!o.get(0).mro){if(openfn[id]){openfn[id]()}}};var center=function(o){var ww=$(window).width();var wh=$(window).height();var id=o.attr("id");var _top=(wh-oh[id])/2+$(document).scrollTop();var _left=(ww-ow[id])/2+$(document).scrollLeft();o.css({"top":_top+"px","left":_left+"px"})};var close=function(o){var id=o.attr("id");
if(closefn[id]){closefn[id]()}clearWinForm(o);$.modalClose();closeZIndex(o);o.hide();if(o.get(0).mro){$("#"+id+" *[name]").each(function(){this.disabled=""});o.get(0).mro=false}$.rmAllValiTn(id)};var clearWinForm=function(o){var id=o.find("form").attr("id");if(id){$.clearForm(id,updateFields)}}})(jQuery);
(function($){$.fn.pannel=function(options){if($.isJson(options)){var o=$(this);createHtml(o,options);if(options.content&&options.content.tbr){initToolbar(options)}}};var createHtml=function(o,opt){var divo=o.get(0);var pw=opt.width;var ph=opt.height;o.addClass("pannel");o.width(pw+"px");o.height(ph+"px");var ctw=pw;var cth=ph;_content=opt.content;if(opt.title){var po=$.newEl("p");$.addCls(po,"title");var spano=$.newEl("span");var texto=$.newTn(opt.title);spano.appendChild(texto);po.appendChild(spano);divo.appendChild(po);if(_content){cth=ph-32}}if(_content){var contento=$.newEl("div");contento.style.width=ctw+"px";contento.style.height=cth+"px";$.addCls(contento,"content");var _tbr=_content.tbr;var dw=ctw;var dh=cth;if(_tbr){var tbro=createToolbar(_content.id,_tbr,dw);contento.appendChild(tbro);dh-=32}var pdiv=createPDiv(_content.id,dw,dh);contento.appendChild(pdiv);divo.appendChild(contento)}};var createPDiv=function(id,dw,dh){var pdivo=$.newEl("div");$.addCls(pdivo,"pdiv",id);pdivo.style.width=dw+"px";pdivo.style.height=dh+"px";return pdivo};var createToolbar=function(id,tbr,dw){var tbro=$.newEl("div");var _tid=tbr.id;if(!_tid){_tid=_content+"_tbr"}$.addCls(tbro,"tbr",_tid);tbro.style.width=dw+"px";return tbro};var initToolbar=function(options){var _tbr=options.content.tbr;if(_tbr){var t_id=_tbr.id;if(!t_id){t_id=options.content.id+"_tbr"}$("#"+t_id).toolbar(_tbr)}}})(jQuery);(function($){var valiReg=/^0.[0-9]*$/;$.fn.layout=function(options){if($.isJson(options)){var o=$(this);initLayout(o,options)}};var initLayout=function(o,options){var pw=0;var ph=0;if(o.get(0).tagName=="BODY"){pw=$(window).width();ph=$(window).height()}else{pw=o.width();ph=o.height()}var items=options.items;var lyMap={};var mag=options.margin;if(!mag){mag=1}if(items){for(var i=0;i<items.length;++i){var item=items[i];getPannelSize(pw,ph,item,lyMap)}fitSzie(pw,ph,lyMap,mag);for(var i=0;i<items.length;++i){var item=items[i];createPannel(o,item,lyMap);arrangePannel(mag,o,item,lyMap)}}};var fitSzie=function(pw,ph,lyMap,mag){var ch=ph;
var cw=pw;if(lyMap["top"]){lyMap["top"].w=pw-(2+2*mag);ch=ch-lyMap["top"].h;lyMap["top"].h=lyMap["top"].h-(2+mag)}else{lyMap["top"]={};lyMap["top"].w=0;lyMap["top"].h=0}if(lyMap["bottom"]){lyMap["bottom"].w=pw-(2+2*mag);ch=ch-lyMap["bottom"].h;lyMap["bottom"].h=lyMap["bottom"].h-(2+2*mag)}else{lyMap["bottom"]={};lyMap["bottom"].w=0;lyMap["bottom"].h=0}if(ch==ph||lyMap["bottom"].h==0){if(lyMap["top"].h==0){ch=ch-(2+2*mag)}else{ch=ch-(3+2*mag)}}else{ch=ch-(2+mag)}if(lyMap["left"]){lyMap["left"].h=ch;cw=cw-lyMap["left"].w;lyMap["left"].w=lyMap["left"].w-(2+mag)}else{lyMap["left"]={};lyMap["left"].w=0;lyMap["left"].h=0}if(lyMap["right"]){lyMap["right"].h=ch;if(lyMap["center"]){cw=cw-lyMap["right"].w;lyMap["right"].w=lyMap["right"].w-(2+mag)}else{lyMap["right"].w=cw-(2+mag*2)}}else{lyMap["right"]={};lyMap["right"].w=0;lyMap["right"].h=0}if(lyMap["center"]){lyMap["center"].h=ch;lyMap["center"].w=cw-(2+mag*2)}else{lyMap["center"]={};lyMap["center"].w=0;lyMap["center"].h=0}};var getPannelSize=function(pw,ph,item,lyMap){if(item){if(item.region){var w=0;var h=0;lyMap[item.region]={};if(valiNum(item.width)){w=pw*item.width}else{w=item.width}if(valiNum(item.height)){h=ph*item.height}else{h=item.height}lyMap[item.region].w=w;lyMap[item.region].h=h}}};var createPannel=function(o,item,lyMap){if(item&&item.region){var fo=o.get(0);var pid=item.id;var divo=$.newEl("div");divo.region=item.region;if(!pid){pid=item.region+"_"+$.getRandom(1000);item.id=pid}divo.id=pid;fo.appendChild(divo);item.width=lyMap[item.region].w;item.height=lyMap[item.region].h;$("#"+pid).pannel(item)}};var valiNum=function(num){return valiReg.test(num)};var arrangePannel=function(mag,o,item,lyMap){var po=$("#"+item.id);var pt=0;var pl=0;if(item.region=="top"){pt=mag;pl=mag}else{if(item.region=="left"){pl=mag;if(lyMap["top"].h==0){pt=mag}else{pt=2+2*mag+lyMap["top"].h}}else{if(item.region=="center"){if(lyMap["left"].w==0){pl=mag}else{pl=2+2*mag+lyMap["left"].w}if(lyMap["top"].h==0){pt=mag}else{pt=2+2*mag+lyMap["top"].h
}}else{if(item.region=="right"){if(lyMap["left"].w!=0&&lyMap["center"].w!=0){pl=4+3*mag+lyMap["left"].w+lyMap["center"].w}else{if(lyMap["center"].w==0&&lyMap["left"].w==0){pl=mag}else{pl=2+2*mag+lyMap["left"].w+lyMap["center"].w}}if(lyMap["top"].h==0){pt=mag}else{pt=2+2*mag+lyMap["top"].h}}else{if(item.region=="bottom"){var ch=0;if(lyMap["left"].h>0){ch=lyMap["left"].h}if(lyMap["center"].h>0){ch=lyMap["center"].h}if(lyMap["right"].h>0){ch=lyMap["right"].h}if(ch==0){pt=2+2*mag+lyMap["top"].h+ch}else{pt=4+3*mag+lyMap["top"].h+ch}pl=mag}}}}}po.css({"top":pt+"px","left":pl+"px"})}})(jQuery);
(function($){$.fn.toolbar=function(options){if($.isJson(options)){var o=$(this);var id=o.attr("id");var tbro=o.get(0);$.addCls(tbro,"u-tbr");var _items=options.items;var ajaxFlag=false;for(var i=0;i<_items.length;++i){var item=_items[i];if(item.code){ajaxFlag=true;break}}var uri=window.location.pathname;if(ajaxFlag){$.ajax({type:"post",url:"getOperate.do",data:{"uri":uri},async:true,dataType:"json",success:function(d){if(_items&&_items.length>0){createHtml(o,_items,d)}$.initWidgetBtn(id,_items)}})}else{if(_items&&_items.length>0){createHtml(o,_items,"null")}$.initWidgetBtn(id,_items)}}};function getCookie(objName){var arrStr=document.cookie.split("; ");for(var i=0;i<arrStr.length;i++){var temp=arrStr[i].split("=");if(temp[0]==objName){return unescape(temp[1])}}}var createHtml=function(o,_items,pids){var of=document.createDocumentFragment();var f=true;var parr=[];if(pids){parr=pids.split(",")}for(var i=0;i<_items.length;++i){var item=_items[i];var bo=null;if(item=="-"){if(f){bo=createSeparator();if(i>0){f=false}}}else{if(!item.type||item.type=="button"){if(!item.code){f=true;bo=createBtn(item);item.id=bo.id;if(item.on){regClickFunc(bo,item.on)}}else{if(item.code&&isHasBtn(parr,item.code)){bo=createBtn(item);item.id=bo.id;f=true;if(item.on){regClickFunc(bo,item.on)}}}}}if(bo){of.appendChild(bo)}}o.get(0).appendChild(of);initAllBtn(_items);var nextNode=o.next().get(0);if(nextNode){if(nextNode.className.indexOf("ui-jqgrid")>-1){o.css("border-bottom","0px")}}};var isHasBtn=function(pArr,code){var f=false;if(pArr&&pArr.length>0){for(var i=0;i<pArr.length;++i){if(code==pArr[i]){f=true;break}}}return f};var initAllBtn=function(_items){for(var i=0;i<_items.length;++i){var item=_items[i];if(item!="-"&&(!item.type||item.type=="button")){if(item.id&&item.on){regClickFunc(item.id,item.on)}}}};var regClickFunc=function(bid,on){$("#"+bid).click(on)};var createBtn=function(item){var ao=$.newEl("a");$.addCls(ao,"u-button",item.id);if(!ao.id){ao.id="tbr_"+$.getRandom(1000)}if(item.icon){var icono=$.newEl("span");
$.addCls(icono,"u-icon");icono.style.backgroundImage="url("+item.icon+")";ao.appendChild(icono)}var nameo=$.newEl("span");var tx=$.newTn(item.name);nameo.appendChild(tx);ao.appendChild(nameo);return ao};var createSeparator=function(){var so=$.newEl("span");$.addCls(so,"u-separator");return so}})(jQuery);(function($){$.fn.ui_button=function(options){if($.isJson(options)){var o=$(this);var tagFlag=0;var ta=o.get(0).tagName;if(ta=="A"){tagFlag=1}else{if(ta=="BUTTON"){tagFlag=2}}if(tagFlag>0){createHtml(o,options,tagFlag)}}};var createHtml=function(o,item,taf){var bo=o.get(0);$.addCls(bo,"u-button");if(taf>1){$.addCls(bo,"u-button-sd")}var nameo=$.newEl("span");var tx=$.newTn(item.name);nameo.appendChild(tx);bo.appendChild(nameo);if(item.icon){var icono=$.newEl("span");$.addCls(icono,"u-icon");icono.style.backgroundImage="url("+item.icon+")";bo.appendChild(icono)}if(item.handler){o.click(item.handler)}}})(jQuery);
var fullData={};var _move=false;var _x,_y;var maxTop=0;var maxLeft=0;var drapFlag=false;var dragWin=null;$(function(){fullData.w=$(window).width();fullData.h=$(window).height();$(window).resize(function(){var rw=$(window).width();$(".modal").width(rw);$(document.body).children().each(function(){if(this.className.indexOf("ui-jqgrid")>-1){var gid=this.id.split("_")[1];$("#"+gid).setGridWidth(rw)}});$(".window").each(function(){$("#"+this.id).window("center")})})});$.extend({rm_ajax:function(r){var _t=r.type?r.type:"get";var _u=r.url;var _a=r.async==false?r.async:true;var _d=r.data?r.data:null;var _dt=r.dataType?r.dataType:"json";$.ajax({type:_t,url:_u,async:_a,data:_d,dataType:_dt,success:function(d){if(d.success>-1){if(d.success===0){if(r.success){if(d.msg){alert(d.msg)}else{alert("操作成功")}r.success()}}else{if(d.success===1){if(d.msg){alert(d.msg)}else{alert("操作失败!")}}else{if(d.success===2){alert("后台异常!"+"\n"+d.msg)}else{if(d.success===3){alert("您登陆已超时,或此账号已在其他电脑登陆,请重新登录!")}else{if(d.success===4){alert("您无此操作权限!")}}}}}}else{r.success(d)}},error:function(XMLHttpRequest,textStatus,errorThrown){if(XMLHttpRequest.status!="200"){alert("后台异常->状态码:"+XMLHttpRequest.status+"; 请求完毕状态码:"+XMLHttpRequest.readyState+"; 文本状态:"+textStatus+"\n"+"异常:"+errorThrown)}}})},modal:function(){var o=null;if(!$.getEl("_mw")){$("body").append("<div id='_mw' class='modal'/>")}$(document.body).css("overflow","hidden");o=$("#_mw");var w=$(window).width();var h=$(window).height();o.css("width",w+"px");o.css("height",h+"px");o.show()},modalClose:function(){$(document.body).css("overflow","auto");$("#_mw").hide()},clearForm:function(id){$("#"+id)[0].reset()},isJson:function(obj){return typeof(obj)=="object"&&Object.prototype.toString.call(obj).toLowerCase()=="[object object]"&&!obj.length},newEl:function(e){return document.createElement(e)},getEl:function(id){return document.getElementById(id)},addIframe:function(pid,url){var p=$("#"+pid);var po=$.getEl(pid);var w=p.width();var h=p.height();var iframeo=$.newEl("iframe");
var fid="ifo_"+$.getRandom(10000);iframeo.id=fid;iframeo.style.width=w+"px";iframeo.style.height=h+"px";iframeo.setAttribute("frameborder","0",0);iframeo.scrolling="no";iframeo.src=uri;po.appendChild(iframeo);po.ifoid=fid},flushIframe:function(pid){var ifid=$.getEl(pid).ifoid;if(ifid){document.frames(ifid).document.location.reload(true)}},newTn:function(t){return document.createTextNode(t)},createSelect:function(obj){var ino=this.newEl("select");if(obj.readOnly==true){ino.disabled=true}if(obj.url){$.ajax({type:"get",url:obj.url,async:true,dataType:"json",success:function(d){if(d&&d.length>0){if(obj.blankText){ino.options.add(new Option(obj.blankText,""));ino.btxt=obj.blankText}for(var i=0;i<d.length;++i){var dd=d[i];if(dd.value){var op=new Option(dd.text,dd.value);ino.options.add(op)}}}}});if(obj.eachLoad){ino.i=0;$(ino).click(function(){var so=this;$.ajax({type:"get",url:obj.url,async:false,dataType:"json",success:function(d){if(d&&d.length>0){$(so).html("");if(obj.blankText){ino.options.add(new Option(obj.blankText,""));ino.btxt=obj.blankText}for(var i=0;i<d.length;++i){var dd=d[i];if(dd.value){var op=new Option(dd.text,dd.value);op.optt=true;so.options.add(op);$(op).click(function(){return false})}}}}})})}}else{if(obj.blankText){ino.options.add(new Option(obj.blankText,""));ino.btxt=obj.blankText}if(obj.option&&obj.option.length>0){for(var i=0;i<obj.option.length;++i){var op=new Option(obj.option[i].text,obj.option[i].value);ino.options.add(op)}}}if(obj.changeFn){$(ino).change(function(){var txt=$(this).find("option:selected").text();var val=$(this).val();obj.changeFn(txt,val)})}return ino},addOptions:function(sid,d){var ino=$.getEl(sid);if(ino&&ino.tagName=="SELECT"){$(ino).html("");if(ino.btxt){ino.options.add(new Option(ino.btxt,""))}for(var i=0;i<d.length;++i){var dd=d[i];if(dd.value){var op=new Option(dd.text,dd.value);ino.options.add(op)}}}},newInput:function(obj){var ino;if(obj.type=="select"){ino=$.createSelect(obj)}else{if(obj.type=="date"){ino=this.newEl("input");
ino.wtype="d";if(!obj.id){ino.id="d_"+this.getRandom(100)}ino.fm=obj.format;ino.rs=obj.conf}else{if(obj.type=="hidden"){ino=this.newEl("input");ino.type="hidden"}else{if(obj.type=="password"){ino=this.newEl("input");ino.type="password"}else{if(obj.type=="textarea"){ino=this.newEl("textarea");ino.style.fontSize="13px"}else{ino=this.newEl("input");ino.type="text"}}}}}if(obj.readOnly==true&&obj.type!="select"){ino.readOnly=true}if(obj.disabled==true){ino.disabled=true}if(obj.name){ino.name=obj.name}if(obj.id){ino.id=obj.id}else{ino.id="f_ino_"+this.getRandom(10000)}if(obj.value){ino.value=obj.value}if(obj.type!="hidden"){$.addvalidate(ino,obj)}return ino},getFormDate:function(id){var para={};$("#"+id+" *[name]").each(function(){var val=this.value;if(this.name&&val){para[this.name]=this.value}});return para},clearGridPostParam:function(gid){var f=$("#"+gid).jqGrid("getGridParam","postData");for(var p in f){delete f[p]}},getUpdateDate:function(para,uf){for(var p in para){if(!uf[p]&&uf[p]>-1){delete para[p]}}return para},createBtnGroup:function(bs){var wbg=this.newEl("div");this.addCls(wbg,"btn_group","bg_"+this.getRandom(1000));for(var i=0;i<bs.length;++i){var o=bs[i];var bo=this.createBtn(o);bo.rn=i;wbg.appendChild(bo)}return wbg},createBtn:function(bs){var bo=$.newEl("button");$.addCls(bo,"u-button u-button-sd",bs.id);if(!bo.id){bo.id="btn_"+$.getRandom(1000)}if(bs.icon){var icono=$.newEl("span");$.addCls(icono,"u-icon");icono.style.backgroundImage="url("+bs.icon+")";bo.appendChild(icono)}else{if(bs.type||bs.on){var icono=$.newEl("span");$.addCls(icono,"u-icon");if(bs.type=="close"){$.addCls(icono,"closeIcon")}else{if(bs.type=="clear"){$.addCls(icono,"clearIcon")}else{$.addCls(icono,"submitIcon")}}bo.appendChild(icono)}}var nameo=$.newEl("span");var tx=$.newTn(bs.name);nameo.appendChild(tx);bo.appendChild(nameo);return bo},initWidgetBtn:function(id,item,opt){var bs=item;$("#"+id+" .u-button").each(function(){var b=bs[this.rn];if(!b){return true}if(b.type=="close"){$.closeBtnFn($(this),id)
}else{if(b.type=="submit"){if(b.url){if(opt){this.u=opt}this.sbt=true;$.postBtnFn($(this),b,id,b.beforeSubmit)}}else{if(b.type=="clear"){}else{if(b.on){$.btnFn($(this),b.on)}}}}})},initWidget:function(id,options){if(options.button){this.initWidgetBtn(id,options.button,options.opt)}$("#"+id+" *[name]").each(function(){if(this.wtype=="d"){var dto=this,q=this.rs,sdf=null;if(!q){q={}}else{sdf=this.rs.onSetDate}q["format"]=this.fm;var sto=function(){$.rmValiTn(dto);$.rmTip(dto)};if(sdf){q["onSetDate"]=function(){sdf();setTimeout(sto,240)}}else{q["onSetDate"]=function(){setTimeout(sto,240)}}$("#"+this.id).calendar(q);delete this.rs}else{if(this.sign){var o1=$.getEl(this.Ele);if(o1){o1.cpv=this.id;o1.vd=true}}}})},closeBtnFn:function(o,id){o.click(function(){$("#"+id).window("close")})},postBtnFn:function(o,b,id,bs){o.click(function(){var para=$.getFormDate(id);var bsf=true;var gid=this.u;if(bs){bsf=bs()}if($.validateForm("wcf_"+id)&&bsf){$.rm_ajax({type:"post",url:b.url,data:para,async:true,dataType:"json",success:function(d){$.closeWinAndFlush(id,gid)}})}})},btnFn:function(o,fn){o.click(fn)},addCls:function(o,cls,id){if(id){o.id=id}var ocls="";if(o.className){ocls=o.className+" "}o.className=ocls+cls},getRandom:function(n){return Math.floor(Math.random()*n+1)},flushGrid:function(gid){if(!gid){return}if(gid.indexOf(",")>0){var _gs=gid.split(",");for(var i=0;i<_gs.length;++i){var g=_gs[i];$("#"+g).jqGrid("setGridParam",{datatype:"json",postData:{},page:1}).trigger("reloadGrid")}}else{$("#"+gid).jqGrid("setGridParam",{datatype:"json",postData:{},page:1}).trigger("reloadGrid")}},closeWinAndFlush:function(id,gid){$("#"+id).window("close");if(gid){$.flushGrid(gid)}},centerEle:function(id){var l=($("#"+id).parent().width()-$("#"+id).width())/2;$("#"+id).css("left",l+"px")},loadFormDate:function(id,url,para){$.ajax({type:"post",url:url,data:para,async:false,dataType:"json",success:function(d){$("#"+id+" *[name]").each(function(){if(this.name){var _val=d[this.name];if(_val||_val==0){this.value=_val;
if(this.vFn){this.oleVal=this._val}}}})},error:function(XMLHttpRequest,textStatus,errorThrown){alert("后台异常:"+XMLHttpRequest.status+"|"+XMLHttpRequest.readyState+"|"+textStatus)}})},createTip:function(t,tn){var to=$(t);var h=to.height();var t=to.offset().top+h+8;var l=to.offset().left;var tid="tt_"+$.getRandom(1000);if(tn){$(document.body).append("<div class='u-tooltip' id='"+tid+"' style='top:"+t+"px;left:"+l+"px;'>"+"<span class='tip-word'>"+tn+"</span></div>");to.get(0).ttid=tid}},rmTip:function(t){$("#"+t.ttid).remove()},addvalidate:function(ino,obj){var f=false;if(obj.allowBlank==false){ino.ab=1;f=true}if(obj.regex){ino.vr=obj.regex;ino.vrt=obj.regexText;f=true}if(obj.compare){ino.sign=obj.compare.sign;ino.Ele=obj.compare.Ele;ino.ct=obj.compare.text;f=true}if(obj.max&&!isNaN(obj.max)){ino.max=obj.max;f=true}if(obj.min&&!isNaN(obj.min)){ino.min=obj.min;f=true}if(obj.maxLen&&!isNaN(obj.maxLen)){ino.maxLen=obj.maxLen;f=true}if(obj.minLen&&!isNaN(obj.minLen)){ino.minLen=obj.minLen;f=true}if(obj.validLenText){ino.vLenTxt=obj.validLenText}if(obj.validFn){ino.vFn=obj.validFn;ino.vFntxt=obj.validFnText;f=true}if(f){ino.vd=true}},reg:{"number":/^0$|^[1-9]+[0-9]*$/,"email":/^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/,"phone":/^[0][1-9]{2,3}-[0-9]{5,10}$/,"money":/^[0-9]+[\.][0-9]{0,3}$/,"mobile":/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/},validateForm:function(fid){var f=true;$("#"+fid+" *[name]").each(function(){if(this.vd){if(!$.validatorData(this)){f=false}}});return f},validatorData:function(o){if(!o||o.disabled||o.readOnly){return true}var f=true;var val=o.value;f=$.validatorBlank(o,val);if(val){if(o.vr&&f){f=$.validatorRegex(o,val)}if(f){f=$.validatorLength(o,val)}if(o.sign&&f){f=$.validatorCompare(o,val)}if(o.vFn&&f){if(o.oleVal&&o.oleVal==val){f=$.validatorFn(o,val)}}}if(!f&&!o.className){o.className="verify"}else{if(f&&o.className){o.className=""}}if(o.cpv){var o1=$.getEl(o.cpv);if(o1){obj=$.getEl(o.cpv);$.validatorData(obj)}}return f},validatorBlank:function(obj,val){var f=true;
if(obj.ab&&!val){obj.ttn="必填项,不能为空!";f=false}return f},getRealLen:function(str){return str.replace(/[^\x00-\xff]/g,"___").length},validatorLength:function(obj,val){var f=true;var min=-1,max=-1;if(obj.vr=="number"){var v=parseInt(val,10);if(obj.min){min=parseInt(obj.min,10)}if(obj.max){max=parseInt(obj.max,10)}if(max>0&&min>0&&!(v>=min&&v<=max)){obj.ttn="此项数字需在"+min+"-"+max+"的范围内";f=false}else{if(max>0&&max<v){obj.ttn="此项不能大于"+max;f=false}else{if(min>0&&min>v){obj.ttn="此项不能小于"+min;f=false}}}}else{var len=$.getRealLen(val);if(obj.maxLen){max=parseInt(obj.maxLen,10)}if(obj.minLen){min=parseInt(obj.minLen,10)}if(max>0&&min>0&&!(len>=min&&len<=max)){obj.ttn="此项的字节长度需在"+min+"-"+max+"的范围内";f=false}else{if(max>0&&max<len){obj.ttn="此项字节长度不能大于"+max;f=false}else{if(min>0&&min>len){obj.ttn="此项字节长度不能小于"+min;f=false}}}}return f},validatorFn:function(obj,val){var f=false;if(obj.vFn(obj.name,val)){f=true}else{obj.ttn=obj.vFntxt}return f},validatorRegex:function(obj,val){var f=true;var r=null;var t="";if(obj.vr=="number"){t="此项必须为数字"}else{if(obj.vr=="email"){r=this.reg.email;t="此项必须为email格式"}else{if(obj.vr=="phone"){r=this.reg.phone;t="此项必须为电话号码格式"}else{if(obj.vr=="money"){r=this.reg.money;t="此项必须为金钱格式"}else{if(obj.vr=="mobile"){r=this.reg.mobile;t="此项必须为手机格式"}else{if(obj.vr=="ip"){t="此项必须为ip格式"}else{r=obj.vr}}}}}}if(obj.vr=="number"){if(isNaN(val)||$.isUnRuleZero(val)){if(obj.vrt){obj.ttn=obj.vrt}else{obj.ttn=t}f=false}}else{if(obj.vr=="ip"){if(!$.isIP(val)){if(obj.vrt){obj.ttn=obj.vrt}else{obj.ttn=t}f=false}}else{if(!r.test(val)){if(obj.vrt){obj.ttn=obj.vrt}else{obj.ttn=t}f=false}}}return f},isUnRuleZero:function(v){var f=false;var arr=(v+"").split("");if(arr.length>1){var a1=arr[0];var a2=arr[1];if(a1=="0"&&a2!="."){f=true}else{if(a1=="-"&&a2=="0"&&arr[2]!="."){f=true}else{if(a1=="."||(a1=="-"&&a2==".")){f=true}}}}return f},validatorCompare:function(obj,val){var f=true;var os=obj.sign;var ev=$.getEl(obj.Ele).value;if(os=="="){if(val!=ev){f=false}}else{if(os==">"){if(parseInt(val,10)<=parseInt(ev,10)){f=false
}}else{if(os==">="){if(parseInt(val,10)<parseInt(ev,10)){f=false}}else{if(os=="<"){if(parseInt(val,10)>=parseInt(ev,10)){f=false}}else{if(os=="<="){if(parseInt(val,10)>parseInt(ev,10)){f=false}}else{if(os=="!="){if(val==ev){f=false}}}}}}}if(!f){obj.ttn=obj.ct}return f},onFormValidator:function(id){$("#"+id).delegate("input,textarea","blur",function(){$.formHandler(this)});$("#"+id).delegate("input,textarea,select","focus",function(){$.rmValiTn(this);$.rmTip(this)});$("#"+id).delegate("input,textarea,select","mouseenter",function(){if(this.vd&&this.className=="verify"){$.createTip(this,this.ttn)}});$("#"+id).delegate("input,textarea,select","mouseleave",function(){if(this.vd){$.rmTip(this)}})},rmValiTn:function(o){o.className="";o.ttn=""},rmAllValiTn:function(id){$("#"+id+" *[name]").each(function(){if(this.vd){$.rmValiTn(this)}})},formHandler:function(obj){if(!obj.vd){return}$.validatorData(obj)},isIP:function(ip){var reSpaceCheck=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;if(reSpaceCheck.test(ip)){ip.match(reSpaceCheck);if(RegExp.$1<=255&&RegExp.$1>=0&&RegExp.$2<=255&&RegExp.$2>=0&&RegExp.$3<=255&&RegExp.$3>=0&&RegExp.$4<=255&&RegExp.$4>=0){return true}else{return false}}else{return false}},drag:function(id){$("#"+id).mousedown(function(){$(this).window("ahead")});$("#"+id+" p").mousedown(function(e){_move=true;var winObj=$(this).parent();$.rmAllValiTn(winObj.get(0).id);_x=e.pageX-parseInt(winObj.css("left"));_y=e.pageY-parseInt(winObj.css("top"));maxLeft=$(window).width()-winObj.width();maxTop=$(window).height()-winObj.height();winObj.css("cursor","move");dragWin=winObj});if(!drapFlag){$(document).mousemove(function(e){if(_move&&dragWin){var x=e.pageX-_x;var y=e.pageY-_y;if((x<0||x>maxLeft)||(y<0||y>maxTop)){if(x<0){x=0}if(y<0){y=0}if(x>maxLeft){x=maxLeft}if(y>maxTop){y=maxTop}}dragWin.css({top:y,left:x})}}).mouseup(function(){if(dragWin){dragWin.css("cursor","")}dragWin=null;_move=false;maxLeft=0;maxTop=0});drapFlag=true}}});
(function($){var maxFieldLen=0;var ww=0;$.fn.searchBar=function(options){var o=$(this);var id=o.attr("id");if($.isJson(options)){o.css("border-top","0px");createHtml(o,options);$.initWidget(id,options);o.find("button").css("margin","2px").css("broder","0px");center(id)}else{if(options=="center"){center(id)}else{if(options=="clearForm"){$.clearForm("sbf_"+id)}else{if(options=="resize"){$.clearForm("sbf_"+id)}}}}};var createHtml=function(o,options){if(options.items){var obj=o.get(0);var oid=obj.id;obj.className="searchBar";var fo=$.newEl("form");$.addCls(fo,"formContext","sbf_"+oid);var ulo=$.newEl("ul");var fields=createFields(options.items);ulo.appendChild(fields);fo.appendChild(ulo);obj.appendChild(fo);if(options.button){var btns=createButton(oid,options.button);obj.appendChild(btns)}var mw=maxFieldLen*16;$(".searchBar label").css("width",mw+"px");$(".searchBar li").css("width",(mw+140)+"px")}};var createFields=function(items){var of=null;if(items.length>0){of=document.createDocumentFragment();for(var i=0;i<items.length;++i){var item=items[i];if(item.field){var len=item.field.length;if(len>maxFieldLen){maxFieldLen=len}of.appendChild(createField(item))}}}return of};var createField=function(item){var lio=$.newEl("li");$.newEl("li");var labo=$.newEl("label");var to=$.newTn(item.field+":");labo.appendChild(to);var ino=$.newInput(item);lio.appendChild(labo);lio.appendChild(ino);return lio};var createButton=function(oid,btns){var dvo=$.newEl("div");$.addCls(dvo,"btnContext","bc_"+oid);for(var i=0;i<btns.length;++i){var btn=btns[i];var bo=$.createBtn(btn);bo.rn=i;dvo.appendChild(bo)}return dvo};var center=function(id){var bcw=$("#bc_"+id).width();var fow=$("#sbf_"+id).width();var sw=$("#"+id).width();if(sw-(bcw+fow)>0){$.addCls($("#bc_"+id).get(0),"last")}else{$("#bc_"+id).css("margin-left",(sw-bcw)/2+"px")}}})(jQuery);
