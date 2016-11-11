
require.config({
    paths: {
        echarts: chartPath //echarts.js的路径
    }
});
var myChart;
require(
        [
            'echarts',
            'echarts/chart/line',   // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
            'echarts/chart/bar'
        ],
        function (ec) {
            myChart = ec.init(document.getElementById('main'));
        }
);
var option = {
	    title : {
	        text: '数据质量问题分析趋势图',
	        x: "center"
	      
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['一般问题','重要问题','重大问题'],
	    	y:'bottom',
	    	x:'center'
	    },

	    calculable : true,
	    xAxis : [ { type : 'category', boundaryGap : false,
	            data : []//--------------------------option.xAxis[0].data
	        }
	    ],
	    yAxis : [{ type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'一般问题',
	            type:'line',
	            data:[],//---------------------option.series[0].data
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	        	name:'重要问题',
	        	type:'line',
	        	data:[],//---------option.series[1].data
	        	markPoint : {
	        		data : [
	        		        {type : 'max', name: '最大值'},
	        		        {type : 'min', name: '最小值'}
	        		        ]
	        	}
	        },
	        {
	            name:'重大问题',
	            type:'line',
	            data:[],//--------------option.series[2].data
	            markPoint : {
	                data : [
	                        {type : 'max', name: '最大值'},
	        		        {type : 'min', name: '最小值'}
	                ]
	            },
//	            markLine : {
//	                data : [
//	                    {type : 'average', name : '平均值'}
//	                ]
//	            }
	        }
	    ]
	};
function doSearch() {
	var startDate=$("#startDate").val();
	if(startDate==null||startDate==""){
		parent.layer.alert("请选择起始日期");
		return false;
	}
	var endDate=$("#endDate").val();
	if(endDate==null||endDate==""){
		parent.layer.alert("请选择结束日期");
		return false;
	}
	if(Date.parse(startDate.replace("/-/g","/"))>Date.parse(endDate.replace("/-/g","/"))){
		parent.layer.alert("结束日期小于开始日期");
		return false;
	}
	var dateFlag=$("#dateFlag").val();
	if(dateFlag==null||dateFlag==""||dateFlag==" "){
		parent.layer.alert("请选择问题类别！");
		return false;
		}
	//如果是相差大于十二个月，就提示startDate = "2016-01"
	var start= new Array(); 
	var end= new Array(); 
	start=startDate.split("-");
	end=endDate.split("-");
	result=(end[0]-start[0])*12+(end[1]-start[1]);
	if(result>12){
		parent.layer.alert("起始日期和结束日期请不要大于12个月");
		return false;
	}
	$.ajax({ 
		type: 'post', 
        url: context + '/quesController.do?method=dataQualityMap', 
        data: {startDate:startDate,endDate:endDate,dateFlag:dateFlag}, 
        success: function(data){
        	if(data.length==0){
        		parent.layer.alert("没有查询到数据！");   
        	}else{
        		option.xAxis[0].data=data[0];
        		option.series[0].data=data[1];
        		option.series[1].data=data[2];
        		option.series[2].data=data[3];
            	myChart.setOption(option); 
        	}
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
}
