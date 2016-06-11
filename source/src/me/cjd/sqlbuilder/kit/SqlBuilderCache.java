package me.cjd.sqlbuilder.kit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.cjd.sqlbuilder.model.SqlBuilderCacheItem;

/**
 * 产品模式使用的缓存工具
 * @author Mr.cjd
 */
public class SqlBuilderCache {
	
	// 声明 缓存MAP
	private final static Map<String, SqlBuilderCacheItem> CACHE = new ConcurrentHashMap<>();
	
	public final static String sql(String sqlFileName, String sqlName){
		// 获取 对应文件
		SqlBuilderCacheItem item = CACHE.get(sqlFileName);
		if (item != null) {
			return item.getSql(sqlName);
		}
		return null;
	}
	
	public final static void sql(String sqlFileName, String sqlName, String sql){
		if (CACHE.containsKey(sqlFileName)) {
			CACHE.get(sqlFileName).put(sqlName, sql);
		} else {
			CACHE.put(sqlName, new SqlBuilderCacheItem(sqlFileName, sqlName, sql));
		}
	}
	
}