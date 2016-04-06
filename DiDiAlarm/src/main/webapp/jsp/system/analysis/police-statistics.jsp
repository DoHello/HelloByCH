<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="marc" />
<title>滴滴报警</title>
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="css/myCss.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="Wrapper p-s">
		<div class="menu">
			<div class="menu-icon">
				<img src="images/menu-icon.png" alt="" /> <span>滴滴报警－大数据后台</span>
			</div>
			<ul class="menu-list">
				<li onclick="create();"><a href="##" id="0" class="on">全部警员</a></li>
				<c:forEach var="obj" items="${model.type}">
					<li onclick="create();"><a href="##" id="${obj.userID}">${obj.userName}</a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="main">
			<div class="search">
				<ul>
					<li><input name="" type="text" class="int-con fl"
						id="datepickerstart" readonly value="${model.time}"></li>
					<li>一</li>
					<li><input name="" type="text" class="int-con fl"
						id="datepickerend" readonly value="${model.time}"></li>
					<li onclick="create();"><a href="##">查询</a></li>
				</ul>
			</div>
			<div class="charts">
				<div class="chart-05" id="chart-05"></div>
				<div class="speed-efficiency">
					<div class="speed">
						<span class="speed2"></span> <i>接警速度</i>
					</div>
					<div id="myStat2" class="efficiency" data-text="50%"
						data-info="接警率" data-percent="100" data-dimension="250"
						data-width="20" data-fgcolor="#61a9dc" data-bgcolor="#eee"></div>
				</div>
				<div class="tit">
					<h3 class="allNum"></h3>
				</div>
			</div>
		</div>
	</div>


	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/jquery-ui-1.9.2.custom.js"></script>
	<!--日期控件-->
	<script src="js/jquery.circliful.min.js"></script>
	<!--百分比插件-->
	<!--
    <script type="text/javascript" src="js/data.js"></script>
-->
	<script src="js/echarts.min.js"></script>
	<script src="js/myCharts3.js"></script>
	<script src="js/myJs.js"></script>
	<script>
		//百分比控件
		$(document).ready(function() {
			$('#myStat2').circliful();
		});
		var myChart05 = echarts.init(document.getElementById('chart-05'));// 指定图表的配置项和数据
		var data;

			var datepickerstart = $("#datepickerstart").val();
			var datepickerend = $("#datepickerend").val();
		$.ajax({
			type : "POST",
			url : "date.do?p=policeStatisticsView&datepickerstart="+ datepickerstart + "&datepickerend=" + datepickerend,
			data : $("#charts").serialize(),
			async : false,
			success : function(result) {
				//alert(result.docketCase.value});
				$(".allNum").text(result.allNum);
				$(".speed2").text(result.avgTime);
				// $("#myStat2").remove("data_text");
				$("#myStat2").attr("data-text", result.percentage);

				// $("#call").attr("text",result.call.value);
				data = result;
			}

		});

		//alert(JSON.stringify(data));
		getEchartsData({
			data : data,
			echarts05 : myChart05,
		});

		function create() {

			var datepickerstart = $("#datepickerstart").val();
			var datepickerend = $("#datepickerend").val();
			var type = $(".on").attr("id");

			var myChart05 = echarts.init(document.getElementById('chart-05'));// 指定图表的配置项和数据
			var data;

			$.ajax({
				type : "POST",
				url : "date.do?p=policeStatisticsView&datepickerstart="+ datepickerstart + "&datepickerend=" + datepickerend
						+ "&type=" + type,
				data : $("#charts").serialize(),
				async : false,
				success : function(result) {
					//alert(result.docketCase.value});
					$(".allNum").text(result.allNum);
					$(".speed2").text(result.avgTime);

					//$(".efficiency").attr("data-text",result.percentage);
			              $("#myStat2").attr("data-text", result.percentage);

					// $("#call").attr("text",result.call.value);
					data = result;
				}

			});

			//alert(JSON.stringify(data))

			//alert(JSON.stringify(data));
			getEchartsData({
				data : data,
				echarts05 : myChart05,
			});

		}
	</script>
</body>
</html>