package me.cjd.sqlbuilder.exception;

/**
 * 动态创建异常初始化错误
 * @author Mr.cjd
 */
public class ErrorInstanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ErrorInstanceException() {
		super();
	}
	
	public ErrorInstanceException(String message) {
		super(message);
	}
	
	public ErrorInstanceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
