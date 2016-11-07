package me.cjd.sqlbuilder.engine;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import me.cjd.sqlbuilder.commons.lang3.ArrayUtils;
import me.cjd.sqlbuilder.exception.EngineException;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

public class SqlFreemarkerEngine implements SqlRenderEngine {
	
	private final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_24);
	
	private final static Logger log = Logger.getLogger(SqlFreemarkerEngine.class);
	
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
			throw new EngineException(EngineException.FMK_INPUT_ERROR, e);
		} catch (TemplateException e) {
			throw new EngineException(EngineException.FMK_RENDER_ERROR, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				log.error("Freemarker 渲染输出 writer 无法关闭，请检查原因！", e);
			}
		}
        
		return writer.toString();
	}

}
