   function getEchartsData(parmObject){
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
            data : ['00:00~01:59','02:00~03:59','04:00~05:59','06:00~07:59','08:00~9:59','10:00~11:59','12:00~13:59','14:00~15:59','16:00~17:59','18:00~19:59','20:00~21:59','22:00~23:59']
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
            data:parmObject.data.model
        }
    ]
};
parmObject.echarts11.setOption(option11);
   }

