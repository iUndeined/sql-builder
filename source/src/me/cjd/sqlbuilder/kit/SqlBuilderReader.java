package me.cjd.sqlbuilder.kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class SqlBuilderReader {
	
	private final static String ENTER = "\r\n";
	
	public final static String in(String sqlId){
		String[] arrays = StringUtils.split(sqlId, ".", 2);
		if (arrays.length != 2) {
			throw new RuntimeException("Sql Builder: 错误的sqlId语句，示例 fileName.sqlName ");
		}
		// 获取 各自信息
		String fileName = arrays[0];
		String sqlName = arrays[1];
		
		// 获取 用户配置
		String sqlMode = ConfigKit.me().getSqlMode();
		// 判断 是不是产品模式
		boolean sqlModeRun = StringUtils.isNotBlank(sqlMode) &&
				StringUtils.equalsIgnoreCase(sqlMode, "run");
		
		if (sqlModeRun) {
			String cacheSql = SqlBuilderCache.sql(fileName, sqlName);
			
			if (StringUtils.isNotBlank(cacheSql)) {
				return cacheSql;
			}
		}
		
		// 声明 sqlMd文件类
		File sqlMdFile = SqlBuilderSearcher.search(ConfigKit.me().getFolders(), fileName + ".md");
		
		if (!sqlMdFile.exists()) {
			throw new RuntimeException("Sql Builder: 错误的sqlId，无法找到" + fileName + ".md");
		}
		
		FileReader fReader = null;
		BufferedReader bReader = null;
		StringBuffer sqlBuffer = new StringBuffer();
		
		try {
			fReader = new FileReader(sqlMdFile);
			bReader = new BufferedReader(fReader);
			
			String sqlLine = null;
			String sqlLineTrim = null;
			// 临时字段
			String sqlLineTemp = null;
			
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
						sqlBuffer.append(sqlLineTemp).append(ENTER);
					}
					
					sqlLineTemp = sqlLine;
				} else {
					// 找sqlName
					if (sqlFound = RegexpKit.test("^" + sqlName + "$", sqlLineTrim)) {
						// 找到了就继续往下走一行
						bReader.readLine();
					}
				}
			}
			
			// 最后一个sql集无结束处理
			if (StringUtils.isNotBlank(sqlLineTemp)) {
				sqlBuffer.append(sqlLineTemp);
			}
			
		} 
		// 因为已经提前预防不会出现这个错误
		catch (FileNotFoundException e) {}
		
		catch (IOException e) {
			throw new RuntimeException("Sql Builder: " + fileName + ".md 读取过程发生错误");
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
				throw new RuntimeException("Sql Builder: " + fileName + ".md 已读取完毕，但在关闭流的时候发生错误");
			}
		}
		
		String sql = sqlBuffer.toString();
		
		if (StringUtils.isNotBlank(sql) && sqlModeRun) {
			SqlBuilderCache.sql(fileName, sqlName, sql);
		}
		
		return sql;
	}
	
}
