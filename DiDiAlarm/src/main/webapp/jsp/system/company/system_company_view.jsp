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
	PageData pageModel = (PageData) request.getAttribute("model");
%>

<!DOCTYPE HTML>
<html>

<link rel="stylesheet" type="text/css" href="css/imgView.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/style.css" />
<link rel="stylesheet" type="text/css"
	href="js/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css"
	href="js/webuploader/webuploadcontoller.css" />
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
					<h2 style="color:#353535; display:inline;">系统管理» 派出所管理</h2>
				</div>
				<hr style="margin:0 10px;">

				<div style="padding:10px; ">
					<div class="row">
						<div class="col-md-3">
							<input type='text' id="searchText" class="form-control"
								placeholder="派出所名">
						</div>
<input type="hidden" id="newsCase" name="newsCase" value="">
						<div class="col-md-9">
							<div class="pull-right">
								<div class="btn-group">
								<c:forEach items="${model.userbutton }" var="buts">
											<c:if test="${buts.buttonID=='companyns' }">
												<button id="btnSearch" type="button" class="btn">搜索</button>
											</c:if>
									</c:forEach>
									<c:forEach items="${model.userbutton }" var="buts">
											<c:if test="${buts.buttonID=='companyna' }">
												<button type="button" id="btnAdd" class="btn">新增</button>
											</c:if>
									</c:forEach>
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
	/*
	var str = (function(){/*
		<c:forEach items="${model.userbutton }" var="buts">
											<c:if test="${buts.buttonID=='naaa' }">
												<button type="button" class="btn btn-xs btn-default" onclick="eye('
													record.articleID');"><i class="fa fa-eye"></i>查看</button>
											</c:if>
		</c:forEach>
		});
 	var stro = str.toString().match(/\/\*!?(?:\@preserve)?[ \t]*(?:\r\n|\n)([\s\S]*?)(?:\r\n|\n)\s*\*\//);
		*/
	var dtGridColumns = [
			{
				id : 'companyName',
				title : '派出所',
				type : 'string',
				columnClass : 'text-center',
				hideType : '',
				fastQuery : true,
				fastQueryType : 'eq'
			},
			{
				id : 'address',
				title : '地址',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq'
			},
		
				{
				id : '',
				title : '操作',
				type : 'string',
				columnClass : 'text-center',
				columnStyle : 'padding:0px;',
				resolution : function(value, record, column, grid, dataNo,
						columnNo) {
					var content = '';
					content += '<c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'companynq\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="eye(\''
							+ record.companyID
							+ '\');"><i class="fa fa-eye"></i>查看</button>'
							+ '</c:if>'
							+ '</c:forEach>';
							
					content += '<c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'companynn\' }">'
							+ '<button type="button" class="btn btn-xs btn-danger" onclick="del(\''
							+ record.companyID
							+ '\', '
							+ dataNo
							+ ');"><i class="fa fa-trash-o"></i>删除</button>'
							+ '</c:if>'
							+ '</c:forEach>';
							return content + '';

				}
			}
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

	function eye(companyID) {
		window.location.href = "company.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&companyID="
				+ companyID;
		return false;
	}

	function del(companyID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');

		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui',
				function() {
				});

		$confirmBtn.off('click.confirm.modal.amui').on(
				'click.confirm.modal.amui', function() {
					$('#loading-waiting-modal').modal({
						closeViaDimmer : false
					});
					$.ajax({
						lang : 'zh-cn',
						type : 'POST',
						url : "company.do?p=del&companyID=" + companyID,
						data : $("#mainForm").serialize(),
						cache : false,
						dataType : 'json',

						success : function(data) {
							handsuc(data, 'delete');
							$('#dtGridContainer').children().remove();
							$('#dtGridToolBarContainer').children().remove();
							grid.load();
						},

						error : function(data) {
							handerr(data);
						}
					});
				});

		$('#delete-comfirm-modal').modal({});
	};
    
