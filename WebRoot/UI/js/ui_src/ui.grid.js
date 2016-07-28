(function ($) {
	var f=false;
	var id="";
	var tdMap={};
	$.fn.tgrid = function (options) {
		
		if($.isJson(options) && !f){
			var o=$(this);
			id=o.attr("id");
			var div_main=o.get(0);
			$.addCls(div_main,"tgrid");
			o.width(options.width+"px");
			o.height(options.height+"px");
			creatHtml(o,div_main,options);
			
			
			var data=loadDate();
			$("#tgbt_"+id).get(0).appendChild(createCells(options.colModel,data));
			
			init(options);
			f=true;
		}
		
	};
	
	
	function init(options){
		$("#ms_"+id).click(function(){
			var cf=false;
			if(this.checked){
				cf=true;
			}else{
				cf=false;
			}
			$("#tgbt_"+id+" :checkbox").each(function(){
				this.checked=cf;
				var tr=$(this).parent().parent().get(0);
				if(cf){
					tr.className="clickOn";
				}else{
					if(tr.odd){
						tr.className="odd";
					}else{
						tr.className="";
					}
					
				}
				
			});
		});
		var cols=options.colModel;
		for(var i=0;i<cols.length;++i){
			var col=cols[i];
			tdMap[col.index]=i;
		}
		
		$("#tgbt_"+id).delegate("tr","click", function(){
			var cb=$(this).find("input").get(0);
			if(cb.type=="checkbox"){
				if(cb.checked){
					if(this.odd){
						this.className="odd";
					}else{
						this.className="";
					}
					cb.checked=false;
				}else{
				    this.className="clickOn";
				    cb.checked=true;
			    }
			}
			
	  });
		
		
	}
	
	function creatHtml(o,divObj,options){
		var tb_head=createTableHead(options.width,options.colModel);
		if(options.toolbar){
			var toolbar=creatToolbar();
			divObj.appendChild(toolbar);
		}
		
		divObj.appendChild(tb_head);
		
		var tb_body=createTableBody(options.width,options.height,options.toolbar,options.pager);
		divObj.appendChild(tb_body);
		
		if(options.pager){
			var p=createPager();
			divObj.appendChild(p);
		}
	}
	
	function createPager(){
		var div_p=$.newEl("div");
		div_p.className="tb-pager";
		div_p.id="p_"+id;
	    return  div_p;
	}
	
	
	function creatToolbar(){
			var div_tb=$.newEl("div");
			div_tb.className="tb-toolbar";
			div_tb.id="t_"+id;
		    return  div_tb;
	}
	
	function createTableBody(w,h,t,p){
		var div_b=$.newEl("div");
		$.addCls(div_b,"tb-body","tgb_"+id);
		
		var tb_b=$.newEl("table");
		$.addCls(tb_b,"tb-body-tb","tgbt_"+id);
		div_b.style.height=getTableBodyHeight(t,p)+"px";
		tb_b.style.width=(w-20)+"px";
		
		div_b.appendChild(tb_b);
		return div_b;
		
	}
	
	function getTableBodyHeight(t,p){
		var h=$("#"+id).height()-20;
		if(t){
			h-=36;
		}
		if(p){
			h-=32;
		}
		return h;
	}
	
	function createCells(cols,data){
		 var of = document.createDocumentFragment();
		 
		 
		 for(var i=0;i<data.length;++i){
			 var tr_h=$.newEl("tr");
			 var sn=createTbSelect("td");
			 tr_h.appendChild(sn);
			 var d=data[i];
			 addTD(tr_h,cols,d,sn);
			 if(i%2==1){
				 tr_h.className="odd";
				 tr_h.odd=true;
			 }
			 of.appendChild(tr_h);
             
		 }
		 return of;
	}
	
	function addTD(tr,cols,d,sn){
		for(var i=0;i<cols.length;++i){
			var col=cols[i];
			var index=col.index;
			
			var val=d[index];
			if(!val){
				val="";
			}
			if(!col.hidden){
				var td=$.newEl("td");
			    if(col.width){
					td.style.width=col.width+"px";	
				}
			    if(col.align){
			    	td.style.textAlign=col.align;
			    }
				var to=$.newTn(val);
				td.appendChild(to);
				tr.appendChild(td);
			}else{
				sn[index]=index;
			}
		}
	}
	
	
	
	function loadDate(){
		var d=null;
		d=[
		 {field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		/*,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		,{field1:"1111111",field2:"2222222",field3:"3333333",field4:"444444",field5:"555555",field6:"6666666",field7:"77777777",field8:"888888888",field9:"999999999"}
		*/];
		return d;
	}
	
	
	function createTableHead(w,cols){
		var div_h=$.newEl("div");
		$.addCls(div_h,"tb-head","tgh_"+id);
		
		var tb_h=$.newEl("table");
		$.addCls(tb_h,"tb-head-tb","tght_"+id);
		tb_h.style.width=(w-20)+"px";
		
		var tr_h=$.newEl("tr");
		
		tr_h.appendChild(createTbSelect("th"));
		
		
		if(cols && cols.length>0){
			for(var j=0;j<cols.length;++j){
				var col=cols[j];
				if(!col.hidden){
					var tho=$.newEl("th");
					if(col.width){
						tho.style.width=col.width+"px";
					}
					var tdon=$.newTn(col.text);
					tho.appendChild(tdon);
					tr_h.appendChild(tho);	
				}
			}
		}
		tb_h.appendChild(tr_h);
		div_h.appendChild(tb_h);
		return div_h;
	};
	
	
	function createTbSelect(t){
		var tdhon=$.newEl(t);
		tdhon.className="ms";
		
		var ino=$.newEl("input");
		ino.type="checkbox";
		if(t=="th"){
			ino.id="ms_"+id;
		}
		tdhon.appendChild(ino);
		
		
		
		return tdhon;
	};

	
})(jQuery);