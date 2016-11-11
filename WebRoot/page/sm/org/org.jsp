<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/icheck/skins/all.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryLayout/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/heigthUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/icheck/icheck.min.js"></script>

</head>
<body>
    <DIV class="ui-layout-west">
    <!-- <button type="button" onclick="TB()" class="button button-info"><i class="fa fa-search icolor"></i>同步机构</button> -->
    <div id="orgTree" class="ztree"></div>
    </DIV>
<div id="ui-layout-center" class="ui-layout-center">
    <div id="managerContent1">
    <!-- <div class="alert" >
        <form id="searchForm">
            <div class="control-group controls-row">
                <label class="span2">机构编号：</label> <input class="span4" type="text" id="orgId" placeholder="请输入机构编号..."> 
                <label class="span2">机构名称：</label> <input class="span4" type="text" id="orgName" placeholder="请输入机构名称...">
            </div>
            <div class="control-group" align="center">
                <button type="button" onclick="doSearch()" class="button button-info"><i class="fa fa-search icolor"></i> 查询</button>
                <button type="reset" class="button button-info"><i class="fa fa-undo icolor"></i> 重置</button>
            </div>
        </form>
    </div> -->
        <!-- <table id="grid-table" class="grid-table"></table> -->
        <!-- <div id="grid-pager" class="grid-pager"></div> -->
        <div id="if_userId_div" class="control-group">
                      <span>机构编号：</span>
                      <input class="input-xlarge" readonly="readonly" id ="orgId" name="orgId" value="" type="text" placeholder="机构编号">
                      <br>
                      <span>父级机构：</span>
                    <input class="input-xlarge" readonly="readonly" id ="porgId" name="porgId" value="" type="text" placeholder="所属父级机构编号">
                    <br>
                    <span>机构全称：</span>
                    <input class="input-xlarge" readonly="readonly" id ="orgName" readonly="readonly" name="orgName" value="" type="text" placeholder="机构详细名称">
                    <br>
                    <span>邮政编码：</span>
                    <input class="input-xlarge" readonly="readonly" id ="zpCd" name="zipCd" value="" type="text" placeholder="机构邮政编码">
                    <br>
                    <span>机构状态：</span>
                    <input class="input-xlarge" readonly="readonly" id ="orgStat" name="orgStat" value="" type="text" placeholder="机构状态">
                    <br>
                    <span>机构级别：</span>
                    <input class="input-xlarge" readonly="readonly" id ="orgLevel" name="orgLevel" value="" type="text" placeholder="机构级别">
                    <br>
                      <span>机构地址：</span>
                    <!-- <input class="input-xlarge" readonly="readonly" id ="if_orgNum" name="orgNum" value="" type="text" placeholder="机构地址"> -->
                    <textarea class="input-xlarge" readonly="readonly" id ="meno" name="meno" rows="5" cols="30" placeholder="机构地址"></textarea>
                    <br>
                    
                    <!-- <div class="form-group">
                    <span>数据之家：</span>
                    <label class="radio-inline">
                         <input type="radio" id="add-square-1" name="categoryId" value="01" checked> 问答版块
                    </label>
                    <label class="radio-inline">  
                         <input type="radio" id="add-square-2" name="categoryId" value="02"> 经验交流版块
                    </label>
                    </div> -->
                    
                    <div class="form-group" id ="questionFrom" >
					      <span>数据之家：</span>
				          <span class="radio-inline">
				              <input type="radio" id="add-square-1" name="categoryId" value="1" checked> 显示
				          </span>
				          <span class="radio-inline">  
				              <input type="radio" id="add-square-2" name="categoryId" value="0"> 不显示
				          </span>
					  </div>
                    <div class="col-md-12 form-inline">
						  <p> 
						   <button type="button" class="btn btn-primary replybtn" onclick="javascript:addSubmit()"><i class="fa fa-save"></i> 保存</button>
						  </p>
					  </div>
                   </div>
           <div>
           <!-- <table id="grid-table"></table>
            <div id="grid-pager"></div> -->
        </div>
    </div>
    </div>
</body>
<script type="text/javascript">
    var context = "<%=request.getContextPath()%>";
    var orgIdFor = '';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/org/js/org.js"></script>
</html>