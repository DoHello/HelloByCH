
var myChart05 = echarts.init(document.getElementById('chart-05'));// 指定图表的配置项和数据
option05 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a}: {c}件"
    },
    legend: {
        itemGap: 10,
        data: ['抢劫', '抢夺', '盗窃', '治安纠纷', '打架斗殴', '杀人', '恐爆案件', '传统诈骗', '网络诈骗', '其他案件']
    },
    grid: {
        top:'25%',
        left: '12%',
        right: '12%',
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
            data: ['抢劫', '抢夺', '盗窃', '治安纠纷', '打架斗殴', '杀人', '恐爆案件', '传统诈骗', '网络诈骗', '其他案件']
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
myChart05.setOption(option05);
