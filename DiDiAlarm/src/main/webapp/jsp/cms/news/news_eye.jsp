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
	<script charset="utf-8" src="js/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="js/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="js/kindeditor/lang/en.js"></script>
	<div id="page" style='top:${model.direct==1?0:'60px'}'>
		<div id="pageBody" class="scrollbar-hover">
			<!-- sidebar start -->
			<%@ include file="../../left.jsp"%>
			<!-- sidebar end -->

			<!-- content start -->
			<div class="admin-content am-animation-slide-right clearfix">
				<div style="padding:20px; ">
					<h2 style="color:#353535; display:inline;">新闻 » 修改</h2>
					<button data-toggle="tooltip" type="button"
						class="btn btn-link path-close-btn path-btn" id="pageCloseBtn"
						data-original-title="关闭（ESC）">
						<i class="path-1"></i>
					</button>
				</div>
				<hr style="margin:0 10px;">
					<form id="mainForm" class="form-horizontal" role="form" method='post'>
				<div style="padding:10px; ">
					<div class="panel">
						<div class="panel-body">
						
								<div class="form-group">
									<label class="col-md-2 control-label">标题</label> 
									 <input class="hidden" name="newsID" id="newsID" value="${model.entity.newsID}"> 
									<div class="col-md-7">
										<input type="text" name="title" id="title" placeholder="标题内容" value="${model.entity.title}" class="form-control" required>
									</div>
								</div>    
								   
					<div class="form-group">
						<label class="col-md-2 control-label">新闻种类</label>
						<div class="col-md-5" >
						   <select name="state" id="state" class="chosen-select form-control" tabindex="2">
								<c:forEach items="${model.NewsState}" var="se">
									<c:choose>
										<c:when test="${model.entity.state.indexOf(se.dicValue) >= 0 }">
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
						<label class="col-md-2 control-label">焦点设置</label>
						<div class="col-md-5" >
						   <select name="focusStatus" id="focusStatus" class="chosen-select form-control" tabindex="2">
								<c:forEach items="${model.NewsFocus}" var="se">
									<c:choose>
										<c:when test="${model.entity.focusStatus.indexOf(se.dicValue)>= 0 }">
											<option value="${se.dicValue}" selected>${se.dicName}</option>
										</c:when>
										<c:otherwise>
											<option value="${se.dicValue}">${se.dicName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								
								
							</select>
						</div>
					</div>
                 
                 
               
					<div class="form-group">
 				<label class="col-md-2 control-label">头条图片</label>
				<div id="uploader">
   				 <!--用来存放item-->
   				
			
				 <ul style="margin-left: 170px"> 
				
    			<div id="fileList" class="uploader-list"></div>
    				 <img   src="${model.entity.savepath}" id="cfile" width="20%" >	
    			<div id="filePicker">选择图片</div>
    			<input hidden="hidden" id="filePath" name="savepath">
    		
    			</ul>
    				</div>
				</div>
								<div class="form-group">
									<label class="col-md-2 control-label">摘要</label>
									<textarea rows="5" name="summary" cols="80">${model.entity.summary}</textarea>
									
								</div>
	
							
						</div>
					</div>
				</div>
					<div style="padding:10px;">
					<div class="panel">
						<div class="panel-body">
		         <div class="form-group">
					<ul style="margin-left: 650px">
					<label>内容</label>							
				</ul>
			    <hr style="margin:0 10px;">
					<div style="padding:10px; ">
				<!-- <ul style="margin-left: 180px"> -->
									<div class="col-md-7">
										<textarea id="texteray" name="context"
											style="width:1400px;height:700px;">${model.entity.context}</textarea>
									</div>
								</div>
			       </div>
							
						</div>
					</div>
<!-- 					</ul> -->
					<hr style="margin:0 10px;">
								<div style="padding:10px 0px"></div>




								<div class="text-center col-md-9">
									<button type="button" id="btnCommit" class="btn btn-primary">提交</button>
									
								</div>
				</div>
				</div>
					</form>
				
				
				
			<!-- content end@ -->

		</div>
	</div>

	<div id="append_parent" style="z-index: 10000"></div>

	<%@ include file="../../footer.jsp"%>
</body>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="js/webuploader/webuploadcontoller.js"></script>

<script type='text/javascript' src='js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript' src='js/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="js/autocomplete/lib/thickbox.css" />

<script type="text/javascript">
<%-- var uploader = WebUploader.create({

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
  
}); --%>
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

function createUpload(){

 var uploader = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,
	extensions: 'gif,jpg,jpeg,bmp,png',
    mimeTypes: 'image/*',
	method: "POST",
    // swf文件路径
    swf: 'js/webuploader/Uploader.swf',

    // 文件接收服务端。
    server: '<%=basePath%>news.do?p=checkFile2',
	fileVal: 'file',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker'
    // 只允许选择图片文件。
});

// 当有文件添加进来的时候
uploader.on( 'fileQueued', function( file ) {
$("#cfile").remove();
$("#filePicker").empty();
    var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
        $img = $li.find('img');
    // $list为容器jQuery实例
    	$("#filePicker").append( $li );

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
    $("#filePath").val(data.filePath);
});

}

setTimeout("createUpload()",500);

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
	$("#filePicker").empty();
    var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
        $img = $li.find('img');
    // $list为容器jQuery实例
    	$("#filePicker").append( $li );

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

/* $("#btnCommit").click(function() {
		$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "news.do?p=add",
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
				handsuc(data, 'insert', 'news.do?p=view');
			},
			
			error:function(data) {
				handerr(data);
			}
		});
	}); */
	
	
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
			url : "news.do?p=edit",
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
				handsuc(data, 'update', 'news.do?p=view&parentID=444&moduleID=11');
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
<script>
KindEditor.options.filterMode = false;
KindEditor.ready(function(K) {
	editor=K.create('#texteray', {
        /* uploadJson : '../DiDiAlarm/order.do?p=checkFile', */
        fileManagerJson : '../../../edit/jsp/file_manager_json.asp',
        uploadJson : '../DiDiAlarm/news.do?p=checkFile',
        url : '../jsp/upload_json.jsp',
		allowFileManager : true,
		pasteType : 1,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
				
	urlType:"domain"});
});
</script>

<!-- 
<script type="text/javascript" src="js/pinYinCode.js"></script>
<script type="text/javascript" src="js/myjs/pageList.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="js/webuploader/webuploadcontoller.js"></script>
-->
<script type='text/javascript'
	src='js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript'
	src='js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript'
	src='js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript'
	src='js/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css"
	href="js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css"
	href="js/autocomplete/lib/thickbox.css" />
</html>


