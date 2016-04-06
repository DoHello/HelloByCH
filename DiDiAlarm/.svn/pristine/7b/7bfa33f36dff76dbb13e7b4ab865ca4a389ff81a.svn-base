	 
	 function view(id){
	        window.location.href="staff.do?p=detail&staffID="+id;
	  return false;
	 }

var dataGridColumn = [

    {id:'realName', title:'用户名称', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
    {id:'userName', title:'登录名', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
    {id:'roleName', title:'登录名', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},  
    {id:'phone', title:'联系方式', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},  
    {id:'dicSexName', title:'性别', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},  
    {id:'dataStatusEnumName', title:'使用状态', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
    {id:'operation', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
		var content = '';
		content += '<button class="btn btn-xs btn-danger" onclick="view(\''+record.userID+'\');"><i class="am-icon-eye"></i> 查看</button>';
		return content;
	}}
];

var dataGridOption = {
	lang : 'zh-cn',
	ajaxLoad : true,
	loadURL : 'staff.do?p=getStaffPage',
	exportFileName : '用户列表',
	columns : dataGridColumn,
	gridContainer : 'dtGridContainer',
	toolbarContainer : 'dtGridToolBarContainer',
	tools : 'export[pdf,excel]',
	pageSize : 10,
	pageSizeLimit : [10, 20, 50]
};

var staffRoleGrid = $.fn.DtGrid.init(dataGridOption);


//自定义查询
function query() {

	
    var searchText = $('#searchText').val();

    staffRoleGrid.parameters = new Object();

    staffRoleGrid.parameters['searchText'] = searchText;

    staffRoleGrid.refresh(true);

}


$(function(){
	$('#page').css('display', 'block');
	staffRoleGrid.load();
    //绑定方法
    $('#btnSearch').click(query);

});

