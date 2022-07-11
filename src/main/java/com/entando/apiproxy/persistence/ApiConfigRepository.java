package com.entando.apiproxy.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.entando.apiproxy.persistence.entity.ApiConfig;

public interface ApiConfigRepository extends JpaRepository<ApiConfig, Long> {

	/**
	 * Find all Api Configurations paginated
	 */
	Page<ApiConfig> findAll(Pageable pageable);

	/**
	 * Find an Api Configuration by contextPath
	 * @param contextPath
	 * @return
	 */
	Optional<ApiConfig> findByContextPath(String contextPath);

}
