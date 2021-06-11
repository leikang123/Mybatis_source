package com.kkb.mybatis.framework.sqlsession;

import java.io.InputStream;
import java.io.Reader;

import org.dom4j.Document;

import com.kkb.mybatis.framework.config.Configuration;
import com.kkb.mybatis.framework.config.XMLConfigParser;

public class SqlSessionFactoryBuilder {
	// 封装全局配置文件信息和所有映射文件的信息
	private Configuration configuration;

	public SqlSessionFactoryBuilder() {
		configuration = new Configuration();
	}

	public SqlSessionFactory build(InputStream inputStream) {
		// 解析全局配置文件，封装为Configuration对象
		// 通过InputStream流对象，去创建Document对象（dom4j）---此时没有针对xml文件中的语义进行解析
		// DocumentReader---去加载InputStream流，创建Document对象的
		Document document = DocumentReader.createDocument(inputStream);
		// 进行mybatis语义解析（全局配置文件语义解析、映射文件语义解析）
		// XMLConfigParser---解析全局配置文件
		// XMLMapperParser---解析全局配置文件
		XMLConfigParser xmlConfigParser = new XMLConfigParser(configuration);
		configuration = xmlConfigParser.parseConfiguration(document.getRootElement());
		return build();
	}

	public SqlSessionFactory build(Reader reader) {
		return build();
	}

	// 构建SqlSessionFactory需要全局配置文件的信息，也就是Configuration
	private SqlSessionFactory build() {
		return new DefaultSqlSessionFactory(configuration);
	}
}
