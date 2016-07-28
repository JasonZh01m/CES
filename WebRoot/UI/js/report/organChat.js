$(function(){
	
	$(document.body).layout({
		items:[
        { 
        	id:"p_top",
    		height:115,
    	    region:"top",
    	    content:{
    	    	id:"p_1"
    	    }
    	},{
    		id:"p_main",
    		title:"机构满意度走势图",
    		region:"center",
    		content : {
					id :"flot-placeholder"
			}
    	}
		]
	});
	$("#p_1").css('overflow',"hidden");
	$("#p_1").append("<div id='sb_q'></div><table id='mainGrid'></table><div></div>");
	$("#sb_q").searchBar({
		items:[
		   	   {field:"组织机构",id:"orgCode",name:"orgCode",type:"select", url: cp+"/organization/optionOrgListJson.do",blankText:"请选择"},
		   	   {field:"年份",name:"year",id:'year',type:"select",option:[{text:"2014",value:"2014"},{text:"2015",value:"2015"},{text:"2016",value:"2016"},{text:"2017",value:"2017"},{text:"2018",value:"2018"},{text:"2019",value:"2019"},{text:"2020",value:"2020"},{text:"2021",value:"2021"},{text:"2022",value:"2022"},{text:"2023",value:"2023"}]},
		   	   {field:"业务类型",id:'bCode',type:"text", readOnly:true}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		    	var formData=$.getFormDate("sb_q");
		    	 var bcv=$("#hide_bc").val();
			       if(bcv){
			    	   formData['serviceCode']=bcv;
			       }
		    	$.ajax({type:"post",url:cp+"/organChat/getOrganChat.do",data:formData,async:true,dataType:"json",
					success: function(date){
						date['orgYear']=$("#year").val();
						var orgName=$("#orgCode_txt").val();
						if(!orgName){
							date['orgName']="全部";
						}else{
							date['orgName']=orgName;
						}
						 $("#mainGrid").jqGrid("delRowData", 1);     
						$("#mainGrid").jqGrid('addRowData',1,date);
						for(var i=1;i<13;++i){
							var md=date['month'+i];
							if(md){
								dataInit[i-1]=[i,md*100];
							}else{
								dataInit[i-1]=[i,0];
							}
						}
						$.plot($("#flot-placeholder"), dataset, options);
				}
		    	});
		    }},
		    {name:"重置",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
		    	$("#sb_q").searchBar("clearForm");
		    	$("#orgCode").val("");
		    	serviceCodes={};
		    	$("#hide_bc").val("");
		    }}
		]
	});
	$("#orgCode").replaceWith("<div id='orgCode_div' class='comboBox'></div>");
	$("#orgCode_div").append("<input id='orgCode_txt' type='text' readOnly=true class='text'></input><input id='orgCode' name='orgCode' type='hidden'></input>");
	$("#orgCode_div").append("<img id='orgCode_trigger' class='trigger' src="+cp+"/UI/css/images/s.gif></img>");
	$(document.body).append("<div id='orgCode_tri_opt' class='cb_option'></div>");
	$("#orgCode_tri_opt").append("<ul id='organTree' class='ztree'></ul>");
	$("#orgCode_tri_opt").hide();
	
	var odo=$("#orgCode_div");
	var oto=$("#orgCode_tri_opt");
	var octo=$("#orgCode_trigger");
	$("#orgCode_div").click(function(event){
		if(octo.hasClass("expand")){
			octo.removeClass("expand");
			oto.hide();
		}else{
			octo.addClass("expand");
			var top=odo.offset().top+21;
			var left=odo.offset().left;
			oto.css({"top":top+"px","left":left+"px"});
			oto.show();
			loadOrganizationTree();
			oto.focus();
		}
		 return false;
	}); 
	
	$("#orgCode_tri_opt").click(function(event){
	   return false;
	});
	
	$(document).click(function(event){
		if(oto.is(":hidden")){
			return;
		}
		octo.removeClass("expand");
		oto.hide();
	});
	$("#bCode").click(function(){
		$("#serviceWin").window("open",{btns:[0,0]});
//		$("#serviceGrid").setSelection(id,true);
		$("#serviceGrid").trigger("reloadGrid");
		serviceCodes={};
		serviceNames="";
		$("#snc_txt").val("");
		$("#sn_q_txt").val("");
		return false;
	});
	$(document.body).append("<div id='serviceWin'></div>");
	$(document.body).append("<inpu id='hide_bc' name='businessCode' type='hidden'></input>");
	
	
	$("#serviceWin").window({
		title:'选择业务',
		width:510,
		height:450,
		content:[{
			type:"form",
			rowNum:2,
			items:[
	               {field:"已选业务",id:"snc_txt",type:"text",readOnly:true}
			]
		},
		{type:"pannel",id:"p1",width:494,height:323,}
		],
		button:[
               {name:"确认",on:function(){
            	   $("#bCode").val(serviceNames);
            	   var codeStr="";
            	   var i=0;
            	   for(var p in serviceCodes){
            		   if(i==0){
            			   codeStr+=p; 
            		   }else{
            			   codeStr+=","+p; 
            		   }
            		   ++i;
            	   }
            	   $("#hide_bc").val(codeStr);
            	   $("#serviceWin").window("close");
               }},
		       {name:"关闭",type:'close'}
		],
		openHandler:function(){
			 for(var p in serviceCodes){
      			var id=serviceCodes[p];
      			$("#serviceGrid").jqGrid('setSelection',id);
      	   }
			 $("#snc_txt").val(serviceNames);
		},
		closeHandler:function(){
			snnum=0;
			$("#serviceGrid").jqGrid('setGridParam',{datatype:'json',postData:{},page:1}).trigger("reloadGrid");
	    }
	});
	
	$("#wct_serviceWin input").css("width","388px");
	$("#p1").append("<div id='sb_win'></div><table id='serviceGrid'></table>");
	$("#sb_win").searchBar({
		items:[
		   	   {field:"业务名称",id:'sn_q_txt',name:"serviceName",type:"text"}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		       var param={"name":$("#sn_q_txt").val()};
		       $.clearGridPostParam("serviceGrid");
		       $("#serviceGrid").setGridParam({  
		  	        datatype:'json',  
		  	        postData:param,
		  	        page:1
		  	    }).trigger("reloadGrid");
		    },
		    },
		    {name:"清空选中",type:"button",icon:cp+"/UI/css/icon/arrow_refresh.png",on:function(){
				for(var p in serviceCodes){
		      			var id=serviceCodes[p];
		      			$("#serviceGrid").setSelection(id,true);
		      	}
				serviceNames="";
				snnum=0;
				serviceCodes={};
				$("#snc_txt").val("");
		    }
		    }
		]
	});
	//$("#orgCode_div").parent().css("width","218px");
	
	$("#sbf_sb_win ul").css("padding","2px");
	$("#sbf_sb_win").css("height","32px");
	$("#sbf_sb_win").css("margin","2px");
	$("#sb_win").css("padding","2px 0px");
	$("#sb_win").css("height","42px");
	$("#sb_win").css("background","#FFFFFF");
	$("#sb_win").css("border","1px");
	var sgw=$("#p1").width();
	var sgh=$("#p1").height();
	$("#serviceGrid").jqGrid({
		width:sgw-2,
		height:sgh-72,
		url:'/CES/business/queryBusiness.do',
		mtype: 'POST',
		datatype: "json", 
		mtype: 'POST',
		altclass:'odd',
		jsonReader:{
		    root:'rows',
		    page: "page",  
		    total: "total",  
		    records: "record",
		    repeatitems : false
		},
		colNames:['id','业务名称','业务编码'], 
		colModel:[
		{name:'id',index:'id', width:10,hidden:true, search: false,sortable:false},
		{name:'name',index:'_role_name', width:10,hidden:false, search: false,sortable:false},
		{name:'code',index:'_role_code', width:10,hidden:false, search: false,sortable:false}
		], 
		multiselect: true,
		rowNum:2000,
		sortname: 'id', 
		viewrecords: true,
		sortorder: "asc",
		scroll:true,
		onSelectRow: function(rowid,status){ 
			 setVal(rowid,status);
		}
	});
	
	$("#serviceGrid").delegate("input:checkbox","click",function(){
		var rwnum=$(this).parent().parent().get(0).id;
		if(this.checked){
			$("#serviceGrid").setSelection(rwnum,true);
		}else{
			$("#serviceGrid").setSelection(rwnum,false);
		}
	});
	
	var serviceNames="";
	var serviceCodes={};
	var snnum=0;
	function setVal(rowid,status){
		var rowData = $("#serviceGrid").jqGrid("getRowData",rowid);
		if(snnum>10){
			alert("选取的业务不能超过10项,请取消部分选中业务后再试!");
			return;
		}
		if(status){
			if(!serviceCodes[rowData.code]){
				if(snnum>0){
					serviceNames+=","+rowData.name;
					serviceCodes[rowData.code]=rowid;
				}else{
					serviceNames+=rowData.name;
					serviceCodes[rowData.code]=rowid;
				}
				++snnum;
			}
		}else{
			if(serviceCodes[rowData.code]){
				var snTemp="";
				var snArr=serviceNames.split(",");
				var f=false;
				for(var i=0;i<snArr.length;++i){
					var sn=snArr[i];
					if(i==0){
						if(sn!=rowData.name){
							snTemp+=sn;
						}else{
							f=true;
						}
					}else{
						if(f && i==1){
							snTemp+=sn;
						}else{
							if(sn!=rowData.name){
							   snTemp+=","+sn;
							}
						}
						
					}
				}
				serviceNames=snTemp;
				delete serviceCodes[rowData.code];
				--snnum;
			}
		}
        $("#snc_txt").val(serviceNames);
	}
	
	var pw=$("#p_1").width();
	var ph=$("#p_1").height();
	
	function loadOrganizationTree(){
		var url = "/CES/organization/getAllOrganiztionTree.do";
		var setting = {
			view : {
				showLine : true,
				selectedMulti : false,
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			async : {
				enable : true,
				url : url,
				autoParam : [ "id=pId" ]/*,
				dataFilter : filter*/
			},
			callback:{
				onClick: onClick
			
			}
		};					
		$.fn.zTree.init($("#organTree"), setting);
	}
	
	function onClick(event, treeId, treeNode, clickFlag){
		$("#orgCode_txt").val(treeNode.name);
		$("#orgCode").val(treeNode.code);
		$("#orgCode_trigger").removeClass("expand");
		$("#orgCode_tri_opt").hide();
	}
	
	
	
	$("#mainGrid").jqGrid({
		width:pw,
		height:ph,
		mtype: 'POST',
		datatype: "json", 
		altclass:'odd',
		jsonReader:{
		    root:'rows',
		    repeatitems : false
		},
		colNames:['年份','机构名称','1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'], 
		colModel:[
		{name:'orgYear',index:'orgYear', width:12,hidden:false, search: false,sortable:false},     
		{name:'orgName',index:'orgName', width:12,hidden:false, search: false,sortable:false,key:true},
		{name:'month1',index:'month1', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month2',index:'month2', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month3',index:'month3', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month4',index:'month4', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month5',index:'month5', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month6',index:'month6', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month7',index:'month7', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month8',index:'month8', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month9',index:'month9', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month10',index:'month10', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month11',index:'month11', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		{name:'month12',index:'month12', width:10,hidden:false, search:false,sortable:false,formatter:formatNum},
		],
		multiselect: false,
		rowNum:15, 
		rowList:[15,25,35], 
		pager: '#pager2', 
		sortname: 'orgName', 
		viewrecords: true, 
		sortorder: "asc"
	});
	$("#gbox_mainGrid").css('border',"0px");
	
	function formatNum(v){
		return !v? '0.00%' : v*100+"%";
	}
	
	var dataInit = [];
	
	var dataset = [ {
		label : "满意度(%)",
		data : dataInit}
	];

	var options = {
		series : {
			lines: {
				lineWidth:1,
				show : true
			},
			points : {
				radius : 2,
				fill : true,
				show : true
			}
		},
		xaxis : {
			tickLength : 0,
			axisLabelFontSizePixels : 14,
			axisLabelFontFamily :"'宋体',tahoma,arial,verdana,sans-serif",
			axisLabelPadding : 10,
			tickLength : 0,
			axisLabel : "2014年",
			axisLabelUseCanvas : true,
			ticks:[[1, "1月"], [2, "2月"],[3, "3月"],[4, "4月"], [5, "5月"],[6, "6月"], [7, "7月"],[8, "8月"], [9, "9月"], [10, "10月"],[11, "11月"],[12, "12月"]], 
			min: 1, max: 12
		},
		yaxes : [{
			axisLabel :"满意度",
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 14,
			axisLabelFontFamily :"'宋体',tahoma,arial,verdana,sans-serif",
			axisLabelPadding : 3,
			min:0,
			max:100,
			ticks: 5,
			tickFormatter : function(v, axis) {
				return v;
			}
		}],
		legend : {
			noColumns : 0,
			labelBoxBorderColor : "#000000",
			position : "nw"
		},
		grid : {
			hoverable : true,
			borderWidth : 2,
			borderColor : "#008ACD",
			backgroundColor : {
				colors : [ "#ffffff", "#FFFFFF" ]
			}
		},
		colors : [ "#FF7F50" ]
	};

	/*$(document).ready(function() {
		
		
		$("#b1").click(function(){
			data1[6][1]=34;
			$.plot($("#flot-placeholder1"), dataset, options);
		});
	});*/


	var previousPoint = null, previousLabel = null;
	var monthNames = [ "XX","1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
			"9月", "10月", "11月", "12月" ];

	$.fn.UseTooltip = function() {
		$(this).bind(
				"plothover",
				function(event, pos, item) {
					if (item) {
						if ((previousLabel != item.series.label)
								|| (previousPoint != item.dataIndex)) {
							previousPoint = item.dataIndex;
							previousLabel = item.series.label;
							$("#tooltip").remove();

							var x = item.datapoint[0];
							var y = item.datapoint[1];

							var color = item.series.color;
							//var month = new Date(x).getMonth();
                            month = x;
							//console.log(item);

							if (item.seriesIndex == 0) {
								showTooltip(item.pageX, item.pageY, color,
										"<strong>" + item.series.label
												+ "</strong><br>"
												+ monthNames[month]
												+ " : <strong>" + y
												+ "</strong>(%)");
							}
						}
					} else {
						$("#tooltip").remove();
						previousPoint = null;
					}
				});
	};

	function showTooltip(x, y, color, contents) {
		$('<div id="tooltip">' + contents + '</div>')
				.css(
						{
							position : 'absolute',
							display : 'none',
							top : y - 20,
							left : x - 120,
							border : '2px solid ' + color,
							padding : '2px',
							'font-size' : '12px',
							'border-radius' : '3px',
							'background-color' : '#fff',
							'font-family' : "'宋体',tahoma,arial,verdana,sans-serif",
							opacity : 0.9
						}).appendTo("body").fadeIn(200);
	}
	
	$.plot($("#flot-placeholder"), dataset, options);
	$("#flot-placeholder").UseTooltip();
	
});