function cancelPublish(newsID) {
		var $confirm = $('#cancelPublish-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');

		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui',
				function() {
				});
        
		$confirmBtn.off('click.confirm.modal.amui').on(
				'click.confirm.modal.amui', function() {
					$('#loading-waiting-modal').modal({
						closeViaDimmer : false
					});
					$.ajax({
						lang : 'zh-cn',
						type : 'POST',
						url : "news.do?p=cancelPublish&newsID=" + newsID,
						data : $("#mainForm").serialize(),
						cache : false,
						dataType : 'json',

						success : function(data) {
							handsuc(data, 'delete');
							$('#dtGridContainer').children().remove();
							$('#dtGridToolBarContainer').children().remove();
							grid.load();
						},

						error : function(data) {
							handerr(data);
						}
					});
				});

		$('#cancelPublish-comfirm-modal').modal({});
	};



function push(newsID,pushStatus1) {
		var $confirm = $('#push-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
        var publishState=$("#state2").val();
       

        $("#newsCase").attr("value",pushStatus1);
    
        
        
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui',
				function() {
				});

		$confirmBtn.off('click.confirm.modal.amui').on(
				'click.confirm.modal.amui', function() {
					$('#loading-waiting-modal').modal({
						closeViaDimmer : false
					});
					$.ajax({
						lang : 'zh-cn',
						type : 'POST',
						url : "news.do?p=push&newsID=" + newsID+"&pushStatus=" + pushStatus1,
						data : $("#mainForm").serialize(),
						cache : false,
						dataType : 'json',

						success : function(data) {
							handsuc(data, 'delete');
							$('#dtGridContainer').children().remove();
							$('#dtGridToolBarContainer').children().remove();
							grid.load();
						},

						error : function(data) {
							handerr(data);
						}
					});
				});

		$('#push-comfirm-modal').modal({});
	};
	

