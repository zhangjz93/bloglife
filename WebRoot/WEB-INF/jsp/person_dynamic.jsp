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
    
    <title>${u.name }-动态列表</title>
    
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

  </head>
  
  <body>
    <jsp:include page="template/head.jsp"></jsp:include>
    <div class="container_12">
    	<div class="grid_4 alpha">                     
			<jsp:include page="template/person_left.jsp"></jsp:include>
		</div>
		<div class="grid_8 omega">
			<div class="dynamic-block block">
				<div class="title">动态列表</div>
				<c:choose>
					<c:when test="${dynamics.size()==0 }">
  						<div class="nothing">
							<i class="fa fa-meh-o"></i>&nbsp;啊哦，这里什么都没有~
						</div>
  					</c:when>
  					<c:otherwise>
  						<ul class="dynamic-items">
							<c:forEach items="${dynamics}" var="dynamic">
								<c:if test="${dynamic.article != null }">
								<li>
									<div class="left" >
										<img class="img-mid" src="${dynamic.user.photo }">
									</div>
									<div class="right">
										<div class="dynamic-time">
											<span class="item"><a href="person/info/${dynamic.user.id }">${dynamic.user.name }</a></span>
											<span class="item">${dynamic.article.submittime }</span>
										</div>
										<div class="dynamic-details">
											<div class="dynamic-title">
												发表了文章：<a href="content/${dynamic.article.id }"><c:out value="${dynamic.article.title }"></c:out></a>
											</div>
											<div class="dynamic-summary">
												<c:out value="${dynamic.article.summary }"></c:out>
											</div>
										</div>
									</div>
								</li>
								</c:if>
							</c:forEach>
						</ul>
  					</c:otherwise>
  				</c:choose>
				
			</div>
			<div class="page" id="page"></div>
		</div>
    </div>
  </body>
  <script type="text/javascript" src="js/plugin.js"></script>
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
		    pages: ${totalPage} ,
		    groups: 8 ,
		    jump: function(obj, first){
				var curr = obj.curr;
				if(!first)
					location.href = '${pageContext.request.contextPath}/person/dynamic' + '?page=' + curr;
			}
		});
	});
  </script>
</html>
