<%@page import="com.derbysoft.enums.MessageEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html class="no-js">

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
			
		</div>
		<!-- content end -->
		
		</div>
	</div>

	<div id="append_parent" style="z-index: 10000"></div>
	
	<%@ include file="../../footer.jsp"%>
</body>

<script>
	$('#page').css('display', 'block');
</script>

<%@ include file="../../footer.jsp"%>
</body>
</html>
