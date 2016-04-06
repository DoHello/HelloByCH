  

 		$.fn.formPage = function (object){
 		
 			this.$form = $(this);
 			this.queryColumn = object.queryColumn;
 			for(var data in this.queryColumn){
 				this.$form.append('<div class="am-u-sm-3 am-u-md-3 am-u-md-offset-3">'+
 				       '<i class="'+queryColumn[data].iconClass+'"></i>'+
 				        '<div title="'+this.queryColumn[data].name+'" class="am-form-group ">"');
 				if(this.queryColumn[data].type==="input"){
 					
 					
 				}else if (this.queryColumn[data].type==="value"){
 					
 				}else if ((this.queryColumn[data].type==="")){
 					
 					
 				}
 				this.queryColumn[data];
 				
 			}
 	
 		 var form = formNode.append("");
 			
 			
 			
 		}

 		
 var dataRoom, index;


function nextPage(page, $node, column,button,callBack) {
	var parent = $node, url;
	var i = 0;
	while (!parent.is('form')) {
		if (i++ > 50) return;
		parent = parent.parent();
	}
	if (true && parent) {
		url = parent.attr("action");
	}
	
	$pageBar = parent.find("#pageBar");
	$.AMUI.progress.start();
	parent.submit(function() {
		$.ajax({
			type : 'POST',
			url : url,
			cache : false,
			data : parent.serialize() + "&currentPage=" + page,
			dataType : 'json'
		}).then(function(data) {
			if (data.MESSAGE == MESSAGE.SUCESSS.id) {
				dataRoom = data.data;
				initializeTable(parent, data.data, column,button);
				$pageBar.empty();
				var pagebar = getPageBar(data.page, column,button);
				if(pagebar)
				pagebar.appendTo($pageBar);
				$.AMUI.progress.done();
				$('#loginMessageDiv').html(MESSAGE.SUCESSS.value);
			} else if (data.MESSAGE == MESSAGE.FAIL.id) {
				$.AMUI.progress.done();
				$('#message').html(MESSAGE.FAIL.value);
				$('#messagealert').modal();
			}
		 else if (data.MESSAGE == MESSAGE.ERROR.id) {
				$.AMUI.progress.done();
			 $('#message').html(MESSAGE.ERROR.value);
				$('#messagealert').modal();
		}
			if(callBack){
			callBack();
			}
		}, function() {
			$.AMUI.progress.done();
			$('#message').html("网络异常！请检查您的网络是否已连接好？");
			$('#messagealert').modal();
			
		});
		return false;
	});
	parent.submit();
	parent.unbind("submit");
}


function nextPageByparm(parm, $node, column,button) {
	var parent = $node, url;
	while (!parent.is('form')) {
		parent = parent.parent();
	}
	if (true && parent) {
		url = parent.attr("action");
	}
	$pageBar = parent.find("#pageBar");
	$.AMUI.progress.start();
	parent.submit(function() {
		$.ajax({
			type : 'POST',
			url : url,
			cache : false,
			data : parm,
			dataType : 'json'
		}).then(function(data) {
			if (data.MESSAGE == MESSAGE.SUCESSS.id) {
				dataRoom = data.data;
				initializeTable(parent, data.data, column,button);
				$pageBar.empty();
				var pagebar = getPageBar(data.page, column,button);
				if(pagebar)
				pagebar.appendTo($pageBar);
				$.AMUI.progress.done();
				$('#loginMessageDiv').html(MESSAGE.SUCESSS.value);
			} else if (data.MESSAGE == MESSAGE.FAIL.id) {
				$('#message').html(MESSAGE.FAIL.value);
			}
		}, function() {
			$.AMUI.progress.done();
			$('#message').html("网络异常！请检查您的网络是否已连接好？");
			$('#messagealert').modal();
		});
		return false;
	});
	parent.submit();
	parent.unbind("submit");
}

