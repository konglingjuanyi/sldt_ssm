<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/sidebar-menu.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>

<!-- 
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/accordion-menu.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/accordion-menu.js"></script>
 -->


<div class="sidebar-menu">
		<a href="#orgUserMenu" class="nav-header menu-first" data-toggle="collapse"><i class="fa fa-group icolor"></i> 用户部门</a>
		<ul id="orgUserMenu" class="nav nav-list menu-second in collapse" style="height: auto;">
			<li><a href="javascript:void(0)"><i class="fa fa-cog icolor"></i> 机构管理</a></li>
			<li><a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/user/userList.jsp"><i class="fa fa-user icolor"></i> 人员管理</a></li>
		</ul>
		<a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/role/roleList.jsp" class="nav-header menu-first" data-toggle="collapse"><i class="fa fa-male icolor"></i> 角色管理</a>
		<a href="#privlgMgrMenu" class="nav-header menu-first" data-toggle="collapse"><i class="fa fa-magic icolor"></i> 权限管理</a>
		<ul id="privlgMgrMenu" class="nav nav-list menu-second in collapse" style="height: auto;">
			<li><a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/privCate/privCate.jsp"><i class="fa fa-list-ul icolor"></i> 权限类别管理</a></li>
			<li><a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/privlg/privlgList.jsp"><i class="fa fa-empire icolor"></i> 权限管理</a></li>
		</ul>
		<a href="#rescMgrMenu" class="nav-header menu-first" data-toggle="collapse"><i class="fa fa-list-alt icolor"></i> 资源管理</a>
		<ul id="rescMgrMenu" class="nav nav-list menu-second in collapse" style="height: auto;">
			<li><a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/menu/menuList.jsp"><i class="fa fa-th-large icolor"></i> 菜单管理</a></li>
			<li><a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/btn/btnList.jsp"><i class="fa fa-btc icolor"></i> 按钮管理</a></li>
		</ul>
		
		
		<c:if test="${activeUser.menudataInfoList!=null }">
		    <c:set var="index" value="0" />		
				<c:forEach items="${activeUser.menudataInfoList}" var="menu"  varStatus="status" >
				<c:if test="${menu.tSmMenu !=null}">
			
                 <a href="#a${status.count}" class="nav-header menu-first" data-toggle="collapse"><i class="fa fa-list-alt icolor"></i> ${menu.tSmMenu.menuName }</a>
                 </c:if>
					
					  <c:if test="${menu.menus!=null }">
					   <ul id="a${status.count}" >
					     <c:forEach items="${menu.menus }" var="menu3" varStatus="status2">
					                  
					                    <a href="#b${status2.count}" url="<%=request.getContextPath()%>${menu3.tSmMenu.menuUrl }" data-toggle="collapse"><i class="fa fa-btc icolor"></i> ${menu3.tSmMenu.menuName }</a>        
					                        <ul id="b${status2.count}">
					                          <c:if test="${menu3.menus!=null }">
					                          
					                              <c:forEach items="${menu3.menus }" var="menu4">
					                                 
					                                     <li><a href="javascript:void(0)" url="<%=request.getContextPath()%>${menu4.tSmMenu.menuUrl }"><i class="fa fa-btc icolor"></i> ${menu4.tSmMenu.menuName }</a></li> 
					                                
					                              </c:forEach> 
					                          
					                         </c:if> 
					                          </ul>  
					     </c:forEach>
					     </ul>   
					  </c:if>
					
				</c:forEach>
				
		
		</c:if>
		
		
		<a href="javascript:void(0)" class="nav-header menu-first" data-toggle="collapse"><i class="fa fa-file-image-o icolor"></i> 其他...</a>
</div>

<!-- 

		<div id="jquery-accordion-menu" class="jquery-accordion-menu blue">
				<div class="jquery-accordion-menu-header">
					<i class="fa fa-list-ul"></i> &nbsp;&nbsp;2级菜单
				</div>
				<ul>
					<li class="active">
						<a href="javascript:void(0)" class="">
							<i class="fa fa-group"></i> 用户部门
							<span class="submenu-indicator">+</span>
						</a>
						<ul class="submenu" style="display: none;">
							<li>
								<a href="javascript:void(0)">
									<i class="fa fa-cog"></i> 部门机构管理
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/user/userList.jsp">
									<i class="fa fa-user"></i> 人员管理
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/role/roleList.jsp">
							<i class="fa fa-male"></i> 角色管理
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" class="">
							<i class="fa fa-magic"></i> 权限管理
						<span class="submenu-indicator">+</span></a>
						<ul class="submenu" style="display: none;">
							<li>
								<a href="javascript:void(0)">
									<i class="fa fa-list-ul"></i> 权限类别登记
								</a>
							</li>
							<li>
								<a href="javascript:void(0)"  url="<%=request.getContextPath()%>/page/sm/privlg/privlgList.jsp">
									<i class="fa fa-empire"></i> 权限管理
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="javascript:void(0)" class="">
							<i class="fa fa-list-alt"></i> 资源管理
						<span class="submenu-indicator">+</span></a>
						<ul class="submenu" style="display: none;">
							<li>
								<a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/menu/menuList.jsp">
									<i class="fa fa-th-large"></i> 菜单管理
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" url="<%=request.getContextPath()%>/page/sm/btn/btnList.jsp">
									<i class="fa fa-btc"></i> 按钮管理
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="javascript:void(0)">
							<i class="fa fa-file-image-o"></i> 其他...
						</a>
						<span class="jquery-accordion-menu-label">
							2
						</span>
					</li>
					
				</ul>
				
			</div>
			

 -->