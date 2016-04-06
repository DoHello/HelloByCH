<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.derbysoft.enums.MessageEnum"%>
<%@page import="com.derbysoft.enums.DataStatusEnum"%>

<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->
<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1"
	id="my-modal-loading">
	<div class="am-modal-dialog">
		<div id="loginMessageDiv" class="am-modal-hd"></div>
		<div class="am-modal-bd">
			<span class="am-icon-spinner am-icon-spin"></span>
		</div>
	</div>
</div>

<script type="text/javascript">
	var userData = {userName:'${model.SessionUser.userName}'};
	var localStorage = window.localStorage;
	if (localStorage&&localStorage.getItem(userData.userName)!=null&&JSON.parse(localStorage.getItem(userData.userName)).inventoryData!=null&&JSON.parse(localStorage.getItem(userData.userName)).inventoryData!=""){
		userData = JSON.parse(localStorage.getItem(userData.userName));
	}
	var MESSAGE = {SUCESSS:{id:"<%=MessageEnum.SUCESSS.getMessageType()%>",value:"<%=MessageEnum.SUCESSS.getMessageValue() %>"},
				FAIL:{id:"<%=MessageEnum.FAIL.getMessageType()%>",value:"<%=MessageEnum.FAIL.getMessageValue()%>"},
				ERROR:{id:"<%=MessageEnum.ERROR.getMessageType()%>",value:"<%=MessageEnum.ERROR.getMessageValue()%>"}
				};
	var DATA_STATUS = {SUBMIT:{value:"<%=DataStatusEnum.SUBMIT.getValue()%>",name:"<%=DataStatusEnum.SUBMIT.getName() %>"},
				THROUGH:{value:"<%=DataStatusEnum.THROUGH.getValue()%>",name:"<%=DataStatusEnum.THROUGH.getName()%>"},
				DELETE:{value:"<%=DataStatusEnum.DELETE.getValue()%>",name:"<%=DataStatusEnum.DELETE.getName()%>"},
				FAILURE:{value:"<%=DataStatusEnum.FAILURE.getValue()%>",name:"<%=DataStatusEnum.FAILURE.getName()%>"},
				TAKE_EFFECT:{value:"<%=DataStatusEnum.TAKE_EFFECT.getValue()%>",name:"<%=DataStatusEnum.TAKE_EFFECT.getName()%>"}
				};
</script>
<header id="header" class="with-shadow" style='display:${model.direct==1?'none':'block'}'>
	<div class="navbar navbar-inverse navbar-fixed-top" id="navbar" role="banner" style="">
		<div style="width: 50%;float:left">
			<div class="navbar-header">
				<a href="http://116.228.89.136:40011/DiDiAlarm/" class="navbar-brand">
					<span ><i class="path-1"></i><i class="path-2"></i></span>
				<span class="brand-title">云警花溪</span> &nbsp;
					<small class="zui-version">v1.0.0</small>
					<i data-toggle="tooltip" id="compactTogger" data-placement="bottom" class="icon icon-home" data-original-title=""></i>
				</a>
			</div>
			<nav class="collapse navbar-collapse zui-navbar-collapse" >
				<ul class="nav navbar-nav navbar-right">
				</ul>
			</nav>
		</div>
		<div style="width: 30%;float:right;">
			<div class="navbar-header">
				<a href="http://116.228.89.136:40021/DiDiJpushServer/map.jsp" class="navbar-brand"  target="_Blank">
				<span class="brand-title">云地图展示</span> &nbsp;
					<i data-toggle="tooltip" id="compactTogger" data-placement="bottom" class="icon icon-home" data-original-title=""></i>
				</a>
			</div>
			<nav class="collapse navbar-collapse zui-navbar-collapse" >
				<ul class="nav navbar-nav navbar-right">
				</ul>
			</nav>
		</div>
		
	</div>
	<!-- 
      <div id="headContainer">
        <div class="container">
          <div id="heading">
            <h1>ZUI</h1>
            <p>一个开源前端实践方案，帮助你快速构现代跨屏应用。</p>
            <div id="download">
              <a href="docs/download/zui-1.3.1-dist.zip" class="btn btn-primary btn-lg">立即下载 <i class="icon icon-download-alt"></i></a>
              <p><small>最新版本 <span class="zui-version">v1.3.1</span> &nbsp; <a style="color: #f1f1f1" href="https://github.com/easysoft/zui/archive/master.zip" target="_blank">下载源码</a></small></p>
            </div>
            <p>QQ群：384135104 &nbsp; <a href="http://forum.zui.sexy/forum/" style="color: #f1f1f1" target="_blank"><i class="icon icon-comments-alt"></i> 论坛</a></p>
          </div>
          <div id="btnSearch">
            <div id="searchForm">
              <input type="text" class="form-control input-lg" id="searchInput" placeholder="按钮, control, icon-star...">
              <i class="icon icon-search"></i>
              <button id="searchHelpBtn" type="button" class="btn btn-link"><i class="icon icon-question"></i></button>
            </div>
          </div>
        </div>
      </div>
       -->
    </header>
<!--  
<header class="am-topbar admin-header">
	<div class="am-topbar-brand">
		<strong>DYCOM</strong> <small>当益药品企业管理平台</small>
	</div>

	<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

	<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		<ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
			<li>
				<a href="javascript:;">
					<span class="am-icon-envelope-o"></span>收件箱
					<span class="am-badge am-badge-warning">5</span>
				</a>
			</li>
			<li class="am-dropdown" data-am-dropdown>
				<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
				<span class="am-icon-users"></span>管理员
				<span class="am-icon-caret-down"></span>
			</a>
				<ul class="am-dropdown-content">
					<li><a href="#"><span class="am-icon-user"></span>资料</a></li>
					<li><a href="#"><span class="am-icon-cog"></span>设置</a></li>
					<li><a href="#"><span class="am-icon-power-off"></span>退出</a></li>
				</ul>
			</li>
			<li class="am-hide-sm-only">
				<a href="javascript:;" id="admin-fullscreen">
					<span class="am-icon-arrows-alt"></span>
					<span class="admin-fullText">开启全屏</span>
				</a>
			</li>
		</ul>
	</div>
</header>-->
