/*
 * 前端验证常用函数，依赖jQuery
 */

function isEqual(str1, str2){
	return str1 == str2;
}

function isLengthOK(str, minLen, maxLen){
	var str0 = $.trim(str);
	return str0.length>=minLen && str0.length<=maxLen;
}

function isFloor(str, minLen){
	var str0 = $.trim(str);
	return str0.length>=minLen;
}

function isCeil(str, maxLen){
	var str0 = $.trim(str);
	return str0.length<=maxLen;
}

function isBlank(str){
	return $.trim(str) == '';
}

function isNotBlank(str){
	return !isNull(str);
}

function isMatch(str, regex){
	return regex.test(str);
}

/*
 * Validator 
 */
var min_title = 3;
var max_title = 30;
var min_content = 5;
var max_summary = 150;
var min_reply = 5;
var max_reply = 600;

/*
 * 文章编辑表单验证
 */
function articleValidate(form){
	var title = $(form).find('#title').val();
	var content = $(form).find('#content').val();
	var summary = $(form).find('#summary').val();
	if(!isLengthOK(title, min_title, max_title)){
		alert('标题需在' + min_title + '~' + max_title + '字符之间！');
		return false;
	}
	if(!isFloor(content, min_content)){
		alert('内容不得少于' + min_content + '个字符！');
		return false;
	}
	if(!isCeil(summary, max_summary)){
		alert('摘要不得多于' + max_summary + '个字符！');
		return false;
	}
	return true;
}

/*
 * 回复表单验证
 */
function replyValidate(form){
	var reply = $(form).find('.reply').val();
	if(!isLengthOK(reply, min_reply, max_reply)){
		alert('回复需在' + min_reply + '~' + max_reply + '字符之间！');
		return false;
	}else{
		return true;
	}
}

var min_categoryName = 3;
var max_categoryName = 10;
var min_categoryIntro = 5;
var max_categoryIntro = 200;
/*
 * 类别更新表单验证
 */
function categoryValidate(form){
	var categoryName = $(form).find('#cname').val();
	var intro = $(form).find('#intro').val();
	var sortNum = $(form).find('#sortNum').val();
	var icon = $(form).find('#icon').val();
	if(!isLengthOK(categoryName, min_categoryName, max_categoryName)){
		alert('名称长度不符合要求！');
		return false;
	}
	if(!isLengthOK(intro, min_categoryIntro, max_categoryIntro)){
		alert('类别介绍长度不符合要求！');
		return false;
	}
	if(!isMatch(sortNum, /^\d{1,3}$/)){
		alert('排序序号不符合要求！');
		return false;
	}
	return true;
}