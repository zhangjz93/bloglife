<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="layui-header header header-index">
	<div class="layui-main">
		<div class="logo"><i class="fa fa-pencil"></i>&nbsp;BlogLife</div>
		<ul class="layui-nav" style="margin-top: 0px;">
		<c:choose>
			<c:when test="${sessionScope.user == null }">
				<li class="layui-nav-item"><a href="index">首页</a></li>
				<li class="layui-nav-item"><a href="login">登录</a></li>
			</c:when>
			<c:otherwise>
				<li class="layui-nav-item"><i class="fa fa-user"></i>&nbsp;${sessionScope.user.name }</li>
				<li class="layui-nav-item"><a href="index">首页</a></li>
			    <li class="layui-nav-item">
				    <a href="javascript:;">通知(${unreadNum })</a>
				    <dl class="layui-nav-child">
				    	<dd><a href="person/notice?page=1">回复(${unreadNum })</a></dd>
				    </dl>
			  	</li>
			  	<li class="layui-nav-item"><a href="person/info/${sessionScope.user.id }">个人中心</a></li>
			 		<li class="layui-nav-item"><a href="exit">登出 </a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>	 
</div>