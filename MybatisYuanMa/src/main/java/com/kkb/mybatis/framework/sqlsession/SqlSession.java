package com.kkb.mybatis.framework.sqlsession;

import java.util.List;

public interface SqlSession {

	<T> T selectOne(String statementId, Object param);

	<T> List<T> selectList(String statementId, Object param);
}
