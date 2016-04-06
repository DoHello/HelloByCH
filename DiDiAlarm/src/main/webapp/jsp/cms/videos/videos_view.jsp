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
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploadcontoller.css" />
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
				<h2 style="color:#353535; display:inline;">列表信息管理» Videos</h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="标题">
					</div>
					
					<div class="col-md-9">
						<div class="pull-right">
						<div class="btn-group">
							<button id="btnSearch"  type="button" class="btn">搜索</button>
							<button type="button" id="btnAdd" class="btn">新增</button>
						</div>				
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
		{id:'title', title:'标题', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'abstractz', title:'摘要', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'url', title:'视频', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
				content+='<video width="320" height="240" src="<%=basePath%>'+record.url+'" controls="controls" autoplay="autoplay">您的浏览器不支持 video 标签。</video>';
						return content + '</div>';}},
		{id:'hitTimes', title:'点击量', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'createTime', title:'创建时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			content += '<button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.slideID+'\', '+dataNo+');"><i class="fa fa-trash-o"></i>删除</button>';
			return content + '</div>';
		}}
	]
	
	
	var grid = null;
	
	
	window.onload = function() {
		$('#page').css('display', 'block');
		
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
	
	
	function eye(slideID) {
		window.location.href="videos.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&slideID="+slideID;
		return false;
	}
	
	
	function del(slideID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "videos.do?p=del&slideID="+slideID,
				data : $("#mainForm").serialize(),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'delete');
					$('#dtGridContainer').children().remove();
					$('#dtGridToolBarContainer').children().remove();
					grid.load();
				},
				
				error:function(data) {
					handerr(data);
				}
			});
		});
		
		$('#delete-comfirm-modal').modal({});
	};
	
	
	$('#btnSearch').on('click', function() {
		$('#dtGridContainer').children().remove();
		$('#dtGridToolBarContainer').children().remove();
		grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: false,
			ajaxLoad : true,
			loadURL : 'videos.do?p=find',
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		});
		grid.parameters = new Object();
		grid.parameters["searchText"] = $("#searchText").val();
		grid.load();
	});
	
	
	$("#btnAdd").on('click', function() {
		window.location.href = "videos.do?p=showAddView";
	});
	
	
	$("#btnEye").on('click', function() {
		window.location.href = "videos.do?p=eyeView";
	});
	
	
	$("#btnQmCheck").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			toast("您没有选择需要审核的数据");
			return;
		}
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "videos.do?p=qmCheck&check=YES",
			processData: false,
			data : 'str='+JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				handsuc(data, 'default');
				if (data.errtype == null) {
					for (var i=0; i<grid.getCheckedRecords().length; i++) {
						(grid.getCheckedRecords())[i].qmCheckName='${model.SessionUser.userName}';
						(grid.getCheckedRecords())[i].qmCheckDate=new Date();
						grid.refresh();
					}
				}
			},
			
			error:function(data) {
				handerr(data);
			}
		});
		
	});
	
	

	
	$("#btnCmCheckNo").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			toast("您没有选择需要取消审核的数据");
			return;
		}
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "chainfirstdc.do?p=cmCheck&check=NO",
			processData: false,
			data : 'str='+JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				handsuc(data, 'default');
				if (data.errtype == null) {
					for (var i=0; i<grid.getCheckedRecords().length; i++) {
						(grid.getCheckedRecords())[i].cmCheckName='';
						(grid.getCheckedRecords())[i].cmCheckDate='';
						grid.refresh();
					}
				}
			},
			
			error:function(data) {
				handerr(data);
			}
		});
	});
	
	
	$("#btnPutIn").on('click', function() {
		//console.log(JSON.stringify(grid.getCheckedRecords()));
		if (grid.getCheckedRecords().length == 0) {
			toast("您没有选择需要导入的数据");
			return;
		}
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "news.do?p=del",
			processData: false,
			data : 'str='+JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				handsuc(data, 'default');
				search();
			},
			
			error:function(data) {
				handerr(data);
			}
		});
	});
	
	
	$("#btnPutInALL").on('click', function() {
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "chainfirstdc.do?p=putinall",
			processData: false,
			data : 'str='+JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',
		
			success: function(data) {
				handsuc(data, 'default');
				search();
			},
			
			error:function(data) {
				handerr(data);
			}
		});
	});
	
</script>
<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


