package me.cjd.sqlbuilder.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 配置工具
 * @author Mr.cjd
 */
public class ConfigKit {
	
	// 声明 sql存在的目录们
	private List<String> folders = null;
	
	private String sqlMode = null;
	private String sqlFolders = null;
	private String sqlFolderBase = null;
	
	// 单例
	private ConfigKit(){
		InputStream userFis = null;
		InputStream defaultFis = null;
		// 获取 项目根路径
		this.sqlFolderBase = WebAppKit.getPath();
		// 加载 用户配置文件
		File userConfigFile = new File(this.sqlFolderBase, "sqlbuilder-builder.properties");
		// 破解 打包在.jar内读取不到的办法
		defaultFis = WebAppKit.getInnerFile("sqlbuilder-builder-default.properties");
		
		try {
			
			Properties user = new Properties();
			Properties defaults = new Properties();
			
			// 如果 存在则载入
			if (userConfigFile.exists()) {
				user.load(userFis = new FileInputStream(userConfigFile));
			}
			
			// 装载 默认配置
			defaults.load(defaultFis);
			// 获取 模式
			this.sqlMode = user.getProperty("sqlMode", defaults.getProperty("sqlMode", "run"));
			// 获取 目录们
			this.sqlFolders = user.getProperty("sqlFolders", defaults.getProperty("sqlFolders", ""));
		} catch (IOException e) {
			throw new RuntimeException("Sql Builder: ConfigKit.me()获取项目地址失败", e);
		} finally {
			try {
				if (userFis != null) {
					userFis.close();
				}
				if (defaultFis != null) {
					defaultFis.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("Sql Builder: ConfigKit.me() 已成功获取配置，但无法关闭文件", e);
			}
		}
	}
	
	private static ConfigKit me = null;
	
	// 单例获取入口
	public final static ConfigKit me(){
		if (me == null) {
			me = new ConfigKit();
		}
		return me;
	}

	public List<String> getFolders() {
		// 用户有配置目录
		if (this.folders == null && StringUtils.isNotBlank(StringUtils.trimToEmpty(this.sqlFolders))) {
			String[] arrays = StringUtils.split(this.sqlFolders, ",");
			if (ArrayUtils.isNotEmpty(arrays)) {
				this.folders = new ArrayList<>(arrays.length + 1);
				this.folders.add(this.sqlFolderBase);
				for (String folder : arrays) {
					this.folders.add(this.sqlFolderBase + StringUtils.trimToEmpty(folder).replaceAll("\\.", "/"));
				}
			}
		}
		return folders;
	}
	
	public static void main(String[] args) {
		ConfigKit.me().getFolders();
	}
	
	public void setFolders(List<String> folders) {
		this.folders = folders;
	}
	
	public String getSqlMode() {
		return sqlMode;
	}
	
	public void setSqlMode(String sqlMode) {
		this.sqlMode = sqlMode;
	}
	
	public String getSqlFolders() {
		return sqlFolders;
	}
	
	public void setSqlFolders(String sqlFolders) {
		this.sqlFolders = sqlFolders;
	}
	
}
