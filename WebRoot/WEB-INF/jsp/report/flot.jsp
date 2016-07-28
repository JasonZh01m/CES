<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link rel="icon" href="${ctx}/js.ico" type="image/x-icon" />

<style>
</style>
<title>ceshi</title>
</head>
<body>
    <button id="b1">按钮</button>
	<div
		style="width: 780px; height: 480px; text-align: center; margin: 10px">
		<div id="flot-placeholder1" style="width: 100%; height: 100%;"></div>
	</div>

	<script type="text/javascript">
		var cp = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/UI/js/jq-1.9.1-min.js"></script>
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${ctx}/test/js/excanvas.min.js"></script><![endif]-->
	<script type="text/javascript" src="${ctx}/test/js/jquery.flot.js"></script>
	<!--  <script type="text/javascript"
		src="${ctx}/test/js/jquery.flot.axislabels.js"></script>
	-->
	<script type="text/javascript">
		/* 
		$(function(){
		var vData = [[1, 103], [2, 28], [3, 135], [4, 130], [5, 145], [6, 155], [7, 155], [8, 155], [9, 155], [10, 155], [11, 155], [12, 155]];
		$.plot($("#placeholder"), [{ color:"#819FF7",label: "例子", data: vData}],
		{
		    series: { lines: { show: true }, points: { show: true} },
		    xaxis: { ticks: [[1, "1月"], [3, "3月"], [5, "5月"], [7, "7月"], [9, "9月"], [11, "11月"]], min: 1, max: 12 },  //指定固定的显示内容
		    yaxis: { ticks: 5, min: 20 }  //在y轴方向显示5个刻度，此时显示内容由 flot 根据所给的数据自动判断
		}
		    );
		}); */
		var data1 = [ [ gd(2012, 1, 1), 26 ], [ gd(2012, 2, 1), 46 ],
				[ gd(2012, 3, 1), 28], [ gd(2012, 4, 1), 57],
				[ gd(2012, 5, 1), 56 ], [ gd(2012, 6, 1), 66],
				[ gd(2012, 7, 1), 98], [ gd(2012, 8, 1), 93],
				[ gd(2012, 9, 1), 87], [ gd(2012, 10, 1), 72 ],
				[ gd(2012, 11, 1),78], [ gd(2012, 12, 2), 88 ] ];

		var data2 = [ [ gd(2012, 1, 1), 0.63 ], [ gd(2012, 2, 1), 5.44 ],
				[ gd(2012, 3, 1), -3.92 ], [ gd(2012, 4, 1), -1.44 ],
				[ gd(2012, 5, 1), -3.55 ], [ gd(2012, 6, 1), 0.48 ],
				[ gd(2012,7, 1), -0.55 ], [ gd(2012, 8, 1), 2.54 ],
				[ gd(2012,9, 1), 7.02 ], [ gd(2012,10, 1), 0.10 ],
				[ gd(2012, 11, 1), -1.43 ], [ gd(2012, 12, 2), -2.14 ] ];
		var dataset = [ {
			label : "满意度(%)",
			data : data1}
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
				}/* ,
				bars: {
					  show : true,
			          barWidth:0.25, 
			          align:"center",
			          lineWidth:0,
			          fillColor:"#87CEFA"
			      } */
			},
			xaxis : {
				tickLength : 0,
				axisLabelFontSizePixels : 14,
				axisLabelFontFamily :"'宋体',tahoma,arial,verdana,sans-serif",
				axisLabelPadding : 10,
				tickLength : 0,
				axisLabel : "2015年",
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
			}/* , {
				position : "right",
				axisLabel : "Change(%)",
				axisLabelUseCanvas : true,
				axisLabelFontSizePixels : 12,
				axisLabelFontFamily : 'Verdana, Arial',
				axisLabelPadding : 3
			} */ ],
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

		$(document).ready(function() {
			$.plot($("#flot-placeholder1"), dataset, options);
			$("#flot-placeholder1").UseTooltip();
			
			$("#b1").click(function(){
				data1[6][1]=34;
				/* dataset = [ {
					label : "满意度(%)",
					data : data1}
				]; */
				//alert(1);
				$.plot($("#flot-placeholder1"), dataset, options);
			});
			
			
		});

		function gd(year, month, day) {
			return /* new Date(year, month, day).getTime() */month;
		}

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
								}/*  else {
									showTooltip(item.pageX, item.pageY, color,
											"<strong>" + item.series.label
													+ "</strong><br>"
													+ monthNames[month]
													+ " : <strong>" + y
													+ "</strong>(%)");
								} */
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
	</script>


</body>

</html>