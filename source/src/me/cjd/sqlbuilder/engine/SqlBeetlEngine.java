package me.cjd.sqlbuilder.engine;

import java.io.IOException;
import me.cjd.sqlbuilder.commons.lang3.ArrayUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

public class SqlBeetlEngine implements SqlRenderEngine {
	
	private static StringTemplateResourceLoader resourceLoader;
	private static Configuration cfg;
	
	static {
		try {
			resourceLoader = new StringTemplateResourceLoader();
			cfg =  Configuration.defaultConfiguration();
		} catch (IOException e) {
			throw new RuntimeException("Sql Builder: 初始化Beetl模板引擎发生错误 ", e);
		}
	}
	
	@Override
	public String render(String sql, SqlBuilderPara... paras) {
		// 获取 sql
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		Template t = gt.getTemplate(sql);
		if (ArrayUtils.isNotEmpty(paras)) {
			for (SqlBuilderPara para : paras) {
				t.binding(para.getKey(), para.getValue());
			}
		}
		return t.render();
	}
	
}
