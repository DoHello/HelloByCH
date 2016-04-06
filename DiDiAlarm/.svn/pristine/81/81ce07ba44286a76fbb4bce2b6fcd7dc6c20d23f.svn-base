<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	PageData pageModel = (PageData)request.getAttribute("model");
%>

<!DOCTYPE HTML>
<html>

<link rel="stylesheet" type="text/css" href="css/imgView.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/style.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploadcontoller.css"/>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
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
				<h2 style="color:#353535; display:inline;">模块管理 » 首页</h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="模块名称">
					</div>
					
					<div class="col-md-9">
						<div class="btn-group pull-right">
							<button type="button" id="btnSearch" class="btn">搜索</button>
							<button type="button" id="btnAdd" class="btn">新增模块</button>
							<button type="button" id="btnHelp" class="btn btn-danger">帮助</button>
						</div>
					</div>
				</div>
			
				<hr style="margin:10px 0 0 0;">
				
				<div style="margin-top:-1px;">
					<div id="dtGridContainer" class="dt-grid-container"></div>
					<div id="dtGridToolBarContainer" class="dt-grid-toolbar-container"></div>
				</div>
				
				<div style="margin-top:-1px;">
					<div id="dtGridContainer2" class="dt-grid-container"></div>
					<div id="dtGridToolBarContainer2" class="dt-grid-toolbar-container"></div>
				</div>
				
				<div style="margin-top:-1px;">
					<div id="dtGridContainer3" class="dt-grid-container"></div>
					<div id="dtGridToolBarContainer3" class="dt-grid-toolbar-container"></div>
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
		{id:'moduleID', title:'模块编码', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'moduleName', title:'模块名称', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'modulePath', title:'路径', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs|', fastQuery:true, fastQueryType:'eq'},
		{id:'parentID', title:'上级模块', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs|', fastQuery:true, fastQueryType:'eq'},
		{id:'isMenu', title:'有子菜单', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs|', fastQuery:true, fastQueryType:'eq'},
		{id:'showIndex', title:'显示顺序', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs|', fastQuery:true, fastQueryType:'eq'},
		{id:'description', title:'描述', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs|', fastQuery:true, fastQueryType:'eq'},
		{id:'appPlatform', title:'应用平台', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'iconClass', title:'菜单图标', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs|', fastQuery:true, fastQueryType:'eq'},
		{id:'url', title:'链接地址', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},

		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			content += '<button type="button" class="btn btn-sm" onclick="eye(\''+record.moduleID+'\');">查看</button>';
			content += '<button type="button" class="btn btn-sm" onclick="add(\''+record.moduleID+'\',\''+record.moduleName+'\');">添加子</button>';
			content += '<button type="button" class="btn btn-sm" onclick="sub(\''+record.moduleID+'\', 1, '+dataNo+');">子菜单</button>';
			content += '<button type="button" class="btn btn-sm btn-sm btn-danger" onclick="del(\''+record.moduleID+'\');">删除</button>';
			return content+"</div>";
		}}
	];
	
	var grid = null;
	
	window.onload = function() {
		$('#page').css('display', 'block');
		
		grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			ajaxLoad : true,
			loadURL : 'systemmodule.do?p=find&moduleID='+unicode($("#searchText").val()),
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : null,
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		})
		grid.load();
	}
	
	
	function eye(moduleID) {
		window.location.href="systemmodule.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&searchText="+moduleID;
		return false;
	}

	function add(parentID, moduleName) {
		window.location.href="systemmodule.do?p=showAddView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&parentID="+parentID+"&moduleName="+unicode(moduleName);
		return false;
	}
	
	var dataNo1, dataNo2;
	function sub(parentID, grade, dataNo) {
		if (grade == 1) {
			dtGridColumns[dtGridColumns.length-1]={id:'', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
				var content = '<div class="btn-group">';
				content += '<button type="button" class="btn btn-sm" onclick="eye(\''+record.moduleID+'\');">查看</button>';
				content += '<button type="button" class="btn btn-sm" onclick="add(\''+record.moduleID+'\',\''+record.moduleName+'\');">添加子</button>';
				content += '<button type="button" class="btn btn-sm" onclick="sub(\''+record.moduleID+'\', 2, '+dataNo+');">子菜单</button>';
				content += '<button type="button" class="btn btn-sm btn-danger" onclick="del(\''+record.moduleID+'\');">删除</button>';
				return content+"</div>";
			}}
			
			$("#dtGridContainer > *").eq(0).find("tbody>tr").eq(dataNo1).css('background-color','white');
			var tr = $("#dtGridContainer > *").eq(0).find("tbody>tr").eq(dataNo*2).css('background-color','#fff0d5');
			dataNo1 = dataNo*2;
			
			$('#dtGridContainer2').children().remove();
			$('#dtGridToolBarContainer2').children().remove();
			
			var grid2 = $.fn.DtGrid.init({
				lang : 'zh-cn',
				ajaxLoad : true,
				loadURL : 'systemmodule.do?p=sub&parentID='+parentID,
				exportFileName : '系统模块信息',
				columns : dtGridColumns,
				gridContainer : 'dtGridContainer2',
				toolbarContainer : null,
				tools : 'print',
				pageSize : 10,
				pageSizeLimit : [10, 20, 50]
			});
			
			grid2.load();
			
		} else if(grade ==2) {
			dtGridColumns[dtGridColumns.length-1]={id:'', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
				var content = '<div class="btn-group">';
				content += '<button type="button" class="btn btn-sm" onclick="eye(\''+record.moduleID+'\');">查看</button>';
				content += '<button type="button" class="btn btn-sm btn-danger" onclick="del(\''+record.moduleID+'\');">删除</button>';
				return content+"</div>";
			}}
			
			$("#dtGridContainer2 > *").eq(0).find("tbody>tr").eq(dataNo2).css('background-color','white');
			var tr = $("#dtGridContainer2 > *").eq(0).find("tbody>tr").eq(dataNo*2).css('background-color','#fff0d5');
			dataNo2 = dataNo*2;
			
			$('#dtGridContainer3').children().remove();
			$('#dtGridToolBarContainer3').children().remove();
			
			var grid2 = $.fn.DtGrid.init({
				lang : 'zh-cn',
				ajaxLoad : true,
				loadURL : 'systemmodule.do?p=sub&parentID='+parentID,
				exportFileName : '系统模块信息',
				columns : dtGridColumns,
				gridContainer : 'dtGridContainer3',
				toolbarContainer : null,
				tools : 'print',
				pageSize : 10,
				pageSizeLimit : [10, 20, 50]
			})
			grid2.load();
		}
	}
	
	function del(moduleID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "systemmodule.do?p=del&searchText="+moduleID,
				data : $("#mainForm").serialize(),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'delete');	
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

		var grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			ajaxLoad : true,
			loadURL : 'systemmodule.do?p=find&moduleName='+unicode($("#searchText").val()),
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		})
		grid.load();
	});
	
	
	$("#btnAdd").on('click', function() {
		window.location.href = "systemmodule.do?p=showAddView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&operatInfo=0";
	});

</script>
<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


