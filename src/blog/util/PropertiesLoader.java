package blog.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/** 
 * 配置文件加载
 * @author zjz
 */
@Component("config")
public class PropertiesLoader {
	private Properties prop;
	
	@PostConstruct
	public void init(){
		try {
			prop = new Properties();
			InputStream in = getClass().getResourceAsStream("/blog.properties");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getInt(String key){
		return Integer.parseInt(prop.getProperty(key));
	}
	
	public String getString(String key){
		return prop.getProperty(key);
	}
	
	public long getLong(String key){
		return Long.parseLong(prop.getProperty(key));
	}
}
