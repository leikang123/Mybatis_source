package com.kkb.mybatis.framework.config;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {
	//解析之后的SQL语句
	private String sql;
	//解析出来的参数
	private List<ParameterMapping> parameterMappings = new ArrayList<>();

	public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
		super();
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void addParameterMapping(ParameterMapping parameterMapping) {
		this.parameterMappings.add(parameterMapping);
	}

}
