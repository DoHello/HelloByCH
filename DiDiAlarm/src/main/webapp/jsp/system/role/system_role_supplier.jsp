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
				<h2 style="color:#353535; display:inline;">职务管理 » 供货企业权限</small></h2>
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
		{id:'supplierID', title:'供应商表主键', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'supplierName', title:'单位名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'companyID', title:'所属企业编码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'companyName', title:'所属企业名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'address', title:'单位地址', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'py', title:'拼音码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'manager', title:'单位负责人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'phone', title:'电话', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'taxNumber', title:'税务登记号', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'owner', title:'企业法人', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'regCapital', title:'注册资金', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'regNumber', title:'注册号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'inputName', title:'录入人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'inputDate', title:'录入时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'qmCheckName', title:'质量负责人审核', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'qmCheckDate', title:'质量负责人审核时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'cmCheckName', title:'企业负责人审核', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'cmCheckDate', title:'企业负责人审核日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'importDate', title:'导入日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'}
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
			url : "role.do?p=findrolesupplier&roleID=${model.roleID}&roleName="+unicode('${model.roleName}'),
			data : null,
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				grid.exhibitDatas = data.supplier;
				grid.refresh();
				
				//console.log($('.dt-grid-check'));
				var grid_check = $('[name="dt-grid-check"]');
				for (var i=0; i<grid.exhibitDatas.length; i++) {
					if (grid.exhibitDatas[i].dataValue == grid.exhibitDatas[i].supplierID) {
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
			url : "role.do?p=addrolesupplier&roleID=${model.roleID}&roleName="+unicode('${model.roleName}'),
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
