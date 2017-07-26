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
    
    <title>后台管理-回复管理</title>
    
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
		<div style="text-align: right;"><a href="admin/articleList?page=1&search=">>>返回文章列表</a></div>
		<table class="layui-table">
			<colgroup>
		    	<col width="5%">
		        <col width="60%">
		        <col width="10%">
		        <col width="15%">
		        <col width="10%">
		    </colgroup>
		    <thead>
		    	<tr>
			        <th>ID</th>
			        <th>内容</th>
			        <th>作者</th>
			        <th>发表时间</th>
			        <th>操作</th>
		        </tr> 
		    </thead>
		    <tbody>
		    	<c:forEach items="${replies }" var="reply">
			    	 <tr>
				     	<td>${reply.id }</td>
				      	<td>${reply.words }</td>
				      	<td>${reply.writer.name }</td>
				      	<td>${reply.replytime }</td>
				      	<td>
				      		<a href="javascript:void(0)" class="layui-btn layui-btn-mini layui-btn-normal" id="d-${reply.id }" onclick="deleteReply(this)">删除</a>
				      	</td>
				    </tr>
			    </c:forEach>
		    </tbody>
		</table>
		&nbsp;共${replyNum }条记录，当前第${currentPage }页
		<div id="page" class="page"></div>
	</div>
  </div>
  
  </body>
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
		    pages: ${totalPage},
		    groups: 8,
		    jump: function(obj, first){
				var curr = obj.curr;
				if(!first)
					location.href = '${pageContext.request.contextPath}/admin/replyList?aid=' + ${articleInfo.id} + '&page=' + curr;
			}
		});
	});
  </script>
</html>
	