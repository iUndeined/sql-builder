package me.cjd.sqlbuilder.kit;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.log4j.Logger;

import me.cjd.sqlbuilder.exception.PathException;

import java.io.InputStream;

public class WebAppKit {
	
	private final static Logger log = Logger.getLogger(WebAppKit.class);
	
	public final static String getPath(){
		// 获取 地址
		URI uri = null;
		try {
			uri = WebAppKit.class.getClassLoader().getResource("/").toURI();
		} catch (URISyntaxException e) {
			LogKit.throwError(log, PathException.GET_ERROR, e, PathException.class);
		}
		if (uri == null) {
			LogKit.throwError(log, PathException.NULL_ERROR, PathException.class);
			// 质量检测
			return null;
		}
		return uri.getPath();
	}
	
	public final static InputStream getInnerFile(String fileName){
		return WebAppKit.class.getResourceAsStream("/" + fileName);
	}
	
}