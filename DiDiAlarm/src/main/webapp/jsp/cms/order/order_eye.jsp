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
				<h2 style="color:#353535; display:inline;">预约» 查看</h2>
				<button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
			</div>
			<hr style="margin:0 10px;">
			
			<div style="padding:10px; ">
			<div class="panel">
			<div class="panel-body">
				<form id="mainForm" class="form-horizontal" role="form" method='post'>
					
						
						<input class="hidden" name="orderID" id="userID" value="${model.entity.orderID}">
               <div class="form-group">
						<label class="col-md-2 control-label">办理人姓名:</label>
						<div class="col-md-7">
							<input type="text" name="realname" id="name" placeholder="办理人姓名" value="${model.entity.realName}" class="form-control" readonly>
						</div>					
					</div>
					
					  <div class="form-group">
						<label class="col-md-2 control-label">办照地点:</label>
						<div class="col-md-7">
							<input type="text" name="police" id="name" placeholder="办照地点" value="${model.entity.police}" class="form-control" readonly>
						</div>					
					</div>
					
					
					
						<div class="form-group">										
					</div>
					
  
					
                       <div class="form-group">
						<label class="col-md-2 control-label">预约情况</label>
						<div class="col-md-7" >
					

	             		<select name="status" id="status" class="chosen-select form-control" tabindex="2">
								<c:forEach items="${model.Reservation}" var="se">
									<c:choose>
										<c:when test="${model.entity.status.indexOf(se.dicValue) >= 0 }">
											<option value="${se.dicValue}" selected>${se.dicName }</option>
										</c:when>
										<c:otherwise>
											<option value="${se.dicValue}">${se.dicName }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
				    </div>
				    </div>
				    
					  	<div class="form-group">
						<label class="col-md-2 control-label">电话:</label>
						<div class="col-md-7">
							<input type="text" name="phone" id="email" placeholder="标题内容" value="${model.entity.phone}" class="form-control" readonly>
						</div>					
					</div>
					
					
					
					<div class="form-group">
									<label class="col-md-2 control-label">群众留言:</label>
									<textarea rows="5" name="summary" cols="80"  readonly>${model.entity.wordes}</textarea>
					</div>
					
					
					
                    </div>
					
					</div>				 	  				
					</div>				 
						
					<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
					
				
					<div class="am-g" style="display:none">
						<hr style="margin:0 10px;"><div style="padding:10px 0px"></div>
						
					
						<div class="form-group">
						</div>
					</div>
					
					<div class="text-center col-md-9">
						<button type="button" id="btnCommit" class="btn btn-primary">提交</button>
<!-- 						<button type="button" id="btnClean" class="btn btn-dm">清空</button> -->
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
	
	
	$("#btnCommit").click(function() {
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
// 		window.location.href = "order.do?p=view&moduleID=${model.chooseModuleID}&parentID=${model.chooseParentID}&direct=${model.direct}";
		window.location.href = "order.do?p=view&parentID=600&moduleID=601";
	});
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

	
