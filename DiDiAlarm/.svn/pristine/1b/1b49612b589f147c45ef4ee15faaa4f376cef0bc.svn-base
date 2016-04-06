 var isCityOnchange = false;
 var isDistrictOnchange =false;
 function goview(){
     window.location.href="store.do?p=showStoreDetail&storeID="+dataRoom[index].storeID;
return false;
}
 
 function view(id){
	 window.location.href="store.do?p=showStoreDetail&storeID="+id;
  return false;
 }

var dataGridColumn = [
{id:'invitationCode', title:'邀请码', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
{id:'dataStatusEnum', title:'使用状态', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
{id:'inputDate', title:'创建时间', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq' ,hideType:'xs'},  
{id:'useDate', title:'使用时间', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq',hideType:'xs'},  
{id:'storeName', title:'使用门店', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq',hideType:'xs|sm'},  
{id:'createUserName', title:'使用人', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq',hideType:'xs|sm'},
{id:'operation', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
	var content = '';
	content += '<button class="btn btn-xs btn-danger" onclick="view(\''+record.storeID+'\');"><i class="am-icon-eye"></i> 发送短信</button>';
	return content;
}}
];

var dataGridOption = {
	lang : 'zh-cn',
	ajaxLoad : true,
	loadURL : 'inviteCode.do?p=getInvitePage',
	exportFileName : '邀请码列表',
	columns : dataGridColumn,
	gridContainer : 'dtGridContainer',
	toolbarContainer : 'dtGridToolBarContainer',
	tools : 'export[pdf]',
	pageSize : 10,
	pageSizeLimit : [10, 20, 50]
};

var staffRoleGrid = $.fn.DtGrid.init(dataGridOption);

//自定义查询
function query(){


var queryString = $('#searchText').val();

staffRoleGrid.parameters = new Object();

staffRoleGrid.parameters['queryString'] = queryString;

staffRoleGrid.load(true);

}


$(function(){
	$('#page').css('display', 'block');
staffRoleGrid.load();
//绑定方法
$('#btnSearch').click(query);

});


 
var $modal = $('#my-modal-loading');
 function goAdd(){
   	$.ajax({
	type : 'POST',
	url : "inviteCode.do?p=addInviteCode",
	cache : false,
	dataType : 'json'
}).then(function(data) {
	if (data.MESSAGE == MESSAGE.SUCESSS.id) {
		$('#message').html(MESSAGE.SUCESSS.value);
		$('#messagealert').modal();
		Refresh();
	} else if (data.MESSAGE == MESSAGE.FAIL.id) {
	alert(22);
		$.AMUI.progress.done();
		$('#message').html(MESSAGE.FAIL.value);
		$('#messagealert').modal();
		Refresh();
	}
else if (data.MESSAGE == MESSAGE.ERROR.id) {
alert(33);
		$.AMUI.progress.done();
	 $('#message').html(MESSAGE.ERROR.value);
		$('#messagealert').modal();
		Refresh();
}
}, function() {
	$.AMUI.progress.done();
	$('#message').html("网络异常！请检查您的网络是否已连接好？");
	$('#messagealert').modal();
	Refresh();
});
return false;
}

function Refresh() {
				setTimeout(function() {
					$modal.modal('close');
					sub();
				}, 2000);
			}
 