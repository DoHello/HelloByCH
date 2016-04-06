<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.derbysoft.enums.*"%>
<%@ page language="java" import="dy.hrtworkframe.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>

<link rel="stylesheet" type="text/css" href="css/imgView.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/style.css"/>
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="js/webuploader/webuploadcontoller.css"/>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
<!-- WARNING: Respond.js doesn't work if you view the page via file://-->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<body>
	<base href="<%=basePath%>">
	<%@ include file="../../top.jsp"%>
	<%@ include file="../../head.jsp"%>
	<script charset="utf-8" src="js/editor/kindeditor.js"></script>
	<script charset="utf-8" src="js/editor/lang/en.js"></script>
	<div id="page" style='top:${model.direct==1?0:'60px'}'>
		<div id="pageBody" class="scrollbar-hover">
		<!-- sidebar start -->
		<%@ include file="../../left.jsp"%>
		<!-- sidebar end -->
		
		<!-- content start -->
		<div class="admin-content am-animation-slide-right clearfix">
			<div style="padding:20px; ">
				<h2 style="color:#353535; display:inline;">案件管理系统» 查看未报警详情</h2>
				<button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
			<div class="panel">
			<div class="panel-body">
				<form id="mainForm" class="form-horizontal" role="form" method='post'>
					
						
						<input class="hidden" name="alarmID" id="alarmID" value="${model.call.alarmID}">
               
					
					  <div class="form-group">
						<label class="col-md-2 control-label">报警单:</label>
						<div class="col-md-7">
							<input type="text" name="police" id="name" placeholder="报警单" value="${model.call.alarmID}" class="form-control" readonly>
						</div>					
					</div>
					
					
					
					
					
  
					
                       <div class="form-group">
						<label class="col-md-2 control-label">报警时间</label>
					<div class="col-md-7">
							<input type="text" name="police" id="name" placeholder="报警时间" value="${model.call.inputDate}" class="form-control" readonly>
						</div>	
				    </div>
				    
					  	<div class="form-group">
						<label class="col-md-2 control-label">报警人:</label>
						<div class="col-md-7">
							<input type="text" name="name" id="name" placeholder="报警人" value="${model.call.name}" class="form-control" readonly>
						</div>					
					</div>
					
						  	<div class="form-group">
						<label class="col-md-2 control-label">报警人电话:</label>
						<div class="col-md-7">
							<input type="text" name="phone" id="phone" placeholder="报警人电话" value="${model.call.phone}" class="form-control" readonly>
						</div>					
					</div>	
					
					
				  	<div class="form-group">
						<label class="col-md-2 control-label">案发地点:</label>
						<div class="col-md-7">
							<input type="text" name="addressName" id="addressName" placeholder="案发地点" value="${model.call.addressName}" class="form-control" readonly>
 						</div>					
					</div>		
           					

             	<c:forEach items="${model.alarmReceive}" var="refuse">			
									
									  <div class="form-group">
										<label class="col-md-2 control-label">拒绝警员:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${refuse.realName} "
													class="form-control" required readonly="readonly">
											</div>
										</div>
									</div>
								 <div class="form-group">
										<label class="col-md-2 control-label">拒绝理由:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${refuse.refuseText} "
													class="form-control" required readonly="readonly">
											</div>
										</div>
									</div>
								 <div class="form-group">
										<label class="col-md-2 control-label">图片展示:</label>
										<ul style="margin-left: 170px">
										<c:forEach items="${refuse.img}" var="item">

											<img src="${item}" width="20%" />
											<!-- <img src="" width="50" height="60" /> -->
										</c:forEach>
									</ul>
									</div>									
									</c:forEach>







								<c:if test="${model.call.messageType=='oneKeyAlarm'}">

									<div class="form-group">
										<label class="col-md-2 control-label">报警内容:</label>


										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${model.call.messageText}"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									</div>
								</c:if>
					
					
					<c:if test="${model.call.status=='dealing'}">
				    <div class="form-group">
										<label class="col-md-2 control-label">案件处理状态:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="处理中"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									
									
									
					<label class="col-md-2 control-label">接警人:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="realName" id="realName"
													placeholder="" value="${model.receive.realName}"
													class="form-control" required readonly="readonly">
											</div>
										</div>
								</div>		
									<label class="col-md-2 control-label">到场时间:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${model.receive.presentTime}"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									<label class="col-md-2 control-label">警察电话:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${model.receive.phone}"
													class="form-control" required readonly="readonly">
											</div>
										</div>		
					
					</c:if>
					
					
					<c:if test="${model.call.status=='nodealing'}">
		               <div class="form-group">
										<label class="col-md-2 control-label">案件处理状态:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="未处理"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									</div>
									
								
									
					
									
									
					</c:if>
					
					
					
                    <c:if test="${model.call.status=='okNoReason'}">
										<label class="col-md-2 control-label">案件处理状态:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="超出处理范围"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									
					</c:if>
					
					
					 <c:if test="${model.call.status=='waiting'}">
				     	<label class="col-md-2 control-label">案件处理状态:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="等待中"
													class="form-control" required readonly="readonly">
											</div>
										</div>
					</c:if>
                   
                   
          <c:if test="${model.call.status=='okCheack'}">
				    <div class="form-group">
										<label class="col-md-2 control-label">案件处理状态:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="已备案"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									
									
									
					<label class="col-md-2 control-label">接警人:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="realName" id="realName"
													placeholder="" value="${model.receive.realName}"
													class="form-control" required readonly="readonly">
											</div>
										</div>
								</div>		
									<label class="col-md-2 control-label">到场时间:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${model.receive.presentTime}"
													class="form-control" required readonly="readonly">
											</div>
										</div>
									<label class="col-md-2 control-label">警察电话:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="messageText" id="messageText"
													placeholder="" value="${model.receive.phone}"
													class="form-control" required readonly="readonly">
											</div>
										</div>		
					
					</c:if>

 <c:if test="${model.call.status=='stop'}">
				     	<label class="col-md-2 control-label">终止原因:</label>
										<div class="form-group">
											<div class="col-md-7">
												<input type="text" name="stopCause" id="stopCause"
													placeholder="" value="${model.call.stopCause}"
													class="form-control" required readonly="readonly">
											</div>
										</div>
				     	<label class="col-md-2 control-label">备注:</label>
										<div class="form-group">
											<div class="col-md-7">
													
												<textarea name="remarkStop" id="remarkStop"
													 
													class="form-control" required readonly="readonly">${model.call.remarkStop}</textarea> 
											</div>
										</div>

					</c:if>


					</div>				 	  				
					</div>				 
						
						
						
					<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
					
				
					<div class="am-g" style="display:none">
						<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
						
					
						<div class="form-group">
						</div>
					</div>
					
					<div class="text-center col-md-9">
						<button type="button" id="btnCommit" class="btn btn-primary">返回</button>
