package com.entando.apiproxy.request;

import javax.validation.constraints.NotEmpty;

import com.entando.apiproxy.persistence.entity.ApiConfig;

import lombok.Data;

@Data
public class ApiConfigRequestView {

//	@NotEmpty(message = "applicationName is mandatory field")
//	private String applicationName;

	@NotEmpty(message = "baseUrl is mandatory field")
	private String baseUrl;
	
	@NotEmpty(message = "contextPath is mandatory field")
	private String contextPath;
	
	public ApiConfig createEntity(ApiConfigRequestView apiConfigRequestView, Long id) {
		ApiConfig entity = new ApiConfig();
		entity.setId(id);
//		entity.setApplicationName(apiConfigRequestView.getApplicationName());
		entity.setBaseUrl(apiConfigRequestView.getBaseUrl());
		entity.setContextPath(apiConfigRequestView.getContextPath());
		return entity;
	}
}
