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
				<h2 style="color:#353535; display:inline;">案件管理系统  » 案件信息</h2>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
				<div class="row">
					<div class="col-md-3">
						<input type='text' id="searchText" class="form-control" placeholder="姓名、报警号或电话">
					</div>
					
					<div class="col-md-9">
						<div class="pull-right">
						<div class="btn-group">
							<c:forEach items="${model.userbutton }" var="buts">
								<c:if test="${buts.buttonID=='ms' }">
									<button id="btnSearch"  type="button" class="btn">搜索</button>
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
// window.setInterval(getAddressByBaidu, 100);
function getAddressByBaidu() {

	if (sessionStorage.getItem("modelStop") != null) {
// 		document.getElementById("causeStops").value = sessionStorage.getItem("causeStops");
		document.getElementById("remarkStops").value = sessionStorage.getItem("remarkStops");
// 		document.getElementById("causeStops").options.add(new Option(sessionStorage.getItem("causeStops"),sessionStorage.getItem("causeStops")));
		var opts = document.getElementById("causeStops");  
        var value = sessionStorage.getItem("causeStops");//这个值就是你获取的值;  
   		if(value!=""||value!=null){  
    	for(var i=0;i<opts.options.length;i++){  
          if(value==opts.options[i].value){ 
        	  opts.options.remove(i);
        	  opts.options.add(new Option(sessionStorage.getItem("causeStops"),sessionStorage.getItem("causeStops")));
                 opts.options[i].selected = "selected";  
//          alert(opts.options[i].value);  
//                    break;  
            }  
         }  
        }  
		sessionStorage.clear();
	}
}
	var dtGridColumns = [
		{id:'alarmID', title:'报警号', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		{id:'name', title:'姓名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;'},
		//{id:'userName', title:'登录名', type:'string', columnClass:'text-center', hideType:'', fastQuery:true, fastQueryType:'eq'},
		//{id:'addressName', title:'位置', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'phone', title:'电话', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
        {id:'inputDate', title:'报警时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},      		
 		//{id:'inputate', title:'电话', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
        {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq',sortable:true,headerStyle:'background:#00a2ca;color:white;',
		resolution:function(value, record, column, grid, dataNo, columnNo){
        var content = '';
        if(value=='nodealing'){
        content += '<span style="background:#66FFCC;padding:2px 10px;color:white;">未处理</span>';
        } else if(value=='dealing'){
        content += '<span style="background:#00BFFF;padding:2px 10px;color:white;">处理中</span>';
        }else if(value=='okCheack'){
        content += '<span style="background:#c447ae;padding:2px 10px;color:white;">备案</span>';
        }else if(value=='okNoReason'){
        content += '<span style="background:#FF6347;padding:2px 10px;color:white;">非管辖范围</span>';
        }else if(value=='ok'){
        content += '<span style="background:#c447ae;padding:2px 10px;color:white;">案件完结</span>';
        }else if(value=='waiting'){
        content += '<span style="background:#c447ae;padding:2px 10px;color:white;">等待中</span>';      
        }else if( value=='noPolice'){
        content += '<span style="background:#c447ae;padding:2px 10px;color:white;">无警员接警</span>';      
        }else if( value=='cancleAlarm'){
        content += '<span style="background:#c447ae;padding:2px 10px;color:white;">取消报警</span>';      
        }else if( value=='stop'){
        content += '<span style="background:#FF6347;padding:2px 10px;color:white;">终止</span>';      
        }else if( value=='timeDisabled'){
        content += '<span style="background:#FF6347;padding:2px 10px;color:white;">超时失效</span>';      
        }        
        return content;}
		},
// 		{id:'company', title:'公司名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		//{id:'address', title:'地址', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		//{id:'telephone', title:'报警人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		//{id:'joined', title:'创建时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'', title:'操作', type:'string', columnClass:'text-center', columnStyle:'padding:0px;', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '<div class="btn-group">';
		    if( record.status!='dealing'&&record.status!='okCheack'&&record.status!='cancleAlarm'&&record.status!='okNoReason'&&record.status!='stop'&&record.status!='timeDisabled'){
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'jp\' }"><button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.alarmID+'\');"><i class="fa fa-edit"></i>案件推送</button></c:if></c:forEach>';
			}
			content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'jp\' }"><button type="button" class="btn btn-xs btn-default" onclick="eyecase(\''+record.alarmID+'\');"><i class="fa fa-eye"></i>查看</button></c:if></c:forEach>';
			 if( record.status=='nodealing'||record.status=='waiting'
				 ||record.status=='noPolice'){
					content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'jp\' }"><button type="button" class="btn btn-xs btn-default" onclick="stopCase(\''+record.alarmID+'\');"><i class="fa fa-trash-o"></i>终止</button></c:if></c:forEach>';
			}
			 if( record.status=='stop'){
					content += '<c:forEach items="${model.userbutton }" var="buts"><c:if test="${buts.buttonID==\'jp\' }"><button type="button" class="btn btn-xs btn-default" onclick="eyeStop(\''+record.alarmID+'\');"><i class="fa fa-eye"></i>终止原因</button></c:if></c:forEach>';
			}
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
	
	
	function eye(alarmID) {
		window.location.href="case.do?p=eyeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&alarmID="+unicode(alarmID);
		return false;
	}
	
	function eyecase(alarmID) {
		window.location.href = "case.do?p=eyeNoCheack&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&alarmID="
				+ alarmID;
		return false;
	}
	
	
	function jPush(AlarmID) {
		alert($.session.get('sys_user'));
	
		var $confirm = $('#jpush-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
										
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "member.do?p=del&userID="+userID,
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
				$('#jpush-comfirm-modal').modal({});		
		};
	
	
	function stopCase(alarmID) {
	
		var $confirm = $('#my-prompt');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			debugger
			var causeStop = $("#causeStop").val();
			var remarkStop = $("#remarkStop").val();
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "case.do?p=stopCall&alarmID="+ alarmID+"&causeStop="+causeStop+"&remarkStop="+remarkStop,
// 				data : ,
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
		
		$('#my-prompt').modal({});
		
	};
	function eyeStop(alarmID) {
		debugger
		$.ajax({
			lang : 'zh-cn',
			type : 'POST',
			url : "case.do?p=findCallStop&alarmID="+ alarmID,
//				data : ,
			cache : false,
			dataType : 'json',
			success: function(data) {
				sessionStorage.setItem("modelStop",data.modelStop);
				sessionStorage.setItem("causeStops",data.modelStop.causeStop);
				sessionStorage.setItem("remarkStops",data.modelStop.remarkStop);
// 				alert(sessionStorage.getItem("modelStop"));
				var $confirm = $('#my-prompt-stop');
				var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
				var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
				
				$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
				
				$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
					$('#loading-waiting-modal').modal({closeViaDimmer:false});
					debugger
					var causeStops = $("#causeStops").val();
					var remarkStops = $("#remarkStops").val();
					$.ajax({
						lang : 'zh-cn',
						type : 'POST',
						url : "case.do?p=editStopCall&alarmID="+ alarmID+"&causeStops="+causeStops+"&remarkStops="+remarkStops,
//		 				data : ,
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
				
				getAddressByBaidu();
				$('#my-prompt-stop').modal({});
				lockWord();
			},
			
			error:function(data) {
				handerr(data);
			}
		});
		
	};
	function del(userID) {
		var $confirm = $('#delete-comfirm-modal');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "member.do?p=del&userID="+userID,
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
	
	
	$('#btnSearch').on('click', function() {
		$('#dtGridContainer').children().remove();
		$('#dtGridToolBarContainer').children().remove();
		grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: false,
			ajaxLoad : true,
			loadURL : 'case.do?p=find',
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
		sessionStorage.setItem("url","case.do?p=find");
		sessionStorage.setItem("check",false);
		sessionStorage.setItem("sortInt",1);
		grid.load();
	});
	

	
	
</script>

<script type="text/javascript" src="js/myjs/imgView.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
</html>


