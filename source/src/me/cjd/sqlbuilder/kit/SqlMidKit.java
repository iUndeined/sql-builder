package me.cjd.sqlbuilder.kit;

import java.util.List;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import me.cjd.sqlbuilder.commons.lang3.StringUtils;
import me.cjd.sqlbuilder.middleware.SqlMiddleware;

/**
 * 中间件工具
 * @author Mr.cjd
 */
public class SqlMidKit {
	
	private static final Logger log = Logger.getLogger(SqlMidKit.class);
	
	public static List<SqlMiddleware> list(){
		// 获取 用户选择了哪些中间件
		String userMids = ConfigKit.me().getSqlMid();
		// 没有中间件
		if (StringUtils.isBlank(userMids)) {
			return null;
		}
		String[] mids = StringUtils.split(userMids, ",");
		List<SqlMiddleware> midList = new ArrayList<>(mids.length);
		for (String mid : mids) {
			try {
				Class<?> clazz = Class.forName("me.cjd.sqlbuilder.middleware." + mid + "Mid");
				midList.add((SqlMiddleware) clazz.newInstance());
			} catch (ClassNotFoundException e) {
				log.error(mid + " 中间件未找到！", e);
			} catch (InstantiationException e) {
				log.error(mid + " 中间件实例无法创建！", e);
			} catch (IllegalAccessException e) {
				log.error(mid + " 中间件实例创建参数不正确！", e);
			}
		} 
		return midList;
	}
	
}
