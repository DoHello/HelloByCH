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

<link rel="stylesheet" type="text/css" href="css/imgView.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/style.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploadcontoller.css"/>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
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
					<h2 style="color:#353535; display:inline;">按钮管理 » 查看</h2>
					<button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
				</div>
				<hr style="margin:0 10px;">
				
				<div style="padding:10px; ">
				<div class="panel">
				<div class="panel-body">
					<form id="mainForm" class="form-horizontal" role="form" method='post'>
						<input type="text" name="mobuID" id="mobuID" value="${model.entity.mobuID}" class="hidden">
 						<div class="form-group">
							<label class="col-md-2 control-label">模块编码</label>
							<div class="col-md-3" >
								<input type="text" name="moduleID" id="moduleID" value="${model.entity.moduleID}" class="form-control">
							</div>
							
							<label class="col-md-1 control-label">页面地址</label>
							<div class="col-md-3" >
								<input type="text" name="requestMapping" id="requestMapping" value="${model.entity.requestMapping}" class="form-control">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-2 control-label">按钮标识</label>
							<div class="col-md-3" >
								<input type="text" name="buttonID" id="buttonID" value="${model.entity.buttonID}" class="form-control">
							</div>
							
							<label class="col-md-1 control-label">按钮名称</label>
							<div class="col-md-3" >
								<input type="text" name="buttonName" id="buttonName" value="${model.entity.buttonName}" class="form-control">
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-md-3" >
								<button type="button" id="btnCommit" class="btn btn-primary">提交</button>
							</div>
						</div>
					</form>
				</div>
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
	window.onload = function() {
		$('#page').css('display', 'block');
		
		
	}
	
	
	$("#btnCommit").click(function() {
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "systemmodule.do?p=editbutton",
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
				handsuc(data, 'insert', 'systemmodule.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}');
			},
			
			error:function(data) {
				handerr(data, 'insert')
			}
		});
	});
	
	
	$('#pageCloseBtn').click(function() {
		window.location.href = "systemmodule.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	});
</script>
<!-- 
<script type="text/javascript" src="js/pinYinCode.js"></script>
<script type="text/javascript" src="js/myjs/pageList.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="js/webuploader/webuploadcontoller.js"></script>
-->
</html>

	
