(function ($) {
	var maxFieldLen=0;
	var ww=0;
	$.fn.searchBar = function (options) {
		var o=$(this);
		var id=o.attr("id");
		if($.isJson(options) ){
//			 var staDate = new Date();
			o.css("border-top","0px");
			createHtml(o,options);
			$.initWidget(id,options);
			o.find("button").css("margin","2px").css("broder","0px");
			center(id);
			/*var sh=o.height()+1;
			o.height(sh);*/
//			 alert(new Date - staDate);
		}else if(options=="center"){
			center(id);
		}else if(options=="clearForm"){
			$.clearForm("sbf_"+id);
		}else if(options=="resize"){
			$.clearForm("sbf_"+id);
		}
	};
	
	var createHtml=function(o,options){
		if(options.items){
			var obj=o.get(0);
			var oid=obj.id;
			obj.className="searchBar";
			var fo=$.newEl("form");
			$.addCls(fo,"formContext","sbf_"+oid);
			var ulo=$.newEl("ul");
			var fields=createFields(options.items);
			ulo.appendChild(fields);
			fo.appendChild(ulo);
			obj.appendChild(fo);
			if(options.button){
				var btns=createButton(oid,options.button);
				obj.appendChild(btns);
			}
			var mw=maxFieldLen*16;
			$(".searchBar label").css("width",mw+"px");
			$(".searchBar li").css("width",(mw+140)+"px");
			
		}
		
	};
	
	var createFields=function(items){
		var of=null;
		if(items.length>0){
			of = document.createDocumentFragment();
			for(var i=0;i<items.length;++i){
				var item=items[i];
				if(item.field){
					var len=item.field.length;
					if(len>maxFieldLen){
						maxFieldLen=len;
					}
					of.appendChild(createField(item));
				}
			}
		}
		return of;
	};
	
	var createField=function(item){
		var lio=$.newEl("li");
		$.newEl("li");
		var labo=$.newEl("label");
//		labo.className="labelLen";
		var to=$.newTn(item.field+":");
		labo.appendChild(to);
		var ino=$.newInput(item);
		lio.appendChild(labo);
		lio.appendChild(ino);
		return lio;
	};
	
	
	var createButton=function(oid,btns){
		var dvo=$.newEl("div");
		$.addCls(dvo,"btnContext","bc_"+oid);
		for(var i=0;i<btns.length;++i){
			var btn=btns[i];
			var bo=$.createBtn(btn);
			bo.rn=i;
			dvo.appendChild(bo);
		}
		return dvo;
	};
	
	var center=function(id){
			var bcw=$("#bc_"+id).width();
			var fow=$("#sbf_"+id).width();
			var sw=$("#"+id).width();
			if(sw-(bcw+fow)>0){
				$.addCls($("#bc_"+id).get(0),"last");
			}else{
				$("#bc_"+id).css("margin-left",(sw-bcw)/2+"px");
			}
	};
	
})(jQuery);