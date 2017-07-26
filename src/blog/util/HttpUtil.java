package blog.util;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/** 
 * HTTP连接辅助类
 * @author zjz
 */
public class HttpUtil {
	
	/**
	 * 获取GET请求返回的内容
	 * @param uri
	 * @return 返回的请求内容
	 */
	public static String doGet(String uri){
        String result= "";  
        HttpGet httpRequest = new HttpGet(uri);  
        try{  
            HttpResponse httpResponse = wrapClient(new DefaultHttpClient()).execute(httpRequest);  
            if(httpResponse.getStatusLine().getStatusCode() == 200){  
            	HttpEntity httpEntity = httpResponse.getEntity();  
                result = EntityUtils.toString(httpEntity);  //取出应答字符串  
                result.replaceAll("\r", "");  //去掉返回结果中的"\r"字符
            }else   
            	httpRequest.abort();  
        }catch (ClientProtocolException e) {  
        	e.printStackTrace();  
            result = e.getMessage().toString();  
        }catch (IOException e) {  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        }  
        return result;  
	}
	
	/**
	 * 忽略证书认证
	 * @param base
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HttpClient wrapClient(HttpClient base){
		try{
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        X509TrustManager tm = new X509TrustManager() {
	        	//override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
				
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
	        	
	        };
	        ctx.init(null, new TrustManager[]{tm}, null);
	        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
	        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	        ClientConnectionManager ccm = base.getConnectionManager();
	        SchemeRegistry sr = ccm.getSchemeRegistry();
	        sr.register(new Scheme("https", ssf, 443));
	        return new DefaultHttpClient(ccm, base.getParams());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * 对返回参数形式的字符串转化为Map
	 * @param str
	 * @return
	 */
	public static Map<String,String> getParamMap(String str){
		Map<String,String> map = new HashMap<String,String>();
		String[] strArr = str.split("&");
		for(int i=0; i<strArr.length; i++){
			String[] itemArr = strArr[i].split("=");
			if(itemArr.length == 1)
				map.put(itemArr[0], " ");
			else
				map.put(itemArr[0], itemArr[1]);
		}
		return map;
	}
}
