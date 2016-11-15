package me.cjd.sqlbuilder.middleware;

import me.cjd.sqlbuilder.model.SqlBuilderPara;

public interface SqlMiddleware {
	
	String render(String fileName, String sql, SqlBuilderPara... paras);
	
}
