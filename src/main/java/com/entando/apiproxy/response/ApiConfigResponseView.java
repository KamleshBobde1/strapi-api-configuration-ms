package com.entando.apiproxy.response;

import java.time.LocalDateTime;

import com.entando.apiproxy.config.ApplicationConstants;
import com.entando.apiproxy.persistence.entity.ApiConfig;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiConfigResponseView {

	private Long id;
//	private String applicationName;
	private String baseUrl;
	private String contextPath;

	@JsonFormat(pattern=ApplicationConstants.API_CONFIG_CREATED_DATE_FORMAT)
	private LocalDateTime createdAt;
	
	@JsonFormat(pattern=ApplicationConstants.API_CONFIG_UPDATED_DATE_FORMAT)
	private LocalDateTime updatedAt;
	
	public ApiConfigResponseView() {
		super();
	}
	public ApiConfigResponseView(ApiConfig entity) {
		this.id = entity.getId();
//		this.styleSheet = entity.getStyleSheet();

//		this.applicationName = entity.getApplicationName();
		this.baseUrl = entity.getBaseUrl();
		this.contextPath = entity.getContextPath();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
	}
}
