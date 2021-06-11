package com.kkb.mybatis.framework.sqlsession;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.kkb.mybatis.framework.config.BoundSql;
import com.kkb.mybatis.framework.config.Configuration;
import com.kkb.mybatis.framework.config.MappedStatement;
import com.kkb.mybatis.framework.config.ParameterMapping;
import com.kkb.mybatis.framework.config.SqlSource;

public class SimpleExecutor implements Executor {

	@Override
	public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
		/*
		 * a).获取连接 //读取配置文件，获取数据源对象，根据数据源获取连接 通过Configuration对象获取DataSource对象
		 * 通过DataSource对象，获取Connection
		 */
		Connection connection = null;
		List<Object> results = new ArrayList<>();
		try {
			DataSource dataSource = configuration.getDataSource();
			connection = dataSource.getConnection();

			// 获取SQL语句
			SqlSource sqlSource = mappedStatement.getSqlSource();
			BoundSql boundSql = sqlSource.getBoundSql();
			String sql = boundSql.getSql();

			// 获取statementType
			String statementType = mappedStatement.getStatementType();
			if ("prepared".equals(statementType)) {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				// 设置参数
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				// 获取入参类型
				Class<?> parameterTypeClass = mappedStatement.getParameterTypeClass();

				// 八种基本类型都可以如此处理
				if (parameterTypeClass == Integer.class) {
					// 根本不关系#{}中的名称到底是什么
					preparedStatement.setObject(1, param);
				} else {// Map和List的我们暂不处理
						// 我们此处主要解决POJO类型
					for (int i = 0; i < parameterMappings.size(); i++) {
						ParameterMapping parameterMapping = parameterMappings.get(i);
						// 得到属性名称
						String name = parameterMapping.getName();
						// 通过反射获取入参对象中执行名称的属性值
						Field field = parameterTypeClass.getDeclaredField(name);
						// 设置暴力赋值，不管属性是不是私有
						field.setAccessible(true);
						Object value = field.get(param);
						preparedStatement.setObject(i + 1, value);
					}
				}

				ResultSet resultSet = preparedStatement.executeQuery();
				Class<?> resultTypeClass = mappedStatement.getResultTypeClass();

				while (resultSet.next()) {
					Object returnObj = resultTypeClass.newInstance();
					ResultSetMetaData metaData = resultSet.getMetaData();
					int count = metaData.getColumnCount();
					for (int i = 1; i <= count; i++) {
						String columnName = metaData.getColumnName(i);
						Field field = resultTypeClass.getDeclaredField(columnName);
						field.setAccessible(true);
						field.set(returnObj, resultSet.getObject(columnName));
					}

					results.add(returnObj);
				}
			} else {
				// ....
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (List<T>) results;
	}

}
