<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
 <script type="text/javascript">

  </script>
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
    <div class="Wrapper c-s">
        <div class="menu">
            <div class="menu-icon">
                <img src="images/menu-icon.png" alt="" />
                <span>云警花溪－大数据后台</span>
            </div>
            
             <ul class="menu-list" > 
             
            <li onclick="create();"><a href="##" id="0" class="on" >所有案件</a></li>
            <c:forEach var="obj" items="${model.type}">
            <li onclick="create();" ><a href="##" id="${obj.dicValue}" >${obj.dicName}</a></li>
            </c:forEach>
            </ul> 
        </div>
        <div class="main">
        <form id="charts">
            <div class="search">
                <ul>
                    <li><input  type="text" class="int-con fl" id="datepickerstart" readonly value="${model.time}"></li>
                    <li>一</li>
                    <li><input  type="text" class="int-con fl" id="datepickerend" readonly value="${model.time}"></li>
                    <li onclick="create()"><a href="#" id="inquiry123">查询</a></li>
                </ul>
            </div>
            </form>
            <div class="charts">
                <div class="chart-03" id="chart-03"></div>
                <div class="chart-04" id="chart-04"></div>
                <div class="tit">
                    <h3 class="allnum1"></h3>
                    <h3>派出所</h3>
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
    <script src="js/myCharts2.js"></script>
    <script src="js/myJs.js"></script>
    <script type="text/javascript">
    
    //通用方法 
     

	
   var ajChart = echarts.init(document.getElementById('chart-03'));// 指定图表的配置项和数据
   var ajChart1 = echarts.init(document.getElementById('chart-04'));// 指定图表的配置项和数据
  
	    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
debugger;
     var data;
    $.ajax({
      type: "POST",
      url: "date.do?p=caseStatistics123&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
 //var  date=JSON.stringify(result);
       $(".allnum1").text(result.allNum1);
       
       
      	 data = result;
       }
       //false表示“遮罩”，前台不显示“请稍后”进度提示
  }) ; 
  
  getEchartsData(
    {
    name:"案件总数",
    data:data,
    echarts:ajChart,
    echarts1:ajChart1
    }
    ); 
    
    
    
    
    
        function create(){   
    var datepickerstart=$("#datepickerstart").val();
    var datepickerend=$("#datepickerend").val();
    var type=$(".on").attr("id");
  
    var data;
   var ajChart = echarts.init(document.getElementById('chart-03'));// 指定图表的配置项和数据
   var ajChart1 = echarts.init(document.getElementById('chart-04'));// 指定图表的配置项和数据
  

    $.ajax({
      type: "POST",
      url: "date.do?p=caseStatistics123&datepickerstart="+datepickerstart+"&datepickerend="+datepickerend+"&type="+type ,
      data:$("#charts").serialize(),
      async: false,
      success: function(result){
 //var  date=JSON.stringify(result);
        $(".allnum1").text(result.allNum1);

      	 data = result;
       }
       //false表示“遮罩”，前台不显示“请稍后”进度提示
  }) ; 
  
  getEchartsData(
    {
    name:"案件总数",
    data:data,
    echarts:ajChart,
    echarts1:ajChart1
    }
    ); 
   
  
    }
/* getEchartsData2({

    data:data,
    echarts:ajChart1
}
) */

 
    


   

    
    </script>
</body>
</html>