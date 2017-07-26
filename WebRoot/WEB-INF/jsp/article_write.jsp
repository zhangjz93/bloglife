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
    
    <title>博客--写文章</title>
    
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
    <jsp:include page="template/head.jsp"></jsp:include>
    <div class="container_12">
	    <div class="grid_12 alpha">
	    	<div class="bread">
		    	<span class="layui-breadcrumb">
			    	<a href="index">首页</a>
				  	<a href="articles/${category.id }?page=1">${category.name }</a>
				  	<a><cite>写文章</cite></a>
				</span>
			</div>
	    </div>
	    <div class="clear"></div>
	    <div class="grid_9 alpha">
    		<div class="write-block block">
	    		<form class="layui-form" action="addArticle" method="post" onsubmit="return articleValidate(this)">
	    			<div class="layui-form-item">
						<input type="text" name="title" id="title" maxlength="30" autocomplete="off" placeholder="请输入标题" class="layui-input" value="${article.title }">
					</div>
	    			<div class="layui-form-item layui-form-text">
					    <textarea id="content" name="content.words">${article.content.words }</textarea>
					</div>
				  	<input type="hidden" name="category.id" value="${category.id }">
				  	<c:if test="${mode == 'e' }">
				  		<input type="hidden" name="id" value="${article.id }">
				  	</c:if>
				  	<input type="hidden" name="mode" value="${mode }">
				  	<div class="layui-form-item layui-form-text">
				  		<textarea id="summary" name="summary" class="layui-textarea" placeholder="默认提取文章的前150字作为文章摘要，你也可以在这里自行编辑">${article.summary }</textarea>
					</div>
				  	<div class="layui-form-item">
					    <button class="layui-btn" type="submit">发布</button>
					</div>
	    		</form>
    		</div>
    	</div>
    	<div class="grid_3 omega">
    		<div class="block">
    			<ul>
    				<li>图片附件大小不要超过2M</li>
    				<li>支持的图片格式:jpg,png,gif,bmp</li>
    			</ul>
    		</div>
    	</div>
  	</div>
  </body>
  
  <script charset="utf-8" src="keditor/kindeditor.js"></script>
  <script charset="utf-8" src="keditor/lang/zh-CN.js"></script>
  <script type="text/javascript">
  	KindEditor.ready(function(K) {
    	window.editor = K.create('#content',{
       		width: '668px',
       		height: '400px',
       		items: ['preview', 'code', 'justifyleft', 'justifycenter', 'justifyright',
        			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'formatblock', 'fontsize', 'forecolor', 'bold',
        			'italic', 'underline', 'strikethrough', 'removeformat', 'image',
        			'table', 'emoticons', 'link', 'unlink', 'fullscreen'],
        	allowFlashUpload: false,
        	allowMediaUpload: false,
        	allowImageRemote: true,
        	uploadJson : 'upload',
        	allowFileManager : false,
        	afterBlur:function(){
        		this.sync();
        	}
        	});
    });
  </script>
  <script type="text/javascript">
  	layui.use('element', function(){
	  var element = layui.element(); 
	});
  </script>
</html>
