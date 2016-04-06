<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- #include PathType = "FileName" --> 
<%@ include file="head.jsp" %>
  <script type="text/javascript">
   setTimeout(function(){
   location.href = "<%=basePath%>";
   },10000);
  
  </script>
  <body>
  <div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
   	 <div class="am-article-bd">
    <p class="am-article-lead">恭喜您，您已经成功注册了您的账号！您的账号为:${user.user.userName}</p>
  </div>
  </div>
  </div>
  </body>
</html>
