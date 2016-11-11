<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	var context = '<%=request.getContextPath()%>';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据管控应用门户-流程定义管理</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/heigthUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/activiti/process/instance/instanceManage.js"></script>
<!-- 调整表头样式 --->
<style type="text/css">
.defbtn{
width:60px;
height:28px;
border-radius:5px;
border:none;
-webkit-border-radius:5px;
color:#FFF;
background-color:red;
}

input[type=button]{
margin:0 10px 10px 0;
}

</style>

</head>

<body style="background-color: #F5F5F5;">
	<div class="gf_all">
			<div class="alert alert-info">
				<form name="searchForm" class="form-inline">
						<label for="businessNo_label">业务号：</label> <input type="text" id="businessNo" name="businessNo" class="input-small span3" placeholder="请输入业务号...">
					 <button class="button button-info ipt_button " type="button" id="seachInstanceButton"><i class="fa fa-search icolor"></i> 查 询</button>
				</form>
			</div>
			<table id="grid-table-instance"></table>
			<div id="grid-pager-instance"></div>
		</div>
	</div>
	
	
	<div class="modal hide fade" id="instanceModal" tabindex="-1" role="dialog" style='width:81%;left:32%;text-align: center;top:2%;border-radius:0px;'>
		<div class="modal-header" style="background-color: #0871C9;color:#FFFFFF"><button class="close" data-dismiss="modal">x</button>
			<h4 id="instanceModalLabel" style="display:block;margin-right:90%;">实例跟踪</h4>
		</div>
		<div class="modal-body" style="max-height:480px;height:480px">
	  	  	<div id="editDiv-instance" style="margin:auto;">
				
			</div>
		</div>
	</div>
	</div>
</body>
</html>	