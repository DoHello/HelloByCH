<%@ page import="com.derbysoft.enums.ModuleEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html class="no-js">
    <base href="<%=basePath%>">
    <%@ include file="../../top.jsp"%>
<body>
    <%@ include file="../../head.jsp"%>
    <div class="am-cf admin-main">
    
    <!-- sidebar start -->
    <%@ include file="../../left.jsp"%>
    <!-- sidebar end -->

    <!-- content start -->
    <div class="admin-content">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">欢迎您"${model.SessionUser.userName}" </strong> / <small>引导帮助</small></div>
    </div>

    <hr/>

    <div class="am-g">
		<div class="am-u-sm-12 am-u-sm-centered " style=" -webkit-font-smoothing: antialiased; -moz-osx-font-smoothing: grayscale;">
			<h2></h2>
			<p class="am-kai">感谢您使用《当益药品企业管理平台》，在此我们为您提供了对您十分便利的功能，请跟随我们的引导完成您的系统初始化。</p>
			<p class="am-kai">请选择您的使用账户类型:</p>
			<hr/>
			<div class="am-u-sm-6 am-u-sm-offset-3 " >
			<form id="guidePageForm" action="user.do?p=init" method="post">
			    <input id="valid" type ="hidden" name="validationCode">
			</form>&nbsp;&nbsp;
			<button  onclick="loginz('<%=ModuleEnum.BOSS_SYSTEM.getSystem() %>')" class="am-btn am-btn-primary am-btn-xl am-round">单体药店</button>&nbsp;&nbsp;
			<button onclick="loginz('<%=ModuleEnum.CHAIN_ENTERPRISE_SYSTEM.getSystem() %>')" class="am-btn am-btn-primary am-btn-xl am-round">连锁公司</button>
		</div>
    </div>
    <div class="am-u-sm-12 am-u-sm-centered ">
    
    </div>

	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
    </div>
	</div>
	<!-- content end -->
	<script	 type="text/javascript">
		function  loginz(type){
			if(type=="<%=ModuleEnum.BOSS_SYSTEM.getSystem()%>"){
			    $("#valid").val("<%=ModuleEnum.BOSS_SYSTEM.getValue()%>");
			}else if(type=="<%=ModuleEnum.CHAIN_ENTERPRISE_SYSTEM.getSystem()%>"){
				$("#valid").val("<%=ModuleEnum.CHAIN_ENTERPRISE_SYSTEM.getValue()%>");
			}
			$("#guidePageForm").submit();
		}
	</script>

	<footer>
	<hr>
	<br>
	<br>
	</footer>

    <%@ include file="../../footer.jsp"%>
    </body>
</html>
