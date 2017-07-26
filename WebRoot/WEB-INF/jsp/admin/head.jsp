<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="layui-header">
	<ul class="layui-nav">
	  <li class="layui-nav-item">你好，管理员</li>
	</ul>
</div>