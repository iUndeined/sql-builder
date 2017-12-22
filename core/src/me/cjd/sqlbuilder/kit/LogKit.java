package me.cjd.sqlbuilder.kit;

import org.apache.log4j.Logger;
import java.lang.reflect.InvocationTargetException;
import me.cjd.sqlbuilder.exception.ErrorInstanceException;

public class LogKit {
	
	private final static Logger log = Logger.getLogger(LogKit.class);
	
	public final static void error(Logger userLog, String message){
		throwError(userLog, message, null, null);
	}
	
	public final static void error(Logger userLog, String message, Throwable cause){
		throwError(userLog, message, cause, null);
	}
	
	public final static <T extends RuntimeException> void throwError(Logger userLog, String message, Class<T> clazz) throws T{
		throwError(userLog, message, null, clazz);
	}
	
	/**
	 * 动态抛错误方法
	 * @param userLog 用户日志实体
	 * @param message 用户消息主体
	 * @param cause 错误实体
	 * @param clazz 错误类
	 * @throws T 抛出对应的错误
	 */
	public final static <T extends RuntimeException> void throwError(Logger userLog, String message, Throwable cause, Class<T> clazz) throws T{
		userLog.error(message, cause);
		// 不指定抛什么错误，就不抛
		if (clazz == null) {
			return;
		}
		try {
			throw clazz.getConstructor(String.class, Throwable.class).newInstance(message, cause);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throwError(log, message, e, ErrorInstanceException.class);
		}
	}
	
}
