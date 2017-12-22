package me.cjd.sqlbuilder.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlBuilderCacheItem {
	
	private String sqlFileName;
	private Map<String, String> mSql;
	
	public String getSql(String sqlName){
		if (this.mSql != null) {
			return this.mSql.get(sqlName);
		}
		return null;
	}
	
	public SqlBuilderCacheItem put(String sqlName, String sql){
		if (this.mSql != null) {
			this.mSql.put(sqlName, sql);
		}
		return this;
	}
	
	public SqlBuilderCacheItem(String sqlFileName, String sqlName, String sql) {
		this.sqlFileName = sqlFileName;
		this.mSql = new ConcurrentHashMap<>();
		this.put(sqlName, sql);
	}
	
	public String getSqlFileName() {
		return sqlFileName;
	}

	public void setSqlFileName(String sqlFileName) {
		this.sqlFileName = sqlFileName;
	}

	public Map<String, String> getmSql() {
		return mSql;
	}

	public void setmSql(Map<String, String> mSql) {
		this.mSql = mSql;
	}
	
}
