package me.cjd.sqlbuilder.engine;

import me.cjd.sqlbuilder.model.SqlBuilderPara;

public interface SqlRenderEngine {
	
	public String render(String sql, SqlBuilderPara... paras);
	
}