function publish(newsID,publishState1) {
	    
        var $confirm = $('#publish-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
	

		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui',
				function() {
				});
          
		$confirmBtn.off('click.confirm.modal.amui').on(
				'click.confirm.modal.amui', function() {
					$('#loading-waiting-modal').modal({
						closeViaDimmer : false
					});
					//var publishState 
				    var publishState=$("#state1").val();
					$.ajax({
						lang : 'zh-cn',
						type : 'POST',
						url : "news.do?p=publish&newsID=" + newsID +"&publishState=" + publishState,
						data : $("#mainForm").serialize(),
						cache : false,
						dataType : 'json',

						success : function(data) {
							handsuc(data, 'delete');
							
							$('#dtGridContainer').children().remove();
							$('#dtGridToolBarContainer').children().remove();
							grid.load();							
							//push(newsID,publishState);
						},

						error : function(data) {
							handerr(data);
						}
					});
				});

		$('#publish-comfirm-modal').modal({});
	};


	$('#btnSearch').on('click', function() {
		$('#dtGridContainer').children().remove();
		$('#dtGridToolBarContainer').children().remove();
		grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			
			ajaxLoad : true,
			loadURL : 'company.do?p=find',
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [ 10, 20, 50 ]
		});
		grid.parameters = new Object();
		grid.parameters["searchText"] = $("#searchText").val();
		grid.load();
	});

	$("#btnAdd").on('click', function() {
		window.location.href = "company.do?p=showAddView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	});

	$("#btnEye").on('click', function() {
		window.location.href = "news.do?p=eyeView";
	});

	$("#btnQmCheck")
			.on(
					'click',
					function() {
						if (grid.getCheckedRecords().length == 0) {
							toast("您没有选择需要审核的数据");
							return;
						}
						$('#loading-waiting-modal').modal({
							closeViaDimmer : false
						});
						$
								.ajax({
									lang : 'zh-cn',
									type : 'POST',
									url : "news.do?p=qmCheck&check=YES",
									processData : false,
									data : 'str='
											+ JSON.stringify(grid
													.getCheckedRecords()),
									cache : false,
									dataType : 'json',

									success : function(data) {
										handsuc(data, 'default');
										if (data.errtype == null) {
											for (var i = 0; i < grid
													.getCheckedRecords().length; i++) {
												(grid.getCheckedRecords())[i].qmCheckName = '${model.SessionUser.userName}';
												(grid.getCheckedRecords())[i].qmCheckDate = new Date();
												grid.refresh();
											}
										}
									},

									error : function(data) {
										handerr(data);
									}
								});

					});

	$("#btnQmCheckNo").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			toast("您没有选择需要取消审核的数据");
			return;
		}
		$('#loading-waiting-modal').modal({
			closeViaDimmer : false
		});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "chainfirstdc.do?p=qmCheck&check=NO",
			processData : false,
			data : 'str=' + JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',

			success : function(data) {
				handsuc(data, 'default');
				if (data.errtype == null) {
					for (var i = 0; i < grid.getCheckedRecords().length; i++) {
						(grid.getCheckedRecords())[i].qmCheckName = '';
						(grid.getCheckedRecords())[i].qmCheckDate = '';
						grid.refresh();
					}
				}
			},

			error : function(data) {
				handerr(data);
			}
		});
	});

	$("#btnCmCheck")
			.on(
					'click',
					function() {
						if (grid.getCheckedRecords().length == 0) {
							toast("您没有选择需要审核的数据");
							return;
						}
						$('#loading-waiting-modal').modal({
							closeViaDimmer : false
						});
						$
								.ajax({
									lang : 'zh-cn',
									type : 'POST',
									url : "chainfirstdc.do?p=cmCheck&check=YES",
									processData : false,
									data : 'str='
											+ JSON.stringify(grid
													.getCheckedRecords()),
									cache : false,
									dataType : 'json',

									success : function(data) {
										handsuc(data, 'default');
										if (data.errtype == null) {
											for (var i = 0; i < grid
													.getCheckedRecords().length; i++) {
												(grid.getCheckedRecords())[i].cmCheckName = '${model.SessionUser.userName}';
												(grid.getCheckedRecords())[i].cmCheckDate = new Date();
												grid.refresh();
											}
										}
									},

									error : function(data) {
										handerr(data);
									}
								});
					});

	$("#btnCmCheckNo").on('click', function() {
		if (grid.getCheckedRecords().length == 0) {
			toast("您没有选择需要取消审核的数据");
			return;
		}
		$('#loading-waiting-modal').modal({
			closeViaDimmer : false
		});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "chainfirstdc.do?p=cmCheck&check=NO",
			processData : false,
			data : 'str=' + JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',

			success : function(data) {
				handsuc(data, 'default');
				if (data.errtype == null) {
					for (var i = 0; i < grid.getCheckedRecords().length; i++) {
						(grid.getCheckedRecords())[i].cmCheckName = '';
						(grid.getCheckedRecords())[i].cmCheckDate = '';
						grid.refresh();
					}
				}
			},

			error : function(data) {
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
		$('#loading-waiting-modal').modal({
			closeViaDimmer : false
		});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "news.do?p=del",
			processData : false,
			data : 'str=' + JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',

			success : function(data) {
				handsuc(data, 'default');
				search();
			},

			error : function(data) {
				handerr(data);
			}
		});
	});

	$("#btnPutInALL").on('click', function() {
		$('#loading-waiting-modal').modal({
			closeViaDimmer : false
		});
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "chainfirstdc.do?p=putinall",
			processData : false,
			data : 'str=' + JSON.stringify(grid.getCheckedRecords()),
			cache : false,
			dataType : 'json',

			success : function(data) {
				handsuc(data, 'default');
				search();
			},

			error : function(data) {
				handerr(data);
			}
		});
	});
</script>

<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


