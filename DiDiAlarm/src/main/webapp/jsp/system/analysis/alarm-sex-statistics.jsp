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
    <div class="Wrapper a-a-s">
        <div class="menu">
            <div class="menu-icon">
                <img src="images/menu-icon.png" alt="" />
                <span>云警花溪－大数据后台</span>
            </div>
            <ul class="menu-list">
                <li onclick="create();"><a href="##" class="on">全区总计</a></li>
            <c:forEach var="obj" items="${model.type}">
            <li onclick="create();"><a  href="##" id="${obj.companyID}">${obj.companyName}</a></li>
            
            </c:forEach>
            </ul>
        </div>
        <div class="main">
            <div class="search">
                <ul>
                    <li><input name="" type="text" class="int-con fl" id="datepickerstart" readonly value="${model.time}"></li>
                    <li>一</li>
                    <li><input name="" type="text" class="int-con fl" id="datepickerend" readonly value="${model.time}"></li>
                    <li onclick="create();"><a href="##">查询</a></li>
                </ul>
            </div>
            <div class="charts">
                <ul class="chatrs-list">
            
            <c:forEach var="obj" items="${model.caseType}">
            <li>
            <div id="${obj.dicValue}" class="chart-12-21"></div>
            <span>${obj.dicName}</span>
            </li>
            </c:forEach>
            
                
                   <!--  <li>
                        <div id="chart-12" class="chart-12-21"></div>
                        <span>抢劫</span>
                    </li>
                    <li>
                        <div id="chart-13" class="chart-12-21"></div>
                        <span>抢夺</span>
                    </li>
                    <li>
                        <div id="chart-14" class="chart-12-21"></div>
                        <span>盗窃</span>
                    </li>
                    <li>
                        <div id="chart-15" class="chart-12-21"></div>
                        <span>治安纠纷</span>
                    </li>
                    <li>
                        <div id="chart-16" class="chart-12-21 chart16"></div>
                        <span>打架斗殴</span>
                    </li>
                    <li>
                        <div id="chart-17" class="chart-12-21 chart-17"></div>
                        <span>杀人</span>
                    </li>
                    <li>
                        <div id="chart-18" class="chart-12-21 chart-18"></div>
                        <span>恐爆案件</span>
                    </li>
                    <li>
                        <div id="chart-19" class="chart-12-21 chart-19"></div>
                        <span>传统诈骗</span>
                    </li>
                    <li>
                        <div id="chart-20" class="chart-12-21 chart-20"></div>
                        <span>电信网络诈骗</span>
                    </li>
                    <li>
                        <div id="chart-21" class="chart-12-21 chart-21"></div>
                        <span>其它案件</span>
                    </li> -->
                </ul>
            </div>
        </div>
    </div>


    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/jquery-ui-1.9.2.custom.js"></script>
<!--
    <script type="text/javascript" src="js/data.js"></script>
-->
    <script src="js/echarts.min.js"></script>
    <script src="js/myCharts7.js"></script>
    <script src="js/myJs.js"></script>
    <script type="text/javascript">    
       //通用方法 
    // var data1= new object();
   // alert("222");
      var data1= new Array();
      var MyCharts =new  Array();
    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
    var type=$(".on").attr("id");
     var data;
     
    $.ajax({
      type: "POST",
      url: "date.do?p=alarmsSexStatisticsView&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
      //alert(result.docketCase.value});
      //alert(result.call.value);
     // $("#call").attr("text",result.call.value);

     //JSON.stringify()
       	
      	 data = result;
       }
  }) ; 
  for (var i = 0; i <data.lengthData; i++) {
  var ajChart = echarts.init(document.getElementById(data.peopleNum[i].dicValue));//value
    MyCharts.push(ajChart);
    
}

 getnima({
 Pro:data,
 Pro1:MyCharts});
 
 
 
 
 
 function create(){
  
    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
    var type=$(".on").attr("id");

    var data1= new Array();
    var MyCharts =new  Array();
    var data;
    $.ajax({
      type: "POST",
      url: "date.do?p=alarmsSexStatisticsView&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend+"&type="+type,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
    	 data = result;
       }
  }) ; 

  var ajChart = echarts.init(document.getElementById(data.caseType[1].dicValue));//value
  
  for (var i = 0; i <data.lengthData; i++) {
  var ajChart = echarts.init(document.getElementById(data.caseType[i].dicValue));//value
    MyCharts.push(ajChart); 
}

 getnima({
 Pro:data,
 Pro1:MyCharts
 });

  
    }
    </script>
</body>
</html>