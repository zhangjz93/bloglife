package blog.util;

/** 
 * 表单验证辅助类
 * @author zjz
 */
public class ValidationUtil {
	
	/**
	 * 判断数值是否在区间以内
	 * @param num
	 * @param floor
	 * @param ceil
	 * @return
	 */
	public static boolean isNum(int num, int floor, int ceil){
		return num>=floor && num<=ceil;
	}
	
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		return str==null;
	}
	
	/**
	 * 字符串是否非空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
	
	/**
	 * 判断是否为空白字串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		boolean flag = true;
		for(int i=0; i<str.length(); i++){
			if(str.charAt(i) != ' '){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否为非空白字串
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	/**
	 * 判断字符串相等
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2){
		return str1.equals(str2);
	}
	
	/**
	 * 字符串长度验证，含首尾空格
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isLengthOK(String str, int min, int max){
		return str.length()>=min && str.length()<=max;
	}
	
	/**
	 * 验证是否匹配正则
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean isMatch(String str, String regex){
		return str.matches(regex);
	}
	
	/**
	 * 文本字段验证，不含首尾空格
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isText(String str, int min, int max){
		if(isBlank(str)){
			return min==0 ? true : false;
		}
		int len = str.length();
		int leftIndex = 0;
		int rightIndex = len - 1;
		for( ;leftIndex<len && str.charAt(leftIndex)==' '; leftIndex++);
		for( ;rightIndex>=0 && str.charAt(rightIndex)==' '; rightIndex--);
		int resLen = rightIndex - leftIndex + 1;
		return resLen>=min && resLen<=max;
	}
	
}
