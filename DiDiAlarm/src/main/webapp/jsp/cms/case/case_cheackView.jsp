<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	PageData pageModel = (PageData)request.getAttribute("model");
%>

<!DOCTYPE HTML>
<html>

<link rel="stylesheet" type="text/css" href="css/imgView.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/style.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploadcontoller.css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries --> 
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]--> 

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
			<div style="padding:20px; ">
				<h2 style="color:#353535; display:inline;">案件管理 » 查看已备案案件</h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="接警号">
					</div>
					
					<div class="col-md-9">
						<div class="pull-right">
						<div class="btn-group">
               
                    <c:forEach items="${model.userbutton }" var="buts">
											<c:if test="${buts.buttonID=='caseFindCheack' }">
												<button id="btnSearch" type="button" class="btn">搜索</button>
											</c:if>
									</c:forEach>
						</div>				
						</div>
					</div>
				</div>
				<hr style="margin:10px 0 0 0;">
				<div style="margin-top:-1px;">
					<div id="dtGridContainer" class="dt-grid-container"></div>
					<div id="dtGridToolBarContainer" class="dt-grid-toolbar-container"></div>
				</div>
			</div>
		</div>
		<!-- content end -->
		
		</div>
	</div>

	<div id="append_parent" style="z-index: 10000"></div>
	
	<%@ include file="../../footer.jsp"%>
</body>


<script type="text/javascript">
	var dtGridColumns = [
		//{id:'realName', title:'姓名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'alarmID', title:'报警号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
// 		{id:'lastlogin', title:'最后登录', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'callTime', title:'报警时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'callAddress', title:'案发地方', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},

        {id:'call_userName', title:'报警人', type:'string', columnClass:'text-center', hideType:'sm|xs',  fastQuery:true, fastQueryType:'eq',hide:true},
         {id:'call_phone', title:'报警人电话', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
        {id:'call_inputDate', title:'报警时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
         {id:'call_addressName', title:'案发地点', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
         {id:'receive_userName', title:'接警人姓名', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
        {id:'receive_phone', title:'接警人电话', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
                {id:'receive_orgName', title:'派出所', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
                      {id:'receive_casetype', title:'案件类型', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
             {id:'receive_inputDate', title:'接警时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
        {id:'receive_presentTime', title:'到达时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
             {id:'receive_presentTime', title:'到达时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
             {id:'cheack_inputDate', title:'备案时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
         {id:'cheack_members', title:'出动人数', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
          {id:'cheack_cart', title:'出动车辆', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
           {id:'cheack_bads', title:'抓捕人数', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
                   {id:'cheack_context', title:'案件描述', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',hide:true},
        
        
        
      /*   select `name`, phone ,InputDate,AddressName from  gms_alarm_call;
select UserName,phone,OrgName,CaseType,InputDate ,PresentTime from gms_alarm_receive where  AlarmRefuseID is  null
select InputTime ,Members,Carts,Bads,Context from gms_alarm_cheack


select  */
        {id:'presentTime', title:'到场时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
       
         
       // {id:'pres', title:'案件状态', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},

// 		{id:'company', title:'公司名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		//{id:'callTime', title:'地址', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		//{id:'callContext', title:'案情描述', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		//{id:'inputTime', title:'备案时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
			//content += '<button type="button" class="btn btn-xs btn-default" onclick="jPush(\''+record.userID+'\',\''+record.alarmID+'\');"><i class="fa fa-eye"></i>推送 </button>';
		    content += '<c:forEach items="${model.userbutton }" var="buts">'
							+ '<c:if test="${buts.buttonID==\'cheackEye\' }">'
							+ '<button type="button" class="btn btn-xs btn-default" onclick="eye(\''
							+ record.alarmReceiveID
							+ '\');"><i class="fa fa-eye"></i>查看</button>'
							+ '</c:if>'
							+ '</c:forEach>';
			//content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'md\' }"><button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.alarmReceiveID+'\', '+dataNo+');"><i class="fa fa-trash-o"></i>删除</button></c:if></c:forEach>';
			return content + '</div>';
		}}
	]
	
	
	var grid = null;
	
	
	window.onload = function() {
		$('#page').css('display', 'block');
		
		$(document).keypress(function(e) {
		if (e.keyCode == 13 && e.target == "body") {
			e.preventDefault();
		} else if (e.keyCode == 13) {
			$('#btnSearch').trigger('click');
			e.preventDefault();
		}
		});
		
		$('#btnSearch').trigger('click');
	}
	
	
	function eye(alarmReceiveID) {
		window.location.href="case.do?p=cheackEye&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&alarmReceiveID="+alarmReceiveID;
		return false;
	}
	

	
	
	$('#btnSearch').on('click', function() {
		$('#dtGridContainer').children().remove();
		$('#dtGridToolBarContainer').children().remove();
		grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: false,
			ajaxLoad : true,
			loadURL : 'case.do?p=findCheack',
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		});
		grid.parameters = new Object();
		grid.parameters["searchText"] = $("#searchText").val();
		sessionStorage.setItem("url","case.do?p=findCheack");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
	});
	
function del(alarmReceiveID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "case.do?p=del&alarmReceiveID="+alarmReceiveID,
				data : $("#mainForm").serialize(),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'delete');
					$('#dtGridContainer').children().remove();
					$('#dtGridToolBarContainer').children().remove();
					grid.load();
				},
				
				error:function(data) {
					handerr(data);
				}
			});
		});
		
		$('#delete-comfirm-modal').modal({});
	};
	
	
</script>
<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


