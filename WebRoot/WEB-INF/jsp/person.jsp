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
    
    <title>${u.name }-个人信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="css/960.css">
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css" media="all">
	
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>
	<script type="text/javascript" src="js/validation.js"></script>
  </head>
  
  <body>
  	<script type="text/javascript">
  		var baseUrl = '${pageContext.request.contextPath}';
  	</script>
    <jsp:include page="template/head.jsp"></jsp:include>
    <div class="container_12">
    	<div class="grid_4 alpha">
    		<jsp:include page="template/person_left.jsp"></jsp:include>
    	</div>
    	<div class="grid_8 omega">
    	<div class="info-block block">
    		<div class="title">头像</div>
			<div class="info-items" style="text-align: center;">
				<div>
					<img id="photo" src="${u.photo }" width="75" height="75">
				</div>
				<c:if test="${sessionScope.user!=null && sessionScope.user.id==u.id}">
					<div class="upload-button">
						<input type="file" name="photo" class="layui-upload-file" id="test">
					</div>
				</c:if>
			</div>
    		<div style="text-align: right;">
    			<c:if test="${sessionScope.user!=null && sessionScope.user.id==u.id}">
    				<a href="javascript: void(0)" onclick="editInfo()"><i class="layui-icon">&#xe642;</i>编辑个人资料</a>
    			</c:if>
    		</div>
    		<div class="title">职业</div>
    		<div class="info-items" id="current-career">
    			<c:out value="${u.career }"></c:out>
    		</div>
    		<div class="title">兴趣爱好</div>
			<div class="info-items" id="current-hobby">
    			<c:out value="${u.hobby }"></c:out>
    		</div>
    		<div class="title">擅长领域</div>
			<div class="info-items" id="current-skill">
    			<c:out value="${u.skill }"></c:out>
    		</div>
    		<div class="title">个人说明</div>
			<div class="info-items" id="current-intro">
    			<c:out value="${u.intro }"></c:out>
    		</div>
    	</div>
    	</div>
    </div>
    
    <!-- 隐藏域 -->
    <div id="edit-info" style="display: none; padding-right: 40px; padding-top: 20px;">
   	   <div class="layui-form-item">
			<label class="layui-form-label">职业</label>
		    <div class="layui-input-block">
		    	<input type="text" id="career" autocomplete="off" class="layui-input" maxlength="20">
		    </div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">兴趣爱好</label>
		    <div class="layui-input-block">
		      <input type="text" id="hobby" autocomplete="off" class="layui-input" maxlength="30">
		    </div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">擅长领域</label>
		    <div class="layui-input-block">
		      <input type="text" id="skill" autocomplete="off" class="layui-input" maxlength="30">
		    </div>
		</div>
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">个人说明</label>
		    <div class="layui-input-block">
		    	<textarea id="intro" class="layui-textarea" placeholder="100字以内"></textarea>
		    </div>
	    </div>
	    <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" onclick="submitInfo()">提交</button>
		    </div>
		</div>
    </div>
  </body>
  <script type="text/javascript">
	layui.use(['element','form','upload'], function(){
	  var element = layui.element(); 
	  var form = layui.form();
	  layui.upload({
		    url: '${pageContext.request.contextPath}/uploadPhoto',
		    success: function(res, input){ 
		    	if(res.error == 0){
		    		var path = res.url;
		    		$('#photo').attr('src', path);
		    		alert('上传成功！');
		    	}else{
		    		layer.msg(res.message);
		    	}
		    }
		});
	});
  </script>
  <script type="text/javascript" src=js/plugin.js></script>
  <script type="text/javascript">
  	var layerIndex = null;  //弹出层索引
  	function editInfo(){
  		//填充信息
  		$('#career').val($.trim($('#current-career').text()));
  		$('#hobby').val($.trim($('#current-hobby').text()));
  		$('#skill').val($.trim($('#current-skill').text()));
  		$('#intro').val($.trim($('#current-intro').text()));
	  	layerIndex = layer.open({
			type: 1,
			skin: 'layui-layer-rim', 
		  	area: ['500px', '400px'], //宽高
		  	content: $('#edit-info')
		});
	}
	//提交个人信息
	function submitInfo(){
		if($('#intro').length > 100){
			alert('自我介绍过长！');
		}else{
			$.ajax({
		  		async: false, 
				url: '${pageContext.request.contextPath}/updateUser',
				data: 'career=' + $('#career').val() + '&hobby=' + $('#hobby').val() + '&skill=' + $('#skill').val()
					  + '&intro=' + $('#intro').val(),
				type: 'POST',
				success: function(text){
							var jsonStr = $.parseJSON(text);
							if(jsonStr.flag == 0){
								layer.close(layerIndex);  //关闭窗口
								$('#current-career').text(jsonStr.career);
								$('#current-hobby').text(jsonStr.hobby);
								$('#current-skill').text(jsonStr.skill);
								$('#current-intro').text(jsonStr.intro);
							}else{
								layer.msg('更新失败！');
							}
						}
		  		});
		}
	}
  </script>
</html>
