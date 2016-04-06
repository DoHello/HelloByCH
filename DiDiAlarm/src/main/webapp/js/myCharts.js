

// 指定图表的配置项和数据
function getEchartsData(parmObject){
	
option01 = {
		
    //color:['#b0e42f','#ffd937','#ff8b4a','#f54441','#f461b7','#a448e7','#a283f1','#0775ef','#4daef2','#2ac8eb'],
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
            data:parmObject.data.casen
        }
    ]
};
parmObject.echarts01.setOption(option01);



option02 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a}: {c}件"
    },
    legend: {
        itemGap: 50,
        data: parmObject.data.caseType
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
            data: parmObject.data.caseType
        }
    ],
    yAxis: [
        {
            type: 'value',
            axisLine:{
                lineStyle:{
                    width: 1,
                  //  color: "#d1e1ee"
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
    series: parmObject.data.caseNum

        
};
parmObject.echarts02.setOption(option02);
}
