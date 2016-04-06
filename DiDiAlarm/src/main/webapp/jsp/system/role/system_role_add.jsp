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
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css"/>
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
				<h2 style="color:#353535; display:inline;">角色管理 » 新增</h2>
				<button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
			<div class="panel">
			<div class="panel-body">
				<form id="mainForm" class="form-horizontal" role="form" method='post'>
					<div class="hidden">
						<input type="text" id="inputDate" name="inputDate" class="form-control" value"${model.entity.inputDate}" placeholder="YYYY-MM-DD" data-am-datepicker="{locale: 'zh_CN'}" readonly/>
						<input type="text" id="inputName" name="inputName" class="form-control" value="${model.entity.inputName}" placeholder="" required>
						<input type="text" id="roleID" name="roleID" class="form-control" value="${model.entity.roleID}" placeholder="" required>
					</div>
				
					<div class="form-group">
						<label class="col-md-1 control-label">选择单位</label>
						<input class="hidden" name="companyName" id="companyName" value="${model.entity.orgName}">
						<div class="col-md-5" >
							<select name="companyID" id="companyID" class="chosen-select form-control" tabindex="2">
								<c:forEach items="${model.role}" var="comp">
									<c:choose>
										<c:when test="${ model.entity.orgID.indexOf(comp.companyID) >= 0 }">
											<option value="${comp.companyID}" selected>${comp.companyName }</option>
										</c:when>
										<c:otherwise>
											<option value="${comp.companyID}">${comp.companyName }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
				
					<div class="form-group">
						<label class="col-md-1 control-label">角色名称</label>
						<div class="col-md-5" >
							<input type="text" name='roleName' id="roleName" value="${model.entity.roleName}" class="form-control">
						</div>
					</div>
					
					<div class="text-center col-md-12" style='margin-top: 30px'>
						<button type="button" id="btnCommit" class="btn btn-primary">提交</button>
					</div>
				</form>
			</div>
			</div>
			</div>
			
			<div style="height:300px"></div>
		</div>
		<!-- content end -->
		
		</div>
	</div>
	

	<div id="append_parent" style="z-index: 10000"></div>
	
	<%@ include file="../../footer.jsp"%>
</body>
<script type="text/javascript">
	window.onload = function() {
		$("#quantity").keyup(function () {
			//如果输入非数字，则替换为''，如果输入数字，则在每4位之后添加一个空格分隔
			this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1");
		})
		
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
		
		$('#storeID').trigger('change');
		$('#companyID').trigger('change');
	}
	
	
	$("#btnCommit").click(function() {
		//if ($('#storeName').val() == "") {
		//	showmsg('请输入药店名称');
		//	return;
		//}
		
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : 'role.do?p=add',
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
				if (data.errtype == null) {
					handsuc(data, 'insert', 'role.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}');
				} else {
					handerr(data, 'insert');
				}
			},
			error:function(data) {
				handerr(data, 'insert');
			}
		});
	});
	
	
	// Chosen联动
	$('#storeId').change(function() {
		var s = $('#storeId').val();
		var t = "";
		$('#storeId option').each(function(idx) {
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
	
	
	// Chosen联动
	$('#companyID').change(function() {
		var s = $('#companyID').val();
		var t = "";
		$('#companyID option').each(function(idx) {
			var txt = $(this).text(); //获取单个text
			var val = $(this).val(); //获取单个value
			
			if (s == null) {
				t = "";
			} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
				//t += "," + txt;
				t = txt;
			}
		});
		$('#companyName').attr('value', t);
	})
	
	
	$('#pageCloseBtn').click(function() {
		window.location.href = "role.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	});
</script>
</html>
