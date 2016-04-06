
require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                 'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                  'echarts/chart/pie', // 
                  'echarts/chart/funnel'
            ],
        
    function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                var myChart1 = ec.init(document.getElementById('chars'),'macarons'); 
                var myChart2 = ec.init(document.getElementById('charsA'),'macarons'); 
                var myChart3 = ec.init(document.getElementById('charsC'),'macarons'); 
        option = {
    title : {
        text: '本日销售金额表',
        subtext: '纯属虚构'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['最高气温','最低气温']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value} °C'
            }
        }
    ],
    series : [
        {
            name:'最高气温',
            type:'line',
            data:[11, 11, 15, 13, 12, 13, 10],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'最低气温',
            type:'line',
            data:[1, -2, 2, 5, 3, 2, 0],
            markPoint : {
                data : [
                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ]
            }
        }
    ]
}
    myChart.setOption(option); 
    
    SIoption = {
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
    },
    toolbox: {
        show : true,
        orient: 'vertical',
        x: 'right',
        y: 'center',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'直接访问',
            type:'bar',
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'邮件营销',
            type:'bar',
            stack: '广告',
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'联盟广告',
            type:'bar',
            stack: '广告',
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'视频广告',
            type:'bar',
            stack: '广告',
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'搜索引擎',
            type:'bar',
            data:[862, 1018, 964, 1026, 1679, 1600, 1570],
            markLine : {
                itemStyle:{
                    normal:{
                        lineStyle:{
                            type: 'dashed'
                        }
                    }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        },
        {
            name:'百度',
            type:'bar',
            barWidth : 5,
            stack: '搜索引擎',
            data:[620, 732, 701, 734, 1090, 1130, 1120]
        },
        {
            name:'谷歌',
            type:'bar',
            stack: '搜索引擎',
            data:[120, 132, 101, 134, 290, 230, 220]
        },
        {
            name:'必应',
            type:'bar',
            stack: '搜索引擎',
            data:[60, 72, 71, 74, 190, 130, 110]
        },
        {
            name:'其他',
            type:'bar',
            stack: '搜索引擎',
            data:[62, 82, 91, 84, 109, 110, 120]
        }
    ]
};

 
    myChart1.setOption(SIoption);
    
    
    optionP = {
    	    title : {
    	        text: '本周销售金额表',
    	        subtext: '纯属虚构'
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['最高气温','最低气温']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'category',
    	            boundaryGap : false,
    	            data : ['周一','周二','周三','周四','周五','周六','周日']
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value',
    	            axisLabel : {
    	                formatter: '{value} °C'
    	            }
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'最高气温',
    	            type:'line',
    	            data:[11, 11, 15, 13, 12, 13, 10],
    	            markPoint : {
    	                data : [
    	                    {type : 'max', name: '最大值'},
    	                    {type : 'min', name: '最小值'}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name: '平均值'}
    	                ]
    	            }
    	        },
    	        {
    	            name:'最低气温',
    	            type:'line',
    	            data:[1, -2, 2, 5, 3, 2, 0],
    	            markPoint : {
    	                data : [
    	                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name : '平均值'}
    	                ]
    	            }
    	        }
    	    ]
    	}
    	    myChart2.setOption(optionP); 
    
    SIoptionA = {
    			
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    	        }
    	    },
    	    legend: {
    	        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
    	    },
    	    toolbox: {
    	        show : true,
    	        orient: 'vertical',
    	        x: 'right',
    	        y: 'center',
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'category',
    	            data : ['周一','周二','周三','周四','周五','周六','周日']
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value'
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'直接访问',
    	            type:'bar',
    	            data:[320, 332, 301, 334, 390, 330, 320]
    	        },
    	        {
    	            name:'邮件营销',
    	            type:'bar',
    	            stack: '广告',
    	            data:[120, 132, 101, 134, 90, 230, 210]
    	        },
    	        {
    	            name:'联盟广告',
    	            type:'bar',
    	            stack: '广告',
    	            data:[220, 182, 191, 234, 290, 330, 310]
    	        },
    	        {
    	            name:'视频广告',
    	            type:'bar',
    	            stack: '广告',
    	            data:[150, 232, 201, 154, 190, 330, 410]
    	        },
    	        {
    	            name:'搜索引擎',
    	            type:'bar',
    	            data:[862, 1018, 964, 1026, 1679, 1600, 1570],
    	            markLine : {
    	                itemStyle:{
    	                    normal:{
    	                        lineStyle:{
    	                            type: 'dashed'
    	                        }
    	                    }
    	                },
    	                data : [
    	                    [{type : 'min'}, {type : 'max'}]
    	                ]
    	            }
    	        },
    	        {
    	            name:'百度',
    	            type:'bar',
    	            barWidth : 5,
    	            stack: '搜索引擎',
    	            data:[620, 732, 701, 734, 1090, 1130, 1120]
    	        },
    	        {
    	            name:'谷歌',
    	            type:'bar',
    	            stack: '搜索引擎',
    	            data:[120, 132, 101, 134, 290, 230, 220]
    	        },
    	        {
    	            name:'必应',
    	            type:'bar',
    	            stack: '搜索引擎',
    	            data:[60, 72, 71, 74, 190, 130, 110]
    	        },
    	        {
    	            name:'其他',
    	            type:'bar',
    	            stack: '搜索引擎',
    	            data:[62, 82, 91, 84, 109, 110, 120]
    	        }
    	    ]
    	};
    var u = [{
            name:'谷歌',
            type:'bar',
            stack: '搜索引擎',
            data:[555, 444, 21,33,555, 33, 555]
        }]
    var ecConfig = require('echarts/config');
    function eConsole(param) {
        var mes = '【' + param.type + '】';
        if (typeof param.seriesIndex != 'undefined') {
        	myChart3.setSeries(u,false	);

        }
        if (param.type == 'hover') {
         	myChart3.setSeries(u,false);
        }
        else {
        }
        console.log(param);
    }
 
    function getConpany(param) {
        var mes = '【' + param.type + '】';
        if (typeof param.seriesIndex != 'undefined') {
        	nextPageByparm("companyName=洒大阿&currentPage=1",$("#company"),["companyName","companyAddress","companyAddress","dicCompCateName","managers","companyOwner"]);
        }
        if (param.type == 'hover') {
         	myChart3.setSeries(u,false);
        }
        else {
        }
        console.log(param);
    }
    
    myChart.on(ecConfig.EVENT.CLICK, getConpany);
    myChart3.on(ecConfig.EVENT.DBLCLICK, eConsole);
    	    myChart3.setOption(SIoptionA);
    	    
    	    

});
                 