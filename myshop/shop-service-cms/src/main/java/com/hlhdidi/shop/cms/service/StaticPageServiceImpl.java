package com.hlhdidi.shop.cms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hlhdidi.shop.interfaces.product.StaticPageService;

import freemarker.template.Configuration;
import freemarker.template.Template;
@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{

	private ServletContext servletContext;
	@Autowired
	private Configuration configuration; //freeMarker configuration  
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.configuration=freeMarkerConfigurer.getConfiguration();
	}
	@Override
	public void toStatic(Map<Object, Object> map, Long id) {
		String path="/html/product/"+id+".html";
		String realPath=getRealPath(path);
		File file=new File(realPath);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		Writer writer=null;
		try {
			Template template = configuration.getTemplate("product.html");
			writer=new OutputStreamWriter(new FileOutputStream(realPath),"UTF-8");
			template.process(map, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}

}
