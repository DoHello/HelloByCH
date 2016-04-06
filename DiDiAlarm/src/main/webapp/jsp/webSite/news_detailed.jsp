<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/Bina.css">  
  </head>
<body>
		<%@ include file="site_top.html"%>
	
    <div class="container newsDetailedCon">
    	<h3 class="Toptitle">NEWS</h3>
        <div class="white_k">
        	<h4 class="longTitle">${model.entity.Title }<em>Publishing Time:${model.entity.CreateTime}</em></h4>
            <div class="newsNr">
              		${model.entity.Context }
            </div>
        	<div class="on_next clearfix">
        		<c:if test="${!empty model.prevEntity }">
                <p>Prev：<a href="/Derby/siteHome.do?p=newsDetail&indexID=${model.prevEntity.indexID }">${model.prevEntity.Title }</a></p>
                </c:if>
                	<c:if test="${!empty model.nextEntity }">
                <p>Next: <a href="/Derby/siteHome.do?p=newsDetail&indexID=${model.nextEntity.indexID }">${model.nextEntity.Title }</a></p>
                </c:if>
                <a href="javascript:history.go(-1);" class="returns_bt">Back>></a>
            </div>
        </div>
    </div>
<script src="js/require.js" data-main="js/main" ></script>
</body>
</html>
