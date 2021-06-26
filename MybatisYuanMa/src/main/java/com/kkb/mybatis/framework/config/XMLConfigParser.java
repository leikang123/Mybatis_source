package com.kkb.mybatis.framework.config;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import com.kkb.mybatis.framework.sqlsession.DocumentReader;

// 解析器的解析类配置

public class XMLConfigParser {

	private Configuration configuration;
	
	public XMLConfigParser(Configuration configuration) {
		super();
		this.configuration = configuration;
	}
	/**
	 * 
	 * @param element
	 *            <configuration>
	 * @return
	 */
	public Configuration parseConfiguration(Element element) {
		// <environments>
		parseEnvironments(element.element("environments"));
		// <mappers>
		parseMappers(element.element("mappers"));

		return configuration;
	}
	//<mappers>
	private void parseMappers(Element element) {
		List<Element> elements = element.elements("mapper");
		for (Element mapperEle : elements) {
			parseMapper(mapperEle);
		}
	}

	private void parseMapper(Element mapperEle) {
		String resource = mapperEle.attributeValue("resource");
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
		Document document = DocumentReader.createDocument(inputStream);
		XMLMapperParser xmlMapperParser = new XMLMapperParser(configuration); 
		xmlMapperParser.parse(document.getRootElement());
	}
	// <environments>主要就是解析数据源
	private void parseEnvironments(Element element) {
		// <environments default="dev">
		String defaultId = element.attributeValue("default");
		List<Element> elements = element.elements("environment");

		for (Element envElement : elements) {
			String envId = envElement.attributeValue("id");
			// 如果和默认的环境ID匹配，才进行解析
			if (envId != null && envId.equals(defaultId)) {
				parseDataSource(envElement.element("dataSource"));
			}
		}
	}

	private void parseDataSource(Element element) {
		String type = element.attributeValue("type");
		if (type == null || type.equals("")) {
			type = "DBCP";
		}
		List<Element> elements = element.elements("property");

		Properties properties = new Properties();
		for (Element propertyEle : elements) {
			String name = propertyEle.attributeValue("name");
			String value = propertyEle.attributeValue("value");

			properties.setProperty(name, value);
		}

		BasicDataSource dataSource = null;
		if (type.equals("DBCP")) {
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(properties.getProperty("driver"));
			dataSource.setUrl(properties.getProperty("url"));
			dataSource.setUsername(properties.getProperty("username"));
			dataSource.setPassword(properties.getProperty("password"));
		}
		configuration.setDataSource(dataSource);
	}
}
