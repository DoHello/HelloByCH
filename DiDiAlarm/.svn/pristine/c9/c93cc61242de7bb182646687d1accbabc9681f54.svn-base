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

			  },

			  // 定义表单提交处理程序
			  //   - 如果没有定义且 `validateOnSubmit` 为 `true` 时，提交时会验证整个表单
			  //   - 如果定义了表单提交处理程序，`validateOnSubmit` 将会失效
			  //        function(e) {
			  //          // 通过 this.isFormValid() 获取表单验证状态
			  //          // Do something...
			  //        }
			  submit:function() {
					
					if(this.isFormValid()){
				$('#my-modal-loading').modal({closeViaDimmer:false});
				$.ajax({
					type : 'POST',
					url : "douclean.do?p=add",
					data : $("#mainForm").serialize(),
					cache : false,
					dataType : 'json',
					
					success: function(data) {
						$.AMUI.progress.done();
						$('#my-modal-loading').modal('close');
						$('#message').html("您已经添加成功！");
						$('#messagealert').modal({closeViaDimmer:false});
						$("#messageConfirm").focus();
						loadGrid.load();
					},
					
					error:function(data) {
						console.log(data)
						$.AMUI.progress.done();
						$('#my-modal-loading').modal('close');
						$('#message').html("网络异常！请检查您的网络是否已连接好？");
						$('#messagealert').modal({closeViaDimmer:false});
						$("#messageConfirm").focus();
					}
				});

				return false;
					}
				return false;
					
				}
			}
			   


var dtGridColumns = [

		{id:'drugName', title:'品名', type:'string', columnClass:'text-center',  fastQuery:true, fastQueryType:'eq'},
		{id:'spec', title:'规格', type:'string', columnClass:'text-center',  fastQuery:true, fastQueryType:'eq'},
		{id:'origin', title:'产地', type:'string', columnClass:'text-center',  fastQuery:true, fastQueryType:'eq'},
		{id:'batchNo', title:'生产批号', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
		{id:'manuName', title:'生产企业', type:'string', columnClass:'text-center',hideType:'xs',  fastQuery:true, fastQueryType:'eq'},
		{id:'licenseNumber', title:'批准文号', type:'string', columnClass:'text-center',hideType:'xs',  fastQuery:true, fastQueryType:'eq'},
		{id:'douDate', title:'出斗日期', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'supplierName', title:'供货单位', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'manuDate', title:'生产日期', type:'string', columnClass:'text-center',hideType:'sm|xs',  fastQuery:true, fastQueryType:'eq'},
		{id:'isThorough', title:'是否彻底清斗', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq',
			resolution:function(value, record, column, grid, dataNo, columnNo){
				content = "";
						if(value=='yes')
					content = "<span style='color:green'>是</span>";
				else if(value=='no')
					content = "<span style='color:red'>否</span>";
			return content;
		}},
		{id:'cleanerUserName', title:'清斗操作人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'dataStatusEnumName', title:'复核状态', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'recheckUserName', title:'清斗复核人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'cleanCause', title:'清斗原因', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
		{id:'inputDate', title:'录入时间', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},

		{id:'', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.cleanID+'\');"><i class="fa fa-eye"></i>  复核</button>';
			content += '<button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.cleanID+'\');"><i class="fa fa-trash-o"></i>  删除</button>';
			return content;
		}}
	]


		var loadGrid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			ajaxLoad : true,
			loadURL : 'douclean.do?p=find&doucleanID='+unicode($("#searchText").val()),
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : '',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50],
			onRowDblClick : function (value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e){
				$("#cleanID").val(record.cleanID) ;
				$("#drugName").val(record.drugName) ;
				$("#douDate").val(record.douDate);
				$("input[name=isThorough][value="+record.isThorough+"]").prop("checked",true);
				$("#stockID").val(record.stockID) ;
				$("#manuDate").val(record.manuDate);
				$("#expireDate").val(record.expireDate) ;
				$("#supplierName").val(record.supplierName);
				$("#supplierID").val(record.supplierID);
				$("#spec").val(record.spec);
				$("#batchNo").val(record.batchNo);
				$("#manuName").val(record.manuName) ;
				$("#origin").val(record.origin);
				$("#barCode").val(record.barCode);
				$("#drugID").val(record.drugID);
				$("#cleanCause").val(record.cleanCause);
	}
		});
		
	    
$("#clearButtom").click(function (){
	 $(':input','#mainForm')
	 .not(':button, :submit, :reset,:radio ')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
});

