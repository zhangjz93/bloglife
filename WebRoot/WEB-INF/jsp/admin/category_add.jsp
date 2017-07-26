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
    
    <title>后台管理-类别添加</title>
    
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
	<script type="text/javascript" src="js/validation.js"></script>

  </head>
  
  <body>
    <div class="layui-layout layui-layout-admin">
    	<jsp:include page="head.jsp"></jsp:include>
	  	<jsp:include page="left.jsp"></jsp:include>
		<div class="layui-body layui-tab-content">
			<div class="title">类别添加</div>
			<div class="category-add-block">
				<form class="layui-form" action="admin/addCategory" method="post" onsubmit="return categoryValidate(this)">
				    <div class="layui-form-item">
				    	<label class="layui-form-label">类别名称</label>
				        <div class="layui-input-block">
				            <input type="text" name="name" id="cname" class="layui-input" maxlength="10">
				        </div>
				    </div>
				    <div class="layui-form-item">
				    	<label class="layui-form-label">排序</label>
				        <div class="layui-input-block">
				            <input type="text" name="sortNum" id="sortNum" class="layui-input" maxlength="3">
				        </div>
				    </div>
		    		<div class="layui-form-item layui-form-text">
				        <label class="layui-form-label">类别介绍</label>
				        <div class="layui-input-block">
					    	<textarea name="intro" id="intro" class="layui-textarea"></textarea>
					    </div>
				    </div>
				    <div class="layui-form-item">
				        <label class="layui-form-label">图标上传</label>
				        <div class="layui-input-block">
				            <input type="file" name="icon" class="layui-upload-file" lay-title="添加图标">
				            <span id="iconpath" style="color: red;"></span>
				        </div>
				    </div>
				    <input type="hidden" name="photo" id="icon" value="">
				    <div class="layui-form-item">
				        <div class="layui-input-block">
				            <button class="layui-btn" type="submit">添加</button>
				            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		       			</div>
	    		   </div>
				</form>	
			</div>
		</div>
    </div>
  </body>
  <script type="text/javascript">
  	layui.use('element', function(){
	  var element = layui.element();
	});
  </script>
  <script type="text/javascript">
  	layui.use('upload', function(){
		layui.upload({
	    	url: '${pageContext.request.contextPath}/admin/uploadIcon',
	    	success: function(res){ 
	      		if(res.error == 0){
	      			$('#icon').attr('value', res.url);
	      			$('#iconpath').text('上传成功！图标保存在：' + res.url);
	      		}else{
	      			layer.msg(res.message);
	      		}
	    	}
	  	});
	});
  </script>
</html>
