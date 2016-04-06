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
				<h2 style="color:#353535; display:inline;">系统管理» 系统配置管理</h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="字典名">
						<input name="categoryName" type="hidden" id="categoryName" >
							<select name="categoryID" id="categoryID" class="chosen-select form-control" tabindex="2">
								<c:forEach items="${model.category}" var="category">
									<c:choose>
										<c:when test="${ model.entity.categoryID.indexOf(category.categoryID) >= 0 }">
											<option value="${category.categoryID}" selected>${category.categoryName}</option>
										</c:when>
										<c:otherwise>
											<option value="${category.categoryID}">${category.categoryName }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
					</div>
					
					<div class="col-md-9">
						<div class="pull-right">
						<div class="btn-group">
							<c:forEach items="${model.userbutton }" var="buts">
								<c:if test="${buts.buttonID=='ds' }">
									<button type="button" id="btnSearch" class="btn">搜索</button>
								</c:if>
							</c:forEach>
							<c:forEach items="${model.userbutton }" var="buts">
								<c:if test="${buts.buttonID=='da' }">
									<button type="button" id="btnAdd" class="btn">添加</button>
								</c:if>
							</c:forEach>
						</div>				
						</div>
					</div>
				</div>
				<hr style="margin:10px 0 0 0;">
				
				<div style="margin-top:-1px;float:right;width: 100%">
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
		{id:'dicName', title:'字典名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'status', title:'是否可用', type:'string', columnClass:'text-center', hideType:'', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group" style="color:red;">';
			if(record.status==DATA_STATUS.DELETE.value){
			content+='删除';
			}else{
				content+='可用';
					}	
						return content + '</div>';}},
		{id:'categoryName', title:'字典类型', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'dq\' }"><button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.dicID+'\');"><i class="fa fa-eye"></i>查看</button></c:if></c:forEach>';
			if(record.status!=DATA_STATUS.DELETE.value){
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'dd\' }"><button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.dicID+'\', '+dataNo+');"><i class="fa fa-trash-o"></i>删除</button></c:if></c:forEach>';
			}
			return content + '</div>';
		}}
	]
	
	
	var grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: false,
			ajaxLoad : true,
			loadURL : 'dic.do?p=find',
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
			
		});
	var grid1 = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: false,
			ajaxLoad : true,
			loadURL : 'dic.do?p=find1',
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		});
	
	
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
	
	
	function eye(dicID) {
		window.location.href="dic.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&dicID="+dicID;
		return false;
	}
	
	
	function del(dicID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "dic.do?p=del&dicID="+dicID,
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
		
		grid.parameters = new Object();
		grid.parameters["searchText"] = $("#searchText").val();
		sessionStorage.setItem("url","dic.do?p=find");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
	});
	
	
	$("#btnAdd").on('click', function() {
		window.location.href = "dic.do?p=showAddView";
	});
	
	
	$("#btnEye").on('click', function() {
		window.location.href = "dic.do?p=eyeView";
	});
	$("#categoryID").change(function() {
			var categoryName = $("#categoryID").find("option:selected").text();
			$("#categoryName").val(categoryName);
			grid.parameters = new Object();
		grid.parameters["searchText"] =categoryName;
		grid.parameters["searchinfo"] ='1';
		if(categoryName === '分类查看'){
			grid.parameters["searchText"] ='';
		}
		sessionStorage.setItem("url","dic.do?p=find");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
			
				});
	
	
</script>
<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


