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
	<script type="text/javascript">
	var BASE_URL = '<%=basePath%>';
	</script>
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
				<h2 style="color:#353535; display:inline;">Videos » 新增</h2>
				<button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
			<div class="panel">
			<div class="panel-body">
				<form id="mainForm" class="form-horizontal" role="form" method='post'>
					<div class="form-group">
						<label class="col-md-2 control-label">标题</label>
						<input class="hidden" name="supplierID" >
						<div class="col-md-7">
							<input type="text" name="title" id="title" placeholder="标题内容" value="" class="form-control" required>
						</div>
						
						
					</div>
					
					<div class="form-group">
						<label class="col-md-2 control-label">摘要</label>
						<div class="col-md-7">
						<textarea rows="5" name="abstractz" cols="80"></textarea>
							</div>
					</div>
					
					
					<div class="form-group">
 				<label class="col-md-2 control-label">视频图片</label>
				<div id="uploader">
   				 <!--用来存放item-->
    			<div id="fileList" class="uploader-list"></div>
    			<div id="filePicker">选择图片</div>
    			<input hidden="hidden" id="picturePath" name="picture">
				</div>
				</div>
				
					<div class="form-group">
					<label class="col-md-2 control-label">视频图片</label>
					<div id="uploader2">
   				 <!--用来存放item-->
    			<div id="fileList1" class="uploader-list"></div>
    			<div id="filePicker1">选择视频</div>
    			<input hidden="hidden" id="url" name="url">
				</div>
					</div>
					 
				
					
					<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
					
				
				
					
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


<!-- 
<script type="text/javascript" src="js/pinYinCode.js"></script>
<script type="text/javascript" src="js/myjs/pageList.js"></script>
-->
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="js/webuploader/webuploadcontoller.js"></script>

<script type='text/javascript' src='js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript' src='js/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="js/autocomplete/lib/thickbox.css" />


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
	
	
var uploader = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,
	extensions: 'gif,jpg,jpeg,bmp,png',
    mimeTypes: 'image/*',
	method: "POST",
    // swf文件路径
    swf: 'js/webuploader/Uploader.swf',

    // 文件接收服务端。
    server: '<%=basePath%>videos.do?p=checkFile',
	fileVal: 'file',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker'
    // 只允许选择图片文件。
  
});



// 当有文件添加进来的时候
uploader.on( 'fileQueued', function( file ) {
$("#fileList").empty();
    var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
        $img = $li.find('img');
    // $list为容器jQuery实例
    	$("#fileList").append( $li );

    // 创建缩略图
    // 如果为非图片文件，可以不用调用此方法。
    // thumbnailWidth x thumbnailHeight 为 100 x 100
    uploader.makeThumb( file, function( error, src ) {
        if ( error ) {
            $img.replaceWith('<span>不能预览</span>');
            return;
        }

        $img.attr( 'src', src );
    }, 100, 100 );
});
	
	
	uploader.on( 'uploadSuccess', function( file,data ) {
    $( '#'+file.id ).find('p.state').text('已上传');
    $("#picturePath").val(data.filePath);
});


var uploader2 = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,

	method: "POST",
    // swf文件路径
    swf: 'js/webuploader/Uploader.swf',
	extensions: 'rmvb,rm,mp4,avi',
    mimeTypes: 'video/*',
    // 文件接收服务端。
    server: '<%=basePath%>videos.do?p=checkFile',
	fileVal: 'file',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker1'
    // 只允许选择图片文件。
  
});



// 当有文件添加进来的时候
uploader2.on( 'fileQueued', function( file ) {
	$("#fileList1").empty();
    var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
        $img = $li.find('img');
    // $list为容器jQuery实例
    	$("#fileList1").append( $li );

    // 创建缩略图
    // 如果为非图片文件，可以不用调用此方法。
    // thumbnailWidth x thumbnailHeight 为 100 x 100
    uploader2.makeThumb( file, function( error, src ) {
        if ( error ) {
            $img.replaceWith('<span>不能预览</span>');
            return;
        }

        $img.attr( 'src', src );
    }, 100, 100 );
});
	
	
	uploader2.on( 'uploadSuccess', function( file,data ) {
    $( '#'+file.id ).find('p.state').text('已上传');
    $("#url").val(data.filePath);
    
});

uploader2.on( 'uploadError', function( file,data ) {
    $( '#'+file.id ).find('p.state').text('上传失败');
    alert("上传失败,文件太大！");
});




	$("#btnCommit").click(function() {
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "videos.do?p=add",
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
				handsuc(data, 'insert', 'videos.do?p=view');
			},
			
			error:function(data) {
				handerr(data);
			}
		});
	});
	
	
	$("#btnClean").click(function() {
		$(':input,#mainForm').removeAttr('checked').removeAttr('selected').attr('value', '').attr('text', '');
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

</html>

	
