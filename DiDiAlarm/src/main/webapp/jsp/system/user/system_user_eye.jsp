<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>

<link rel="stylesheet" type="text/css" href="css/imgView.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/style.css" />
<link rel="stylesheet" type="text/css"
	href="js/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css"
	href="js/webuploader/webuploadcontoller.css" />
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
				<div style="padding:20px">
					<h2 style="color:#353535; display:inline;">警员信息» 查看</h2>
					<button data-toggle="tooltip" type="button"
						class="btn btn-link path-close-btn path-btn" id="pageCloseBtn"
						data-original-title="关闭（ESC）">
						<i class="path-1"></i>
					</button>
				</div>
				<hr style="margin:0 10px;">

				<div style="padding:10px;height: 900px  ">
					<div class="panel">
						<div class="panel-body">
							<form id="mainForm" class="form-horizontal" role="form"
								method='post'>
								<div class="hidden">
									<input type="text" id="inputDate" name="inputDate"
										class="form-control"
										value"${model.entity.inputDate}" placeholder="YYYY-MM-DD"
										data-am-datepicker="{locale: 'zh_CN'}" readonly /> <input
										type="text" id="inputName" name="inputName"
										class="form-control" value="${model.entity.inputName}"
										placeholder="" required> <input type="text"
										id="userID" name="userID" class="form-control"
										value="${model.entity.userID}" placeholder="" required>
								</div>

								<div class="form-group">
									<label class="col-md-1 control-label">选择公司</label> <input
										class="hidden" name="companyName" id="companyName"
										value="${model.entity.companyName}">
									<div class="col-md-5">
										<select name="companyID" id="companyID"
											class="chosen-select form-control" tabindex="2">
											<c:forEach items="${model.companyAll}" var="sto">
												<c:choose>
													<c:when
														test="${ model.entity.companyID.indexOf(sto.companyID) >= 0 }">
														<option value="${sto.companyID}" selected>${sto.companyName }</option>
													</c:when>
													<c:otherwise>
														<option value="${sto.companyID}">${sto.companyName }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									<label class="col-md-1 control-label">警号</label>
									<div class="col-md-5">
										<input type="text" name='policeNumber' id="policeNumber"
											value="${model.entity.policeNumber}" class="form-control">
									</div>

								</div>

								<div class="form-group">
									<label class="col-md-1 control-label">选择角色</label> <input
										class="hidden" name="roleName" id="roleName"
										value="${model.entity.roleName}">
									<div class="col-md-5">
										<select name="roleID" id="roleID"
											class="chosen-select form-control" tabindex="2">
											<c:forEach items="${model.roleAll}" var="rol">
												<c:choose>
													<c:when
														test="${ model.entity.roleID.indexOf(rol.companyID) >= 0 }">
														<option value="${rol.roleID}" selected>${rol.roleID }</option>
													</c:when>
													<c:otherwise>
														<option value="${rol.roleID}">${rol.roleName }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>

									<label class="col-md-1 control-label">选择性别</label> <input
										class="hidden" name="sexName" id="sexName"
										value="${model.entity.sexName}">
									<div class="col-md-5">
										<select name="sexID" id="sexID"
											class="chosen-select form-control" tabindex="2">
											<c:forEach items="${model.sex}" var="se">
												<c:choose>
													<c:when
														test="${ model.entity.sexID.indexOf(se.dicValue) >= 0 }">
														<option value="${se.dicValue}" selected>${se.dicName }</option>
													</c:when>
													<c:otherwise>
														<option value="${se.dicValue}">${se.dicName }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-1 control-label">登录名称</label>
									<div class="col-md-5">
										<input type="text" name='userName' id="userName"
											value="${model.entity.userName}" class="form-control">
									</div>

									<label class="col-md-1 control-label">真实姓名</label>
									<div class="col-md-5">
										<input type="text" name='realName' id="realName"
											value="${model.entity.realName}" class="form-control">
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-1 control-label">手机</label>
									<div class="col-md-5">
										<input type="text" name='phone' id="phone"
											value="${model.entity.phone}" class="form-control">
									</div>

									<label class="col-md-1 control-label">QQ</label>
									<div class="col-md-5">
										<input type="text" name='qq' id="qq"
											value="${model.entity.qq}" class="form-control">
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-1 control-label">密码</label>
									<div class="col-md-5">
										<input type="password" name='password' id="password"
											value="******" class="form-control">
									</div>
									<label class="col-md-1 control-label">身份证号</label>
									<div class="col-md-5">
										<input type="text" name='idCard' id="idCard"
											value="${model.entity.idCard}" class="form-control">
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-1 control-label">职位</label> <input
										class="hidden" name="job" id="job" value="${model.entity.job}">
									<div class="col-md-5">
										<select name="jobValue" id="jobValue"
											class="chosen-select form-control" tabindex="2">
											<c:forEach items="${model.jobAll}" var="job">
												<c:choose>
													<c:when
														test="${ model.entity.jobValue.indexOf(job.dicValue) >= 0 }">
														<option value="${job.dicValue}" selected>${job.dicName }</option>
													</c:when>
													<c:otherwise>
														<option value="${job.dicValue}">${job.dicName }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									<label class="col-md-1 control-label">巡警级别</label> <input
										class="hidden" name="policeLevel" id="policeLevel"
										value="${model.entity.policeLevel}">
									<div class="col-md-5">
										<select name="policeLevelValue" id="policeLevelValue"
											class="chosen-select form-control" tabindex="2">
											<c:forEach items="${model.levelAll}" var="level">
												<c:choose>
													<c:when
														test="${ model.entity.policeLevelValue.indexOf(level.dicValue) >= 0 }">
														<option value="${level.dicValue}" selected>${level.dicName }</option>
													</c:when>
													<c:otherwise>
														<option value="${level.dicValue}">${level.dicName }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="form-group">
									<label class="col-md-1 control-label">辖区</label> <input
										class="hidden" name="underArea" id="underArea"
										value="${model.entity.underArea}">
									<div class="col-md-5">
										<select name="underValue" id="underValue"
											class="chosen-select form-control" tabindex="2">
											<c:forEach items="${model.underAll}" var="under">
												<c:choose>
													<c:when
														test="${ model.entity.underValue.indexOf(under.dicValue) >= 0 }">
														<option value="${under.dicValue}" selected>${under.dicName }</option>
													</c:when>
													<c:otherwise>
														<option value="${under.dicValue}">${under.dicName }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									<label class="col-md-1 control-label">Email</label>
									<div class="col-md-5">
										<input type="text" name='email' id="email"
											value="${model.entity.email}" class="form-control">
									</div>
								</div>
								<div class="form-group">
									
									<label class="col-md-1 control-label">地区</label>
									<div class="col-md-5">
									<input class="hidden" name="province" id="province" value="${model.entity.province}">
										<select style="width: 100px;float:left;" id="provinceID" name="provinceID" onchange="chg(this,${model.entity.cityID});"class="form-control" >
										<option value="${model.entity.provinceID}">${model.entity.province}</option>
										</select>
										<input class="hidden" name="city" id="city" value="${model.entity.city}">
										<select style="width: 100px;float:left;" id="cityID" name='cityID' onchange="chg2(this,${model.entity.areaID});" class="form-control" >
									<option value="${model.entity.cityID}">${model.entity.city}</option>
										</select>
										</select>
										<input class="hidden" name="area" id="area" value="${model.entity.area}">
										<select style="width: 100px;float:left;" id="areaID" name='areaID' class="form-control">
									<option value="${model.entity.areaID}">${model.entity.area}</option>
										</select>
										</select>
									</div>
									<label class="col-md-1 control-label">地址</label>
									<div class="col-md-5">
										<input type="text" name='stroeID' id="storeName"
											value="${model.entity.storeName}" class="form-control">
									</div>
								</div>
								<div class="text-center col-md-12" style='margin-top: 30px'>
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
 <script src="js/myjs/city.js"></script>
