//配置页面加载模块参数
require.config({
	paths: {
		"jquery"		:['lib/jquery-1.11.0.min','http://cdn.bootcss.com/jquery/1.11.0/jquery.min','http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min','lib/jquery-2.1.1.min'],
		"bootstrap"		:['lib/bootstrap.min','http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js','lib/bootstrap.min'],
		"sly"     		:'lib/sly.min',
		"hammer"     	:'lib/hammer.min',
		"lazyload"      :'lib/jquery.lazyload.min',
		"rose"          :'rose'
	},
	shim: {//模块依赖关系
		jquery			: {exports: '$'},
        'bootstrap' : {deps: ['jquery']},
        'sly' : {deps: ['jquery']},
        'hammer' : {deps: ['jquery']},
        'lazyload' : {deps: ['jquery']},
        'rose' : {deps: ['jquery']}
	}
});
require(['jquery','bootstrap','sly','hammer','lazyload','rose'],function($){
	$(function(){
		 rosefunction();
		
	});
});
