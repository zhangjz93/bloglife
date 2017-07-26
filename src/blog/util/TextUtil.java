package blog.util;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/** 
 * 用户输入文本处理辅助类
 * @author zjz
 */
public class TextUtil {
	private static final String HTML_REG = "<[^>]+>";

	/**
	 * 去除输入文本中的HTML标签及格式化符号
	 * @param str
	 * @return 过滤后的文本
	 */
	public static String HtmlFormatFilter(String str){
		return str.replaceAll(HTML_REG, "").
				replace("&nbsp;","").
				replace("&lt;","<").
				replace("&gt;", ">").
				replace("&amp;","&").
				replace("\r", "").
				replace("\t", "").
				replace("\n", "");
	}
	
	/**
	 * HTML标签白名单设置,依赖Jsoup
	 * @return
	 */
	public static Whitelist ArticleAllowTags(){
		return new Whitelist()
        .addTags(
                "a", "b", "blockquote", "br", "caption", "code", "col",
                "colgroup", "dd", "div", "dl", "dt", "del", "em", "embed", "h1", "h2", "h3", "h4", "h5", "h6",
                "i", "img", "li", "ol", "p", "pre", "small", "span", "strike", "strong",
                "sub", "sup", "s", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
                "ul", "font")

        .addAttributes("a", "href", "target", "name")
        .addAttributes("span", "style")
        .addAttributes("font", "style", "color", "size", "face")
        .addAttributes("blockquote", "align", "style")
        .addAttributes("img", "src", "width", "height", "border", "alt", "title", "align", "style")
        .addAttributes("ol", "align", "style")
        .addAttributes("table", "width", "border", "cellspacing", "cellpadding", "height", "align", "bordercolor",
        		"style")
        .addAttributes("td", "align", "valign", "width", "height", "colspan", "rowspan", "bgcolor")
        .addAttributes("th", "align", "valign", "width", "height", "colspan", "rowspan", "bgcolor")
        .addAttributes("ul", "align", "style")
        .addAttributes("li", "align", "style")
        .addAttributes("p", "align", "style")
        .addAttributes("div", "align", "style")
        .addAttributes("h1", "align", "style")
        .addAttributes("h2", "align", "style")
        .addAttributes("h3", "align", "style")
        .addAttributes("h4", "align", "style")
        .addAttributes("h5", "align", "style")
        .addAttributes("h6", "align", "style")
        .addAttributes("pre", "class")
        .addAttributes("embed", "src", "width", "height", "type", "loop", "autostart", "quality", "style")
        .addAttributes("hr", "class", "style")
        .addAttributes("code", "class", "style")
        .addProtocols("a", "href", "ftp", "http", "https", "mailto")
        .addProtocols("blockquote", "cite", "http", "https")
        .addProtocols("cite", "cite", "http", "https")
        .addProtocols("img", "src", "http", "https")
        .addProtocols("embed", "src", "http", "https");
	}
	
	/**
	 * 将格式化字符转换为HTML输出格式
	 * @param str
	 * @return
	 */
	public static String HtmlFormatConverter(String str){
		return str.replace("<", "&lt;").
				replace(">", "&gt;").
				replace(" ", "&nbsp;").
				replace("\n", "<br>");
	}
	
	/**
	 * 生成图片路径集合，依赖Jsoup
	 * @param words
	 * @return
	 */
	public static List<String> convertImgToPaths(String words){
		List<String> list = new LinkedList<String>();
		Elements elements = Jsoup.parse(words).select("img");
		for(int i=0; i<elements.size(); i++){
			list.add(elements.get(i).attr("src"));
		}
		return list;
	}
	
	/**
	 * 根据给定的字符集对输入字符串进行解码生成新串
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String encodingStr(String str, String charset){
		String encodingStr = null;
		try {
			encodingStr = new String(str.getBytes("ISO-8859-1"), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodingStr;
	}
	
}
