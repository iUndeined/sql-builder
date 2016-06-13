package me.cjd.sqlbuilder.engine;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import me.cjd.sqlbuilder.commons.lang3.ArrayUtils;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

public class SqlFreemarkerEngine implements SqlRenderEngine {
	
	private final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_24);
	
	static {
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLogTemplateExceptions(false);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}
	
	@Override
	public String render(String sql, SqlBuilderPara... paras) {
        // 数据模型
        Map<String, Object> root = new HashMap<>();
        if (ArrayUtils.isNotEmpty(paras)) {
			for (SqlBuilderPara para : paras) {
				root.put(para.getKey(), para.getValue());
			}
		}
        
        // 输出结果
        Writer writer = null;
        
        try {
			Template template = new Template("SqlBuilder", new StringReader(sql), cfg);
			template.process(root, writer = new StringWriter());
		} catch (IOException e) {
			throw new RuntimeException("Sql Builder: Freemarker 输入未处理源字串错误.", e);
		} catch (TemplateException e) {
			throw new RuntimeException("Sql Builder: Freemarker 处理模板发生错误.", e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("Sql Builder: Freemarker 处理结果输出发生错误.", e);
			}
		}
        
		return writer.toString();
	}

}
