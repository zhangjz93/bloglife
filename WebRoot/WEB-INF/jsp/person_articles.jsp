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
    
    <title>${u.name }-文章列表</title>
    
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
	<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
	
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>

  </head>
  
  <body>
  	<script type="text/javascript">
  		var baseUrl = '${pageContext.request.contextPath}';
  	</script>
    <jsp:include page="template/head.jsp"></jsp:include>
  	<div class="container_12">
	  	<div class="grid_4 alpha">
			<jsp:include page="template/person_left.jsp">
		    	<jsp:param value="${u.id }" name="uid"/>
		   	</jsp:include>
	   	</div>
		<div class="grid_8 omega">
		<div class="articles-block block">
			<div class="title">文章列表</div>
			<div class="person-articles-items-block">
				<c:choose>
					<c:when test="${articles.size()==0 }">
						<div class="nothing">
							<i class="fa fa-meh-o"></i>&nbsp;啊哦，这里什么都没有~
						</div>
					</c:when>
					<c:otherwise>
					<ul class="person-articles-items">
						<c:forEach items="${articles }" var="article">
							<li>
								<div class="articles-title">
									<a href="content/${article.id }">${article.title }</a>
								</div>
								<div class="articles-date">
									${article.submittime }
								</div>
								<c:if test="${sessionScope.user!=null && sessionScope.user.id==u.id }">
									<div class="articles-operation">
										<a href="javascript:void(0)" id="a-${article.id }-${u.id}-${article.category.id}" onclick="deleteUserArticle(this)">
											<i class="fa fa-trash-o" title="删除"></i>
										</a>
				    					&nbsp;&nbsp;<a href="edit?aid=${article.id }"><i class="fa fa-pencil" title="编辑"></i></a>
									</div>
								</c:if>
							</li>
						</c:forEach>
					</ul>
					</c:otherwise>
				</c:choose>
				
			</div>
			</div>
			<div class="page" id="page"></div>
		</div>
	</div>
  </body>
  <script type="text/javascript">
  	layui.use('element', function(){
	  var element = layui.element();
	});
  </script>
  <script type="text/javascript" src="js/plugin.js"></script>
  <script type="text/javascript">
  	layui.use(['laypage'], function(){
  		var userId = ${u.id};
  		var laypage = layui.laypage;
  		laypage({
			cont: 'page',
			curr: ${currentPage},
		    pages: ${totalPage} ,
		    groups: 8 ,
		    jump: function(obj, first){
				var curr = obj.curr;
				if(!first)
					location.href = '${pageContext.request.contextPath}/person/articles/' + userId + '?page=' + curr;
			}
		});
	});
  </script>
</html>

