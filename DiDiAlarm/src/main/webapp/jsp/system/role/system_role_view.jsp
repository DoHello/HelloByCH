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
				<h2 style="color:#353535; display:inline;">职务管理 » 首页</small></h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" placeholder="角色名称" class="form-control">
					</div>
					
					<div class="col-md-9">
						<div class="btn-group pull-right">
						<c:forEach items="${model.userbutton }" var="buts">
							<c:if test="${buts.buttonID=='rs' }">
								<button type="button" id="btnSearch" class="btn">搜索</button>
							</c:if>
						</c:forEach>
						<c:forEach items="${model.userbutton }" var="buts">
							<c:if test="${buts.buttonID=='ra' }">
								<button type="button" id="btnAdd" class="btn">添加角色</button>
							</c:if>
							<c:if test="${buts.buttonID=='rm' }">
								<button type="button" id="btnModule" class="btn">模块权限</button>
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
		{id:'roleID', title:'角色ID,主键', type:'string', columnClass:'text-center', hide:true,hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'roleName', title:'角色名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'companyID', title:'角色所属单位ID', type:'string', columnClass:'text-center',hide:true, hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'companyName', title:'单位名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'inputName', title:'录入人员', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'inputDate', title:'录入时间', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},

		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'rq\' }"><button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.roleID+'\');"><i class="fa fa-eye"></i>查看</button></c:if></c:forEach>';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'rd\' }"><button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.roleID+'\', '+dataNo+');"><i class="fa fa-trash-o"></i>删除</button></c:if></c:forEach>';
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
	
	
	function eye(roleID) {
		window.location.href="role.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&roleID="+roleID;
		return false;
	}
	
	
	function del(roleID, dataNo) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "role.do?p=del&roleID="+roleID,
				//data : $("#mainForm").serialize(),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
			
						handerr(data, 'delete');
						grid.load();
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
			check: true,
			ajaxLoad : true,
			loadURL : 'role.do?p=find&searchText='+unicode($("#searchText").val()),
			exportFileName : '采购计划',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		})
		grid.parameters = new Object();
		grid.parameters["searchText"] = $("#searchText").val();
		grid.load();
	});
	
	
	$("#btnAdd").on('click', function() {
		window.location.href="role.do?p=showAddView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	})
	
	
	$("#btnStore").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			showmsg("请选择一个角色");
			return;
		}
		
		if (grid.getCheckedRecords().length > 1) {
			showmsg("只能选择一个角色");
			return;
		}
		var roleID = (grid.getCheckedRecords())[0].roleID;
		var roleName = (grid.getCheckedRecords())[0].roleName;
		window.location.href = "role.do?p=showstoreview&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&roleID="+roleID+"&roleName="+unicode(roleName);
	});
	
	
	$("#btnSupplier").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			showmsg("请选择一个角色");
			return;
		}
		
		if (grid.getCheckedRecords().length > 1) {
			showmsg("只能选择一个角色");
			return;
		}
		var roleID = (grid.getCheckedRecords())[0].roleID;
		var roleName = (grid.getCheckedRecords())[0].roleName;
		window.location.href = "role.do?p=showsupplierview&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&roleID="+roleID+"&roleName="+unicode(roleName);
	});
	
	
	$("#btnModule").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			showmsg("请选择一个角色");
			return;
		}
		
		if (grid.getCheckedRecords().length > 1) {
			showmsg("只能选择一个角色");
			return;
		}
		var roleID = (grid.getCheckedRecords())[0].roleID;
		var roleName = (grid.getCheckedRecords())[0].roleName;
		window.location.href = "role.do?p=showmoduleview&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&roleID="+roleID+"&roleName="+unicode(roleName);
	});
</script>
</html>
