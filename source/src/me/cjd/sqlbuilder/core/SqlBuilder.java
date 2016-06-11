package me.cjd.sqlbuilder.core;

import me.cjd.sqlbuilder.engine.SqlNoneEngine;
import me.cjd.sqlbuilder.engine.SqlRenderEngine;
import me.cjd.sqlbuilder.kit.SqlBuilderReader;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

public class SqlBuilder {
	
	private static SqlRenderEngine engine = new SqlNoneEngine();
	
	public final static String render(SqlRenderEngine iEngine, String sqlId, SqlBuilderPara... paras){
		return iEngine.render(SqlBuilderReader.in(sqlId), paras);
	}
	
	public final static String render(String sqlId, SqlBuilderPara... paras){
		return render(engine, sqlId, paras);
	}
	
	public final static void setEngine(SqlRenderEngine iEngine){
		SqlBuilder.engine = iEngine;
	}
	
}
