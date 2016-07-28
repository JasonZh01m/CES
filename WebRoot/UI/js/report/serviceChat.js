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
    		title:"业务满意度走势图",
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
		   	   {field:"组织机构",id:"orgCode",name:"orgCode",type:"select", url: cp+"/organization/optionOrgListJson.do"},
		   	   {field:"年份",name:"year",id:'year',type:"select",option:[{text:"2014",value:"2014"},{text:"2015",value:"2015"},{text:"2016",value:"2016"},{text:"2017",value:"2017"},{text:"2018",value:"2018"},{text:"2019",value:"2019"},{text:"2020",value:"2020"},{text:"2021",value:"2021"},{text:"2022",value:"2022"},{text:"2023",value:"2023"}]},
		   	   {field:"业务类型",id:"serviceCode",name:"serviceCode",type:"select", url: cp+"/business/optionBusinessListJson.do", blankText:"请选择"}
		],
		button:[
		    {name:"查询",type:"button",icon:cp+"/UI/css/icon/zoom.png",on:function(){
		    	var formData=$.getFormDate("sb_q");
		    	$.ajax({type:"post",url:cp+"/organChat/getOrganChat.do",data:formData,async:true,dataType:"json",
					success: function(date){
						date['orgYear']=$("#year").val();
						var serviceName=$("#serviceCode option:selected").text();
						if(!serviceName){
							date['serviceCode']="全部";
						}else{
							date['serviceCode']=serviceName;
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
		    }}
		]
	});
	
	var pw=$("#p_1").width();
	var ph=$("#p_1").height();
	
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
		colNames:['年份','业务名称','1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'], 
		colModel:[
		{name:'orgYear',index:'orgYear', width:12,hidden:false, search: false,sortable:false},     
		{name:'serviceCode',index:'serviceCode', width:12,hidden:false, search: false,sortable:false,key:true},
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