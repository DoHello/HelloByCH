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
					<h2 style="color:#353535; display:inline;">新闻管理» 新闻发布</h2>
				</div>
				<hr style="margin:0 10px;">

				<div style="padding:10px; ">
					<div class="row">
						<div class="col-md-3">
							<input type='text' id="searchText" class="form-control"
								placeholder="标题">
						</div>
<input type="hidden" id="newsCase" name="newsCase" value="">
						<div class="col-md-9">
							<div class="pull-right">
								<div class="btn-group">
								<c:forEach items="${model.userbutton }" var="buts">
											<c:if test="${buts.buttonID=='ns' }">
												<button id="btnSearch" type="button" class="btn">搜索</button>
											</c:if>
									</c:forEach>
									<c:forEach items="${model.userbutton }" var="buts">
											<c:if test="${buts.buttonID=='na' }">
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
				id : 'publishTime',
				title : '最近一次发布时间',
				type : 'string',
				columnClass : 'text-center',
				hideType : '',
				fastQuery : true,
				fastQueryType : 'eq'
			},
			{
				id : 'title',
				title : '标题',
				type : 'string',
				
				hideType : '',
				fastQuery : true,
				fastQueryType : 'eq'
			},
			{
				id : 'state',
				title : '资讯类型',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;',
				resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                if(value=='notice'){
                content += '<span style="padding:2px 10px;color:black;">公告</span>';
                } else if(value=='news'){
                content += '<span style="padding:2px 10px;color:black;">新闻</span>';
                }
                return content;}
			},
			{
				id : 'focusStatus',
				title : '资讯焦点',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;',
				resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                if(value=='yesFocus'){
                content += '<span style="padding:2px 10px;color:black;">焦点</span>';
                } else if(value=='noFocus'){
                content += '<span style="padding:2px 10px;color:black;">非焦点</span>';
                }
                return content;}
			},
			{
				id : 'realName',
				title : '录入人',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq'
			},
			{
				id : 'pushNum',
				title : '推送次数',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'
			},
			
			{
				id : 'publishState',
				title : '发布状态',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;',
				resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                if(value=='all'){
                content += '<span style="background:#66FFCC;padding:2px 10px;color:white;">全部</span>';
                } else if(value=='member'){
                content += '<span style="background:#c447ae;padding:2px 10px;color:white;">群众</span>';
                }else if(value=='police'){
                content += '<span style="background:#A52A2A;padding:2px 10px;color:white;">警察</span>';
                }else if(value=='noPublish'){
                content += '<span style="padding:2px 10px;color:black;">未发布</span>';
                }
                return content;}
			},
			{
				id : 'summary',
				title : '摘要',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'lg|md|sm|xs',
				fastQuery : true,
				fastQueryType : 'eq'
			},
			{
				id : 'url',
				title : '查看新闻',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'xs',
				fastQuery : true,
				fastQueryType : 'eq',
				resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                
/*                 <a target="_blank" href="new.php"></a>
 */                
              /*   if(value=='all'){
                content += '<span style="background:#66FFCC;padding:2px 10px;color:white;">全部</span>';
                } else if(value=='member'){
                content += '<span style="background:#c447ae;padding:2px 10px;color:white;">群众</span>';
                }else if(value=='police'){
                content += '<span style="background:#FFFF00;padding:2px 10px;color:white;">警察</span>';
                }else if(value=='noPublish'){
                content += '<span style="background:#c447ae;padding:2px 10px;color:white;">未发布</span>';
                } */
        content += '<a target="_blank" href=\''
							+ value
							+ '\'>查看</a>';
                
                return content;}
			},
			/* {
				id : 'context',
				title : '全文',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'lg|md|sm|xs',
				fastQuery : true,
				fastQueryType : 'eq'
			}, */
			/* {
				id : 'hitTimes',
				title : '点击量',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'sm|xs',
				fastQuery : true,
				fastQueryType : 'eq'
			}, */
			{
				id : 'createTime',
				title : '创建时间',
				type : 'string',
				columnClass : 'text-center',
				hideType : 'lg|md|sm|xs',
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
							+ '<c:if test="${buts.buttonID==\'nq\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="eye(\''
							+ record.newsID
							+ '\');"><i class="fa fa-eye"></i>修改</button>'
							+ '</c:if>'
							+ '</c:forEach>';
								
/* 					content += '<c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'np\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="publish(\''
							+ record.newsID
							+ '\', '
							+ dataNo
							+ ');"><i class="fa fa"></i>推送人群</button>'
							+ '</c:if>'
							+ '</c:forEach>';	 */	
				    
				  /*  content += '<div class="btn-group">'
				            + '<button type="button" class="btn btn-xs dropdown-toggle" data-toggle="dropdown">'
				            + '<span class="caret "></span>'
				            + '<span class="sr-only">操作</span>'
				            + '</button>'
				        + '<ul class="dropdown-menu" role="menu">'
				            + '<li><a  onclick="push(\''
				            +record.newsID
				            +'\','
				            +'\'pushMember\' )">群众端</a></li>'
				            
				            + '<li><a  onclick="push(\''
				            +record.newsID
				            +'\','
				            +'\'pushPolice\' )">警察端</a></li>'
				            
				            + '<li><a  onclick="push(\''fjquery
				            +record.newsID
				            +'\','
				            +'\'pushAll\')">全部人</a></li>'
				            +'</ul>'
				            +'</div>' */
				            /* alert(record.publishState) */
				            
				            //content+=''
				            //alert(record.publishState)
				            
                     if(record.publishState=='noPublish'){	
							
							content += ' <c:forEach items="${model.userbutton}" var="buts">'
							+ '<c:if test="${buts.buttonID==\'np\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="publish(\''
							+ record.newsID
							+ '\', '
							+ dataNo
							+ ');"><i class="fa fa"></i>发布</button>'
							+ '</c:if>'
							+ '</c:forEach>';
				    }
	/* 		可以用的取消发布	  content += ' <c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'np\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="cancelPublish(\''
							+ record.newsID
							+ '\', '
							+ dataNo
							+ ');"><i class="fa fa"></i>取消发布</button>'
							+ '</c:if>'
							+ '</c:forEach>'; */
				         
		             //  debugger
		              // if(record.newsID.equals("7e4bae23f6804c749d5759b77e0823db")){
		         
		               //<c:if test="${record.newsID!='7e4bae23f6804c749d5759b77e0823db'}">alert(record.newsID)</c:if>
 	                    //alert(ddd)

 	                  if(record.publishState!='noPublish'){				 
					  content +=' <c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'np\' }">'
											
							+ '<button type="button" class="btn btn-xs btn-default" onclick="cancelPublish(\''
							+ record.newsID
							+ '\', '
							+ dataNo
							+ ');"><i class="fa fa"></i>取消发布</button>'
							+ '</c:if>'
							
							+ '</c:forEach>';
							}
							 if(record.publishState!='noPublish'){
							content += '<c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'np\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="push(\''
							+ record.newsID
							+ '\', \''
							+ record.publishState
							+ '\');"><i class="fa fa"></i>推送人群</button>'
							+ '</c:if>'
							+ '</c:forEach>';	
							}
							
					content += '<c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'nd\' }">'
							+ '<button type="button" class="btn btn-xs btn-danger" onclick="del(\''
							+ record.newsID
							+ '\', '
							+ dataNo
							+ ');"><i class="fa fa-trash-o"></i>删除</button>'
							+ '</c:if>'
							+ '</c:forEach>';
				
					return content + '';
				}
			} ]
	
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

	function eye(newsID) {
		window.location.href = "news.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&newsID="
				+ newsID;
		return false;
	}

	function del(newsID) {
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
						url : "news.do?p=del&newsID=" + newsID,
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
			loadURL : 'news.do?p=find',
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
		sessionStorage.setItem("url","news.do?p=find");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
	});

	$("#btnAdd").on('click', function() {
		window.location.href = "news.do?p=showAddView";
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


