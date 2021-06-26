package com.kkb.mybatis.framework.config;

import com.kkb.mybatis.framework.utils.GenericTokenParser;
import com.kkb.mybatis.framework.utils.ParameterMappingTokenHandler;

// 数据库的数据解析类
public class SqlSource {
	private String sqlText;

	public SqlSource(String sqlText) {
		this.sqlText = sqlText;
	}

	public BoundSql getBoundSql() {
		// 解析sql文本
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
		String sql = genericTokenParser.parse(sqlText);
		// 就是将解析之后的SQL语句，和解析出来的SQL参数使用组合模式绑定到一个类中
		return new BoundSql(sql,tokenHandler.getParameterMappings());
	}
}
