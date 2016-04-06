	var dtGridColumns = [
	
	{id:'requestDate', title:'采购日期', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
	{id:'companyName', title:'采购单位名称', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
	{id:'supplierName', title:'供货商名称', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
	{id:'supplierSalesman', title:'供货商的业务员', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'totalMoney', title:'合计金额(包含税金)', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
	{id:'totalTaxes', title:'合计税金', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
	{id:'totalNumber', title:'合计数量', type:'string', columnClass:'text-center', hideType:'xs', fastQuery:true, fastQueryType:'eq'},
	{id:'purchasingAgent', title:'采购员或者代购员或者请货员', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'inputDate', title:'录入时间', type:'string', columnClass:'text-center', hideType:'sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'inputName', title:'录入人员', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'dcCheckName', title:'配送中心审核人', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'dcCheckDate', title:'配送中心审核日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'qmCheckName', title:'质量负责人姓名(QM=Quality Manager)', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'qmCheckDate', title:'质量负责人审核日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'storehouseName', title:'仓库名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'shelvesName', title:'货架名称', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'cmCheckName', title:'药店负责人（企业负责人）', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'cmCheckDate', title:'药店负责人审核日期', type:'string', columnClass:'text-center', hideType:'lg|md|sm|xs', fastQuery:true, fastQueryType:'eq'},
	{id:'', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
			var content = '';
			content += '<button type="button" class="btn btn-xs btn-default" onclick="eye(\''+record.requestID+'\');"><i class="fa fa-eye"></i>  查看</button>';
			content += '<button type="button" class="btn btn-xs btn-danger" onclick="del(\''+record.requestID+'\');"><i class="fa fa-trash-o"></i>  删除</button>';
			return content;
		}}
	]
	
   	function eye(requestID) {
		window.location.href="request.do?p=eyeView&requestID="+requestID;
		return false;
	}

	
	
	function del(requestID) {
		var $confirm = $('#my-confirm');
		var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
		var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
		
		$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {});
		
		$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				lang : 'zh-cn',
				type : 'POST',
				url : "request.do?p=del&requestID="+requestID,
				data : $("#mainForm").serialize(),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'delete');	
				},
				
				error:function(data) {
					handerr(data, 'delete')
				}
			});
		});
		
		$('#my-confirm').modal({});
	};
   	


	$(function() {
		$(".am-btn-toolbar").find("button").click(function() {
			$(".am-btn-toolbar").find("button").attr("isActive", false);
			$(".am-btn-toolbar").find("button").removeClass("am-active");
			var $e = $(this);
			if ($e.attr("id") == "btnDelSel") return;
			$e.attr("isActive", true);
			$e.addClass("am-active");
		});
		
		var grid = $.fn.DtGrid.init({
			lang : 'zh-cn',
			check: true,
			ajaxLoad : true,
			loadURL : 'request.do?p=find&requestID='+$("#searchValue").val(),
			exportFileName : 'User List',
			columns : dtGridColumns,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : 'advanceQuery|print|export[pdf]',
			pageSize : 10,
			pageSizeLimit : [10, 20, 50]
		})
		
		$("#search").on('click', function() {
		var storeID = $('#storeID').val();
		grid.parameters = new Object();
		grid.parameters['storeID'] = storeID;
		grid.load();
		});
		
		 
		
		$("#search").click();
		
		$("#btnAdd").on('click', function() {
			var storeID = $('#storeID').val();
			window.location.href = "request.do?p=addView&storeID="+storeID;
		});
		
		$("#btnEye").on('click', function() {
			window.location.href = "request.do?p=eyeView";
		});
		
		$("#btnDelSel").on('click', function() {
			var $confirm = $('#my-confirm');
			var $confirmBtn = $confirm.find('[data-am-modal-confirm]');
			var $cancelBtn = $confirm.find('[data-am-modal-cancel]');
			
			$confirmBtn.off('click.confirm.modal.amui').on('click.confirm.modal.amui', function() {
				$('#loading-waiting-modal').modal({closeViaDimmer:false});
				$.ajax({
					type : 'POST',
					url : "request.do?p=del",
					data : $("#mainForm").serialize(),
					cache : false,
					dataType : 'json',
				
					success: function(data) {
						handsuc(data, 'delete');	
					},
					
					error:function(data) {
						handerr(data, 'delete')
					}
				});
			});
			
			$cancelBtn.off('click.cancel.modal.amui').on('click.cancel.modal.amui', function() {
				
			});
			
			$('#my-confirm').modal({});
		});
		
		$("#btnQmCheck").on('click', function() {
			if (grid.getCheckedRecords().length == 0) {
				toast("您没有选择需要取消审核的数据");
				return;
			}
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				type : 'POST',
				url : "request.do?p=check",
				data : 'status=QmYes&str='+JSON.stringify(grid.getCheckedRecords()),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'default')
				},
				error:function(data) {
					handerr(data, 'default')
				}
			});
		});
		
		$("#btnQmCheckNo").on('click', function() {
			if (grid.getCheckedRecords().length == 0) {
				toast("您没有选择需要取消审核的数据");
				return;
			}
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				type : 'POST',
				url : "request.do?p=check",
				data : 'status=QmNo&str='+JSON.stringify(grid.getCheckedRecords()),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'default')
				},
				error:function(data) {
					handerr(data, 'default')
				}
			});
		});
		
		$("#btnCmCheck").on('click', function() {
			if (grid.getCheckedRecords().length == 0) {
				toast("您没有选择需要取消审核的数据");
				return;
			}
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				type : 'POST',
				url : "request.do?p=check",
				data : 'status=CmYes&str='+JSON.stringify(grid.getCheckedRecords()),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'default')
				},
				error:function(data) {
					handerr(data, 'default')
				}
			});
		});
		
		$("#btnCmCheckNo").on('click', function() {
			if (grid.getCheckedRecords().length == 0) {
				toast("您没有选择需要取消审核的数据");
				return;
			}
			$('#loading-waiting-modal').modal({closeViaDimmer:false});
			$.ajax({
				type : 'POST',
				url : "request.do?p=check",
				data : 'status=CmNo&str='+JSON.stringify(grid.getCheckedRecords()),
				cache : false,
				dataType : 'json',
			
				success: function(data) {
					handsuc(data, 'default')
				},
				error:function(data) {
					handerr(data, 'default')
				}
			});
		});
	})
