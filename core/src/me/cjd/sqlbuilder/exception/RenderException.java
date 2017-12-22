package me.cjd.sqlbuilder.exception;

/**
 * 渲染异常
 * @author Mr.cjd
 */
public class RenderException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final static String ERROR_PREFIX = "Sql Builder Render Runtime Error: ";
	
	public final static String FORMAT_ERROR = "错误的sqlId格式，示例 fileName.sqlName ";
	
	public RenderException() {
		super();
	}
	
	public RenderException(String message) {
		super(ERROR_PREFIX + message);
	}
	
	public RenderException(String message, Throwable cause) {
		super(ERROR_PREFIX + message, cause);
	}
	
}
