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
				<h2 style="color:#353535; display:inline;">留言反馈» 警察反馈</h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="用户名">
					</div>
					
					<div class="col-md-9">
						<div class="pull-right">
						<div class="btn-group">
							<button id="btnSearch"  type="button" class="btn">搜索</button>
					
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
		{id:'userName', title:'用户名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
		//{id:'title', title:'标题', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'context', title:'内容', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'phone', title:'电话', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'createTime', title:'创建时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	    {id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
var content = '<div class="btn-group">';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'polic\' }"><button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.userID+'\');"><i class="fa fa-eye"></i>查看</button></c:if></c:forEach>';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'policeMessageD\' }"><button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.messageID+'\', '+dataNo+');"><i class="fa fa-trash-o"></i>删除</button></c:if></c:forEach>';
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
	
	
	function del(messageID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "message.do?p=del&messageID="+messageID,
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
			loadURL : 'message.do?p=policeFind',
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
		sessionStorage.setItem("url","message.do?p=policeFind");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
	});

	
</script>
<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


