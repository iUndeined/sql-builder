package me.cjd.sqlbuilder.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import me.cjd.sqlbuilder.engine.SqlNoneEngine;
import me.cjd.sqlbuilder.engine.SqlRenderEngine;
import me.cjd.sqlbuilder.kit.ConfigKit;
import me.cjd.sqlbuilder.kit.SqlBuilderReader;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

public class SqlBuilder {

	private static SqlRenderEngine ENGINE;

	public static final String VERSION = "1.3.4";

	public static final String render(SqlRenderEngine iEngine, String sqlId, SqlBuilderPara... paras) {
		// 原始 未经渲染的sql串
		String sqlBefore = SqlBuilderReader.in(sqlId, paras);
		// 通过 用户指定引擎渲染后的sql串
		String sqlAfter = iEngine.render(sqlBefore, paras);
		// 获取 是否调试模式
		boolean sqlDebug = ConfigKit.me().getSqlDebug();
		// 打印至控制台
		if (sqlDebug) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(
					"SqlBuilder Report ----------- " + format.format(new Date()) + " ------------------------------");
			System.out.println("version     : " + VERSION);
			System.out.println("sqlId       : " + sqlId);
			System.out.println("before      : " + sqlBefore.trim());
			System.out.println("after       : " + sqlAfter.trim());
			System.out.println("parameter   : " + SqlBuilderPara.toJson(paras));
			System.out.println("--------------------------------------------------------------------------------");
		}
		return sqlAfter;
	}

	public static final String render(String sqlId, SqlBuilderPara... paras) {
		return render(getEngine(), sqlId, paras);
	}
	
	public static final Builder sql(String sqlId) {
		return new Builder(getEngine(), sqlId);
	}
	
	public static final SqlRenderEngine getEngine() {
		if (ENGINE == null) {
			ENGINE = new SqlNoneEngine();
		}
		return ENGINE;
	}

	public static final synchronized void setEngine(SqlRenderEngine iEngine) {
		ENGINE = iEngine;
	}

	public static class Builder {
		
		private static final Logger log = Logger.getLogger(Builder.class);
		
		private SqlRenderEngine engine;
		private String sqlId;
		private List<SqlBuilderPara> paras;

		protected Builder(SqlRenderEngine engine, String sqlId) {
			super();
			this.engine = engine;
			this.sqlId = sqlId;
		}
		
		public Builder engine(SqlRenderEngine engine){
			this.engine = engine;
			return this;
		}
		
		public Builder engine(Class<? extends SqlRenderEngine> engineClass){
			try {
				this.engine = engineClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("SqlBuilder.Builder.engine(Class<SqlRenderEngine>) -> 实体模板渲染引擎失败", e);
			}
			return this;
		}
		
		public Builder para(String key, Object value){
			if (this.paras == null) {
				this.paras = new ArrayList<>();
			}
			this.paras.add(new SqlBuilderPara(key, value));
			return this;
		}
		
		public String render(){
			return SqlBuilder.render(this.engine, this.sqlId, this.paras.toArray(new SqlBuilderPara[this.paras == null || this.paras.isEmpty() ? 0 : this.paras.size()]));
		}
		
	}

}