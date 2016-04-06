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
				<h2 style="color:#353535; display:inline;">系统管理 » 警员信息</small></h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="登录账号、真实姓名">
					</div>
					
					<div class="col-md-9">
						<div class="btn-group pull-right">
						<c:forEach items="${model.userbutton }" var="buts">
								<c:if test="${buts.buttonID=='us' }">
									<button type="button" id="btnSearch" class="btn">搜索</button>
								</c:if>
							</c:forEach>
							<c:forEach items="${model.userbutton }" var="buts">
								<c:if test="${buts.buttonID=='ua' }">
									<button type="button" id="btnAdd" class="btn">添加</button>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			
				<hr style="margin:10px 0 0 0;">
				
				<div style="margin-top:-1px;">
				<div id="dtGridContainer" class="dt-grid-container"></div>
				<div id="dtGridToolBarContainer" class="dt-grid-toolbar-container"></div>
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
		{id:'userID', title:'用户id,主键', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs',hide:true, fastQuery:true, fastQueryType:'eq'},
		{id:'userName', title:'登录账号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq' ,sortable:true,headerStyle:'background:#00a2ca;color:white;'},
// 		{id:'password', title:'用户密码', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
// 		{id:'companyID', title:'单位id', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'companyName', title:'单位名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
		{id:'realName', title:'真实姓名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
		{id:'phone', title:'手机', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'qq', title:'QQ号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'email', title:'电子邮件', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'idCard', title:'身份证', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
// 		{id:'roleID', title:'角色ID', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'roleName', title:'角色名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
// 		{id:'sexID', title:'性别', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'sexName', title:'性别', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
// 		{id:'inputID', title:'录入人员', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
// 		{id:'inputName', title:'录入人员', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
// 		{id:'inputDate', title:'创建日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
// 		{id:'modifyID', title:'修改人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
// 		{id:'modifyName', title:'修改人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
// 		{id:'modifyDate', title:'修改人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},

		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'uq\' }"><button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.userID+'\');"><i class="fa fa-eye"></i>查看</button></c:if></c:forEach>';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'ud\' }"><button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.userID+'\', '+dataNo+');"><i class="fa fa-trash-o"></i>删除</button></c:if></c:forEach>';
			return content + '</div>';
		}}
	]
	
	var grid = null;
	
	
	window.onload = function() {
		$('#page').css('display', 'block');
		
		if ($.fn.chosen) {
			$('#pageBody .chosen-select').chosen();
		}
	
		if ($.fn.datetimepicker) {
			$('#pageBody .form-date').datetimepicker({
				language: 'zh-CN',
				weekStart: 1,
				todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0,
				format: 'yyyy-mm-dd'
			});
		}
		
		// Chosen联动
		$('#storeID').change(function() {
			var s = $('#storeID').val();
			var t = "";
			$('#storeID option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value
				
				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
					//t += "," + txt;
					t = txt;
				}
			});
			$('#storeName').attr('value', t);
		})
		$(document).keypress(function(e) {
		if (e.keyCode == 13 && e.target == "body") {
			e.preventDefault();
		} else if (e.keyCode == 13) {
			$('#btnSearch').trigger('click');
			e.preventDefault();
		}
		});
		$('#btnSearch').trigger('click');
	}
	
	
	function eye(userID) {
		window.location.href="user.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&userID="+userID;
		return false;
	}
	
	
	function del(userID, dataNo) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "user.do?p=del&userID="+userID,
		//		data : $("#mainForm").serialize(),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					if (data.errtyp == null) {
						handsuc(data, 'delete');
						$("#dtGridContainer > *").eq(0).find("tbody>tr").eq(dataNo*2+1).remove();
						$("#dtGridContainer > *").eq(0).find("tbody>tr").eq(dataNo*2).remove();
					} else {
						handerr(data, 'delete');
					}
				},
				error:function(data) {
					handerr(data, 'delete')
				}
			});
		});
		
		$('#delete-comfirm-modal').modal({});
	};
	
	
	$("#btnSearch").on('click', function() {
		$('#dtGridContainer').children().remove();
		$('#dtGridToolBarContainer').children().remove();
	
		grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: false,
			ajaxLoad : true,
			loadURL : 'user.do?p=find&searchText='+unicode($("#searchText").val()),
			exportFileName : '人员列表',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		})
		grid.parameters = new Object();
		grid.parameters["searchText"] = $("#searchText").val();
		sessionStorage.setItem("url","user.do?p=find");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
	});
// 	$('#dt_grid_'+sessionStorage.getItem("gridId")+' .dt-grid-headers').attr('th').click(function(){
// 		debugger
// 		$('#dtGridContainer').children().remove();
// 		$('#dtGridToolBarContainer').children().remove();
// 		var columnId = $(this).attr('columnId');
// 		grid = $.fn.DtGrid.init({
// 			lang : 'zh-cn',
// 			check: true,
// 			ajaxLoad : true,
// 			loadURL : 'user.do?p=find&searchText='+unicode($("#searchText").val())+'&sortFiled='+columnId,
// 			exportFileName : '人员列表',
// 			columns : dtGridColumns,
// 			gridContainer : 'dtGridContainer',
// 			toolbarContainer : 'dtGridToolBarContainer',
// 			tools : '',
// 			pageSize : 10,
// 			pageSizeLimit : [10, 20, 50]
// 		})
// 		grid.parameters = new Object();
// 		grid.parameters["searchText"] = $("#searchText").val();
// 		grid.load();
// 	});
	
	$("#btnAdd").on('click', function() {
		window.location.href = "user.do?p=showAddView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	});
</script>
</html>
