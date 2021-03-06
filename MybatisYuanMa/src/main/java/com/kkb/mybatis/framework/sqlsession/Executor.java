package com.kkb.mybatis.framework.sqlsession;

import java.util.List;

import com.kkb.mybatis.framework.config.Configuration;
import com.kkb.mybatis.framework.config.MappedStatement;
// 执行器接口

public interface Executor {

	<T> List<T> query(Configuration configuration, MappedStatement mappedStatement,Object param);
}
