package com.entando.apiproxy.rest;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.entando.apiproxy.config.ApplicationConstants;
import com.entando.apiproxy.exception.ApiConfigNotFoundException;
import com.entando.apiproxy.persistence.entity.ApiConfig;
import com.entando.apiproxy.request.ApiRequestView;
import com.entando.apiproxy.service.ApiConfigService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/proxy")
public class StrapiApiController {
	
	@Autowired
	private ApiConfigService apiConfigService;
	
	private final Logger logger = LoggerFactory.getLogger(StrapiApiController.class);
	private RestTemplate restTemplate  = new RestTemplate();
	
	@Operation(summary = "Get data from strapi", description = "Private api, authentication required.")
	@PostMapping("/")
	@CrossOrigin
	//@RolesAllowed({ ApplicationConstants.ADMIN })
	public Object execute(@RequestBody ApiRequestView apiRequestView) {
		logger.info("REST request to call third party api, context: ", apiRequestView.getContextPath());
		Optional<ApiConfig> apiConfigOptional = apiConfigService.getApiConfigurationByContextPath(apiRequestView.getContextPath());
		String baseUrl = "";
		if (apiConfigOptional.isPresent()) {
			baseUrl = apiConfigOptional.get().getBaseUrl();
			logger.debug("Application base url: ", baseUrl);
		} else {
			logger.warn("Requested template '{}' does not exists", apiRequestView.getContextPath());
			throw new ApiConfigNotFoundException(String.format(ApplicationConstants.API_CONFIG_NOT_FOUND_ERR_MSG, "contextPath", apiRequestView.getContextPath()));
		}
		
		HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", apiRequestView.getToken());
        
        HttpEntity<String> entity=new HttpEntity<String>(headers);
        logger.debug("Application API url: ", baseUrl + apiRequestView.getUri());
        Object obj = restTemplate.exchange(baseUrl + apiRequestView.getUri(), apiRequestView.getHttpMethod(), entity, String.class).getBody();
        System.out.println("response: "+ obj);
        return obj;
	}

}
