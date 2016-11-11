<!-- <jsp:directive.include file="includes/top.jsp" />
		<div id="msg" class="success">
			<h2><spring:message code="screen.success.header" /></h2>
			<p><spring:message code="screen.success.success" /></p>
			<p><spring:message code="screen.success.security" /></p>
		</div>
<jsp:directive.include file="includes/bottom.jsp" />
 -->
	<script>
	/**
 * ���¶��򵽵�¼����
 *
 */
 <%
 String dirUrl = "";
 org.apache.shiro.subject.Subject subject = org.apache.shiro.SecurityUtils.getSubject();
 if(subject.isPermitted("priv_menu_dmc_02")) {//元数据
	 dirUrl = "/mds/mdsFrame.do?method=mdsHome";
	 dirUrl = "/ssm/smFrame.do?method=sysMgrMain";
 }else{
		if(subject.isPermitted("priv_menu_dmc_03")){//数据标准
			dirUrl = "/dsm";
		}else if(subject.isPermitted("priv_menu_dmc_04")) {//数据质量
			dirUrl = "/dqs";
		}else if(subject.isPermitted("priv_menu_dmc_05")) {//系统管理
			dirUrl = "/ssm/smFrame.do?method=sysMgrMain";
		}
		dirUrl = "/ssm/smFrame.do?method=sysMgrMain";
	}
 request.setAttribute("dirUrl", dirUrl);
 %>
function gotubip(){
	var url = "<%=request.getAttribute("dirUrl")%>" || "";
	//window.location.href="/ssm/first.do";
	window.location.href="/mds/mdsFrame.do?method=mdsHome";
	if(url != "") {
		window.location.href=url;
	}
	window.location.href="/../dap/";//门户集成的首页
	//window.location.href="/ssm/smFrame.do?method=sysMgrMain";
}
 gotubip();
	</script>