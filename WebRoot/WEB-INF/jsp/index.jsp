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
    
    <title>首页</title>
    
	<meta http-equiv="proagma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="descriptioln" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="css/960.css">
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css" media="all">
	<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
	
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>
  </head>
  
  <body>
  	<jsp:include page="template/head.jsp"></jsp:include>
  	<div class="container_12" >
  		<div class="grid_8 alpha">
  			<div class="good-block block"> 
  				<div class="title">推荐</div>
  				<div class="articlelist-items-block">
	  				<ul class="articlelist-items">
	  					<c:forEach items="${recommendArticles }" var="article">
	  						<li>
	  							<div class="articlelist-photo">
			    			 		<img src="${article.writer.photo }" width="50" height="50">
			    			 	</div>
			    			 	<div class="articlelist-details">
			    			 		<div class="articlelist-title">
			    			 			<a href="content/${article.id }"><c:out value="${article.title }"></c:out></a>
			    			 		</div>
			    			 		<div class="summary">
			    			 			<c:out value="${article.summary }"></c:out>
			    			 		</div>
			    			 		<div class="time">
			    			 			<span class="info">
			    			 				<a href="articles/${article.category.id }?page=1" class="layui-btn layui-btn-primary layui-btn-mini">${article.category.name }</a>
			    			 			</span>
			    			 			<span class="info">作者：<a href="person/info/${article.writer.id }">${article.writer.name }</a></span>
			    			 			<span class="info">${article.submittime }</span>
			    			 			<span class="info"><i class="fa fa-eye"></i>${article.clickNum }</span>
			    			 			<span class="info"><i class="fa fa-commenting-o"></i>${article.replyNum }</span>
			    			 		</div>
			    			 	</div>
	  						</li>
	  					</c:forEach>
	  				</ul>
  				</div>
  			</div>
  		</div>
  		<div class="grid_4 omega">
  			<div class="category-block block">
  				<div class="title">分类</div>
  					<div class="category-items-block">
	  					<ul class="category-items">
	  						<c:forEach items="${categories }" var="category">
		  						<li>
		  							<div class="category-photo">
		  								<img src="${category.photo }" class="img-small">
		  							</div>
		  							<div class="category-name">
		  								<a href="articles/${category.id }?page=1">${category.name }</a>(${category.articleNum })
		  							</div>
		  						</li>
	  						</c:forEach>
	  					</ul>
  					</div>
  			</div>
  			<div class="new-block block">
  				<div class="title">最新</div>
  				<div class="new-items-block">
  					<ul class="new-items">
		    			<c:forEach items="${newArticles }" var="newArticle">
			    			<li>
			    				<a href="content/${newArticle.id }"><c:out value="${newArticle.title }"></c:out></a>
			    			</li> 
		    			</c:forEach>
			    	</ul>
  				</div>
  			</div>
  		</div>
  	</div>
	  	
  </body>
  <script type="text/javascript">
	layui.use('element', function(){
	  var element = layui.element();
	});
</script>
</html>
