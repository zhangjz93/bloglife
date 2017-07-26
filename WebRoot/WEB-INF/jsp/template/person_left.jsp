<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="person-block">
	<div class="person-user block">
		<div class="person-user-name">
			${u.name }
		</div>
		<div class="person-user-photo">
			<img src="${u.photo }" width="100" height="100">
		</div>
		<div class="person-user-info">
			<ul class="items">
				<li>
					<span class="person-items-color" style="margin-right: 5px;">关注：<a href="person/focus/${u.id}?page=1">${focusNum }</a></span>
					<span class="person-items-color" style="margin-right: 5px;">|</span>
					<span class="person-items-color">粉丝：<a href="person/fans/${u.id}?page=1">${fansNum }</a></span>
				</li>
				<li>
					<span class="person-items-color">注册时间：</span>${u.registTime }
				</li>
				<li>
					<span class="person-items-color">文章数：</span>${articleNum }篇
				</li>
				<li>
					<c:choose>
						<c:when test="${isFocused }">
							<a href="javascript:void(0)" class="layui-btn layui-btn-small" id="a-${u.id }" onclick="cancelAttention(this)">-取消关注</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0)" class="layui-btn layui-btn-small" id="a-${u.id }" onclick="addAttention(this)">+加关注</a>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
	</div>
	<div class="person-list block">
		<ol class="person-items">
			<c:if test="${sessionScope.user!=null && sessionScope.user.id==u.id }">
				<li><a href="person/dynamic?page=1"><i class="fa fa-list-alt"></i>&nbsp;文章动态</a></li>
			</c:if>
			<li><a href="person/info/${u.id}"><i class="fa fa-id-card-o"></i>&nbsp;基本信息</a></li>
			<li><a href="person/articles/${u.id}?page=1"><i class="fa fa-edit"></i>&nbsp;用户文章</a></li>
			<c:if test="${sessionScope.user!=null && sessionScope.user.id==u.id }">
				<li><a href="person/collect?page=1"><i class="fa fa-star-o"></i>&nbsp;用户收藏</a></li>
			</c:if>
		    <c:if test="${sessionScope.user!=null && sessionScope.user.id==u.id }">
		    	<li><a href="person/notice?page=1"><i class="fa fa-bell-o"></i>&nbsp;回复提醒</a></li>
		    </c:if>
		</ol>
	</div>
</div>