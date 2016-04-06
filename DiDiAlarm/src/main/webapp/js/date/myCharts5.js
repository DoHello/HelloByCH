
var myChart10 = echarts.init(document.getElementById('chart-10'));// 指定图表的配置项和数据
option10 = {
    color:['#4daef2', '#f461b7'],
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['男性','女性']
    },
    grid: {
        top:'10%',
        left: '10%',
        right: '10%',
        bottom: '20%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            name:'年龄',
            boundaryGap : false,
            axisLine:{
                lineStyle:{
                    width: 1,
                    color: "#19649a"
                }
            },
            axisTick:{
                show: false
            },
            splitLine:{
                show: false
            },
            axisLabel:{
                textStyle:{
                    color: "#19649a"
                }
            },
            data : [0,10,20,30,40,50,60,70,80,90,100]
        }
    ],
    yAxis : [
        {
            type: 'value',
            axisLine:{
                lineStyle:{
                    width: 1,
                    color: "#19649a"
                }
            },
            axisLabel:{
                textStyle:{
                    color: "#19649a"
                }
            },
            axisTick:{
                show: false
            },
            splitLine:{
                lineStyle:{
                    type: 'dashed',
                    color: '#d1e1ee'
                }
            }

        }
    ],
    series : [
        {
            name:'男性',
            type:'line',
            showSymbol: false,
            smooth:true,
            areaStyle: {normal: {opacity:'0.2'}},
            data:[220, 182, 191, 234, 290, 330, 310,134, 90, 230, 210]
        },
        {
            name:'女性',
            type:'line',
            showSymbol: false,
            smooth:true,
            areaStyle: {normal: {opacity:'0.2'}},
            data:[120, 132, 101, 134, 90, 230, 210, 182, 191, 234, 290,]
        }
    ]
};
myChart10.setOption(option10);


