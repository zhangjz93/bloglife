package blog.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/** 
 * 验证问答生成
 * @author zjz
 */
public class IdentifyUtil {
	private static final int[] number = {1,2,3,4,5,6,7,8,9,0};
	
	private static Random rand = null;
	
	static{
		rand = new Random();
	}
	
	public Map<String,String> generate(){
		Map<String,String> res = new HashMap<String,String>(2);
		
		int index = rand.nextInt(number.length);  //随机生成索引
		for(int i=0; i<2; i++){
			
		}
		return null;
	}
}
