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
    
    <title>后台管理-权限管理</title>
    
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
		<div class="title">角色权限列表</div>
		<div>
			<a href="javascript:void(0)" class="layui-btn layui-btn-small" onclick="addPermission(this)">新增权限</a>
		</div>
		<table class="layui-table">
			<colgroup>
		    	<col width="5%">
		        <col width="40%">
		        <col width="40%">
		        <col width="15%">
		    </colgroup>
		    <thead>
		    	<tr>
			        <th>ID</th>
			        <th>角色</th>
			        <th>URL</th>
			        <th>操作</th>
		        </tr> 
		    </thead>
		    <tbody>
		    	<c:forEach items="${roles }" var="role">
		    		<c:forEach items="${role.permissions }" var="permission">
				    	 <tr>
					     	<td>${permission.id }</td>
					      	<td>${role.name }</td>
					      	<td>${permission.url }</td>
					      	<td>
					      		<a href="javascript:void(0)" class="layui-btn layui-btn-mini layui-btn-normal" id="e-${permission.id }" onclick="editPermission(this)">编辑</a>
					      		<a href="javascript:void(0)" class="layui-btn layui-btn-mini layui-btn-normal" id="d-${permission.id }" onclick="deletePermission(this)">删除</a>
					      	</td>
					    </tr>
				    </c:forEach>
			    </c:forEach>
		    </tbody>
		</table>
	</div>
  </div>
  
  	<!-- 编辑权限 -->
  	<div id="edit-permission" style="display: none; padding-right: 40px; padding-top: 20px;">
  		<form class="layui-form" action="admin/updatePermission" method="post">
		    <div class="layui-form-item layui-form-text">
		        <label class="layui-form-label">URL设置</label>
		        <div class="layui-input-block">
			    	<input type="text" name="url" id="permission-url" required class="layui-input">
			    </div>
		    </div>
		    <input type="hidden" name="id" id="permission-id" value="">
		    <div class="layui-form-item">
	            <div class="layui-input-block">
	            	<button class="layui-btn" lay-submit="submit">更新</button>
      			</div>
    		</div>
		</form>
  	</div>
  	
  	<!-- 新增权限 -->
  	<div id="add-permission" style="display: none; padding-right: 40px; padding-top: 20px;">
  		<form class="layui-form" action="admin/addPermission" method="post">
		    <div class="layui-form-item layui-form-text">
		        <label class="layui-form-label">选择角色</label>
		        <div class="layui-input-block">
			    	<select name="rid">
			    		<c:forEach items="${roles }" var="role">
				       		<option value="${role.id }">${role.name }</option>
				        </c:forEach>
				    </select>
			    </div>
		    </div>
		    <div class="layui-form-item">
				<label class="layui-form-label">允许的URL</label>
			    <div class="layui-input-block">
			    	<input type="text" name="url" required class="layui-input">
			    </div>
		    </div>
		    <div class="layui-form-item">
	            <div class="layui-input-block">
	            	<button class="layui-btn" lay-submit="submit">添加</button>
      			</div>
    		</div>
		</form>
  	</div>
  </body>
  <script type="text/javascript">
  	layui.use(['element','form'], function(){
	  var element = layui.element(); 
	  var form = layui.form();
	});
  </script>
  <script type="text/javascript">
  	function editPermission(obj){
  		$('#permission-url').text('');
  		var permissionId = obj.id.split('-')[1];
  		$('#permission-id').attr('value', permissionId);
  		layer.open({
				type: 1,
				skin: 'layui-layer-rim', 
			  	area: ['400px', '180px'], 
			  	content: $('#edit-permission')
			});
  	}
  
  	function addPermission(obj){
  		layer.open({
				type: 1,
				skin: 'layui-layer-rim', 
			  	area: ['400px', '300px'], 
			  	content: $('#add-permission')
			});
  	}
  </script>
</html>
	