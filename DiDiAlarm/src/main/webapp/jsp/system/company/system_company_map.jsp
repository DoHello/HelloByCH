<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<script src="js/amazeui2.2.1/js/jquery.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
	font-size: 14px;
}

#l-map {
	height: 70%;
	width: 100%;
}

#r-result {
	width: 100%;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=6066cf2819dbd803b5a1799007d7e3e0"></script>
<title>关键字输入提示词条</title>
</head>
<body>
	<div id="l-map"></div>

	<div id="r-result">
		请输入:<input type="text" id="suggestId" size="20" value="百度"
			style="width:150px;" />
	</div>
	<div>
		<div id="result-address" style="float:left;width:80%">
			<h3>当前地址:</h3>
			<input type="text" id="textAddress" name="textAddress" size="0"
				value=""
				style="height:30px; width:60%;border:0px;color: red;font-size:25px" />
		</div>

		<div id="result-address" style="float:left;width:20%">
			<button type="button" id="btnCommit" style="width:140px;height:100px">
				<h1>确认</h1>
			</button>
		</div>
	</div>
	<div id="searchResultPanel"
		style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
</html>
<script type="text/javascript"
	src="js/myjs/mapVar.js"></script> 
<script type="text/javascript">
	$("#btnCommit").click(function() {
		if(address===undefined){
			alert("请选择地址后提交");
		}else{
		
		if (confirm("你确认以 "+address+" 为地址作为机构地址吗?确认后我们将关闭当前页面,并自动设置详细地址。")) {
			localStorage.setItem('addressInBaidu',address);
			localStorage.setItem('ptLng',pt.lng);
			localStorage.setItem('ptLat',pt.lat);
			window.close();
		} 
		}
	});
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}

	var map = new BMap.Map("l-map");
	////////////////
	var geoc = new BMap.Geocoder();
	var address;
	var pt;
	map.addEventListener("click", function(e) {
		pt = e.point;
		geoc.getLocation(pt, function(rs) {
			var addComp = rs.addressComponents;
			//alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
			address = addComp.province + addComp.city + addComp.district
					+ addComp.street + addComp.streetNumber;
			document.getElementById("textAddress").value = address;
			var label = new BMap.Label("当前地址为:" + address, {
				offset : new BMap.Size(20, -10)
			});
			marker.setLabel(label);

		});
	});
	////////
	function myFun(result) {
		var cityName = result.name;
		map.setCenter(cityName);
		map.centerAndZoom(cityName, 12);
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
	// 初始化地图,设置城市和地图级别。
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用
	var ac = new BMap.Autocomplete( //建立一个自动完成的对象
	{
		"input" : "suggestId",
		"location" : map
	});

	//////
	ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
		var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province + _value.city + _value.district
					+ _value.street + _value.business;
		}
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = "
				+ value;

		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province + _value.city + _value.district
					+ _value.street + _value.business;
		}
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = "
				+ value;
		G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
		var _value = e.item.value;
		myValue = _value.province + _value.city + _value.district
				+ _value.street + _value.business;
		G("searchResultPanel").innerHTML = "onconfirm<br />index = "
				+ e.item.index + "<br />myValue = " + myValue;

		setPlace();
	});
	var marker;
	function setPlace() {
		map.clearOverlays(); //清除地图上所有覆盖物
		function myFun() {
			var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
			map.centerAndZoom(pp, 18);
			marker = new BMap.Marker(pp); // 创建标注
			map.addOverlay(marker); // 将标注添加到地图中
			//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			marker.enableDragging();//可拖拽
			//map.addOverlay(new BMap.Marker(pp));    //添加标注
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
			onSearchComplete : myFun
		});
		local.search(myValue);
	}
	//////////
	var top_left_control = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_TOP_LEFT
	});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl(); //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT,
		type : BMAP_NAVIGATION_CONTROL_SMALL
	}); //右上角，仅包含平移和缩放按钮
	/*缩放控件type有四种类型:
	BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/

	//添加控件和比例尺
	map.addControl(top_left_control);
	map.addControl(top_left_navigation);
	map.addControl(top_right_navigation);
</script>
