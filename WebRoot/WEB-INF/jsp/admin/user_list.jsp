<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台管理-用户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" type="text/css" href="css/admin.css">
	
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>
	<script type="text/javascript" src="js/admin_plugin.js"></script>

  </head>
  
  <body>
    <div class="layui-layout layui-layout-admin">
  	<jsp:include page="head.jsp"></jsp:include>
  	<jsp:include page="left.jsp"></jsp:include>
  	<script type="text/javascript">
  		var baseUrl = '${pageContext.request.contextPath}';
  	</script>
	<div class="layui-body layui-tab-content">
		<div class="title">用户列表</div>
		<div class="search-block">
			<form class="layui-form" action="admin/userList" method="get">
				<div class="layui-inline">
				    <div class="layui-input-inline" style="width: 150px;">
				      <input type="text" name="search" placeholder="输入用户名搜索" autocomplete="off" class="layui-input">
				    </div>
				    <input type="hidden" name="page" value="1">
				    <div class="layui-inline">
				    	<button class="layui-btn">搜索</button>
				    </div>
				</div>
			</form>
		</div>
		<table class="layui-table">
			<colgroup>
		    	<col width="5%">
		        <col width="45%">
		        <col width="15%">
		        <col width="15%">
		        <col width="20%">
		    </colgroup>
		    <thead>
		    	<tr>
			        <th>ID</th>
			        <th>用户名</th>
			        <th>注册时间</th>
			        <th>用户权限</th>
			        <th>操作</th>
		        </tr> 
		    </thead>
		    <tbody>
		    	<c:forEach items="${users }" var="user">
			    	 <tr>
				     	<td>${user.id }</td>
				      	<td>${user.name }</td>
				      	<td>${user.registTime }</td>
				      	<td>
				      		<c:forEach items="${user.roles }" var="role">
				      			${role.name } |
				      		</c:forEach>
				      	</td>
				      	<td>
				      		<a href="javascript:void(0)" class="layui-btn layui-btn-mini layui-btn-normal" id="d-${user.id }" onclick="deleteUser(this)">删除</a>
				      		<a href="javascript:void(0)" class="layui-btn layui-btn-mini layui-btn-normal" id="s-${user.id }" onclick="setRole(this,'admin',false)">设为管理</a>
				      		<a href="javascript:void(0)" class="layui-btn layui-btn-mini layui-btn-normal" id="c-${user.id }" onclick="setRole(this,'admin',true)">取消管理</a>
				      	</td>
				    </tr>
			    </c:forEach>
		    </tbody>
		</table>
		&nbsp;共${userNum }条记录，当前第${currentPage }页
		<div id="page" class="page"></div>
	</div>
  </div>
  
  </body>
  <script type="text/javascript">
  	layui.use('element', function(){
	  var element = layui.element(); 
	});
  </script>
  <script type="text/javascript">
  	layui.use(['laypage'], function(){
  		var laypage = layui.laypage;
  		laypage({
			cont: 'page',
			curr: ${currentPage},
		    pages: ${totalPage},
		    groups: 8,
		    jump: function(obj, first){
				var curr = obj.curr;
				if(!first)
					location.href = '${pageContext.request.contextPath}/admin/userList?' + 'page=' + curr + '&search=' + '${search}';
			}
		});
	});
  </script>
</html>
	