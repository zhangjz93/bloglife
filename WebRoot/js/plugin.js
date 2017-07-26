
//检查登录
function isLogin(){
	var islogin = true;
	$.ajax({
		async: false, 
		url: 'isLogin?t=' + new Date().toTimeString(),  //解决IE下缓存问题
		type: 'GET',
		success: function(v){
				if(v == 'true')
					islogin = true;
				else{
					islogin = false;
				}
		}
	});
	return islogin;
};

//添加关注
function addAttention(obj){
	if(!isLogin()){
		alert('请先登录！');
		return;
	}
	var focusedId = obj.id.split('-')[1];
	$.ajax({
		async: false, 
		url: baseUrl + '/addAttention',
		data: 'focusedId=' + focusedId,
		type: 'POST',
		success: function(text){
					if(text == 'true'){
						$(obj).text('-取消关注');
						$(obj).attr('onclick','cancelAttention(this)');
					}else{
						alert('不能关注自己！');
					}
				}
		});
}

//取消关注
function cancelAttention(obj){
	if(!isLogin()){
		alert('请先登录！');
		return;
	}
	var focusedId = obj.id.split('-')[1];
	var flag = confirm('取消关注？');
	if(flag){
		$.ajax({
			async: false, 
			url: baseUrl + '/cancelAttention',
			data: 'focusedId=' + focusedId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							$(obj).text('+加关注');
							$(obj).attr('onclick','addAttention(this)');
							layer.close(index);
						}else{
							alert('操作失败');
						}
					}
			});
	}		
}

//删除文章
function deleteArticle(){
	var flag = confirm('删除文章？');
	if(flag){
		$.ajax({
  			async: false,  
			url: baseUrl + '/deleteArticle',
			data: 'aid=' + articleId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.href = baseUrl + '/articles/' + categoryId + '?page=1';
						}else{
							alert('操作失败！');
						}
					}
  		});
	}
}

//删除回复
function deleteClick(obj){
	var replyId = obj.id.split('-')[1];
	var flag = confirm('删除回复？');
	if(flag){
		$.ajax({
  			async: false, 
			url: baseUrl + '/deleteReply',
			data: 'rid=' + replyId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload();
						}else{
							layer.msg('操作失败！');
						}
					}
  		});
		}
}

//收藏
function collectArticle(){
	if(!isLogin()){
		alert('请先登录！');
		return;
	}
	$.ajax({
		async: false, 
		url: baseUrl + '/collectArticle',
		data: 'aid=' + articleId,
		type: 'POST',
		success: function(text){
					if(text == 'true'){
						$('#collect').html('<i class="fa fa-star" style="color: #ffcc33;"></i>已收藏');
					}else{
						return;
					}
				}
		});
}

//取消收藏
function cancelCollect(obj){
	var articleId = obj.id.split('-')[1];
	var flag = confirm('取消收藏？');
	if(flag){
  		$.ajax({
  			async: false, 
			url: baseUrl + '/cancelCollect',
			data: 'aid=' + articleId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload(); 
						}else{
							layer.msg('操作失败！');
						}
					}
  		});
	}
}

//删除用户文章
function deleteUserArticle(obj){
	var articleId = obj.id.split('-')[1]; 
	var writerId = obj.id.split('-')[2]; 
	var categoryId = obj.id.split('-')[3]; 	
	var flag = confirm('删除文章？');
	if(flag){
  		$.ajax({
  			async: false, 
			url: baseUrl + '/deleteArticle',
			data: 'aid=' + articleId,
			type: 'POST',
			success: function(text){
						if(text == 'true'){
							location.reload(); 
						}else{
							layer.msg('操作失败！');
						}
					}
  		});
	}
}


