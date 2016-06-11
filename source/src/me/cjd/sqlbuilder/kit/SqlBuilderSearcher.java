package me.cjd.sqlbuilder.kit;

import java.io.File;
import java.util.List;

/**
 * 查找.md文件类
 * @author me.cjd
 */
public class SqlBuilderSearcher {

	/**
	 * 在指定目录里找对应名称的文件
	 * @param folders 目录列表
	 * @param fileName 文件名称
	 * @return 返回io文件类
	 */
	public final static File search(List<String> folders, String fileName){
		File sqlFile = null;
		for (String folder : folders) {
			sqlFile = new File(folder, fileName);
			if (sqlFile.exists()) {
				break;
			}
			sqlFile = null;
		}
		return sqlFile;
	}
	
}