function getPageBar(page, column,button) {
	var showTag = 5, pageBar,bttonz = JSON.stringify(button), // 分页标签显示数量
	startTag = 1;
	if (page.currentPage > showTag) {
		startTag = page.currentPage - 1;
	}
	var endTag = startTag + showTag - 1;
	if (page.totalResult > 0) {
		pageBar = "<div class='am-cf'>共<font color=red>" + page.totalResult
				+ "</font>条记录<a>共" + page.totalPage
				+ "页</a><div class='am-fr'>";
		pageBar += "<ul class='am-pagination'>";
		if (page.currentPage < showTag) {
			pageBar += "<li class='am-disabled'><a >«</a></li>";
		} else {
			pageBar += "<li ><a  style='cursor:pointer' onclick='javascript:nextPage("
					+ (page.currentPage - showTag) + ",$(this),[";
			var columnStr = "";
			for ( var value in column) {
				columnStr += '"' + column[value] + '",';
			}
			columnStr = columnStr.substring(0, columnStr.length - 1);
			pageBar = pageBar + columnStr;
			pageBar += "],"+bttonz+")'>«</a></li>";
		}
		for (var i = startTag; i <= page.totalPage && i <= endTag; i++) {
			if (page.currentPage == i) {
				pageBar += "<li class='am-active'><a >" + i + "</a></li>";
			} else {
				pageBar += "<li><a style='cursor:pointer' onclick='javascript:nextPage("
						+ i + ",$(this),[";
				var columnStr = "";
				for ( var value in column) {
					columnStr += '"' + column[value] + '",';
				}
				columnStr = columnStr.substring(0, columnStr.length - 1);
				pageBar = pageBar + columnStr;
				pageBar += "],"+bttonz+")'>" + i + "</a></li>";
			}
		}
		if (endTag >= page.totalPage) {
			pageBar += "<li  class='am-disabled' ><a href='#'>»</a></li>";
		} else {
			pageBar += "<li><a style='cursor:pointer'onclick='javascript:nextPage("
					+ (page.currentPage + showTag) + ",$(this)"+bttonz+",[";
			var columnStr = "";
			for ( var value in column) {
				columnStr += '"' + column[value] + '",';
			}
			columnStr = columnStr.substring(0, columnStr.length - 1);
			pageBar = pageBar + columnStr;
			pageBar += "],"+bttonz+")'>»</a></li>";
		}
		pageBar += "</ul></div>";
		return $(pageBar);
	}

}
function getIndex(sndex){
	index = sndex;
}

function initializeTable($form/*<from>*/, data, columns/*array*/, buttons) {
	var $tbody = $form.find('tbody'); $tbody.empty();
	
	for (var obj in data) {
		$tr = $("<tr ></tr>");
		
		for (var value in columns) {
			if (data[obj][columns[value]] == null){
				data[obj][columns[value]] = "";
			}
			$td = $("<td>" + data[obj][columns[value]] + "</td>");
			$td.appendTo($tr);
		}
		
		if (buttons != null) {
			$td = $('<td><div class="am-btn-toolbar">'+
				'<div class="am-btn-group am-btn-group-xs"></div></div></td>');
			$btnDiv = $td.find(".am-btn-group");
		
			for (var button in buttons) {
				var	$button = $('<button class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="getIndex('+obj+');'+buttons[button].fn+';return false;" ><span class="'+buttons[button].icon+'"></span>'+buttons[button].name+'</button>');
				$button.appendTo($btnDiv);
			}
			$td.appendTo($tr);
		}
		$tr.appendTo($tbody);
	}
}
/*
function initializeTable($form, data, column,button) {
	var $tbody = $form.find('tbody');
	$tbody.empty();
		for ( var obj in data) {
			$tr = $("<tr ></tr>");
			for ( var value in column) {
				
				 if(data[obj][column[value]]==null){
					
					 data[obj][column[value]] ="";
				 }
				$td = $("<td>" + data[obj][column[value]] + "</td>");
				$td.appendTo($tr);
			} 
			if(button !=null){
				$td = $('<td><div class="am-btn-toolbar">'+
                  '<div class="am-btn-group am-btn-group-xs"></div></div></td>');
				 $btnDiv  = $td.find(".am-btn-group");
			for(var ss in  button){
			var	$button = $('<button class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="getIndex('+obj+');'+button[ss].fn+';return false;" ><span class="'+button[ss].icon+'"></span>'+button[ss].name+'</button>');
				$button.appendTo($btnDiv);
			}
			$td.appendTo($tr);
			}
			$tr.appendTo($tbody);
		}

}*/

// 调整每页显示条数
function changeCount(value) {
	window.parent.jzts();
	if (true && document.forms[0]) {
		var url = document.forms[0].getAttribute("action");
		if (url.indexOf('?') > -1) {
			url += "&" + (entityOrField ? "currentPage" : "page.currentPage")
					+ "=";
		} else {
			url += "?" + (entityOrField ? "currentPage" : "page.currentPage")
					+ "=";

		}
		url = url + "1&" + (entityOrField ? "showCount" : "page.showCount")
				+ "=" + value;
		document.forms[0].action = url;
		document.forms[0].submit();
	} else {
		var url = document.location + '';
		if (url.indexOf('?') > -1) {
			if (url.indexOf('currentPage') > -1) {
				var reg = /currentPage=\\d*/g;
				url = url.replace(reg, 'currentPage=');
			} else {
				url += "1&"
						+ (entityOrField ? "currentPage" : "page.currentPage")
						+ "=";
			}
		} else {
			url += "?" + (entityOrField ? "currentPage" : "page.currentPage")
					+ "=";
		}
		url = url + "&" + (entityOrField ? "showCount" : "page.showCount")
				+ "=" + value;
		document.location = url;
	}
}
