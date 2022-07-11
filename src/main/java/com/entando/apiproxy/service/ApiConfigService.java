package com.entando.apiproxy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.entando.apiproxy.config.ApplicationConstants;
import com.entando.apiproxy.persistence.ApiConfigRepository;
import com.entando.apiproxy.persistence.entity.ApiConfig;
import com.entando.apiproxy.request.ApiConfigRequestView;
import com.entando.apiproxy.response.ApiConfigResponseView;
import com.entando.apiproxy.util.PagedContent;

@Service
public class ApiConfigService {

	private final Logger logger = LoggerFactory.getLogger(ApiConfigService.class);
	private final String CLASS_NAME = this.getClass().getSimpleName();

	@Autowired
	private ApiConfigRepository apiConfigRepository;

	/**
	 * Get all Api Configurations
	 * @return
	 */
	public List<ApiConfig> getAllApiConfigurations() {
		return apiConfigRepository.findAll();
	}
	
	/**
	 * Get an Api Configuration by context path
	 * @return
	 */
	public Optional<ApiConfig> getApiConfigurationByContextPath(String contextPath) {
		return apiConfigRepository.findByContextPath(contextPath);
	}

	/**
	 * Get Api Configurations paginated
	 * @param pageNum
	 * @param pageSize
	 * @param code
	 * @return
	 */
	public PagedContent<ApiConfigResponseView, ApiConfig> getFilteredApiConfigurations(Integer pageNum,
			Integer pageSize, String sanitizedContextPath) {
		logger.debug("{}: getFilteredApiConfigurations: Get Api Configurations in paginated manner", CLASS_NAME);
		Pageable pageable;
		Page<ApiConfig> page = null;

		if (pageSize == 0) {
			pageable = Pageable.unpaged();
		} else {
			pageable = PageRequest.of(pageNum, pageSize, Sort.by(new Sort.Order(Sort.Direction.ASC, ApplicationConstants.API_CONFIG_SORT_PARAM_NAME))
					.and(Sort.by(ApplicationConstants.API_CONFIG_SORT_PARAM_UPDATAED_AT).descending()));
		}

		//Check if search parameter is 'all/All/ALL'
		if(sanitizedContextPath.equalsIgnoreCase(ApplicationConstants.API_CONFIG_SEARCH_PARAM_ALL)) {
			page = apiConfigRepository.findAll(pageable);
		} else {
//			page = apiConfigRepository.findByCollectionType(sanitizedCollectionType, pageable);
		}

		PagedContent<ApiConfigResponseView, ApiConfig> pagedContent = new PagedContent<>(toResponseViewList(page), page);
		return pagedContent;
	}

	/**
	 * Get an Api Configuration by id
	 * @param apiConfigId
	 * @return
	 */
	public Optional<ApiConfig> getApiConfiguration(Long apiConfigId) {
		return apiConfigRepository.findById(apiConfigId);
	}

	/**
	 * 
	 * @param toSave
	 * @return
	 */
//	public ApiConfig createApiConfiguration(ApiConfig toSave) {
//		toSave.setCreatedAt(LocalDateTime.now());
//		toSave.setUpdatedAt(LocalDateTime.now());
//		return apiConfigRepository.save(toSave);
//	}
	
	public ApiConfig createApiConfiguration(ApiConfig toSave) {
		List<ApiConfig> configs = getAllApiConfigurations();
		if(CollectionUtils.isEmpty(configs)) {
			toSave.setCreatedAt(LocalDateTime.now());
			toSave.setUpdatedAt(LocalDateTime.now());
			return apiConfigRepository.save(toSave);
		} else {
			ApiConfig apiConfigDb = configs.get(0);
			apiConfigDb.setBaseUrl(toSave.getBaseUrl());
			apiConfigDb.setContextPath(toSave.getContextPath());
			apiConfigDb.setUpdatedAt(LocalDateTime.now());
			return apiConfigRepository.save(apiConfigDb);
		}
	}

	/**
	 * Update an Api Configuration
	 * @param toUpdate
	 * @param reqView
	 * @return
	 */
	public ApiConfig updateApiConfiguration(ApiConfig toUpdate, ApiConfigRequestView reqView) {
//		toUpdate.setApplicationName(reqView.getApplicationName());
		toUpdate.setBaseUrl(reqView.getBaseUrl());
		toUpdate.setContextPath(reqView.getContextPath());		
		toUpdate.setUpdatedAt(LocalDateTime.now());
		
		return apiConfigRepository.save(toUpdate);
	}

	/**
	 * Delete an Api Configuration
	 * @param apiConfigId
	 */
	public void deleteApiConfiguration(Long apiConfigId) {
		apiConfigRepository.deleteById(apiConfigId);
	}

	/**
	 * Convert to response view list
	 * 
	 * @param page
	 * @return
	 */
	private List<ApiConfigResponseView> toResponseViewList(Page<ApiConfig> page) {
		logger.debug("{}: toResponseViewList: Convert ApiConfig list to response view list", CLASS_NAME);
		List<ApiConfigResponseView> list = new ArrayList<ApiConfigResponseView>();
		page.getContent().stream().forEach((entity) -> {
			ApiConfigResponseView viewObj = new ApiConfigResponseView();
			viewObj.setId(entity.getId());
//			viewObj.setApplicationName(entity.getApplicationName());
			viewObj.setBaseUrl(entity.getBaseUrl());
			viewObj.setContextPath(entity.getContextPath());
			viewObj.setCreatedAt(entity.getCreatedAt());
			viewObj.setUpdatedAt(entity.getUpdatedAt());

			list.add(viewObj);
		});
		return list;
	}
}
