package me.cjd.sqlbuilder.exception;

/**
 * 配置文件初始化异常
 * @author Mr.cjd
 */
public class ConfigInstanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final static String ERROR_PREFIX = "Sql Builder Config Instance Error: ";
	
	public ConfigInstanceException() {
		super();
	}
	
	public ConfigInstanceException(String message) {
		super(ERROR_PREFIX + message);
	}
	
	public ConfigInstanceException(String message, Throwable cause) {
		super(ERROR_PREFIX + message, cause);
	}
	
}
