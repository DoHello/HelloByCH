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
    <title>云警花溪</title>
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css">
    <link href="css/myCss.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="Wrapper a-s">
        <div class="menu">
            <div class="menu-icon">
                <img src="images/menu-icon.png" alt="" />
                <span>云警花溪－大数据后台</span>
            </div>
            <ul class="menu-list">
            <li onclick="create();"><a href="##" class="on" id="0" >所有案件</a></li>
            <c:forEach var="obj" items="${model.type}">
            <li onclick="create();"><a  href="##" id="${obj.companyID}">${obj.companyName}</a></li>
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
                <div class="chart-01" id="chart-01"></div>
                <div class="chart-02" id="chart-02"></div>
                <div class="tit">
                    <h3 class="allnum1"></h3>
                    <h3>案件种类</h3>
                </div>
            </div>
        </div>
    </div>


    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/jquery-ui-1.9.2.custom.js"></script>
<!--
    <script type="text/javascript" src="js/data.js"></script>
-->
    <script src="js/echarts.min.js"></script>
    <script src="js/myCharts.js"></script>
    <script src="js/myJs.js"></script>
     <script type="text/javascript">
    
    //通用方法 
     

	
var myChart01 = echarts.init(document.getElementById('chart-01'));
var myChart02 = echarts.init(document.getElementById('chart-02'));// 指定图表的配置项和数据  
	
    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
     var data;
    $.ajax({
      type: "POST",
      url: "date.do?p=areaStatisticsView&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
 var  date=JSON.stringify(result);
   $(".allnum1").text(result.allNum1);
   //$(".receive").text(result.receive.value);
      	 data = result;
         
       }
       //false表示“遮罩”，前台不显示“请稍后”进度提示

  }) ; 
  
  getEchartsData(
    {
    name:"案件总数",
    data:data,
    echarts01:myChart01,
    echarts02:myChart02
    }
    ); 
    
    
    
    function create(){
    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
    var type=$(".on").attr("id");
    //alert(type);
   var myChart01 = echarts.init(document.getElementById('chart-01'));
   var myChart02 = echarts.init(document.getElementById('chart-02'));// 指定图表的配置项和数据  
	

     var data;
    $.ajax({
      type: "POST",
      url: "date.do?p=areaStatisticsView&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend+"&type="+type,
      
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
 var  date=JSON.stringify(result);

   $(".allnum1").text(result.allNum1);
   
      	 data = result;
         
       }
       //false表示“遮罩”，前台不显示“请稍后”进度提示

  }) ; 
  
  getEchartsData(
    {
    name:"案件总数",
    data:data,
    echarts01:myChart01,
    echarts02:myChart02
    }
    );}

    </script>
</body>
</html>