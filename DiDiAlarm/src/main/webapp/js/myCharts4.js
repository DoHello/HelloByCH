
 function getEchartsData(parmObject){
option06 = {
    color:['#a2cf31','#3398e1'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    series: [
        {
            name:'报警/接警统计',
            type:'pie',
            radius: ['40%', '50%'],
            center:['40%', '50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,

                    textStyle: {
                        fontSize: '20',
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
                {value:parmObject.data.receive.value, name:'接警',label:{emphasis:{formatter:parmObject.data.receive.formatter }}},//接警
                {value:parmObject.data.call.value,    name:'报警',label:{emphasis:{formatter:parmObject.data.call.formatter }}}//报警
            ]
        }
    ]
};parmObject.echarts06.setOption(option06);



option07 = {
    color:['#ffce00','#a2cf31'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    series: [
        {
            name:'报警/接警统计',
            type:'pie',
            radius: ['40%', '50%'],
            center:['40%', '50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,

                    textStyle: {
                        fontSize: '20',
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
                {value:parmObject.data.cheack.value, name:'备案',label:{emphasis:{formatter: parmObject.data.cheack.formatter}}},//备案
                {value:parmObject.data.receive.value, name:'接警',label:{emphasis:{formatter: parmObject.data.receive.formatter}}}//接警
            ]
        }
    ]
};
parmObject.echarts07.setOption(option07);




option08 = {
    color:['#fe8b4a','#ffce00'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    series: [
        {
            name:'报警/接警统计',
            type:'pie',
            radius: ['40%', '50%'],
            center:['40%', '50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,

                    textStyle: {
                        fontSize: '20',
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
                {value:parmObject.data.docketCase.value, name:'立案',label:{emphasis:{formatter: parmObject.data.docketCase.formatter}}},//立案
                {value:parmObject.data.cheack.value, name:'备案',label:{emphasis:{formatter: parmObject.data.cheack.formatter}}}//备案
            ]
        }
    ]
};
parmObject.echarts08.setOption(option08);


option09 = {
    color:['#f54441','#fe8b4a'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    series: [
        {
            name:'报警/接警统计',
            type:'pie',
            radius: ['40%', '50%'],
            center:['40%', '50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,

                    textStyle: {
                        fontSize: '20',
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
                {value:parmObject.data.windCase.value, name:'结案',label:{emphasis:{formatter: parmObject.data.windCase.formatter}}},//结案
                {value:parmObject.data.docketCase.value, name:'立案',label:{emphasis:{formatter: parmObject.data.docketCase.formatter}}}//立案
            ]
        }
    ]
};
parmObject.echarts09.setOption(option09);}
