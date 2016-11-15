package me.cjd.sqlbuilder.middleware;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.cjd.sqlbuilder.core.SqlBuilder;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

/**
 * sql语句引入中间件
 * @author Mr.cjd
 */
public class SqlImportMid implements SqlMiddleware {
	
	@Override
	public String render(String fileName, String sql, SqlBuilderPara... paras) {
		String regx = "sql\\('([^']+)'\\)";
		String parsed = sql;
		Matcher matcher = Pattern.compile(regx).matcher(parsed);
		while (matcher.find()) {
			// 获取 结果
			String result = matcher.group();
			// 获取 key
			String key = result.replaceAll(regx, "$1");
			// 获取 引入sql语句
			String importSql = SqlBuilder.render(key.indexOf('.') > -1 ? key : (fileName + "." + key), paras);
			// 替换
			parsed = parsed.replaceAll("sql\\('" + key + "'\\)", importSql);
		}
		return parsed;
	}
	
}