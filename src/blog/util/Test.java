package blog.util;
/** 
 * 说明
 * @author zjz
 */
public class Test {

	
	public static void main(String[] args) {
		String s = "/admin/permission";
		String reg = "/admin/.*";
		System.out.println(s.matches(reg));

	}

}
