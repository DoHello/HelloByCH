<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="marc" />
    <title>滴滴报警</title>
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css">
    <link href="css/myCss.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="Wrapper c-s">
        <div class="menu">
            <div class="menu-icon">
                <img src="images/menu-icon.png" alt="" />
                <span>滴滴报警－大数据后台</span>
            </div>
            <ul class="menu-list">
                <li onclick="create();"><a href="##" class="on" id="0" >所有案件</a></li>
            
            <c:forEach var="obj" items="${model.type}">
            <li onclick="create();"><a href="##"  id="${obj.companyID}">${obj.companyName}</a></li>            

            </c:forEach>
            </ul>
        </div>
        <div class="main">
          <form id="charts">
            <div class="search">
                <ul>
                    <li><input name="" type="text" class="int-con fl" id="datepickerstart" readonly value="${model.time}"></li>
                    <li>一</li>
                    <li><input name="" type="text" class="int-con fl" id="datepickerend" readonly value="${model.time}"></li>
                    <li onclick="create();"><a href="##">查询</a></li>
                </ul>
            </div>
            </form>
            <div class="charts">
                <div class="chart-10" id="chart-10"></div>
            </div>
        </div>
    </div>


    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/jquery-ui-1.9.2.custom.js"></script>
<!--
    <script type="text/javascript" src="js/data.js"></script>
-->
    <script src="js/echarts.min.js"></script>
    <script src="js/myCharts5.js"></script>
    <script src="js/myJs.js"></script>
    
    <script type="text/javascript">
  var myChart10 = echarts.init(document.getElementById('chart-10'));
      var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
   var data;
    $.ajax({
      type: "POST",
      url: "date.do?p=alarmsAgeStatisticsView&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
      //alert(result.docketCase.value});
     //$(".windCase").text(result.windCase.value);
     // $("#call").attr("text",result.call.value);
	 data = result;
       }

  }) ; 
 
  getEchartsData(
    {
    data:data,
    echarts10:myChart10,
    }
    );
    
    
    
    
     function create(){    
   
    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
    var type=$(".on").attr("id");
    
    var myChart10 = echarts.init(document.getElementById('chart-10'));
  
    var data;
    $.ajax({
      type: "POST",
      url: "date.do?p=alarmsAgeStatisticsView&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend+"&type="+type,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
	 data = result;
       }
  }) ; 
 
  getEchartsData(
    {
    data:data,
    echarts10:myChart10,          
    }
    );
  
    } 
</script>
    
    
    


</body>
</html>