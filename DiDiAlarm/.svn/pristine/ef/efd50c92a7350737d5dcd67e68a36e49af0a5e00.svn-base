
var myChart03 = echarts.init(document.getElementById('chart-03'));// 指定图表的配置项和数据
option03 = {
    color:['#b0e42f','#ffd937','#ff8b4a','#f54441','#f461b7','#a448e7','#a283f1'],
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c}件</br>比例：{d}%"
    },
    series: [
        {
            name:'案件总数2',
            type:'pie',
            radius: ['50%', '80%'],
            avoidLabelOverlap: false,
            selectedMode: 'single',
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:20, name:'阳光派出所'},
                {value:30, name:'明珠派出所'},
                {value:40, name:'贵筑派出所'},
                {value:20, name:'溪北派出所'},
                {value:20, name:'清溪派出所'},
                {value:20, name:'松山派出所'},
                {value:30, name:'银晖派出所'}
            ]
        }
    ]
};
myChart03.setOption(option03);


var myChart04 = echarts.init(document.getElementById('chart-04'));// 指定图表的配置项和数据
option04 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a}: {c}件"
    },
    legend: {
        itemGap: 50,
        data: ['阳光派出所', '明珠派出所', '贵筑派出所', '溪北派出所', '清溪派出所', '松山派出所', '银晖派出所']
    },
    grid: {
        top:'20%',
        left: '43%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
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
                show: false
            },
            data: ['阳光派出所', '明珠派出所', '贵筑派出所', '溪北派出所', '清溪派出所', '松山派出所', '银晖派出所']
        }
    ],
    yAxis: [
        {
            type: 'value',
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
                lineStyle:{
                    type: 'dashed',
                    color: '#d1e1ee'
                }
            }

        }
    ],
    series: [
        {
            name: '阳光派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    barBorderRadius: 10,
                    color: '#b0e42f',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }
                }
            },
            data: [12]
        },
        {
            name: '明珠派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#ffd937',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [21]
        },
        {
            name: '贵筑派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#ff8b4a',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [10]
        },
        {
            name: '溪北派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#f54441',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [4]
        },
        {
            name: '清溪派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#f461b7',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [12]
        },
        {
            name: '松山派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#a448e7',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [5]
        },
        {
            name: '银晖派出所',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#a283f1',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [6]
        }
    ]

};
myChart04.setOption(option04);


