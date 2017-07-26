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
    
    <title>${u.name }-粉丝</title>
    
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
		<div class="grid_8 omega" style="padding-left: 5px;">
	  	<div class="attention-block block">
	  		<div class="title">粉丝列表</div>
	  		<table class="attention-table" >
	  			<c:forEach items="${users }" var="focusUser" varStatus="status">
	  				<c:if test="${status.index%5 == 0 }">
	  					<tr>
	  				</c:if>
	  				<td>
	  					<div class="attention-item"><img src="${focusUser.photo }" class="img-mid"></div>
	  					<div class="attention-item">
	  						<a href="person/info/${focusUser.id }">${focusUser.name }</a>
	  					</div>
	  					<div class="attention-item">
	  						<span>
			  					<c:choose>
									<c:when test="${focusUser.isFocused }">
										<a href="javascript:void(0)" id="b-${focusUser.id }" class="layui-btn layui-btn-mini" onclick="cancelAttention(this)">-取消关注</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="b-${focusUser.id }" class="layui-btn layui-btn-mini" onclick="addAttention(this)">+加关注</a>
									</c:otherwise>
								</c:choose>
							</span>
						</div>
	  				</td>
	  				<c:if test="${status.index%5==4 || status.last }">
	  					</tr>
	  				</c:if>
	  			</c:forEach>
	  			</tr>
	  		</table>
	  	</div>
  	  <div class="page" id="page"></div>
	</div>
	</div>
  </body>
  <script type="text/javascript" src="js/plugin.js"></script>
  <script type="text/javascript">
  	var userId = ${u.id};
  	layui.use('element', function(){
		var element = layui.element(); 
	  	});
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
					location.href = '${pageContext.request.contextPath}/person/fans/' + userId + '?page=' + curr;
			}
		});
	});
  </script>
</html>
