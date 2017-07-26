package blog.dao;

import java.sql.Date;
import java.util.List;

import blog.model.Attach;

/** 
 * 附件管理DAO接口
 * @author zjz
 */
public interface FileDao {
	
	/**
	 * 新增附件路径
	 * @param aid 文章id
	 * @param paths 该参数为null时直接返回
	 */
	public void addPaths(final int aid, final List<String> paths);
	
	/**
	 * 删除附件路径
	 * @param aid
	 */
	public void deletePaths(int aid);
	
	/**
	 * 获取某日期内附件列表
	 * @param date
	 * @return
	 */
	public List<Attach> getAttachesOfDate(Date date);
}
