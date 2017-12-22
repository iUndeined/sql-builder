package me.cjd.sqlbuilder.kit;

import java.io.File;
import java.io.FileNotFoundException;
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
	 * @throws FileNotFoundException 
	 */
	public final static File search(List<String> folders, String fileName) throws FileNotFoundException{
		File sqlFile = null;
		for (String folder : folders) {
			sqlFile = new File(folder, fileName);
			if (sqlFile.exists()) {
				break;
			}
			sqlFile = null;
		}
		
		if (sqlFile == null) {
			throw new FileNotFoundException("没有找到Sql语句存储文件 ‘" + fileName + "’ 请检查拼写是否正确或文件是否存在，不然就是配置文件未指定目录");
		}
		
		return sqlFile;
	}
	
}