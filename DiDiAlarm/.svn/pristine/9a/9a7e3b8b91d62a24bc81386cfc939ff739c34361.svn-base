<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- am-animation-slide-left -->
<% 
String css = "";
if (request.getRequestURI().equals("/dy_service/jsp/system/home/index.jsp")) css = "";

%>
<div class="admin-sidebar am-offcanvas <%=css %>" id="admin-offcanvas" style='display:${model.direct==1?'none':'block'}'>
<div class="am-offcanvas-bar admin-offcanvas-bar">
	<nav class="menu" data-toggle="menu" style="width: 100%">
		<ul class="nav nav-primary">
			<li style="background-color:#EAEDF1; color: #000; display:${model.hasphar==1?'block':'none'}"><a href="javascript:;"><i class="icon-th-list"></i>DiDi管理子系统</a></li>
			
			<c:forEach items="${model.MenuList.childList}" var="menu">
				<c:choose>
					<c:when test="${ (menu.obj.moduleID == '300') }">
					
						<c:forEach items="${menu.childList}" var="sub">
							<c:choose>
								<c:when test="${sub.obj.isMenu eq 'y'}">
									<c:choose>
										<c:when test="${ (sub.obj.moduleID == model.chooseParentID) }">
											<li class="active nav-parent show">
										</c:when>
										<c:otherwise>
											<li class="nav-parent">
										</c:otherwise>
									</c:choose>
									<a href="#">
										<i class="icon-folder-open">${sub.obj.moduleName }</i>
									</a>
									
									<ul class="nav">
										<c:forEach items="${sub.childList}" var="subsub">
											<c:choose>
												<c:when test="${subsub.obj.isMenu eq 'n'}">
													<c:choose>
														<c:when test="${ (subsub.obj.moduleID == model.chooseModuleID) }">
															<li class="active"><a href="${fn:trim(subsub.obj.url)}&parentID=${subsub.obj.parentID }&moduleID=${subsub.obj.moduleID }">${subsub.obj.moduleName }</a></li>
														</c:when>
														<c:otherwise>
															<li class=""><a href="${fn:trim(subsub.obj.url)}&parentID=${subsub.obj.parentID }&moduleID=${subsub.obj.moduleID }">${subsub.obj.moduleName }</a></li>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<li><a href="javascript:void(0);">${subsub.obj.moduleName }</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</ul>
									</li>
								</c:when>
							</c:choose>
						</c:forEach>
						
					</c:when>
				</c:choose>
			</c:forEach>
			
			<li style="background-color:#EAEDF1; color: #000; display:${model.haschain==1?'block':'none'}"><a href="javascript:;"><i class="icon-th-list"></i>管理子系统</a></li>
			<c:forEach items="${model.MenuList.childList}" var="menu">
				<c:choose>
					<c:when test="${ (menu.obj.moduleID == '200') }">
					
						<c:forEach items="${menu.childList}" var="sub">
							<c:choose>
								<c:when test="${sub.obj.isMenu eq 'y'}">
									<c:choose>
										<c:when test="${ (sub.obj.moduleID == model.chooseParentID) }">
											<li class="active nav-parent show">
										</c:when>
										<c:otherwise>
											<li class="nav-parent">
										</c:otherwise>
									</c:choose>
									<a href="#">
										<i class="icon-folder-open">${sub.obj.moduleName }</i>
									</a>
									
									<ul class="nav">
										<c:forEach items="${sub.childList}" var="subsub">
											<c:choose>
												<c:when test="${subsub.obj.isMenu eq 'n'}">
													<c:choose>
														<c:when test="${ (subsub.obj.moduleID == model.chooseModuleID) }">
															<li class="active"><a href="${fn:trim(subsub.obj.url)}&parentID=${subsub.obj.parentID }&moduleID=${subsub.obj.moduleID }">${subsub.obj.moduleName }</a></li>
														</c:when>
														<c:otherwise>
															<li class=""><a href="${fn:trim(subsub.obj.url)}&parentID=${subsub.obj.parentID }&moduleID=${subsub.obj.moduleID }">${subsub.obj.moduleName }</a></li>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<li><a href="javascript:void(0);">${subsub.obj.moduleName }</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</ul>
									</li>
								</c:when>
							</c:choose>
						</c:forEach>
						
					</c:when>
				</c:choose>
			</c:forEach>
			
			<c:forEach items="${model.MenuList.childList}" var="menu">
				<c:choose>
					<c:when test="${ (menu.obj.moduleID == '400') }">
					
						<c:forEach items="${menu.childList}" var="sub">
							<c:choose>
								<c:when test="${sub.obj.isMenu eq 'y'}">
									<c:choose>
										<c:when test="${ (sub.obj.moduleID == model.chooseParentID) }">
											<li class="active nav-parent show">
										</c:when>
										<c:otherwise>
											<li class="nav-parent">
										</c:otherwise>
									</c:choose>
									<a href="#">
										<i class="icon-folder-open">${sub.obj.moduleName }</i>
									</a>
									
									<ul class="nav">
										<c:forEach items="${sub.childList}" var="subsub">
											<c:choose>
												<c:when test="${subsub.obj.isMenu eq 'n'}">
													<c:choose>
														<c:when test="${ (subsub.obj.moduleID == model.chooseModuleID) }">
															<li class="active"><a href="${fn:trim(subsub.obj.url)}&parentID=${subsub.obj.parentID }&moduleID=${subsub.obj.moduleID }">${subsub.obj.moduleName }</a></li>
														</c:when>
														<c:otherwise>
															<li class=""><a href="${fn:trim(subsub.obj.url)}&parentID=${subsub.obj.parentID }&moduleID=${subsub.obj.moduleID }">${subsub.obj.moduleName }</a></li>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<li><a href="javascript:void(0);">${subsub.obj.moduleName }</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</ul>
									</li>
								</c:when>
							</c:choose>
						</c:forEach>
						
					</c:when>
				</c:choose>
			</c:forEach>
		</ul>
	</nav>
	
	
	</div>
</div>