//删除文章
function deleteArticle(obj){
	var articleId = obj.id.split('-')[1];
	var flag = confirm('确定要删除？');
	if(flag){
		$.ajax({
  			async: false, 
			url: baseUrl + '/admin/deleteArticle',
			data: 'aid=' + articleId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//推荐设置
function setRecommend(obj, isCancel){
	var articleId = obj.id.split('-')[1];
	var requestUrl = null;
	if(isCancel)
		requestUrl = baseUrl + '/admin/deleteRecommend';
	else
		requestUrl = baseUrl + '/admin/addRecommend';
	var flag = confirm('确定执行此操作？');
	if(flag){
		$.ajax({
  			async: false, 
			url: requestUrl,
			data: 'aid=' + articleId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//删除类别
function deleteCategory(obj){
	var categoryId = obj.id.split('-')[1];
	var flag = confirm('确定要删除？');
	if(flag){
		$.ajax({
  			async: false, 
			url: baseUrl + '/admin/deleteCategory',
			data: 'cid=' + categoryId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//删除权限
function deletePermission(obj){
	var permissionId = obj.id.split('-')[1];
	var flag = confirm('确定要删除？');
	if(flag){
		$.ajax({
  			async: false, 
			url: baseUrl + '/admin/deletePermission',
			data: 'pid=' + permissionId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//删除回复
function deleteReply(obj){
	var replyId = obj.id.split('-')[1];
	var flag = confirm('确定要删除？');
	if(flag){
		$.ajax({
  			async: false, 
			url: baseUrl + '/admin/deleteReply',
			data: 'rid=' + replyId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//删除用户
function deleteUser(obj){
	var userId = obj.id.split('-')[1];
	var flag = confirm('确定要删除？');
	if(flag){
		$.ajax({
  			async: false, 
			url: baseUrl + '/admin/deleteUser',
			data: 'uid=' + userId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//设置角色
function setRole(obj, role, isCancel){
	var userId = obj.id.split('-')[1];
	var requestUrl = null;
	if(isCancel)
		requestUrl = baseUrl + '/admin/deleteRole';
	else
		requestUrl = baseUrl + '/admin/addRole';
	var flag = confirm('确定执行此操作？');
	if(flag){
		$.ajax({
  			async: false, 
			url: requestUrl,
			data: 'uid=' + userId + '&role=' + role,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}