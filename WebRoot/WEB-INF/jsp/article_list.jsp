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
    
    <title>${category.name }</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="css/960.css">
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css" media="all">
	
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>
	<script type="text/javascript" src="js/plugin.js"></script>

  </head>
  
  <body> 
    <jsp:include page="template/head.jsp"></jsp:include>
    <div class="container_12">
    	<!-- 面包屑 -->
    	<div class="grid_12 alpha">
    		<div class="bread">
		    	<span class="layui-breadcrumb">
			    	<a href="index">首页</a>
				  	<a><cite>${category.name }</cite></a>
				</span>
			</div>
    	</div>
    	<!-- end -->
    	<div class="clear"></div>
    	<div class="grid_8 alpha">
	    	<div class="articlelist-block block">
	    		<div style="text-align: right; padding: 5px;">
	    			<a href="write?cid=${category.id }" class="layui-btn" onclick="return preventJump()">
						写文章
					</a>
	    		</div>
	    		<div class="articlelist-items-block">
	    			<ul class="articlelist-items">
	    			<c:forEach items="${articles }" var="article">
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
    		<div id="page" style="text-align: center;"></div>
    	</div>
    	<div class="grid_4 omega">
	    
	    		<div class="category-intro-block block">
	    			<c:out value="${category.intro }"></c:out>
	    		</div>
		    	<div class="hot-block block">
		    		<div class="title">
		    			热门
		    		</div>
		    		<div>
			    		<ul class="hot-items">
		    			<c:forEach items="${hotArticles }" var="hotArticle">
			    			<li>
			    				<a href="content/${hotArticle.id }"><c:out value="${hotArticle.title }"></c:out></a>
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
  <script type="text/javascript">
  	function preventJump(){
  		if(!isLogin()){
  			alert('请先登录！');
  			return false;
  		}else{
  			return true;
  		}
  	}
  	var categoryId = ${category.id};
  	layui.use(['laypage'], function(){
  		var laypage = layui.laypage;
  		laypage({
			cont: 'page',
			curr: ${currentPage},
		    pages: ${totalPage} ,
		    groups: 8,
		    jump: function(obj, first){
				var curr = obj.curr;
				if(!first)
					location.href = '${pageContext.request.contextPath}/articleList/' + categoryId + '?page=' + curr;
			}
		});
	});
  </script>
</html>