date = (new Date()).Format("yyyy-MM-dd"); 
$("#douDate").val(date);


	$("#inputInformation").hide();

	$('#inputInformation').validator(options);
	
	
	
	$("#btnSearch").on('click', function() {
	    var queryString = $('#searchText').val();

	    loadGrid.parameters = new Object();

	    loadGrid.parameters['searchValue'] = queryString;

	    loadGrid.refresh(true);
		loadGrid.load();
	});
	
	
	$("#queryForm").submit(function (){
		$("#btnSearch").click();
		
		return false;
		
	});
	

	$("#btnSearch").click();
	
	
	
   	function eye(cleanID) {
   		var $confirm = $('#my-confirm');
		var $confirmMessage = $('#my-confirmNessage').text("你,确定要复核这条记录吗？");

		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#my-modal-loading').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "douclean.do?p=review&cleanID="+cleanID,
				cache : false,
				dataType : 'json',
				success: function(data) {
					$.AMUI.progress.done();
					$('#my-modal-loading').modal('close');
					$('#message').html("您已经复核成功！");
					$('#messagealert').modal({closeViaDimmer:false});
					loadGrid.load();
				},
				
				error:function() {
					$.AMUI.progress.done();
					$('#my-modal-loading').modal('close');
					$('#message').html("网络异常！请检查您的网络是否已连接好？");
					$('#messagealert').modal({closeViaDimmer:false});

				}
			});
		});
		
		$('#my-confirm').modal({});
	}
	
$(".am-btn-toolbar").find("button").attr("isActive", false);
$("#viewBar").find("button").click(function() {
			var $El = $(this);
			if ($El.attr("isActive") == "false") {
				if ($El.attr("id") == "btnAdd") {
					$El.addClass("am-active");
					$("#inputInformation").show();
					$("#inputInformation").addClass("am-animation-slide-top");
					$("#companyInformation").removeClass("am-animation-slide-bottom");
					$El.attr("isActive", true);
				} 
			} else {
				$El.removeClass("am-active");
				$("#inputInformation").removeClass("am-animation-slide-top");
				$("#inputInformation").hide();
				$("#companyInformation").addClass("am-animation-slide-bottom");
				$El.attr("isActive", false);
			}
		});

$("#toolBar").find("button").click(function() {
$("#toolBar").find("button").removeClass("am-active");
var $e = $(this);
$e.attr("isActive", true);
$e.addClass("am-active");
});



	function del(cleanID) {
		var $confirm = $('#my-confirm');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#my-modal-loading').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "douclean.do?p=del&cleanID="+cleanID,
				cache : false,
				dataType : 'json',
				success: function(data) {
					$.AMUI.progress.done();
					$('#my-modal-loading').modal('close');
					$('#message').html("您已经删除成功！");
					$('#messagealert').modal({closeViaDimmer:false});
					loadGrid.load();
					
				},
				
				error:function() {
					$.AMUI.progress.done();
					$('#my-modal-loading').modal('close');
					$('#message').html("网络异常！请检查您的网络是否已连接好？");
					$('#messagealert').modal({closeViaDimmer:false});

				}
			});
		});
		
		$('#my-confirm').modal({});

	};
   	
	
	
	$("#searchText").autocomplete('stock.do?p=getStock', {
		multiple: false,
		dataType: "json",
		parse: function(data) {
			return $.map(data, function(row) {
				return {
					data: row,
					value: row.drugID,
					result: row.drugName 
				}
			});
		},
		formatItem: function(item) {
			return format(item);
		}
	}).result(function(e, item) {
		$.each(item, function(key, values) {
			$("#"+key).prop("value", values);
		});
	});

	function format(row) {
		return "<table><tr><td>药品名称:</td><td><span style='color:#ff0000'>"+row.drugName + "</span>&nbsp;&nbsp;&nbsp;</td><td>规格:</td><td>"+row.spec+"&nbsp;&nbsp;&nbsp;</td><td>产地:</td><td>"+row.origin+"&nbsp;&nbsp;&nbsp;</td></tr>" +
				"<tr><td>生产厂商:</td><td>"+row.manuName+"&nbsp;&nbsp;&nbsp;</td><td>批号：</td><td>"+row.batchNo+"&nbsp;&nbsp;&nbsp;</td><td>生产日期:</td><td>"+row.manuDate+"&nbsp;&nbsp;&nbsp;</td><td>现存数量:</td><td><span style='color:#ff0000'>"+row.quanlity+"</span>&nbsp;&nbsp;&nbsp;</td></tr></table>";
	}
	
	
	
	
		
	
		
		$("#btnDelSel").on('click', function() {
			var $confirm = $('#my-confirm');
			var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
			var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
			
			$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
				$('#my-modal-loading').modal({closeViaDimmer:false});
				$.ajax({
					type : 'POST',
					url : "douclean.do?p=del",
					data : $("#mainForm").serialize(),
					cache : false,
					dataType : 'json',
				
					success: function(data) {
						$.AMUI.progress.done();
						$('#my-modal-loading').modal('close');
						$('#message').html("您已经删除成功！");
						$('#messagealert').modal({closeViaDimmer:false});
						
					},
					
					error:function() {
						$.AMUI.progress.done();
						$('#my-modal-loading').modal('close');
						$('#message').html("网络异常！请检查您的网络是否已连接好？");
						$('#messagealert').modal({closeViaDimmer:false});

					}
				});
			});
			
			$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {
				
			});
			
			$('#my-confirm').modal({});
		});