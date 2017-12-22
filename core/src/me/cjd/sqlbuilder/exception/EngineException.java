package me.cjd.sqlbuilder.exception;

/**
 * 引擎异常
 * @author Mr.cjd
 */
public class EngineException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final static String ERROR_PREFIX = "Sql Builder Engine Runtime Error: ";
	
	public final static String BEETL_INIT_ERROR = "初始化Beetl模板引擎发生错误";
	public final static String FMK_INPUT_ERROR = "Freemarker 输入未处理源字串错误.";
	public final static String FMK_RENDER_ERROR = "Freemarker 处理模板发生错误.";
	
	public EngineException(String message) {
		super(ERROR_PREFIX + message);
	}
	
	public EngineException(String message, Throwable cause) {
		super(ERROR_PREFIX + message, cause);
	}
	
}
