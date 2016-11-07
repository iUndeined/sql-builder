package me.cjd.sqlbuilder.kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import me.cjd.sqlbuilder.commons.lang3.StringUtils;
import me.cjd.sqlbuilder.exception.RenderException;

public class SqlBuilderReader {
	
	private final static Logger log = Logger.getLogger(SqlBuilderReader.class);
	
	private final static String ENTER = "\r\n";
	
	public final static String in(String sqlId){
		String[] arrays = StringUtils.split(sqlId, ".", 2);
		if (arrays.length != 2) {
			LogKit.throwError(log, "错误的sqlId格式，示例 fileName.sqlName ", RenderException.class);
		}
		// 获取 各自信息
		String fileName = arrays[0];
		String sqlName = arrays[1];
		
		// 获取 用户配置
		String sqlMode = ConfigKit.me().getSqlMode();
		// 判断 是不是产品模式
		boolean sqlModeRun = StringUtils.isNotBlank(sqlMode) &&
				StringUtils.eqlsIgnoreCase(sqlMode, "run");
		
		if (sqlModeRun) {
			String cacheSql = SqlBuilderCache.sql(fileName, sqlName);
			
			if (StringUtils.isNotBlank(cacheSql)) {
				return cacheSql;
			}
		}
		
		// 声明 sqlMd文件类
		File sqlMdFile = null;
		try {
			sqlMdFile = SqlBuilderSearcher.search(ConfigKit.me().getFolders(), fileName + ".md");
		} catch (FileNotFoundException e) {
			LogKit.throwError(log, "错误的sqlId，无法找到" + fileName + ".md", e, RenderException.class);
		}
		
		FileReader fReader = null;
		BufferedReader bReader = null;
		StringBuilder sqlBuilder = new StringBuilder();
		
		try {
			fReader = new FileReader(sqlMdFile);
			bReader = new BufferedReader(fReader);
			
			String sqlLine = null;
			String sqlLineTrim = null;
			// 临时字段
			String sqlLineTemp = null;
			String sqlSonar = null;
			
			// 是否已经找到sql
			boolean sqlFound = false;
			
			while ((sqlLine = bReader.readLine()) != null) {
				// 处理前后空格
				sqlLineTrim = StringUtils.trimToEmpty(sqlLine);
				
				// 已经找到sql
				if (sqlFound) {
					// 已经到下一个sql域了，跳出
					if (RegexpKit.test("^===$", sqlLineTrim)) {
						sqlLineTemp = null;
						break;
					}
					
					if (StringUtils.isNotBlank(sqlLineTemp)) {
						// 插入 结果
						sqlBuilder.append(sqlLineTemp).append(ENTER);
					}
					
					sqlLineTemp = sqlLine;
				} else {
					// 找sqlName
					if (sqlFound = RegexpKit.test("^" + sqlName + "$", sqlLineTrim)) {
						// 找到了就继续往下走一行
						sqlSonar = bReader.readLine();
						log.debug("找到 sql 命名: " + sqlSonar + "，跳过本行");
					}
				}
			}
			
			// 最后一个sql集无结束处理
			if (StringUtils.isNotBlank(sqlLineTemp)) {
				sqlBuilder.append(sqlLineTemp);
			}
			
		} 
		// 因为已经提前预防不会出现这个错误，但是质量检测需要，那就加上吧
		catch (FileNotFoundException e) {
			LogKit.throwError(log, fileName + ".md文件找不到噢", e, RenderException.class);
		}
		
		catch (IOException e) {
			LogKit.throwError(log, fileName + ".md 读取过程发生错误", e, RenderException.class);
		}
		
		// 最后全要关闭
		finally {
			try {
				if (bReader != null) {
					bReader.close();
				}
				if (fReader != null) {
					fReader.close();
				}
			} catch (IOException e) {
				LogKit.error(log, fileName + ".md 已读取完毕，但在关闭流的时候发生错误", e);
			}
		}
		
		String sql = sqlBuilder.toString();
		
		if (StringUtils.isNotBlank(sql) && sqlModeRun) {
			SqlBuilderCache.sql(fileName, sqlName, sql);
		}
		
		return sql;
	}
	
}
