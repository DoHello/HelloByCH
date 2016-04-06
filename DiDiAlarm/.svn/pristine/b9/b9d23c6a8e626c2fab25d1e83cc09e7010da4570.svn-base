<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<base href="<%=basePath%>">

<link rel="stylesheet" type="text/css" href="js/webuploader/style.css"/>
<%@ include file="../../top.jsp"%>

<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="css/imgView.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploadcontoller.css"/>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
<!-- WARNING: Respond.js doesn't work if you view the page via file://-->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<body>
	<base href="<%=basePath%>">
	<%@ include file="../../top.jsp"%>
	<%@ include file="../../head.jsp"%>

	<div id="page" style='top:${model.direct==1?0:'60px'}'>
		<div id="pageBody" class="scrollbar-hover">
		<!-- sidebar start -->
		<%@ include file="../../left.jsp"%>
		<!-- sidebar end -->
		
		<!-- content start -->
		<div class="admin-content am-animation-slide-right clearfix">
			<div style="padding:20px; ">
				<h2 style="color:#353535; display:inline;">职务管理 » 门店权限</small></h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group pull-right">
							<button type="button" id="btnCommit" class="btn">保存</button>
							<button type="button" id="btnHelp" class="btn btn-danger">帮助</button>
						</div>
					</div>
				</div>
			
				<hr style="margin:10px 0 0 0;">
				
				<div style="margin-top:-1px;">
					<div id="dtGridContainer" class="dt-grid-container"></div>
				</div>
			</div>
		</div>
		<!-- content end -->
		
		</div>
	</div>
	
	<div id="append_parent" style="z-index: 10000"></div>
	
	<%@ include file="../../footer.jsp"%>
</body>
<script type="text/javascript">
	var dtGridColumns = [
		{id:'dataValue', title:'门店id,主键', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'storeID', title:'门店id,主键', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'storeName', title:'门店名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'companyID', title:'所属企业或者个人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'companyName', title:'企业名称或者个人名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'provinceID', title:'所属省份编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'provinceName', title:'所属省份名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'cityID', title:'所属地市编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'cityName', title:'所属地市名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'countyID', title:'所属县区编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'countyName', title:'所属县区名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'address', title:'门店地址', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'typeID', title:'门店类型编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'typeName', title:'门店类型名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'py', title:'拼音码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'manager', title:'门店负责人', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'phone', title:'电话', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'qualityManager', title:'质量负责人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'uniformity', title:'统一售价', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'latitude', title:'纬度', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'longitude', title:'经度', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'inputName', title:'录入人员', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'inputDate', title:'创建日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'modifyDate', title:'修改时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'modifyName', title:'修改人员', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'status', title:'创建、批准、停用', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'checkDate', title:'当益公司审核日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'checkName', title:'当益公司审核人员', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'chainID', title:'连锁企业编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'chainName', title:'连锁企业名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'mealID', title:'套餐编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'mealName', title:'套餐名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'joinDate', title:'加盟时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'joinStatus', title:'加盟状态（申请、批准、拒绝）', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'joinCheck', title:'加盟审核人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'}
	]
	
	var grid = $.fn.DtGrid.init({
		lang : 'zh-cn',
		ajaxLoad : false,
		check: true,
		data: [],
		columns : dtGridColumns,
		gridContainer : 'dtGridContainer',
		tools : '',
		pageSize : 10,
		pageSizeLimit : [10, 20, 50]
	});
	
	window.onload = function() {
		$('#page').css('display', 'block');
		
		$('#dtGridContainer').children().remove();
		$('#dtGridToolBarContainer').children().remove();
		
		
		$.ajax({
			type : 'POST',
			url : "role.do?p=findrolestore&roleID=${model.roleID}&roleName="+unicode('${model.roleName}'),
			data : null,
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				grid.exhibitDatas = data.store;
				grid.refresh();
				
				//console.log($('.dt-grid-check'));
				var grid_check = $('[name="dt-grid-check"]');
				for (var i=0; i<grid.exhibitDatas.length; i++) {
					if (grid.exhibitDatas[i].dataValue == grid.exhibitDatas[i].storeID) {
						$(grid_check.get(i)).prop('checked', true);
					}
				}
			},
			error:function(data) {
				handerr(data, 'default')
			}
		});
	}
	
	
	$("#btnCommit").on('click', function() {
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "role.do?p=addrolestore&roleID=${model.roleID}&roleName="+unicode('${model.roleName}'),
			data : 'str='+JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				handsuc(data, 'default')
			},
			error:function(data) {
				handerr(data, 'default')
			}
		});
	});
	
	
</script>
</html>
