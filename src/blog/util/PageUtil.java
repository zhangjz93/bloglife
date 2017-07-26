package blog.util;

/** 
 * 分页辅助类
 * @author zjz
 */
public class PageUtil {
	
	/**
	 * 分页
	 * @param totalNum 总条目数量
	 * @param pageNum 每页显示数量
	 * @return 总页数
	 */
	public static int getTotalPage(int totalNum, int pageNum){
		return totalNum%pageNum==0 && totalNum/pageNum>0 ? totalNum/pageNum : totalNum/pageNum+1;
	}
}
