package me.cjd.sqlbuilder.middleware.exception;

/**
 * Sql 引入错误
 * @author Mr.cjd
 * @date 2016-12-26
 */
public class SqlImportException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SqlImportException(){}
	
	public SqlImportException(String message, Throwable cause){
		super(message, cause);
	}
	
}