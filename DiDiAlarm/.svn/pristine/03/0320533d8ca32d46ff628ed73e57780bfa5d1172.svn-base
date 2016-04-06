
function getEchartsData(parmObject){
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
            data : [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,82,83,84,85,86,87,88,89,90.91,92,93,94,95,96,97,98,99,100]
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
            data:parmObject.data.manNum
        },
        {
            name:'女性',
            type:'line',
            showSymbol: false,
            smooth:true,
            areaStyle: {normal: {opacity:'0.2'}},
            data:parmObject.data.womNum
        }
    ]
};
parmObject.echarts10.setOption(option10);


}