<script src="js/myjs/area.js"></script>
<script type="text/javascript">
	window.onload = function() {
	
		$("#quantity").keyup(
				function() {
					//如果输入非数字，则替换为''，如果输入数字，则在每4位之后添加一个空格分隔
					this.value = this.value.replace(/[^\d]/g, '').replace(
							/(\d{4})(?=\d)/g, "$1");
				})

		$('#page').css('display', 'block');

		if ($.fn.chosen) {
			$('#pageBody .chosen-select').chosen();
		}

		if ($.fn.datetimepicker) {
			$('#pageBody .form-date').datetimepicker({
				language : 'zh-CN',
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2,
				forceParse : 0,
				format : 'yyyy-mm-dd'
			});
		}
		////////////////////////////////
		$('#provinceID').change(function() {
			var s = $('#provinceID').val();
			var t = "";
			$('#provinceID option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
				//	t += "," + txt;
					t = txt;
				}
			});
			$('#province').attr('value', t);
		})
		$('#provinceID').trigger('change');
		$('#cityID').change(function() {
			var s = $('#cityID').val();
			var t = "";
			$('#cityID option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
				//	t += "," + txt;
					t = txt;
				}
			});
			$('#city').attr('value', t);
		})
		$('#cityID').trigger('change');
		$('#areaID').change(function() {
			var s = $('#areaID').val();
			var t = "";
			$('#areaID option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
				//	t += "," + txt;
					t = txt;
				}
			});
			$('#area').attr('value', t);
		})
		$('#areaID').trigger('change');
		////////////////////////////////
		// Chosen联动
		$('#jobValue').change(function() {
			var s = $('#jobValue').val();
			var t = "";
			$('#jobValue option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
					//t += "," + txt;
					t = txt;
				}
			});
			$('#job').attr('value', t);
		})
		$('#jobValue').trigger('change');
		// Chosen联动
		$('#underValue').change(function() {
			var s = $('#underValue').val();
			var t = "";
			$('#underValue option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
					//t += "," + txt;
					t = txt;
				}
			});
			$('#underArea').attr('value', t);
		})
		$('#underValue').trigger('change');
		// Chosen联动
		$('#policeLevelValue').change(function() {
			var s = $('#policeLevelValue').val();
			var t = "";
			$('#policeLevelValue option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
					//t += "," + txt;
					t = txt;
				}
			});
			$('#policeLevel').attr('value', t);
		})
		$('#policeLevelValue').trigger('change');
		////////////////////////////////////
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
		$('#companyID').trigger('change');

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
		$('#storeID').trigger('change');

		// Chosen联动
		$('#roleID').change(function() {
			var s = $('#roleID').val();
			var t = "";
			$('#roleID option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
					//t += "," + txt;
					t = txt;
				}
			});
			$('#roleName').attr('value', t);
		})
		$('#roleID').trigger('change');

		// Chosen联动
		$('#sexID').change(function() {
			var s = $('#sexID').val();
			var t = "";
			$('#sexID option').each(function(idx) {
				var txt = $(this).text(); //获取单个text
				var val = $(this).val(); //获取单个value

				if (s == null) {
					t = "";
				} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
					//t += "," + txt;
					t = txt;
				}
			});
			$('#sexName').attr('value', t);
		})
		$('#sexID').trigger('change');
	}

	$("#btnCommit")
			.click(
					function() {
						//if ($('#storeName').val() == "") {
						//	showmsg('请输入药店名称');
						//	return;
						//}

						$('#loading-waiting-modal').modal({
							closeViaDimmer : false
						});
						$
								.ajax({
									type : 'POST',
									url : 'user.do?p=edit',
									data : $("#mainForm").serialize(),
									cache : false,
									dataType : 'json',

									success : function(data) {
										if (data.errtype == null) {
											handsuc(
													data,
													'update',
													'user.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}');
										} else {
											handerr(data, 'update');
										}
									},
									error : function(data) {
										handerr(data, 'update');
									}
								});
					});

	$('#pageCloseBtn')
			.click(
					function() {
						window.location.href = "user.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
					});
</script>
</html>
