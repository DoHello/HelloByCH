<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html>
<html lang="zh-cn">
  <head>
	<base href="<%=basePath%>jsp/webSite/">
    <meta charset="utf-8">
    <title>DerbySoft MetaSearch Manager</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="X-UA-Compatible" content="IE-9">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="mobile-web-app-capable" content="yes"> 
    <meta name="renderer" content="webkit">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no,minimal-ui">
    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">   
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="css/style-ie.css">  
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/lib/prefixfree.min.js"></script>
  </head>
<body>

	<%@ include file="site_top.html"%>

  	<div class="logpor">
		<div class="container logBox">
			<div class="row logHi">
				<div class="logfrom">
					<div class="LogSignin clearfix">
						<a href="index.html"><h3 class="logLogo"></h3></a>
						<div class="col-sm-12 col-xs-12 pB20"> 
							<input type="text" value="User Name" id="userName" class="form-control Inpfocus">   
						 </div>
						<div class="col-sm-12 col-xs-12 pB20"> 
							<input type="text" value="Password" class="form-control clickTextfocus">
							<input type="password" value="" id="password" class="form-control clickfocus">
						 </div>
						 <p class="col-sm-12  col-xs-12 signText visible-xs"><a href="forgotPassword.html" class="pull-left">Forgot your password ?</a><a href="signUp.html" class="pull-right">Sign up</a></p>

						 <p class="col-sm-12  col-xs-12"><button class="btn btn-Login" type="submit">Log in</button></p>
						 <p class="col-sm-12  col-xs-12 signText hidden-xs"><a href="forgotPassword.html" class="pull-left">Forgot your password ?</a><a href="signUp.html" class="pull-right">Sign up</a></p>
					</div>
				</div>
				<div class="logRbg"></div>
			</div>
		</div>
	</div>
    <script src="js/require.js" data-main="js/main" ></script>
</body>
</html>