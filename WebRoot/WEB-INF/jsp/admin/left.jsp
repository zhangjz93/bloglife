<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="layui-side layui-bg-black">
  	<div class="layui-side-scroll">
    <ul class="layui-nav layui-nav-tree">
	  <li class="layui-nav-item">
	  	<a href="javascript:;">类别管理</a>
	    <dl class="layui-nav-child">
	      <dd><a href="admin/categoryAdd">添加类别</a></dd>
	      <dd><a href="admin/categoryList">类别列表</a></dd>
	    </dl>
	  </li>
	  <li class="layui-nav-item"><a href="admin/userList?page=1&search=">用户管理</a></li>
	  <li class="layui-nav-item">
	  	<a href="javascript:;">文章管理</a>
	    <dl class="layui-nav-child">
	      <dd><a href="admin/articleList?page=1&search=">所有文章</a></dd>
	      <dd><a href="admin/recommendList?page=1">推荐文章</a></dd>
	    </dl>
	  </li>
	  <li class="layui-nav-item"><a href="admin/permissionList">权限管理</a></li>
	  <li class="layui-nav-item"><a href="">清除缓存</a></li>
	</ul>
	</div>
</div>