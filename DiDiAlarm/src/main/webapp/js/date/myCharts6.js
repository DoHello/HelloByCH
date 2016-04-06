
var myChart11 = echarts.init(document.getElementById('chart-11'));// 指定图表的配置项和数据
option11 = {
    color:['#ffd937'],
    tooltip : {
        trigger: 'axis',
        formatter: "案件：{c}件</br>时间：{b}"
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
            name:'时间',
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
            data : ['00:00','02:00','04:00','06:00','08:00','10:00','12:00','14:00','16:00','18:00','20:00','22:00','24:00']
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
            name:'案件',
            type:'line',
            showSymbol: false,
            smooth:true,
            areaStyle: {normal: {opacity:'0.2'}},
            data:[1, 182, 191, 234, 290, 330, 310,134, 90, 230,100,57, 1]
        }
    ]
};
myChart11.setOption(option11);


