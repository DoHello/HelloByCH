var isCityOnchange = false;
var isDistrictOnchange =false;


function getCityList() {
	if (isCityOnchange) {
		$.ajax({
			type : 'POST',
			url : "sysdistrict.do?p=getCity",
			cache : false,
			data : "provinceID="+$("#province").val(),
			dataType : 'json'
		}).then(function(data) {
			if (data.MESSAGE == MESSAGE.SUCESSS.id) {
				var $select = $("#city");
				var value = $select.val();
				$select.empty();
				for ( var city in data.model) {
					
					if(value==data.model[city].cityID){
						$select.append("<option value='"+data.model[city].cityID+"'  selected='selected'  >"+data.model[city].cityName+"</option>"); 
					}
					else{
						$select.append("<option value='"+data.model[city].cityID+"'   >"+data.model[city].cityName+"</option>"); 
					}
				}
				$select.data('amui.selected').destroy();
				$select.removeData('amui.selected').selected();
			} else if (data.MESSAGE == MESSAGE.FAIL.id) {
			
			} else if (data.MESSAGE == MESSAGE.ERROR.id) {
				
			}
		}, function() {
			$.AMUI.progress.done();
			$('#message').html("网络异常！请检查您的网络是否已连接好？");
			$('#messagealert').modal();
			
		});
	}
}

function getDistrictList() {
	if (isDistrictOnchange) {
		$.ajax({
			type : 'POST',
			url : "sysdistrict.do?p=getDistrict",
			cache : false,
			data : "cityId="+$("#city").val(),
			dataType : 'json'
		}).then(function(data) {
			if (data.MESSAGE == MESSAGE.SUCESSS.id) {
				var $select	= $("#district");
				var  value = $select.val();
				$select.empty();
				for ( var city in data.model) {
					if(value==data.model[city].cityID){
						 $select.append("<option value='"+data.model[city].id+"'  selected='selected'  >"+data.model[city].disName+"</option>"); 
					}
					else{
						 $select.append("<option value='"+data.model[city].id+"'   >"+data.model[city].disName+"</option>"); 
					}
				}
				$select.data('amui.selected').destroy();
				$select.removeData('amui.selected').selected();
			} else if (data.MESSAGE == MESSAGE.FAIL.id) {
			
			} else if (data.MESSAGE == MESSAGE.ERROR.id) {
				
			}
		}, function() {
			$.AMUI.progress.done();
			$('#message').html("网络异常！请检查您的网络是否已连接好？");
			$('#messagealert').modal();
			
		});
	}
}

$(function() {
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
		submit: null
	}

	$('#inputInformation').validator(options);
});
