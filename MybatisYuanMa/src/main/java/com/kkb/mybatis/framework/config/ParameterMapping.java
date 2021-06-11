package com.kkb.mybatis.framework.config;

public class ParameterMapping {
	private String name;

	public ParameterMapping(String content) {
		this.name = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
