
   function getEchartsData(parmObject){
/*alert(JSON.stringify(parmObject.data.casen))
alert(parmObject.data.police)
alert(parmObject.data.caseNum)*/
    options = {
 
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c}件</br>比例：{d}%"
    },
    series: [
        {
            name:parmObject.name,
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
    parmObject.echarts.setOption(options);

option04 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a}: {c}件"
    },
    legend: {
        itemGap: 50,
       
        data: parmObject.data.police//['阳光派出所', '明珠派出所', '贵筑派出所', '溪北派出所', '清溪派出所', '松山派出所', '银晖派出所']
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
          data: parmObject.data.police//['阳光派出所', '明珠派出所', '贵筑派出所', '溪北派出所', '清溪派出所', '松山派出所', '银晖派出所']
        }
    ],
    yAxis: [
        {
            type: 'value',
            axisLine:{
                lineStyle:{
                    width: 1,
                  
                }
            },
            axisTick:{
                show: false
            },
            splitLine:{
                lineStyle:{
                    type: 'dashed',
                  
                }
            }

        }
    ],
    series:parmObject.data.caseNum
      
};
parmObject.echarts1.setOption(option04);
}


