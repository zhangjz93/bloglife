package blog.service;

import java.util.Map;

import blog.model.Reply;

/** 
 * 回复管理Service接口
 * @author zjz
 */
public interface ReplyService {
	
	/**
	 * 获取回复列表
	 * @param aid
	 * @param page
	 * @param getArticleInfo 是否获取文章信息
	 * @return
	 */
	public Map<String,Object> getReplyList(int aid, int page, boolean getArticleInfo);
	
	/**
	 * 添加文章回复
	 * @param reply
	 * @return 当返回为false时，代表父回复不存在
	 */
	public boolean addReply(Reply reply);
	
	/**
	 * 删除回复
	 * @param rid
	 * @param wid 作者id
	 * @return
	 */
	public boolean deleteReply(int rid, int wid);
	
	/**
	 * 删除回复
	 * @param rid
	 * @return
	 */
	public boolean deleteReply(int rid);
}
