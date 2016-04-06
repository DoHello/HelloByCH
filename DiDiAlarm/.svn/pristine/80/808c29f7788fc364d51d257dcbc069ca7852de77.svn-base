<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
<script src="js/amazeui2.2.1/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/webuploader-0.1.5/webuploader.css">
<script type="text/javascript" src="js/webuploader-0.1.5/webuploader.js"></script>
    <title>My JSP 'UpLoadFile.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
  <div>
 	<div style="width: 100%">
 	<div style="width: 40%"><img  width="100px" height="100px" src = "http://b.hiphotos.baidu.com/baike/w%3D268/sign=19ce4f22acc379317d68812fd3c4b784/29381f30e924b899c83ff41c6d061d950a7bf697.jpg"></div><div style="width:  30%">12121</div><div style="width:  30%">1212</div>
 	</div>
 	</div>
 <!--dom结构部分-->
 <form enctype="MULTIPART/FORM-DATA">
 	
<div id="uploader-demo">
    <!--用来存放item-->
    <div id="fileList" class="uploader-list"></div>
    <div id="filePicker">选择图片</div>
</div>
</form>
 <script type="text/javascript">
 // 初始化Web Uploader
var uploader = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,
	
	method: "POST",
    // swf文件路径
    swf: 'js/webuploader-0.1.5/Uploader.swf',

    // 文件接收服务端。
    server: '<%=basePath%>company.do?p=upload',
	fileVal: 'file',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker'

    // 只允许选择图片文件。
  
});

// 当有文件添加进来的时候
uploader.on( 'fileQueued', function( file ) {
    var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
        $img = $li.find('img');
		alert(file.name);
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

 </script>
  </body>
</html>
