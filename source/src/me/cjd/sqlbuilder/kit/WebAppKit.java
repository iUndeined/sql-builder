package me.cjd.sqlbuilder.kit;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.InputStream;

public class WebAppKit {
	
	public final static String getPath(){
		// 获取 地址
		URI uri = null;
		try {
			uri = WebAppKit.class.getResource("/").toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Sql Builder: WebAppKit.getPath()获取项目地址失败", e);
		}
		return uri.getPath();
	}
	
	public final static InputStream getInnerFile(String fileName){
		return WebAppKit.class.getResourceAsStream("/" + fileName);
	}
	
}
