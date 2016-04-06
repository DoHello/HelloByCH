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
				<h2 style="color:#353535; display:inline;">角色权限管理 » 模块与按钮权限</small></h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				
			
				<hr style="margin:10px 0 0 0;">
				
				<div style="margin-top:-1px;">
					<div class="row">
						<div class="col-md-2">
							<div id="dtGridContainer1" class="dt-grid-container"></div>
						</div>
						<div class="col-md-2">
							<div id="dtGridContainer2" class="dt-grid-container"></div>
						</div>
						<div class="col-md-2">
							<button type="button" id="btnCommit" class="btn btn-xs btn-success">保存</button>
							<div id="dtGridContainer3" class="dt-grid-container"></div>
						</div>
						<div class="col-md-6">
							<div class="btn-group">
								<button id="btnAdd" class="btn btn-xs" style="display: none">新增按钮</button>
								<button id="btnEdit" class="btn btn-xs" style="display: none">修改按钮</button>
								<button id="btnSave" class="btn btn-xs btn-success">保存</button>
							</div>
							<div id="dtGridContainer4" class="dt-grid-container"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->
		
		</div>
	</div>
	
	<%@ include file="../../footer.jsp"%>
</body>
<script type="text/javascript">
	var dtGridColumns1 = [
		{id:'moduleName', title:'子系统名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'', title:'', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			content += '<button type="button" class="btn btn-sm btn-primary" onclick="sub(\''+record.moduleID+'\', 1, '+dataNo+');"><i class="icon-hand-right"></i></button>';
			return content+"</div>";
		}}
	]
	
	var dtGridColumns4 = [
		{id:'buttonID', title:'按钮ID', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'buttonName', title:'按钮名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'requestMapping', title:'方法映射', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'}
	]
	
	var grid = $.fn.DtGrid.init({
		lang : 'zh-cn',
		ajaxLoad : false,
		data: [],
		columns : dtGridColumns1,
		gridContainer : 'dtGridContainer1',
		tools : '',
		pageSize : 10,
		pageSizeLimit : [10, 20, 50]
	});
	
	var grid3 = null;
	
	var grid4 = $.fn.DtGrid.init({
		lang : 'zh-cn',
		check: true,
		ajaxLoad : false,
		data:[],
		exportFileName : '系统模块信息',
		columns : dtGridColumns4,
		gridContainer : 'dtGridContainer4',
		toolbarContainer : null,
		tools : 'print',
		pageSize : 10,
		pageSizeLimit : [10, 20, 50]
	})
	
	window.onload = function() {
		$('#page').css('display', 'block');
		
		$('#dtGridContainer1').children().remove();
		
		initTable();
	}
	
	
	$('#appPlatform').change(function() {
		$('#dtGridContainer1').children().remove();
		initTable();
	});
	
	
	function initTable() {
		$.ajax({
			type : 'POST',
			url : "systemmodule.do?p=find&roleID=${model.roleID}&roleName="+unicode('${model.roleName}')+'&appPlatform='+$('#appPlatform').val(),
			data : null,
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				grid.exhibitDatas = data.exhibitDatas;
				grid.refresh();
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
		var str = JSON.stringify(grid3.getCheckedRecords());
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "role.do?p=addrolemodule&roleID=${model.roleID}&roleName="+unicode('${model.roleName}')+"&module1="+module1+"&module2="+module2+'&appPlatform='+$('#appPlatform').val(),
			data : 'modulestr='+unicode(str),
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
	
	
	var module1, module2, module3;
	var dataNo1, dataNo2;
	function sub(parentID, grade, dataNo, buttonID) {
		if (grade == 1) {
			var dtGridColumns2 = [
				{id:'moduleName', title:'一级菜单', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
				{id:'', title:'', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
					var content = '<div class="btn-group">';
					content += '<button type="button" class="btn btn-sm btn-primary" onclick="sub(\''+record.moduleID+'\', 2, '+dataNo+');"><i class="icon-hand-right"></i></button>';
					return content+"</div>";
				}}
			]
			
			$("#dtGridContainer1 > *").eq(0).find("tbody>tr").eq(dataNo1).css('background-color','white');
			var tr = $("#dtGridContainer1 > *").eq(0).find("tbody>tr").eq(dataNo*2).css('background-color','#fff0d5');
			dataNo1 = dataNo*2;
			
			$('#dtGridContainer2').children().remove();
			$('#dtGridToolBarContainer2').children().remove();
			
			var grid2 = $.fn.DtGrid.init({
				lang : 'zh-cn',
				ajaxLoad : true,
				loadURL : 'systemmodule.do?p=sub&parentID='+parentID,
				exportFileName : '系统模块信息',
				columns : dtGridColumns2,
				gridContainer : 'dtGridContainer2',
				toolbarContainer : null,
				tools : 'print',
				pageSize : 10,
				pageSizeLimit : [10, 20, 50]
			});
			
			grid2.load();
			
			module1 = parentID;
			
		} else if(grade ==2) {
			module2 = parentID;
			var dtGridColumns3 = [
				{id:'moduleName', title:'二级菜单', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
				{id:'', title:'', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
					var content = '<div class="btn-group">';
					content += '<button type="button" class="btn btn-sm btn-primary" onclick="sub(\''+record.moduleID+'\', 3, '+dataNo+', \''+record.buttonID+'\');"><i class="icon-hand-right"></i></button>';
					return content+"</div>";
				}}
			]
			
			$("#dtGridContainer2 > *").eq(0).find("tbody>tr").eq(dataNo2).css('background-color','white');
			var tr = $("#dtGridContainer2 > *").eq(0).find("tbody>tr").eq(dataNo*2).css('background-color','#fff0d5');
			dataNo2 = dataNo*2;
			
			$('#dtGridContainer3').children().remove();
			$('#dtGridToolBarContainer3').children().remove();
			
			grid3 = $.fn.DtGrid.init({
				lang : 'zh-cn',
				check: true,
				ajaxLoad : true,
				loadURL : 'systemmodule.do?p=rolemodule&parentID='+parentID+'&roleID=${model.roleID}',
				exportFileName : '系统模块信息',
				columns : dtGridColumns3,
				gridContainer : 'dtGridContainer3',
				toolbarContainer : null,
				tools : 'print',
				pageSize : 10,
				pageSizeLimit : [10, 20, 50]
			})
			grid3.load(function() {
				var grid_check = $("#dtGridContainer3").eq(0).find('[name="dt-grid-check"]');
				for (var i=0; i<grid3.exhibitDatas.length; i++) {
					if (grid3.exhibitDatas[i].moduleID.indexOf(grid3.exhibitDatas[i].searchText) >= 0) {
						$(grid_check.get(i)).prop('checked', true);
					}
				}
			});
		} else if(grade ==3) {
			module3 = parentID;
			$("#dtGridContainer3 > *").eq(0).find("tbody>tr").eq(dataNo2).css('background-color','white');
			var tr = $("#dtGridContainer3 > *").eq(0).find("tbody>tr").eq(dataNo*2).css('background-color','#fff0d5');
			dataNo2 = dataNo*2;
			
			$('#dtGridContainer4').children().remove();
			$('#dtGridToolBarContainer4').children().remove();
			
			$.ajax({
				type : 'POST',
				url : 'role.do?p=findrolebutton&moduleID='+parentID+'&roleID=${model.roleID}&roleName='+unicode('${model.roleName}')+'&module3='+parentID,
				data : null,
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					grid4.exhibitDatas = data.button;
					grid4.refresh();
					
					var grid_check = $("#dtGridContainer4").eq(0).find('[name="dt-grid-check"]');
					for (var i=0; i<grid4.exhibitDatas.length; i++) {
						if (grid4.exhibitDatas[i].requestMapping.indexOf(grid4.exhibitDatas[i].dataValue) >= 0) {
							$(grid_check.get(i)).prop('checked', true);
						}
					}
				},
				error:function(data) {
					handerr(data, 'default')
				}
			});
		}
	}
	
	
	$("#btnAdd").on('click', function() {
		if (module3 == null) {
			showmsg("必须选择一个二级菜单");
			return;
		}
		window.open("systemmodule.do?p=showButtonAdd&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&searchText="+module3);
	});
	
	
	$("#btnEdit").on('click', function() {
		if (module2 == null) {
			showmsg("必须选择一个二级菜单");
			return;
		}
		if (grid4.getCheckedRecords().length == 0) {
			showmsg("请选择一个按钮");
			return;
		}
		window.open(window.location.href = "systemmodule.do?p=showButtonEdit&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&mobuID="+grid4.getCheckedRecords()[0].mobuID);
	});
	
	
	$("#btnSave").on('click', function() {
		if (module2 == null) {
			showmsg("必须选择一个二级菜单");
			return;
		}
		
		var str = JSON.stringify(grid4.getCheckedRecords()).replace("p=","p__");
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "role.do?p=addrolebutton&roleID=${model.roleID}&roleName="+unicode('${model.roleName}')+"&module3="+module3,
			data : 'str='+unicode(str),
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
