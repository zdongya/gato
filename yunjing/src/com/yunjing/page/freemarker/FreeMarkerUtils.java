package com.yunjing.page.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {
	// 创建和配置Configuration对象
	private static final Configuration cfg = new Configuration();

	static {
		cfg.setClassForTemplateLoading(FreeMarkerUtils.class, "template");
		// 设置对象包装器，用于将对象包装为数据模型
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setEncoding(Locale.CHINA, "utf-8");
	}

	// 获得指定名称的模板
	private static Template getInstance(String templateName) throws IOException {
		return cfg.getTemplate(templateName);
	}

	// 填充指定名称的模板到指定流
	public static void merge(String templateName, Map root, Writer out)
			throws IOException {
		try {
			Template template = FreeMarkerUtils.getInstance(templateName);
			template.process(root, out);
		} catch (TemplateException e) {
			throw new RuntimeException("FreeMarker填充错误:"+e);
		}
	}
}
