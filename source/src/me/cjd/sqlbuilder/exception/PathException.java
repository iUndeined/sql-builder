package me.cjd.sqlbuilder.exception;

/**
 * 渲染异常
 * @author Mr.cjd
 */
public class PathException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final static String ERROR_PREFIX = "Sql Builder Path Runtime Error: ";
	
	public final static String GET_ERROR = "WebAppKit.getPath()获取项目地址失败";
	public final static String NULL_ERROR = "WebAppKit.getPath()获取到的路径为null，为不正常现象";
	
	public PathException() {
		super();
	}
	
	public PathException(String message) {
		super(ERROR_PREFIX + message);
	}
	
	public PathException(String message, Throwable cause) {
		super(ERROR_PREFIX + message, cause);
	}
	
}