<!-- 						<button type="button" id="btnClean" class="btn btn-dm">清空</button> -->
						<c:if test="${model.call.status.indexOf('nodealing') >= 0}">
							<button type="button" id="btnStop" onclick="btnStop1('${model.call.alarmID}');" class="btn btn-primary" style="background-color:  red">终止</button>
						</c:if>
						<c:if test="${model.call.status.indexOf('noPolice') >= 0}">
						<button type="button" id="btnStop" onclick="btnStop1('${model.call.alarmID}');" class="btn btn-primary" style="background-color:  red">终止</button>
						</c:if>
						<c:if test="${model.call.status.indexOf('waiting') >= 0}">
							<button type="button" id="btnStop" onclick="btnStop1('${model.call.alarmID}');" class="btn btn-primary" style="background-color:  red">终止</button>
						</c:if>
					</div>
				</form>
			</div>
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

 var 	editor =null;
	window.onload = function() {
		$("#quantity").keyup(function () {
			//如果输入非数字，则替换为''，如果输入数字，则在每4位之后添加一个空格分隔
			this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1");
		})
		
		$('#page').css('display', 'block');
		
		if ($.fn.chosen) {
			$('#pageBody .chosen-select').chosen();
		}
	
		if ($.fn.datetimepicker) {
			$('#pageBody .form-date').datetimepicker({
				language: 'zh-CN',
				weekStart: 1,
				todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0,
				format: 'yyyy-mm-dd'
			});
		}
		
		$('#companyID').trigger('change');
	};
	
	
	// Chosen联动
	$('#companyID').change(function() {
		var s = $('#companyID').val();
		var t = "";
		$('#companyID option').each(function(idx) {
			var txt = $(this).text(); //获取单个text
			var val = $(this).val(); //获取单个value
			
			if (s == null) {
				t = "";
			} else if (s.indexOf(val) >= 0 && t.indexOf(txt) < 0) {
				t += "," + txt;
			}
		});
		$('#companyName').attr('value', t.substring(1));
	});
	
	function gobackF() {
		window.location.href="case.do?p=policeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
		return false;
	}
	$("#btnCommit").click(function() {
		gobackF();
	});
	$("#btnCommitwwww").click(function() {
    options = {
    	// 是否使用 H5 原生表单验证，不支持浏览器会自动退化到 JS 验证
        H5validation: false,

        // 内置规则的 H5 input type，这些 type 无需添加 pattern
        H5inputType: ['email', 'url', 'number'],

        // 验证正则
        // key1: /^...$/，包含 `js-pattern-key1` 的域会自动应用改正则
        patterns: {},

        // 规则 class 钩子前缀
        patternClassPrefix: 'js-pattern-',

        activeClass: 'am-active',

        // 验证不通过时添加到域上的 class
        inValidClass: 'am-field-error',

        // 验证通过时添加到域上的 class
        validClass: 'am-field-valid',

        // 表单提交的时候验证
        validateOnSubmit: true,

        // 表单提交时验证的域
        // Elements to validate with allValid (only validating visible elements)
        // :input: selects all input, textarea, select and button elements.
        allFields: ':input:visible:not(:button, :disabled, .am-novalidate)',

        // 调用 validate() 方法的自定义事件
        customEvents: 'validate',

        // 下列元素触发以下事件时会调用验证程序
        keyboardFields: ':input:not(:button, :disabled,.am-novalidate)',
        keyboardEvents: 'focusout, change', // keyup, focusin

        activeKeyup: true,

        // 鼠标点击下列元素时会调用验证程序
        pointerFields: 'input[type="range"]:not(:disabled, .am-novalidate), ' +
        'input[type="radio"]:not(:disabled, .am-novalidate), ' +
        'input[type="checkbox"]:not(:disabled, .am-novalidate), ' +
        'select:not(:disabled, .am-novalidate), ' +
        'option:not(:disabled, .am-novalidate)',
        pointerEvents: 'click',

        // 域通过验证时回调
        onValid: function(validity) {
        
        },

	    // 验证出错时的回调， validity 对象包含相关信息，格式通 H5 表单元素的 validity 属性
	    onInValid: function(validity) {
	    },
	
	    // 域验证通过时添加的操作，通过该接口可定义各种验证提示
	    markValid: function(validity) {
	        // this is Validator instance
	        var options = this.options;
	        var $field  = $(validity.field);
	        var $parent = $field.closest('.am-form-group');
	        $field.addClass(options.validClass).
	        removeClass(options.inValidClass);
	
	        $parent.addClass('am-form-success').removeClass('am-form-error');
	
	        options.onValid.call(this, validity);
	    },
	
		// 域验证失败时添加的操作，通过该接口可定义各种验证提示
		markInValid: function(validity) {
			var options = this.options;
			var $field  = $(validity.field);
			var $parent = $field.closest('.am-form-group');
			$field.addClass(options.inValidClass + ' ' + options.activeClass).
			  removeClass(options.validClass);
			
			$parent.addClass('am-form-error').removeClass('am-form-success');
			
			options.onInValid.call(this, validity);
		},
	
		// 自定义验证程序接口，详见示例
		validate: function(validity) {
			if ($(validity.field).is('.js-ajax-validate')) {
				// 异步操作必须返回 Deferred 对象
			    return $.ajax({
			        url: '<%=basePath%>user.do',
						// cache: false, 实际使用中请禁用缓存
						data : "p=hasUser&userName=" + $(validity.field).val(),
						dataType : 'json'
				}).then(function(data) {
					if (data) {
						validity.valid = true;
					} else {
						validity.valid = false;
					}
					return validity;
				}, function() {
					validity.valid = false;
					return validity;
				});
			}
		},
		
		// 定义表单提交处理程序
		//   - 如果没有定义且 `validateOnSubmit` 为 `true` 时，提交时会验证整个表单
		//   - 如果定义了表单提交处理程序，`validateOnSubmit` 将会失效
		//        function(e) {
		//          // 通过 this.isFormValid() 获取表单验证状态
		//          // Do something...
		//        }
		submit : null
	}
	
	$('#mainForm').validator(options);
	$('#loading-waiting-modal').modal({closeViaDimmer:false});
		$.ajax({
			type : 'POST',
			url : "order.do?p=edit",
			data : $("#mainForm").serialize(),
			cache : false,
			dataType : 'json',
			
			success: function(data) {
			if(data.error==null)
				handsuc(data, 'update', 'order.do?p=view&parentID=600&moduleID=601');
			else
				handerr(data);	
			},			
			error:function(data) {
				handerr(data);
			}
		});
	});
	
	
	$("#btnClean").click(function() {
		$(':input,#mainForm').removeAttr('checked').removeAttr('selected').attr('value', '').attr('text', '');
		$(':textarea,#mainForm').removeAttr('checked').removeAttr('selected').attr('value', '').attr('text', '');
		alert(11);
	});
	
	
	$("#searchText").autocomplete('dykmsupplier.do?p=find', {
		multiple: false,
		dataType: "json",
		parse: function(data) {
			return $.map(data.exhibitDatas, function(row) {
				return {
					data: row,
					value: row.supplierID,
					result: row.supplierName 
				}
			});
		},
		formatItem: function(item) {
			return format(item);
		}
	}).result(function(e, item) {
		$.each(item, function(key, values) {
			$('#' + key).attr('value', values);
		});
	});


	function format(row) {
		return "<table><tr><td>供应商名称:</td><td><span style='color:#ff0000'>"+row.supplierName + "</span>&nbsp;&nbsp;&nbsp;</td><td>供应商地址:</td><td>"+
		row.supplierAddress+"&nbsp;&nbsp;&nbsp;</td><td>注册证号:</td><td>"+row.taxNumber+"&nbsp;&nbsp;&nbsp;</td></tr>" +
				"</table>";
	}
	
	
	$('#pageCloseBtn').click(function() {
		window.location.href = "case.do?p=policeView&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
	});
	function btnStop1(alarmID) {
		
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
//					data : ,
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					window.location.href = "case.do?p=eyeNoCheack&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}&alarmID="
						+ alarmID;
					return false;
// 					handsuc(data, 'delete');
// 					$('#dtGridContainer').children().remove();
// 					$('#dtGridToolBarContainer').children().remove();
// 					grid.load();
				},
				
				error:function(data) {
					handerr(data);
				}
			});
		});
		
		$('#my-prompt').modal({});
		
	};
</script>
<script>
KindEditor.ready(function(K) {
	editor=K.create('#texteray', {
		allowFileManager : true
	});
});
</script>

<!-- 
<script type="text/javascript" src="js/pinYinCode.js"></script>
<script type="text/javascript" src="js/myjs/pageList.js"></script>
<script type="text/javascript" src="js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="js/webuploader/webuploadcontoller.js"></script>
-->
<script type='text/javascript' src='js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript' src='js/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="js/autocomplete/lib/thickbox.css" />
</html>

	
