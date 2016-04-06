

var myChart01 = echarts.init(document.getElementById('chart-01'));// 指定图表的配置项和数据
option01 = {
    color:['#b0e42f','#ffd937','#ff8b4a','#f54441','#f461b7','#a448e7','#a283f1','#0775ef','#4daef2','#2ac8eb'],
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c}件</br>比例：{d}%"
    },
    series: [
        {
            name:'案件总数',
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
                {value:20, name:'抢劫'},
                {value:30, name:'抢夺'},
                {value:40, name:'盗窃'},
                {value:20, name:'治安纠纷'},
                {value:20, name:'打架斗殴'},
                {value:20, name:'杀人'},
                {value:30, name:'恐爆案件'},
                {value:40, name:'传统诈骗'},
                {value:30, name:'网络诈骗'},
                {value:20, name:'其他案件'}
            ]
        }
    ]
};
myChart01.setOption(option01);


var myChart02 = echarts.init(document.getElementById('chart-02'));// 指定图表的配置项和数据
option02 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a}: {c}件"
    },
    legend: {
        itemGap: 50,
        data: ['抢劫', '抢夺', '盗窃', '治安纠纷', '打架斗殴', '杀人', '恐爆案件', '传统诈骗', '网络诈骗', '其他案件']
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
                    color: "#d1e1ee"
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
            data: ['抢劫', '抢夺', '盗窃', '治安纠纷', '打架斗殴', '杀人', '恐爆案件', '传统诈骗', '网络诈骗', '其他案件']
        }
    ],
    yAxis: [
        {
            type: 'value',
            axisLine:{
                lineStyle:{
                    width: 1,
                    color: "#d1e1ee"
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
            name: '抢劫',
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
            name: '抢夺',
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
            name: '盗窃',
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
            name: '治安纠纷',
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
            name: '打架斗殴',
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
            name: '杀人',
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
            name: '恐爆案件',
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
        },
        {
            name: '传统诈骗',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#0775ef',
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
            name: '网络诈骗',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#4daef2',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [25]
        },
        {
            name: '其他案件',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#2ac8eb',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: '{a}\n{c}件'
                    }

                }
            },
            data: [23]
        }
    ]

};
myChart02.setOption(option02);
