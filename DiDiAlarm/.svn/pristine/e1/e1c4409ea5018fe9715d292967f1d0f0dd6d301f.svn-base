	 
	 function view(id){
		 window.location.href="store.do?p=showStoreDetail&storeID="+id;
	  return false;
	 }

var dataGridColumn = [

	{id:'storeName', title:'门店名称', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
	{id:'address', title:'地址', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq'},
	{id:'managers', title:'企业负责人', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq' ,hideType:'xs'},  
	{id:'licenseNumber', title:'GSP证号/药品流通许可证', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq',hideType:'xs'},  
	{id:'qualityManager', title:'质量负责人', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq',hideType:'xs|sm'},  
	{id:'phone', title:'联系方式', type:'string', columnClass:'text-center', fastQuery:true, fastQueryType:'eq',hideType:'xs|sm'},
	{id:'operation', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
	var content = '';
		content += '<button class="btn btn-xs btn-danger" onclick="view(\''+record.storeID+'\');"><i class="am-icon-eye"></i> 查看</button>';
		return content;
	}}
];

var dataGridOption = {
	lang : 'zh-cn',
	ajaxLoad : true,
	loadURL : 'store.do?p=getShopPager',
	exportFileName : '门店列表',
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

