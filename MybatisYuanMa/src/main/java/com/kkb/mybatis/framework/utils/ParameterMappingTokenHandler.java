package com.kkb.mybatis.framework.utils;

import java.util.ArrayList;
import java.util.List;

import com.kkb.mybatis.framework.config.ParameterMapping;
// 解析映射器实现工具类
public class ParameterMappingTokenHandler implements TokenHandler {
	// 解析映射器排列，可重复，有序列
	private List<ParameterMapping> parameterMappings = new ArrayList<>();

	// context是参数名称
	@Override
	public String handleToken(String content) {
		parameterMappings.add(buildParameterMapping(content));
		return "?";
	}

	private ParameterMapping buildParameterMapping(String content) {
		ParameterMapping parameterMapping = new ParameterMapping(content);
		return parameterMapping;
	}
        // 获取解析映射器
	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}
       // 设置解析映射器
	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}

}
