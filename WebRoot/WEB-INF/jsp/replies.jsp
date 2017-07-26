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
    
    <title>${article.title }-全部评论</title>
    
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
	<script type="text/javascript" src=js/plugin.js></script>
	<script type="text/javascript" src="js/validation.js"></script>

  </head>
  
  <body>
  	<!-- 回复DIV -->
     <div id="reply-layer" style="display: none;">
		<form class="layui-form" action="addReply" method="post" onsubmit="return replyValidate(this)">
			<input type="hidden" id="reply-layer-id" name="parent.id" value="">
			<input type="hidden" name="parent.writer.id" id="reply-layer-uid" value="">
			<input type="hidden" name="article.id" value="${article.id }">
			<input type="hidden" name="article.writer.id" value="${article.writer.id }">
			<div class="layui-form-item">
    			<textarea name="words" class="layui-textarea"></textarea>
    			</div>
    			<div class="layui-form-item">
     			<button type="submit" class="layui-btn layui-btn-small">发表评论</button>
     		</div>
     	</form>
	</div>
	<!-- end -->
  	<script type="text/javascript">
		var articleId = ${article.id};
		var categoryId = ${article.category.id};
		var writerId = ${article.writer.id};
		var baseUrl = '${pageContext.request.contextPath}';
	</script>
    <jsp:include page="template/head.jsp"></jsp:include>
    <div class="container_12">
    	<div class="grid_12 alpha">
	    	<div class="bread">
		    	<span class="layui-breadcrumb">
			    	<a href="index">首页</a>
				  	<a href="articles/${article.category.id }?page=1">${article.category.name }</a>
				  	<a href="content/${article.id }">${article.title }</a>
				  	<a><cite>查看全部评论</cite></a>
				</span>
			</div>
	    </div>
	    <div class="clear"></div>
	    <div class="grid_12 alpha">
	    	<div class="content-reply block">
     			<div class="reply-title title">全部评论(${article.replyNum })</div>
     			<div class="reply-block">
     				<c:choose>
	     				<c:when test="${replyNum != 0 }">
			     			<ul class="reply-items">
				  				<c:forEach items="${replies }" var="reply">
				  				<li>
				     				<div class="replyer-photo" style="text-align: center;">
				     					<img src="${reply.writer.photo }" class="img-mid">
					     			</div>
					     			<div class="reply-details">
					     				<div class="reply-info">
					     					<div class="left">
					     						<a href="person/info/${reply.writer.id }">${reply.writer.name }</a>
					     					</div>
					     					<div class="right">
						     					<span class="reply-item">#${reply.count }</span>
						     					<span class="reply-item">${reply.replytime }</span>
						     					<span class="reply-item">
						     						<a href="javascript:void(0)" id="r-${reply.id }-${reply.writer.id }" onclick="replyClick(this)"><i class="fa fa-reply"></i>&nbsp;回复</a>
						     					</span>
						     					<c:if test="${sessionScope.user!=null && sessionScope.user.id==reply.writer.id }">
						     						<span class="reply-item">
						     							<a href="javascript:void(0)" id="d-${reply.id }-${reply.writer.id }" onclick="deleteClick(this)">删除</a>
						     						</span>
						     					</c:if>
					     					</div>
					     				</div>
					     				<div class="reply-content">
					     					<c:if test="${reply.parent.id != 0 }">
						     					<div class="reply-reply">
						     						<c:choose>
							     						<c:when test="${reply.parent.id != -1 }">
							     							<strong>Re:${reply.parent.writer.name }在#${reply.parent.count }的回复：</strong>
							     							<div>
							     								${reply.parent.words }
							     							</div>
							     						</c:when>
							     						<c:otherwise>
							     							<span style="color: #cccccc;">该条回复已删除</span>
							     						</c:otherwise>
						     						</c:choose>
						     					</div>
					     					</c:if>
					     					<div class="reply-words">
					     						${reply.words }
					     					</div>
					     				</div>
					     			</div> <!-- 右半部分 -->
					     		</li>
					     		</c:forEach>
					     	</ul>
				     	</c:when>
				     	<c:otherwise>
				     		<div class="reply-no">这里还没有回复，来抢沙发吧~</div>
				     	</c:otherwise>
			     	</c:choose>
		     	</div>
		     	<div id="page" class="page"></div>
    			<div class="reply-textarea">
	     			<c:choose>
	     				<c:when test="${sessionScope.user != null }">
	     					<form class="layui-form" action="addReply" method="post" onsubmit="return replyValidate(this)">
		     					<input type="hidden" name="article.id" value="${article.id }">
		     					<input type="hidden" name="article.writer.id" value="${article.writer.id }">
		     					<input type="hidden" name="parent.id" value="0">
		     					<input type="hidden" name="parent.writer.id" value="0">
		     					<div class="layui-form-item">
		     						<textarea id="replyarea" name="words" class="layui-textarea"></textarea>
		     					</div>
		     					<div class="layui-form-item">
			     					<button type="submit" class="layui-btn layui-btn-small" lay-submit>发表评论</button>
			     				</div>
		     				</form>
			     		</c:when>
		     			<c:otherwise>
		     				<span>你没有登录，还不能回复呢。</span>
		     			</c:otherwise>
		     		</c:choose>
	     		</div>
		     </div>
	    </div>
    </div>
  </body>
  <script type="text/javascript">
  	layui.use(['element','form'], function(){
	  var element = layui.element(); 
	});
  </script>
  <script type="text/javascript">
  	function replyClick(obj){
		if(!isLogin()){
			alert('请先登录！');
			return;
		}
		var splitArr = obj.id.split('-');
		var parentId = splitArr[1];
		var parentWriterId = splitArr[2];
		$('#reply-layer-id').attr('value',parentId);  
		$('#reply-layer-uid').attr('value',parentWriterId);
		layer.open({
			type: 1,
			title: '发表评论',
			skin: 'layui-layer-rim', 
			area: ['420px', '240px'], 
			offset: ['100px', '450px'],
			content: $('#reply-layer')
		});
	} 
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
					location.href = '${pageContext.request.contextPath}/replyList/' + articleId + '?page=' + curr;
			}
		});
	});
  </script>
</html>
