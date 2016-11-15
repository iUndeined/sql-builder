package me.cjd.sqlbuilder.engine;

import me.cjd.sqlbuilder.model.SqlBuilderPara;

public class SqlNoneEngine implements SqlRenderEngine {
	
	@Override
	public String render(String sql, SqlBuilderPara... paras) {
		return sql;
	}
	
}