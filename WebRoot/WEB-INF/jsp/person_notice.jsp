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
    
    <title>${u.name }-通知列表</title>
    
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
  	<script type="text/javascript">
  		var baseUrl = '${pageContext.request.contextPath}';
  	</script>
    <jsp:include page="template/head.jsp"></jsp:include>
	<div class="container_12">
		<div class="grid_4 alpha">
			<jsp:include page="template/person_left.jsp"></jsp:include>
		</div>
		<div class="grid_8 omega">
			<div class="notice-block block">
				<div class="title">通知列表</div>
				<c:choose>
					<c:when test="${replies.size()==0 }">
						<div class="nothing">
							<i class="fa fa-meh-o"></i>&nbsp;啊哦，这里什么都没有~
						</div>
					</c:when>
					<c:otherwise>
						<ul class="notice-item">
							<c:forEach items="${replies }" var="reply">
								<li>
									<div class="left" >
										<img src="${reply.writer.photo }" class="img-mid">
									</div>
									<div class="right">
										<div class="notice-time">
											<span class="item">
												<a href="person/info/${reply.writer.id }"><c:out value="${reply.writer.name }"></c:out></a>
											</span> 
											<span class="item"><c:out value="${reply.replytime }"></c:out></span>
										</div>
										<div class="notice-detail">
											<c:if test="${reply.parent.id!=0 && reply.parent.id!=-1}">
												回复
												<a href="person/info/${reply.parent.writer.id }">
													<c:out value="${reply.parent.writer.name }"></c:out>
												</a>:
											</c:if>
											${reply.words }
										</div>
										<div class="notice-quote">
											评论文章：<a href="content/${reply.article.id }"><c:out value="${reply.article.title }"></c:out></a>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
				
			</div>
			<div id="page" class="page"></div>
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
  	var uid = ${u.id};
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
					location.href = '${pageContext.request.contextPath}/person/notice?page=' curr;
			}
		});
	});
  </script>
</html>
