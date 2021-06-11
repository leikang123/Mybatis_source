package com.kkb.mybatis.framework.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class Configuration {

	private DataSource dataSource;

	private Map<String, MappedStatement> mappedStatements = new HashMap<>();

	public Map<String, MappedStatement> getMappedStatements() {
		return mappedStatements;
	}

	public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
		this.mappedStatements.put(statementId, mappedStatement);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
