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
	<script charset="utf-8" src="js/editor/kindeditor.js"></script>
	<script charset="utf-8" src="js/editor/lang/en.js"></script>
	<div id="page" style='top:${model.direct==1?0:'60px'}'>
		<div id="pageBody" class="scrollbar-hover">
		<!-- sidebar start -->
		<%@ include file="../../left.jsp"%>
		<!-- sidebar end -->
		
		<!-- content start -->
		<div class="admin-content am-animation-slide-right clearfix">
			<div style="padding:20px; ">
				<h2 style="color:#353535; display:inline;">NEWS » 修改/查看</h2>
				<button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
			<div class="panel">
			<div class="panel-body">
				<form id="mainForm" class="form-horizontal" role="form" method='post'>
					<div class="form-group">
						<label class="col-md-2 control-label">标题</label>
						<input class="hidden" name="articleID" id="articleID" value="${model.entity.articleID}">
						<div class="col-md-7">
							<input type="text" name="title" id="title" placeholder="标题内容" value="${model.entity.title}" class="form-control" required>
						</div>
						
						
					</div>
					
					<div class="form-group">
						<label class="col-md-2 control-label">摘要</label>
						<div class="col-md-7">
						<textarea rows="5" name="abstractz" cols="80">
						${model.entity.abstractz}
						</textarea>
							</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-md-2 control-label">全文</label>
						<div class="col-md-7">
						<textarea id="texteray" name="context" style="width:700px;height:300px;">
						${model.entity.context}
						
						</textarea>
						</div>
					</div>
					 
					<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
					
				
					<div class="am-g" style="display:none">
						<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
						
					
						<div class="form-group">
						</div>
					</div>
					
					<div class="text-center col-md-9">
						<button type="button" id="btnCommit" class="btn btn-primary">提交</button>
						<button type="button" id="btnClean" class="btn btn-dm">清空</button>
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
 var 	editor =null;
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
		
		$('#companyID').trigger('change');
	};
	
	
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
				t += "," + txt;
			}
		});
		$('#companyName').attr('value', t.substring(1));
	});
	
	
	$("#btnCommit").click(function() {
		editor.sync();
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "news.do?p=add",
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
				handsuc(data, 'update', 'dic.do?p=view');
			},
			
			error:function(data) {
				handerr(data);
			}
		});
	});
	
	
	$("#btnClean").click(function() {
		$(':input,#mainForm').removeAttr('checked').removeAttr('selected').attr('value', '').attr('text', '');
		$(':textarea,#mainForm').removeAttr('checked').removeAttr('selected').attr('value', '').attr('text', '');
		alert(11);
	});
	
	
	$("#searchText").autocomplete('dykmsupplier.do?p=find', {
		multiple: false,
		dataType: "json",
		parse: function(data) {
			return $.map(data.exhibitDatas, function(row) {
				return {
					data: row,
					value: row.supplierID,
					result: row.supplierName 
				}
			});
		},
		formatItem: function(item) {
			return format(item);
		}
	}).result(function(e, item) {
		$.each(item, function(key, values) {
			$('#' + key).attr('value', values);
		});
	});


	function format(row) {
		return "<table><tr><td>供应商名称:</td><td><span style='color:#ff0000'>"+row.supplierName + "</span>&nbsp;&nbsp;&nbsp;</td><td>供应商地址:</td><td>"+
		row.supplierAddress+"&nbsp;&nbsp;&nbsp;</td><td>注册证号:</td><td>"+row.taxNumber+"&nbsp;&nbsp;&nbsp;</td></tr>" +
				"</table>";
	}
	
	
	$('#pageCloseBtn').click(function() {
		window.location.href = "news.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	});
</script>
<script>
KindEditor.ready(function(K) {
	editor=K.create('#texteray', {
		allowFileManager : true
	});
});
</script>

<!-- 
<script type="text/javascript" src="js/pinYinCode.js"></script>
<script type="text/javascript" src="js/myjs/pageList.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="js/webuploader/webuploadcontoller.js"></script>
-->
<script type='text/javascript' src='js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript' src='js/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="js/autocomplete/lib/thickbox.css" />
</html>

	
